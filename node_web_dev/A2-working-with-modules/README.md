# Assignment 2: Node modules and packages

Start with out the version of the simple HTTP server example from class that's included in this project.  You'll be
making several modifications to the starter code, and adding some files of your own.

1. Add a `package.json` to manage dependencies in your project
2. Install a third-party module from npm - you select one that does something useful for you.  It can be log-util, underscore or something else.  Use it in your project. Be sure to include it as a dependency in `package.json`.
3. Write your own module and use it in your Web server.  It can do anything you like.  One idea would be to extract the mime-types functionality of our server out into a module that accepts a filename extension and returns a mime-type, or that accepts the filename/filepath and the response object, and directly sets the Content-type header.
4. This server delivers files in its own home directory - the directory where the code itself resides.  Modify the server code so that the files requested in the URL are served from an `htdocs` directory inside this project. You'll do this by adding the `htdocs` directory, and then changing how the value of the `filepath` variable is determined.
5. Add a static HTML file,, in `htdocs`, to the project for the server to serve. It's fine to add some images, CSS or other assets as well, if you like.
6. Push your code back up to github, and deploy it to DO
7. Submit the URL to the page served by your server on DO, and the URL to your Github repo to Assignment 2 in Canvas



TODO:
[x] NPM init and add start and dev npm scripts
[x] Rename entrypoint to app.js
[x] add a gitignore to ignore .DS_Store and webstorms .idea folder
[x] Install log util and change all of the console logs to be log util based and color accordingly
[x] Add a mime type module that gets the file ext and returns the mime type for the response header
[x] Break the 404 and 500 response into a module
[x] Add a 404 and 500 html page for the error module to report
[x] Add a home/index.html page as well as two other pages which contain images
[x] Create a stream to stream the images and html pages into the response
[x] Add a .config file and start the server via the specs in the .config file
[x] Load the 404 and 500 pages into memory on server start so the filesystem isn't accessed everyone someone hits an error
[x] Comment code and comment config file