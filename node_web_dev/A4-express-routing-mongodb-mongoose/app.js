require('dotenv').config();
// import standard express modules
const express = require('express');
const path = require('path');
const favicon = require('serve-favicon');
const logger = require('morgan');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

// import auth related modules
const Account = require('./models/accountModel');
const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const session = require('express-session');
const MongoStore = require('connect-mongo')(session);

// import routes
const index = require('./routes/index');
const teams = require('./routes/teams');

// import utilities and sass transpiler
const utilities = require('./utilities/utilities');
const sassMiddleware = require('node-sass-middleware');


const app = express();

// create connection to mongoDB
mongoose.connect(`mongodb://${process.env.DB_USER}:${process.env.DB_PASSWORD}@${process.env.DB_HOST}`)
  .then(() => { console.log("Successfully connected to mongoDB"); })
  .catch((err) => console.error(err));

// configure view engine and template directory
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

// configure sass transpiler (bootstrap theme is in scss)
app.use(sassMiddleware({
    src: path.join(__dirname, 'public/scss'),
    dest: path.join(__dirname, 'public'),
    debug: true,
    outputStyle: 'compressed',
    prefix:  '/stylesheets'
}));

// run standard express configuration. setup favicon, logger, and req parsers
app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

// setup app session management using mongostore and passport
app.use(session({
  store: new MongoStore({ mongooseConnection: mongoose.connection }),
  secret: `${process.env.EXPRESS_SESSION_SECRET}`,
  resave: false,
  saveUninitialized: false
}));
app.use(passport.initialize());
app.use(passport.session());

// configure passport strategy and setup user serializer
passport.use(new LocalStrategy(Account.authenticate()));
passport.serializeUser(Account.serializeUser());
passport.deserializeUser(Account.deserializeUser());

// setup routes for /public/*, /*, and an auth protected /teams/*
app.use(express.static(path.join(__dirname, 'public')));
app.use('/', index);
app.use('/teams', utilities.isAuthenticated, teams);

// setup last route, if no routes match here's a 404
app.use(function(req, res, next) {
  const err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// render the standard express server error if an error is thrown/caught
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;