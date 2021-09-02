/* Good to go for final */
const utilities = require('../utilities/utilities');
const express = require('express');
const router = express.Router({mergeParams: true});

const postController = require('../controllers/posts');

/**
 * config routes to standard RESTful/CRUD spec
 */
router.get('/', utilities.isProfileOwner, postController.list);

router.post('/', utilities.isProfileOwner, postController.create);

router.put('/:postId', utilities.isProfileOwner, postController.update);

router.delete('/:postId', utilities.isProfileOwner, postController.delete);

/**
 * configure RESTful but not implemented routes
 * so that we know they're intentionally inactive
 */
router.get('/:postId', utilities.isProfileOwner, utilities.respondWith501);

module.exports = router;