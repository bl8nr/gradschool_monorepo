/* Good to go for final */
const utilities = require('../utilities/utilities');
const postService = require('../services/post');

const postController = {};

/**
 * return a list of posts all posts belonging to a profile
 */
postController.list = function(req, res) {
  postService.list(req.params.profileId)
    .then((posts) => {
      res.status(200).json(posts);
    })
    .catch((err) => {
      utilities.respondWith500(req, res, err);
    });
};

/**
 * create one post then return the changed list of posts
 */
postController.create = function(req, res) {
  postService.create(req.params.profileId, req.user.id, req.body)
    .then((updatedPosts) => {
      res.status(200).json(updatedPosts);
    })
    .catch((err) => {
      utilities.respondWith500(req, res, err);
    });
};

/**
 * update one post then return the changed list of posts
 */
postController.update = function(req, res) {
  postService.update(req.params.profileId, req.params.postId, req.body)
    .then((updatedPosts) => {
      res.status(200).json(updatedPosts);
    })
    .catch((err) => {
      utilities.respondWith500(req, res, err);
    });
};

/**
 * delete one post then return the changed list of posts
 */
postController.delete = function(req, res) {
  postService.delete(req.params.profileId, req.params.postId)
    .then((updatedPosts) => {
      res.status(200).json(updatedPosts);
    })
    .catch((err) => {
      utilities.respondWith500(req, res, err);
    });
};

module.exports = postController;