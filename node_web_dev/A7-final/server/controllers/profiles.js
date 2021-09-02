/* Good to go for final */
const utilities = require('../utilities/utilities');
const profileService = require('../services/profile');

const profileController = {};

/**
 * return profiles where the ownerId matches
 * sessions userId
 */
profileController.list = function(req, res) {
	profileService.list(req.user.id)
		.then((profiles) => {
			res.status(200).json(profiles);
		})
    .catch((err) => {
      utilities.respondWith500(req, res, err);
    });
};

/**
 * return one profile by id
 */
profileController.read = function(req, res) {
	profileService.read(req.params.profileId)
		.then((profile) => {
			res.status(200).json(profile);
		})
    .catch((err) => {
      utilities.respondWith500(req, res, err);
    });
};

/**
 * create one reddit profile/integration and get the OAuth
 * tokens for that profile, then return the new profile
 */
profileController.create = function(req, res) {
	let authorizationData = null;
	let accountInfo = null;

	// get the reddit access tokens and add it to the profile object
	utilities.getRedditOAuthToken(req.body.originalCode)
		.then((authData) => {
			authorizationData = {
				originalState: req.body.originalState,
				originalCode: req.body.originalCode,
				accessToken: authData.access_token,
				tokenType: authData.token_type,
				expiresIn: authData.expires_in,
				refreshToken: authData.refresh_token,
				scope: authData.scope
			};
			return utilities.getRedditAccountInfo(authorizationData.accessToken)
		})
		.then((accountInfoFromReddit) => {
		  // get the reddit profile/account info and add it to the account object
			accountInfo = accountInfoFromReddit;
			return profileService.create(req.user.id, authorizationData, accountInfo);
		})
		.then((createdProfile) => {
			res.status(200).json(createdProfile);
		})
		.catch((err) => {
      utilities.respondWith500(req, res, err);
		});
};

/**
 * delete one reddit profile and return it too
 */
profileController.delete = function(req, res) {
	profileService.delete(req.params.profileId)
		.then((deletedTeam) => {
			res.status(200).json(deletedTeam);
		})
		.catch((err) => {
      utilities.respondWith500(req, res, err);
		});
};

module.exports = profileController;