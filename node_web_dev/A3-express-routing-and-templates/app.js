var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var bodyParser = require('body-parser');

/* ROUTES */
var index = require('./routes/index');
var workspaces = require('./routes/workspaces');

var app = express();

// loads seed workspace data into app.locals for assignment 3
app.locals.workspaces = require('./seed');

// sets up pug view engine
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

// adds a favicon to the website
app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
// adds realtime req logging in the console
app.use(logger('dev'));
// adds body parser to process POST stringified JSON reqs
app.use(bodyParser.json());
// adds body parser to process POST URL encoded reqs for forms
app.use(bodyParser.urlencoded({ extended: false }));
// allows routing to static files for CSS or JS
app.use(express.static(path.join(__dirname, 'public')));

/* configure route middleware */
app.use('/', index);
app.use('/workspaces', workspaces);

// raises a 404 when no other routes matched
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// handles errors for the entire app/api
app.use(function(err, req, res, next) {
  // sets locals, only providing full error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // renders the error page with thrown code, else 500
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
