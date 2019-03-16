/* eslint-disable no-console */
import express from 'express'
import next from 'next'
import favicon from 'serve-favicon'
import path from 'path'
import fetch from 'isomorphic-unfetch'
import cookieParser from 'cookie-parser'
import crypto from 'crypto'
import config from './config'

const __dirname = path.resolve()
const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
const handle = app.getRequestHandler()

const algorithm = process.env.algorithm || 'aes-256-cbc'
const secret = process.env.secret || 'this is my secret'
const salt = process.env.salt || 'this is my salt'
const key = crypto.scryptSync(secret, salt, 32)
const iv = crypto.randomBytes(16)
const cipher = crypto.createCipheriv(algorithm, key, iv)
const decipher = crypto.createDecipheriv(algorithm, key, iv)

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

const encrypt = (value) => {
    let encrypted = cipher.update(value, 'utf8', 'hex')
    encrypted += cipher.final('hex')
    return encrypted
}

const decrypt = (value) => {
    let decrypted = decipher.update(value, 'hex', 'utf8')
    decrypted += decipher.final('utf8')
    return decrypted
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
            const encrypted = encrypt(userinfo.login)
            
            res.cookie('user', encrypted, {
                httpOnly: true,
                secure: !dev
            })

            //TODO: Store access token in db { login, issuer, access_token }
            app.render(req, res, '/authentication')
        })

        server.get('/login', (req, res) => {
            const encryptedUser = req.cookies.user
            if (!encryptedUser) {
                return handle(req, res)
            }
            const decrypted = decrypt(encryptedUser)
            app.render(req, res, '/login', { user: decrypted })
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