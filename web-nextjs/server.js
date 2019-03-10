/* eslint-disable no-console */
const express = require('express')
const next = require('next')
const favicon = require('serve-favicon')
const path = require('path')
const http = require('http')

const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev })
const handle = app.getRequestHandler()

app
    .prepare()
    .then(() => {
        const server = express()
        const port = dev ? 3000 : 80

        server.use(favicon(path.join(__dirname, 'static', 'favicon.ico')))

        /*server.get('/p/:id', (req, res) => {
            const actualPage = '/post';
            const queryParams = { id: req.params.id };
            app.render(req, res, actualPage, queryParams);
        });*/

        server.get('/authentication', (req, res) => {
            const issuer = req.query.issuer
            const code= req.query.code
            console.log(`Server payload: issuer=${issuer}, code=${code}`)
            const options = {
                host: 'localhost',
                port: 5000,
                path: `/api/v1/identity/token/${issuer}?code=${code}`,
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                }
            }
            const req2 = http.request(options, (res2) => {
                console.log(`Server Status: ${res2.statusCode}`)
                console.log(`Server Headers: ${JSON.stringify(res2.headers)}`)
                res2.on('data', (chunk) => {
                    console.log(`Server Body: ${chunk}`)
                    const data = JSON.parse(chunk)
                    console.log(`${issuer} token: ${data.access_token}`)
                    res.cookie(`${issuer}-token`, data.access_token, {
                        maxAge: 900000, //15 mins
                        httpOnly: true
                    })
                    app.render(req, res, '/authentication')
                })
                res2.on('end', () => {
                    console.log('Server: No more data in response.')
                })
            })
            req2.end()
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