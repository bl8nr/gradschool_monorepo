var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  // renders index template, passes workspaces to it from app.locals
  res.render('index', { title: 'Workspaces', workspaces: req.app.locals.workspaces });
});

module.exports = router;
