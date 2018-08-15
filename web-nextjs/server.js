const express = require('express');
const next = require('next');
const favicon = require('serve-favicon');
const path = require('path');

const dev = process.env.NODE_ENV !== 'production';
const app = next({ dev });
const handle = app.getRequestHandler();

app
.prepare()
.then(() => {
    const server = express();
    const http = require('http');
    const https = require('https');
    const fs = require('fs');
    
    //Self-Signed SSL: https://www.petri.com/create-self-signed-certificate-using-powershell
    const pfx = fs.readFileSync('./cert.pfx');
    const credentials = { pfx: pfx, passphrase: '7ANZNx^BVd12' };

    server.use(favicon(path.join(__dirname, 'static', 'favicon.ico')));

    /*server.get('/p/:id', (req, res) => {
        const actualPage = '/post';
        const queryParams = { id: req.params.id };
        app.render(req, res, actualPage, queryParams);
    });*/

    server.get('*', (req, res) => {
        return handle(req, res);
    });

    http.createServer(server).listen(3000, (err) => {
        if (err) throw err
        console.log('> Ready on http://localhost:3000');
    });

    https.createServer(credentials, server).listen(3001, (err) => {
        if (err) throw err
        console.log('> Ready on https://localhost:3001');
    });
})
.catch((ex) => {
    console.error(ex.stack);
    process.exit(1);
});