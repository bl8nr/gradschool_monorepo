/**
 * MIME Types Lookup Module
 * Brett Bloethner - CSCI-E31 Homework 2
 */

// create object literal of MIME types to use for lookup
const mimeTypes = {
          '.html': 'text/html',
          '.css': 'text/css',
          '.js': 'application/javascript',
          '.json': 'application/json',
          '.png': 'image/png'
};

// lookup and return mime type via index in mimeTypes object
exports.lookup = lookup = function (fileExtension) {
    return mimeTypes[fileExtension] || 'application/octet-stream';
};