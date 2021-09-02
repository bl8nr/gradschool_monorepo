/* Good to go for final */
/**
 * Middleware utilities for dealing HTTP
 * responses used so that we can standardize
 * our responses in one place
 */
const responseUtilities = {};

responseUtilities.respondWith401 = function(req, res) {
  res.status(401).json('401 - Not Authorized');
};

responseUtilities.respondWith500 = function(req, res, err) {
  // here is where we would report the error to a APM service
  res.status(500).json('500 - Internal Server Error');
};

responseUtilities.respondWith501 = function(req, res) {
  res.status(501).json('501 - Not Implemented');
};

module.exports = responseUtilities;