/* Good to go for final */
const utilities = require('../utilities/utilities');
const express = require('express');
const router = express.Router();

const accountController = require('../controllers/accounts');

/**
 * configure headers and pre-flight response
 */
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

/**
 * configure non-RESTful routes
 * mainly for session management
 */
router.post('/login', accountController.createSession);

router.get('/logout', accountController.deleteSession);

router.get('/session', accountController.getSession);

/**
 * config routes to standard RESTful/CRUD spec
 */
// POST also creates a session and logs in a user
router.post('/', accountController.create);

router.get('/:userId', accountController.read);

router.put('/:userId', accountController.update);

/**
 * configure RESTful but not implemented routes
 * so that we know they're intentionally inactive
 */
router.delete(':/userId', utilities.respondWith501);

module.exports = router;