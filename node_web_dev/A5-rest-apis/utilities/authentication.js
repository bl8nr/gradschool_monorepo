/* GENERAL UTILITIES FOR DEALING WITH AUTHENTICATION */
const authenticationUtilities = {};

// check if session exists, if not respond with status code 401
authenticationUtilities.isAuthenticated = function (req, res, next) {
		if(req.isAuthenticated()){
			// if session exists, let user continue request
			next();
		} else{
			// if session does not exist respond with 401
			res.status(401).json('Not Authorized');
		}
};

// export authentication utilities
module.exports = authenticationUtilities;