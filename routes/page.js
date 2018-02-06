import express from 'express';
import UI from '../components/main.jsx';

const router = express.Router();
const ui = new UI();

/* GET home page. */
router.get('/', function(req, res, next) {
  let model = { title: 'Home', greeting: 'This is Home page' };
  let html = ui.renderServer(req.path, model);
  let css = ui.sheetsRegistry.toString();
  res.render('index', { title: model.title, container: html, model: JSON.stringify(model), css: css });
});

/* GET about page. */
router.get('/about', function(req, res, next) {
  let model = { title: 'About', text:'About page' };
  let html = ui.renderServer(req.path, model);
  let css = ui.sheetsRegistry.toString();
  res.render('index', { title: model.title, container: html, model: JSON.stringify(model), css: css });
});

export default router;
