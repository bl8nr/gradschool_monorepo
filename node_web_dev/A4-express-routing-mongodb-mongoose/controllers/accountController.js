const Account = require('../models/accountModel');
const passport = require('passport');

// create account controller object to add functions to later
const accountController = {};

// register a user
accountController.register = function(req, res, next) {

    // create a new account/user using mongoose-passport-local
    Account.register(new Account({
        email: req.body.email,
        username: req.body.username
    }), req.body.password, function (err, account) {

        // report registration error if one occurs
        if (err) {
            return res.render('index', {error: err.message});
        }

        // otherwise authenticate and redirect user to teams page
        passport.authenticate('local')(req, res, function () {
            res.redirect('/teams');
        });
    });
};

// login user and establish a session
accountController.login = function(req, res, next) {

    // authenticate user against passport local strategy
    passport.authenticate('local', function (err, user, info) {

        // throw error if one occurs
        if (err) {
            next(err)
        }

        // report auth failure if one occurs
        if (!user) {
            console.log(info.message);
            return res.render('login', {error: info.message})
        }

        // otherwise establish session and redirect user to teams page
        req.logIn(user, function (err) {
            if (err) {
                next(err);
            }
            return res.redirect('/teams');
        })
    })(req, res, next);
};

// destroy the users session and send them to the login page
accountController.logout = function(req, res, next) {
    req.logout();
    res.redirect('/login');
};

// export all of the functions under the accountController object
module.exports = accountController;