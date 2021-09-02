const express = require('express');
const router = express.Router();

const teamsController = require('../controllers/teams');

// set the response headers for API routes /teams
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

// teams - list all
router.get('/', teamsController.list);

// teams - find one
router.get('/:teamId', teamsController.read);

// teams - create one
router.post('/', teamsController.create);

// teams - update one
router.put('/:teamId', teamsController.update);

// teams - delete one
router.delete('/:teamId', teamsController.delete);

// export router
module.exports = router;