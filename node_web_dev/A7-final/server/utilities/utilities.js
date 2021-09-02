/* Good to go for final */
const authentication = require('./authentication');
const oauth = require('./oauth');
const response = require('./response');

/**
 * map authentication related utilities
 */
exports.isAuthenticated = authentication.isAuthenticated;
exports.isProfileOwner = authentication.isProfileOwner;

/**
 * map reddit OAuth related utilities
 */
exports.getRedditOAuthToken = oauth.getAccessToken;
exports.getRedditAccountInfo = oauth.getAccountInfo;
exports.refreshRedditToken = oauth.refreshAccessToken;
exports.checkRedditAccessToken = oauth.checkAccessTokenExpiration;

/**
 * map response related utilities
 */
exports.respondWith401 = response.respondWith401;
exports.respondWith500 = response.respondWith500;
exports.respondWith501 = response.respondWith501;