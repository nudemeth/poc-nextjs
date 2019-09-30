/* eslint-disable no-console */
import express from 'express'
import next from 'next'
import jwt from 'jsonwebtoken'
import favicon from 'serve-favicon'
import path from 'path'
import fetch from 'isomorphic-unfetch'
import cookieParser from 'cookie-parser'
import { parse } from 'url'
import config from './config'

const __dirname = path.resolve()
const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
const refreshTokenLifeTime = parseInt(process.env.REFRESH_TOKEN_LIFE_TIME) || 1000 * 60 * 60

const authSites = [
    { name: 'github', url: process.env.GITHUB_AUTH_URL || null }
]

const getIssuerToken = (issuer, code) => {
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

const createOrUpdateUser = (issuer, token, login) => {
    const data = {
        issuer,
        token,
        login
    }
    const options = {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
        },
        body: JSON.stringify(data)
    }
    return fetch(`${config.api.identity.uri}users/login/${login}?issuer=${issuer}`, options)
        .then(r => r.json())
}

const getUserToken = async (id) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    const res = await fetch(`${config.api.identity.uri}users/${id}/token`, options)
    if (!res.ok) {
        return
    }
    return res.json()
}

const canRefreshToken = async (accessToken) => {
    const decoded = jwt.decode(accessToken)
    if (!decoded) {
        return false
    }
    const token = await getUserToken(decoded.id)
    if (!token) {
        return false
    }
    return true
}

app
    .prepare()
    .then(() => {
        const server = express()
        const port = dev ? 3000 : 80

        server.use(favicon(path.join(__dirname, 'static', 'favicon.ico')))
        server.use(cookieParser())

        server.get('/authentication', async (req, res) => {
            const issuer = req.query.issuer
            const code= req.query.code

            if (!issuer || !code) {
                return res.sendStatus(404)
            }

            const issuerResponse = await getIssuerToken(issuer, code)
            const userinfo = await getUserInfo(issuer, issuerResponse.access_token)
            const id = await createOrUpdateUser(issuer, issuerResponse.access_token, userinfo.login)
            const token = await getUserToken(id)

            if (!token) {
                return res.sendStatus(400)
            }

            res.cookie('accessToken', token, {
                httpOnly: true,
                secure: !dev,
                sameSite: true,
            })

            res.cookie('exp', Date.now(), {
                httpOnly: true,
                secure: !dev,
                maxAge: refreshTokenLifeTime
            })

            const parsedUrl = parse(req.url, true)
            const { query } = parsedUrl
            
            app.render(req, res, '/authentication', { ...query, sites: authSites, accessToken: token })
        })

        server.get('/logout', async (req, res) => {
            const parsedUrl = parse(req.url, true)
            const { query } = parsedUrl
            res.clearCookie('accessToken')
            res.clearCookie('exp')
            return app.render(req, res, '/logout', query)
        })

        server.get('*', async (req, res) => {
            const accessToken = req.cookies.accessToken
            const expiry = req.cookies.exp
            const parsedUrl = parse(req.url, true)
            const { pathname, query } = parsedUrl
            const isResourceRequest = pathname.startsWith('/_next') || pathname.startsWith('/static')
            const hasExpiry = expiry !== undefined
            const isNormalRequest = isResourceRequest || hasExpiry
            
            if (!accessToken) {
                return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: null })
            }

            if (isNormalRequest) {
                return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: accessToken })
            }
            
            if (!await canRefreshToken(accessToken)) {
                res.clearCookie('accessToken')
                res.clearCookie('exp')
                return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: null })
            }

            res.cookie('exp', Date.now(), {
                httpOnly: true,
                secure: !dev,
                maxAge: refreshTokenLifeTime
            })
            
            return app.render(req, res, pathname, { ...query, sites: authSites, accessToken: accessToken })
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