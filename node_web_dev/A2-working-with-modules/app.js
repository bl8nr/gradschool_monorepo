/**
 * Application server entrypoint
 * Brett Bloethner - CSCI-E31 Homework 2
 */
const http = require('http');
const path = require('path');
const url = require('url');
const fs = require('fs');
const log = require('log-util');
const config = require('./config');
const mime = require('./mime');
const error = require('./error');

http.createServer((req,res) => {
    // get the file path requested via url, log it
    let { pathname } = url.parse(req.url);
    log.info(`Client requested pathname: ${pathname}`);

    // if requested path is root then send user to index.html
    pathname === '/' ? pathname = '/index.html' : null;

    // get the system path of the requested pathname. log it verbosely
    const filepath = path.join(process.cwd(), 'htdocs/', pathname);
    log.verbose(`Requested pathname has file path: ${filepath}`);

    // extract the file extension from the file path
    const extname = String(path.extname(filepath)).toLocaleLowerCase();

    fs.stat(filepath, (err) => {

        // if error exists, send it to the error module to response to client
        if (err) {
            console.log(err.code);
            err.code === 'ENOENT' ? error.respondWith(404, res, err) : error.respondWith(500, res, err);
            return;
        }

        // else create a fs read stream and lookup/store the MIME type
        const readstream = fs.createReadStream(filepath);
        const responseMIMEtype = mime.lookup(extname);

        // write the response header, pipe the read stream into the response body
        res.writeHead(200, {"Content-Type": responseMIMEtype});
        readstream.pipe(res);

        // log response specs once the readstream/response has ended
        readstream.on('end', () => log.info(`Server responded with /htdocs${pathname} as ${responseMIMEtype}`));

    });
}).listen(config.port, config.address, function () {

    // log server listening specs once the http listen has successfully started
    log.info(`Server listening on: http://${config.address}:${config.port}`);

});
