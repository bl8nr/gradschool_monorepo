/* Good to go for final */
/**
 * Utilities for dealing with authentication
 * as it relates to route guarding middleware
 */
const utilities = require('./utilities');
const profileService = require('../services/profile');

const authenticationUtilities = {};

/**
 * check if the request comes from a client who has an
 * active session. If so, proceed, if not respond with a
 * HTTP code 401
 */
authenticationUtilities.isAuthenticated = function (req, res, next) {
		if(req.isAuthenticated()){
			next();
		} else{
      utilities.respondWith401(req, res);
		};
};

/**
 * check if the request comes from a client who is the
 * owner (by Profile.ownerId) of the Profile they're
 * requesting access to. If so, proceed, if not respond with a
 * HTTP code 401.
 */
authenticationUtilities.isProfileOwner = function (req, res, next) {
  profileService.read(req.params.profileId)
    .then((Profile) => {
      if (Profile.ownerId === req.user.id) {
        next();
      } else {
        utilities.respondWith401(req, res);
      }
    });
};

module.exports = authenticationUtilities;