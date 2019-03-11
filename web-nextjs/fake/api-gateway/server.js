/* eslint-disable no-console */
const path = require('path')
const jsonServer = require('json-server')
const http = require('follow-redirects').http
const querystring = require('querystring')
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
    console.log(`API payload: issuer=${issuer}, code=${code}`)
    const data = querystring.stringify({
        'client_id': 'f4b44543204f5b40deec',
        'client_secret': '9bc72fae341b431a1ff000d6ef12c7fcf45fc4de',
        'code': code
    })
    const options = {
        host: 'github.com',
        path: `/login/oauth/access_token?${data}`,
        method: 'POST',
        headers: {
            'Accept': 'application/json',
        }
    }
    console.log(`data: ${data}`)
    const req2 = http.request(options, (res2) => {
        console.log(`API Token Status: ${res2.statusCode}`)
        console.log(`API Token Headers: ${JSON.stringify(res2.headers)}`)
        let chunks = []
        res2.on('data', (chunk) => {
            chunks.push(chunk)
        })
        res2.on('end', () => {
            console.log('API Token: No more data in response.')
            const data = Buffer.concat(chunks)
            console.log(`API Token data: ${data}`)
            res.send(data)
        })
    })
    req2.end()
})
server.get('/api/v1/identity/userinfo/:issuer', (req, res) => {
    const token = req.query.token
    const options = {
        host: 'api.github.com',
        path: '/user',
        method: 'GET',
        headers: {
            'Authorization': `token ${token}`,
            'User-Agent': 'poc-microservice-dev'
        }
    }
    const req2 = http.request(options, (res2) => {
        console.log(`API Userinfo Status: ${res2.statusCode}`)
        console.log(`API Userinfo Headers: ${JSON.stringify(res2.headers)}`)
        let chunks = []
        res2.on('data', (chunk) => {
            chunks.push(chunk)
        })
        res2.on('end', () => {
            console.log('API Userinfo: No more data in response.')
            const data = Buffer.concat(chunks)
            console.log(`API Userinfo data: ${data}`)
            res.send(data)
        })
    })
    req2.end()
})
server.use(jsonServer.rewriter(routes))
server.use(router)
server.listen(5000, '0.0.0.0', () => {
    console.log('Fake api gateway server is running at 0.0.0.0:5000')
})