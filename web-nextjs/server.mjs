/* eslint-disable no-console */
import express from 'express'
import next from 'next'
import favicon from 'serve-favicon'
import path from 'path'
import fetch from 'isomorphic-unfetch'
import cookieParser from 'cookie-parser'
import crypto from 'crypto'
import { parse } from 'url'
import config from './config'

const __dirname = path.resolve()
const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
//const handle = app.getRequestHandler()

const algorithm = process.env.ENCRYPTION_ALGORITHM || 'aes-256-cbc'
const secret = process.env.ENCRYPTION_SECRET || 'this is my secret'
const salt = process.env.ENCRYPTION_SALT || 'this is my salt'
const key = crypto.scryptSync(secret, salt, 32)
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

const getUserinfo = (issuer, token) => {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    }
    return fetch(`${config.api.identity.uri}userinfo?issuer=${issuer}&token=${token}`, options)
        .then(r => r.json())
}

const saveUserinfo = (issuer, token, login) => {
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
    return fetch(`${config.api.identity.uri}userinfo`, options)
        .then(r => r.json())
}

const encrypt = (value) => {
    const iv = crypto.randomBytes(16)
    const ivStr = iv.toString('base64')
    const cipher = crypto.createCipheriv(algorithm, key, iv)
    let encrypted = cipher.update(value, 'utf8', 'hex')
    encrypted += cipher.final('hex')
    return `${encrypted}.${ivStr}`
}

const decrypt = (value) => {
    try {
        const portions = value.split('.')
        const encryptedValue = portions[0]
        const ivStr = portions[1]
        const iv = Buffer.from(ivStr, 'base64')
        const decipher = crypto.createDecipheriv(algorithm, key, iv)
        let decrypted = decipher.update(encryptedValue, 'hex', 'utf8')
        decrypted += decipher.final('utf8')
        return decrypted
    } catch (err) {
        console.warn(`Unable to parse: ${value} (${err.message})`)
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
            const userinfo = await getUserinfo(issuer, token.access_token)
            await saveUserinfo(issuer, token, userinfo.login)
            const encrypted = encrypt(userinfo.login)
            
            res.cookie('user', encrypted, {
                httpOnly: true,
                secure: !dev
            })

            const parsedUrl = parse(req.url, true)
            const { query } = parsedUrl
            //TODO: Store access token in db { login, issuer, access_token }
            app.render(req, res, '/authentication', { ...query, sites: authSites, user: userinfo.login })
        })

        server.get('*', (req, res) => {
            const encryptedUser = req.cookies.user
            const parsedUrl = parse(req.url, true)
            const { pathname, query } = parsedUrl

            if (!encryptedUser) {
                return app.render(req, res, pathname, { ...query, sites: authSites, user: null })
            }

            const decryptedUser = decrypt(encryptedUser)
            if (!decryptedUser) {
                res.clearCookie('user')
                return app.render(req, res, pathname, { ...query, sites: authSites, user: null })
            }
            
            return app.render(req, res, pathname, { ...query, sites: authSites, user: decryptedUser })
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