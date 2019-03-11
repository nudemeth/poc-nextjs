/* eslint-disable no-console */
const express = require('express')
const next = require('next')
const favicon = require('serve-favicon')
const path = require('path')
const http = require('http')
const cookieParser = require('cookie-parser')

const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
const handle = app.getRequestHandler()

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

        server.get('/authentication', (req, res) => {
            const issuer = req.query.issuer
            const code= req.query.code
            console.log(`Server payload: issuer=${issuer}, code=${code}`)
            const options2 = {
                host: 'localhost',
                port: 5000,
                path: `/api/v1/identity/token/${issuer}?code=${code}`,
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                }
            }
            const req2 = http.request(options2, (res2) => {
                console.log(`Server Token Status: ${res2.statusCode}`)
                console.log(`Server Token Headers: ${JSON.stringify(res2.headers)}`)
                res2.on('data', (chunk2) => {
                    console.log(`Server Token Body: ${chunk2}`)
                    const data2 = JSON.parse(chunk2)
                    console.log(`${issuer} token: ${data2.access_token}`)
                    const options3 = {
                        host: 'localhost',
                        port: 5000,
                        path: `/api/v1/identity/userinfo/${issuer}?token=${data2.access_token}`,
                        method: 'GET',
                        headers: {
                            'Accept': 'application/json',
                        }
                    }
                    const req3 = http.request(options3, (res3) => {
                        //TODO: Store access toeken in db { login, issuer, access_token }
                        console.log(`Server Userinfo Status: ${res2.statusCode}`)
                        console.log(`Server Userinfo Headers: ${JSON.stringify(res2.headers)}`)
                        res3.on('data', (chunk3) => {
                            console.log(`Server Userinfo Body: ${chunk3}`)
                            const data3 = JSON.parse(chunk3)
                            res.cookie('user', data3.login, {
                                httpOnly: true,
                                secure: !dev
                            })
                            app.render(req, res, '/authentication')
                        })
                    })
                    req3.end()
                })
                res2.on('end', () => {
                    console.log('Server: No more data in response.')
                })
            })
            req2.end()
        })

        server.get('/login', (req, res) => {
            const token = req.cookies.token
            if (!token) {
                return handle(req, res)
            }
            console.log(`Server Login token: ${token}`)
            app.render(req, res, '/login', { token: token })
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