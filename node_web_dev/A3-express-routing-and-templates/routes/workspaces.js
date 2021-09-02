var express = require('express');
var router = express.Router();
var uuid = require('uuid/v1');

/*** WORKSPACE COLLECTION ROUTES ***/
router.route('/')
  // GET - get all workspaces in the collection
  .get(function (req, res, next) {
    // renders workspaces template, passes workspaces to it from app.locals
    res.render('workspaces', { workspaces: req.app.locals.workspaces });
  })

  // POST - add a workspace to the collection
  .post(function (req, res, next) {
    // gets and assigns a uuid to the new workspace object
    var workspaceId = uuid();
    // saves the new workspace object in app.locals
    req.app.locals.workspaces.push({ name: req.body.name, description: req.body.description, format: req.body.format, id: workspaceId })
    // redirects client to the newly saved workspace page
    res.redirect('/workspaces/' + workspaceId);
  })

  // PUT - replace the entire collection of workspaces
  .put(function (req, res, next) {
    // assignment 3 did not require updating a collection
    var err = new Error('Not implemented');
    err.status = 501;
    next(err);
  })

  // DELETE - delete the entire collection of workspaces
  .delete(function (req, res, next) {
    // assignment 3 did not require deleting a collection
    var err = new Error('Not implemented');
    err.status = 501;
    next(err);
  });

/** WORKSPACE ITEM ROUTES ***/
router.route('/:id')
  // GET - get a single workspace item by id
  .get(function (req, res, next) {
    // searches app.locals for the workspace
    var workspace = req.app.locals.workspaces.find(function (workspace) {
      return workspace.id == req.params.id;
    });
    // renders workspace template, passes workspace item to it
    res.render('workspace', { workspace: workspace });
  })

  // PUT - update/replace a single workspace item
  .put(function (req, res, next) {
    // assignment 3 did not require updating entries
    var err = new Error('Not implemented');
    err.status = 501;
    next(err);
  })

  // DELETE - delete a single workspace item by id
  .delete(function (req, res, next) {
    // assignment 3 did not require deleting entires
    var err = new Error('Not implemented');
    err.status = 501;
    next(err);
  });

module.exports = router;
