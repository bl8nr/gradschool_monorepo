/* GOOD TO GO FOR FINAL */
const utilities = require('../utilities/utilities');
const passport = require('passport');
const accountService = require('../services/account');

const accountsController = {};

/**
 * create a user session, i.e. login user
 * HTTP return the user ID to acknowledge login success
 */
accountsController.createSession = function(req, res, next) {
	passport.authenticate('local', function(err, user) {
		if (err) { return next(err); }
		req.logIn(user, function(err) {
			if (err) { return next(err); }
			return res.status(200).json({ userId: req.user._id });
		});
	})(req, res, next);
};

/**
 * destroy a user session, i.e. logout user
 * HTTP return code 200 and 'success' to acknowledge
 * logout success
 */
accountsController.deleteSession = function(req, res) {
	req.logout();
	res.status(200).json('success');
};

/**
 * check to see if session exits, i.e. is a user logged in?
 * HTTP return the user id to acknowledge user is logged in
 * else return a code 401
 */
accountsController.getSession = function(req, res) {
	if (req.user) {
		res.status(200).json({ userId: req.user._id });
	} else {
	  utilities.respondWith401(req, res);
	};
};

/**
 * create a new user account, i.e. signup
 * HTTP return the new users userID to acknowledge
 * account creation and successful login
 */
accountsController.create = function(req, res) {

  // create object to match Account model shape
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

	// create the account
	accountService.create(accountData, req.body.password)
		.then(() => {
			// create the user session
			passport.authenticate('local')(req, res, function () {
				req.session.save((err) => {
					if (err) {
					  utilities.respondWith500(req, res, err.message);
					} else {
						res.status(200).json({ userId: req.user._id });
					}
				});
			});
		})
		.catch((err) => {
      utilities.respondWith500(req, res, err);
		});
};

/**
 * read one user account, HTTP return the user account only
 * if the users userID requested is the same as the userId
 * of the current session, i.e. logged in user, otherwise
 * return 401 unauthorized
 */
accountsController.read = function(req, res) {
	if (req.params.userId === req.user.id.toString()) {
		accountService.read(req.params.userId)
			.then((user) => {
				res.status(200).json(user);
			})
			.catch((err) => {
			  utilities.respondWith500(req, res, err);
			})
	} else {
	  utilities.respondWith401(req, res);
	}
};

/**
 * update one user account, HTTP return the updated user account
 * with the changes made only if the user changeds' userID is
 * the same as the userId of the current session, i.e. logged
 * in user. Otherwise return 401 unauthorized
 */
accountsController.update = function(req, res) {
	if (req.params.userId === req.user._id.toString()) {
		// update account then return status code 200 and account object
		accountService.update(req.params.userId, req.body.profile)
			.then((account) => {
				res.status(200).json(account);
			})
			.catch((err) => {
        utilities.respondWith500(req, res, err);
			})
	} else {
	  utilities.respondWith401(req, res);
	}
};

/**
 * delete one user account
 * NOT YET IMPLEMENTED
 */
accountsController.delete = function(req, res) {
  utilities.respondWith501(req, res);
};

module.exports = accountsController;