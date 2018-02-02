import express from 'express';
const router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Index', container: '<h1>Hello World!</h1>', model: '' });
});

export default router;
