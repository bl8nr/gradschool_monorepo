/* Good to go for final */
const utilities = require('../utilities/utilities');
const express = require('express');
const router = express.Router({mergeParams: true});
const profilesController = require('../controllers/profiles');

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
 * configure nested routers
 * postsRouter requires :profileId (and thus mergeParams)
 */
const postsRouter = require('./posts');
router.use('/:profileId/posts', postsRouter);

/**
 * config routes to standard RESTful/CRUD spec
 */
router.get('/', profilesController.list);

router.post('/', profilesController.create);

router.get('/:profileId', utilities.isProfileOwner, profilesController.read);

router.delete('/:profileId', utilities.isProfileOwner, profilesController.delete);

/**
 * configure RESTful but not implemented routes
 * so that we know they're intentionally inactive
 */
router.put('/:profileId', utilities.isProfileOwner, utilities.respondWith501);

module.exports = router;