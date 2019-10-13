/* eslint-disable no-console */
import path from 'path'
import jsonServer from 'json-server'
import routes from './routes'
import fake from './db'
import { fileURLToPath } from 'url'

const __dirname = path.dirname(fileURLToPath(new URL(import.meta.url)))
const server = jsonServer.create()
const router = jsonServer.router(path.join(__dirname, 'db.json'))
const middlewares = jsonServer.defaults()

const isAuthorized = function(req) {
    if (!req.headers) {
        return false
    }
    if (!req.headers.authorization) {
        return false
    }
    return true
}

server.use(middlewares)
server.get('/api/v1/catalog/items/:id/img', (req, res) => {
    const id = req.params.id
    const item = fake.items.find(function(value) {
        return value.id === id
    })
    const imgPath = path.join(__dirname, `${item.imagePath}`)
    res.sendFile(imgPath)
})
server.put('/api/v1/identity/users/login/:login', ({ res }) => {   
    res.send(JSON.stringify('ec6c1cb4-d3b6-4f0c-908d-722ca798fa87'))
})
server.put('/api/v1/identity/users/issuer/:issuer/code/:code', ({ res }) => {   
    res.send(JSON.stringify('ec6c1cb4-d3b6-4f0c-908d-722ca798fa87'))
})
server.get('/api/v1/identity/users/:id/token', ({ res }) => {
    res.send(JSON.stringify('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMzQ1LTY3ODkwLTEyMzQ1LTY3ODkwIiwibG9naW4iOiJudWRlbWV0aCJ9.dah0nTw1wPaOzNiCeQvnPnhhKFnUmMoL0783Sz6jsho'))
})
server.get('/api/v1/catalog/catalogBrands', (req, res, next) => {
    if (isAuthorized(req)) {
        next()
    } else {
        res.sendStatus(401)
    }
})
server.get('/api/v1/catalog/catalogTypes', (req, res, next) => {
    if (isAuthorized(req)) {
        next()
    } else {
        res.sendStatus(401)
    }
})
server.use(jsonServer.rewriter(routes))
server.use(router)
server.listen(5000, '0.0.0.0', () => {
    console.log('Fake api gateway server is running at 0.0.0.0:5000')
})