require('dotenv').config({path: './current.env'});
const express = require('express');
const path = require('path');
const logger = require('morgan');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const session = require('express-session');
const MongoStore = require('connect-mongo')(session);
const utilities = require('./utilities/utilities');

// import routers
const teams = require('./routes/teams');
const accounts = require('./routes/accounts');

const app = express();

// setup mongoose database connection
const mongoose = require('mongoose');
mongoose.connect(`mongodb://${process.env.DB_USER}:${process.env.DB_PASSWORD}@${process.env.DB_HOST}`)
	.then(() => { console.log("Successfully connected to mongoDB"); })
	.catch((err) => console.error(err));

// configure request parsers
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

// configure passport for storing session data in mongodb
app.use(session({
	store: new MongoStore({ mongooseConnection: mongoose.connection }),
	secret: `${process.env.EXPRESS_SESSION_SECRET}`,
	resave: false,
	saveUninitialized: false
}));
app.use(passport.initialize());
app.use(passport.session());

// setup routing for static assets
app.use(express.static(path.join(__dirname, 'public')));

// configure routes and whether or not they're protected by auth
app.use('/api/accounts', accounts);
app.use('/api/teams', utilities.isAuthenticated, teams);

// configure passport authentication via Account schema plugin
const Account = require('./models/account');
passport.use(new LocalStrategy(Account.authenticate()));
passport.serializeUser(Account.serializeUser());
passport.deserializeUser(Account.deserializeUser());

// catch 404 (although maybe i should be returning 501 instead) and forward to error handler
app.use(function(req, res, next) {
  const err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
