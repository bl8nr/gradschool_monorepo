// import utilities files
const objectStorage = require('./objectStorage');
const authentication = require('./authentication');

// map functions the utilities files for easy access
exports.uploadImage = objectStorage.uploadImage;
exports.isAuthenticated = authentication.isAuthenticated;
exports.isNotAuthenticated = authentication.isNotAuthenticated;