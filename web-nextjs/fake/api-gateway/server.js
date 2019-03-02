/* eslint-disable no-console */
const path = require('path')
const jsonServer = require('json-server')
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
    res.send({ access_token: 'e72e16c7e42f292c6912e7710c838347ae178b4a', token_type: 'bearer' })
})
server.use(jsonServer.rewriter(routes))
server.use(router)
server.listen(5000, '0.0.0.0', () => {
    console.log('Fake api gateway server is running at 0.0.0.0:5000')
})