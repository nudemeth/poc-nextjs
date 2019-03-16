/* eslint-disable no-console */
import express from 'express'
import next from 'next'
import favicon from 'serve-favicon'
import path from 'path'
import fetch from 'isomorphic-unfetch'
import cookieParser from 'cookie-parser'
import config from './config'

const __dirname = path.resolve()
const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
const handle = app.getRequestHandler()

const getToken = (issuer, code) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    return fetch(`${config.api.identity.uri}token/${issuer}?code=${code}`, options)
        .then(r => r.json())
}

const getUserinfo = (issuer, token) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    return fetch(`${config.api.identity.uri}userinfo/${issuer}?token=${token}`, options)
        .then(r => r.json())
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
            console.log(`Server payload: issuer=${issuer}, code=${code}`)

            const token = await getToken(issuer, code)
            const userinfo = await getUserinfo(issuer, token.access_token)

            res.cookie('user', userinfo.login, {
                httpOnly: true,
                secure: !dev
            })
            //TODO: Store access token in db { login, issuer, access_token }
            app.render(req, res, '/authentication')
        })

        server.get('/login', (req, res) => {
            const user = req.cookies.user
            if (!user) {
                return handle(req, res)
            }
            console.log(`Server Login user: ${user}`)
            app.render(req, res, '/login', { user: user })
        })

        server.get('*', (req, res) => {
            return handle(req, res)
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