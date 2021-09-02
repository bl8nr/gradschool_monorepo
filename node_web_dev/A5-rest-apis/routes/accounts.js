const express = require('express');
const router = express.Router();

const accountController = require('../controllers/accounts');

// set RESTful response headers
router.use((req, res, next) => {
	res.set({
		'Content-type':'application/json',
		'Access-Control-Allow-Origin':'*',
		'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,OPTIONS',
		'Access-Control-Allow-Headers':'Content-Type, Access-Control-Allow-Headers'
	});

	// end and respond with code 200 if req is for OPTIONS
	if(req.method === 'OPTIONS') {
		return res.status(200).end();
	}

	next();
});

// create a session for the user (not really RESTful)
router.post('/login', accountController.createSession);

// destroy the users session (not really RESTful)
router.get('/logout', accountController.deleteSession);

// check to see if the client has an existing user session (not really RESTful)
router.get('/session', accountController.getSession);

// account - create one
router.post('/', accountController.create);

// account - find one (only returns user if/whose session is active)
router.get('/:userId', accountController.read);

// account - update one (only updates user if/whose session is active)
router.put('/:userId', accountController.update);

// TODO: query for userId (for adding team members) route
// TODO: delete user account route

// export the accounts routes
module.exports = router;