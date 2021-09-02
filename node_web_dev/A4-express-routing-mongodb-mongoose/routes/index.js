const express = require('express');
const router = express.Router();
const accounts = require('../controllers/accountController');
const utilities = require('../utilities/utilities');

// render the index (home) page only if the user is not authed
router.get('/', utilities.isNotAuthenticated, function (req, res) {
    res.render('index');
});

// register a new account/user, form POST submission
router.post('/register', accounts.register);

// render the login template only if the user is not authed
router.get('/login', utilities.isNotAuthenticated, function (req, res) {
    res.render('login');
});

// log the user into the app, form POST submission
router.post('/login', accounts.login);

// log the user out of the app
router.get('/logout', accounts.logout);

module.exports = router;