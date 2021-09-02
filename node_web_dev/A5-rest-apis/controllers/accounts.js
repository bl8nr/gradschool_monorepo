const passport = require('passport');
const accountService = require('../services/account');

// create the accounts controller object
const accountsController = {};

// use passport middleware to attempt to make a session
accountsController.createSession = function(req, res, next) {
	passport.authenticate('local', function(err, user, info) {
		if (err) { return next(err); }
		// if session creation is successful, return the userId
		req.logIn(user, function(err) {
			if (err) { return next(err); }
			return res.status(200).json({ userId: req.user._id });
		});
	})(req, res, next);
};

// use passport middleware to destroy the session
accountsController.deleteSession = function(req, res, next) {
	req.logout();
	res.status(200).json('success');
};

// check if a session exists
accountsController.getSession = function(req, res, next) {
	// if session exists return the session userId, else return code 401
	if (req.user) {
		res.status(200).json({ userId: req.user._id });
	} else {
		res.status(401).json('Unauthorized');
	};
};

// create a new account, return the user id for the new account
accountsController.create = function(req, res, next) {
	const accountData = {
		username: req.body.username,
		profile: {
			firstName: req.body.profile.firstName,
			lastName: req.body.profile.lastName,
			displayName: req.body.profile.displayName,
			email: req.body.profile.email,
			bio: req.body.profile.bio,
			company: req.body.profile.company,
			profilePicture: req.body.profile.profilePicture
		}
	};

	// create a new account
	accountService.create(accountData, req.body.password)
		.then((account) => {
			// create a session, returning the new userId
			passport.authenticate('local')(req, res, function () {
				req.session.save((err) => {
					if (err) {
						res.status(500).json(err.message);
					} else {
						res.status(200).json({ userId: req.user._id });
					}
				});
			});
		})
		.catch((err) => {
			res.status(500).json(err.message);
		});
};

// read and return one account by id as long as it matches the session account id
accountsController.read = function(req, res, next) {
	if (req.params.userId === req.user.id.toString()) {
		// return status code 200 and account object
		accountService.read(req.params.userId)
			.then((user) => {
				res.status(200).json(user);
			})
			.catch((err) => {
				console.log(err);
			})
	} else {
		// return 401 if session is requesting to get user other than itself
		res.status(401).json('401 - Not Authorized');
	}
};

// update and return one account by id as long as it matches the session account id
accountsController.update = function(req, res, next) {
	if (req.params.userId === req.user._id.toString()) {
		// update account then return status code 200 and account object
		accountService.update(req.params.userId, req.body.profile)
			.then((account) => {
				res.status(200).json(account);
			})
			.catch((err) => {
				console.log(err);
			})
	} else {
		// return 401 if session is requesting to change user other than itself
		res.status(401).json('401 - Not Authorized');
	}
};

// TODO: delete account

// export accountsController
module.exports = accountsController;

