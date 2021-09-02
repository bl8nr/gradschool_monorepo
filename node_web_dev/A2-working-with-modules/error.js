/**
 * HTTP Error Module
 * Brett Bloethner - CSCI-E31 Homework 2
 */
const log = require('log-util');
const fs = require('fs');

// loading the 404 and 500 html sync so they're ready to go before any reqs can be processed
// loading them into memory so the filesystem isn't accessed every time a 404 or 500 is reqd
let html404 = fs.readFileSync('./htdocs/notfound.html');
let html500 = fs.readFileSync('./htdocs/servererror.html');

// handle HTTP errors and log errors based on passed in args
exports.respondWith = respondWith = function (errorCode, res, error) {

    // if code is 404 respond with 404 and log error
    if(errorCode === 404) {
        res.writeHead(404, {"Content-Type": "text/html"});
        res.end(html404);
        log.error('Server responded with 404 Not Found');
        error ? log.verbose(error) : null;
        return;
    }

    // else assume 500, respond with a 500 and log error
    res.writeHead(500, {"Content-Type": "text/html"});
    res.end(html500);
    log.error('Server responded with 500 Internal Server Error');
    error ? log.verbose(error) : null;
    return;
};