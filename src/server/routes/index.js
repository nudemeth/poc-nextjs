import express from 'express';
import UI from '../../client/main.jsx';

const router = express.Router();
const ui = new UI();

/* GET home page. */
router.get('/', function(req, res, next) {
  let html = ui.renderServer();
  res.render('index', { title: 'Index', container: html, model: '' });
});

export default router;
