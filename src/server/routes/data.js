import express from 'express';
const router = express.Router();

/* GET data/home */
router.get('/home', function(req, res, next) {
  let model = { title: 'Home', greeting: 'This is Home page' };
  res.type('aplication/json');
  res.send(JSON.stringify(model));
});

/* GET data/about */
router.get('/about', function(req, res, next) {
  let model = { title: 'About', text:'About page' };
  res.type('aplication/json');
  res.send(JSON.stringify(model));
});

export default router;
