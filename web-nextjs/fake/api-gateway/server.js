/* eslint-disable no-console */
const path = require('path')
const jsonServer = require('json-server')
const fetch = require('isomorphic-unfetch')
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
server.get('/api/v1/identity/token/:issuer', async (req, res) => {
    const issuer = req.params.issuer
    const code = req.query.code
    console.log(`API payload: issuer=${issuer}, code=${code}`)
    const paramz = querystring.stringify({
        'client_id': 'f4b44543204f5b40deec',
        'client_secret': '9bc72fae341b431a1ff000d6ef12c7fcf45fc4de',
        'code': code
    })
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
        }
    }
    console.log(`data: ${paramz}`)
    const data = await fetch(`https://github.com/login/oauth/access_token?${paramz}`, options)
        .then(r => r.json())
    console.log(`API Token data: ${JSON.stringify(data)}`)
    res.send(data)
})
server.get('/api/v1/identity/userinfo/:issuer', async (req, res) => {
    const token = req.query.token
    const options = {
        method: 'GET',
        headers: {
            'Authorization': `token ${token}`,
            'User-Agent': 'poc-microservice-dev'
        }
    }
    const data = await fetch('https://api.github.com/user', options)
        .then(r => r.json())
    console.log(`API Userinfo data: ${JSON.stringify(data)}`)
    res.send(data)
})
server.use(jsonServer.rewriter(routes))
server.use(router)
server.listen(5000, '0.0.0.0', () => {
    console.log('Fake api gateway server is running at 0.0.0.0:5000')
})