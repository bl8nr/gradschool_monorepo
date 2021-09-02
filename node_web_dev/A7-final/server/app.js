/* Good to go for final */
/**
 * load env vars, this is unsafe as
 * it requires secrets be stored in github
 */
require('dotenv').config({path: `./environments/${process.env.NODE_ENV}.env`});

/**
 * require express, mongoose, and passport
 * packages
 */
const express = require('express');
const logger = require('morgan');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const session = require('express-session');
const MongoStore = require('connect-mongo')(session);
const utilities = require('./utilities/utilities');

/**
 * init express application
 */
const app = express();

/**
 * setup mongoose database connection
 */
const mongoose = require('mongoose');
mongoose.connect(`mongodb://${process.env.DB_USER}:${process.env.DB_PASSWORD}@${process.env.DB_HOST}`)
	.then(() => { console.log("Successfully connected to mongoDB"); })
	.catch((err) => console.error(err));

/**
 * configure logger and request parser
 */
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

/**
 * configure passports implementation of
 * mongoDB session storage
 */
app.use(session({
	store: new MongoStore({ mongooseConnection: mongoose.connection }),
	secret: `${process.env.EXPRESS_SESSION_SECRET}`,
	resave: false,
	saveUninitialized: false
}));
app.use(passport.initialize());
app.use(passport.session());

/**
 * configure high level routes and protect them
 * with isAuthenticated if needed
 */
const accounts = require('./routes/accounts');
const profiles = require('./routes/profiles');
app.use('/api/accounts', accounts);
app.use('/api/profiles', utilities.isAuthenticated, profiles);
app.use('/', express.static('../client/dist'));
app.use('/redditcb', express.static('../client/dist'));

/**
 * configure passport authentication via the
 * Account schema plugin
 */
const Account = require('./models/account');
passport.use(new LocalStrategy(Account.authenticate()));
passport.serializeUser(Account.serializeUser());
passport.deserializeUser(Account.deserializeUser());

/**
 * configure a fallback to a code 404 response
 * for routes which have no match
 */
app.use(function(req, res, next) {
  const err = new Error('Not Found');
  err.status = 404;
  next(err);
});

/**
 * configure fallback error handler to be verbose
 * only if the app is running in development
 */
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.json('error');
});

/**
 * export the app to be run in ./bin/www
 */
module.exports = app;