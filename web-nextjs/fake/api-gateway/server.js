/* eslint-disable no-console */
const path = require('path')
const jsonServer = require('json-server')
const http = require('http')
const server = jsonServer.create()
const router = jsonServer.router(path.join(__dirname, 'db.json'))
const middlewares = jsonServer.defaults()
const routes = require('./routes')
const fake = require('./db.json')

server.use(middlewares)
server.get('/api/v1/catalog/items/:id/img', (req, res) => {
    const id = req.params.id
    const item = fake.items.find(function(value) {
        return value.id === id
    })
    const imgPath = path.join(__dirname, `${item.imagePath}`)
    res.sendFile(imgPath)
})
server.get('/api/v1/identity/token/:issuer', (req, res) => {
    const issuer = req.params.issuer
    const code = req.query.code
    console.log(`Fake payload: issuer=${issuer}, code=${code}`)
    const data = JSON.stringify({
        client_id: 'f4b44543204f5b40deec',
        client_secret: '9bc72fae341b431a1ff000d6ef12c7fcf45fc4de',
        code: code
    })
    const options = {
        host: 'github.com',
        port: 80,
        path: '/login/oauth/access_token',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': Buffer.byteLength(data),
            'Accept': 'application/json'
        }
    }
    console.log(`data: ${data}`)
    const req2 = http.request(options, (res2) => {
        console.log(`Status: ${res2.statusCode}`)
        console.log(`Headers: ${JSON.stringify(res2.headers)}`)
        res2.setEncoding('utf8')
        res2.on('data', (chunk) => {
            console.log(`Body: ${chunk}`)
            res.send(chunk)
        })
        res2.on('end', () => {
            console.log('No more data in response.')
        })
    })
    req2.write(data)
    req2.end()
    console.log('Check point 2')
    //res.send({ access_token: 'e72e16c7e42f292c6912e7710c838347ae178b4a', token_type: 'bearer' })
})
server.use(jsonServer.rewriter(routes))
server.use(router)
server.listen(5000, '0.0.0.0', () => {
    console.log('Fake api gateway server is running at 0.0.0.0:5000')
})