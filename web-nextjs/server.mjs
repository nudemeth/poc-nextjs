/* eslint-disable no-console */
import express from 'express'
import next from 'next'
import favicon from 'serve-favicon'
import path from 'path'
import fetch from 'isomorphic-unfetch'
import cookieParser from 'cookie-parser'
import { parse } from 'url'
import config from './config'
import jwt from 'jsonwebtoken'

const __dirname = path.resolve()
const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
//const handle = app.getRequestHandler()

const authSites = [
    { name: 'github', url: process.env.GITHUB_AUTH_URL || null }
]

const getToken = (issuer, code) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    return fetch(`${config.api.identity.uri}token?issuer=${issuer}&code=${code}`, options)
        .then(r => r.json())
}

const getUserInfo = (issuer, token) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    return fetch(`${config.api.identity.uri}userinfo?issuer=${issuer}&token=${token}`, options)
        .then(r => r.json())
}

const createUser = (issuer, token, login) => {
    const data = {
        issuer,
        token,
        login
    }
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
        },
        body: JSON.stringify(data)
    }
    return fetch(`${config.api.identity.uri}users`, options)
        .then(r => r.json())
}

const getUserToken = (id) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    return fetch(`${config.api.identity.uri}users/token/${id}`, options)
        .then(r => r.json())
}

const decodeJwt = (token) => {
    try {
        return jwt.decode(token)
    } catch (err) {
        console.warn(`Unable to decode JWT: ${token} (${err.message})`)
        return null
    }
}

app
    .prepare()
    .then(() => {
        const server = express()
        const port = dev ? 3000 : 80

        server.use(favicon(path.join(__dirname, 'static', 'favicon.ico')))
        server.use(cookieParser())

        /*server.get('/p/:id', (req, res) => {
            const actualPage = '/post';
            const queryParams = { id: req.params.id };
            app.render(req, res, actualPage, queryParams);
        });*/

        server.get('/authentication', async (req, res) => {
            const issuer = req.query.issuer
            const code= req.query.code

            if (!issuer || !code) {
                return res.sendStatus(404)
            }

            const token = await getToken(issuer, code)
            const userinfo = await getUserInfo(issuer, token.access_token)
            const id = await createUser(issuer, token, userinfo.login)
            const result = await getUserToken(id)

            if (!result) {
                return res.status(500).send('Authentication process failed.')
            }

            res.cookie('accessToken', result.token, {
                httpOnly: true,
                secure: !dev
            })

            const parsedUrl = parse(req.url, true)
            const { query } = parsedUrl
            
            app.render(req, res, '/authentication', { ...query, sites: authSites, accessToken: result.token })
        })

        server.get('*', (req, res) => {
            const accessToken = req.cookies.accessToken
            const parsedUrl = parse(req.url, true)
            const { pathname, query } = parsedUrl

            if (!accessToken) {
                return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: null })
            }

            const jwt = decodeJwt(accessToken)
            if (!jwt) {
                res.clearCookie('user')
                return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: null })
            }
            
            return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: jwt })
        })

        server.listen(port, (err) => {
            if (err) throw err
            console.log(`> Ready on http://localhost:${port}`)
        })
    })
    .catch((ex) => {
        console.error(ex.stack)
        process.exit(1)
    })