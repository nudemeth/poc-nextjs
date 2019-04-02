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

//TODO: Isolate issuer function
const getGithubToken = async function(code) {
    const fakeToken = `12345-${code}-67890`
    return await { access_token: fakeToken }
}

const getGithubUserinfo = async function(token) {
    token
    const fakeLogin = 'nudemeth'
    return await { login: fakeLogin }
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
server.get('/api/v1/identity/token/:issuer', async (req, res) => {
    const issuer = req.params.issuer
    const code = req.query.code
    let data

    switch (issuer) {
    case 'github': data = await getGithubToken(code); break
    default: throw new Error(`Invalid issuer: ${issuer}`)
    }
    
    res.send(data)
})
server.get('/api/v1/identity/userinfo/:issuer', async (req, res) => {
    const issuer = req.params.issuer
    const token = req.query.token
    let data
    
    switch (issuer) {
    case 'github': data = await getGithubUserinfo(token); break
    default: throw new Error(`Invalid issuer: ${issuer}`)
    }

    res.send(data)
})
server.use(jsonServer.rewriter(routes))
server.use(router)
server.listen(5000, '0.0.0.0', () => {
    console.log('Fake api gateway server is running at 0.0.0.0:5000')
})