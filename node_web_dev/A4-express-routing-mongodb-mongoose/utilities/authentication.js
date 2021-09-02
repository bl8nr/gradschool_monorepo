/* GENERAL UTILITIES FOR DEALING WITH AUTHENTICATION */

// create an object to add functions to to export later
const authenticationUtilities = {};

// pass user to next middleware if they're authed otherwise redirect to login
authenticationUtilities.isAuthenticated = function (req, res, next) {
    if (req.user) {
        next();
    } else {
        res.redirect('/login');
    }
};

// pass user to next middleware if they're not authed otherwise redirect to teams
authenticationUtilities.isNotAuthenticated = function (req, res, next) {
    if (req.user) {
        res.redirect('/teams');
    } else {
        next();
    }
};

module.exports = authenticationUtilities;