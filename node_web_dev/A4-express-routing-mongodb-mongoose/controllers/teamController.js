const Team = require("../models/teamModel");

// create team controller object to add functions to later
const teamController = {};

// list all of the users teams and show them via the teams template
teamController.list = function (req, res, next) {
    // query for all the teams which the current user owns
    Team.find({'ownerId': req.user._id})
        .then((teams) => {
            res.render('teams/teams', {teams: teams});
        })
        .catch((err) => {
            next(err);
        });
};

// create and save a new team
teamController.create = function (req, res, next) {
    // create new team from form/req.body and add path for default logo
    const team = new Team({
        teamName: req.body.teamName,
        teamDescription: req.body.teamDescription,
        teamLogo: '/images/thumbnail.jpg',
        organizationName: req.body.organizationName,
        billingEmail: req.body.billingEmail,
        ownerId: req.user._id,
        ownerUsername: req.user.username
    });

    // save new team and redirect user to teams page, or throw error
    team.save()
        .then(() => {
            res.redirect('/teams');
        })
        .catch((err) => {
            next(err);
        });
};

// read/view one team, by item id
teamController.read = function (req, res, next) {
    // query for a team that matches the id and that the user owns
    Team.findOne({'_id': req.params.teamId, 'ownerId': req.user._id.toString()})
        .then((team) => {
            res.render('teams/team', {team: team});
        })
        .catch((err) => {
            next(err);
        });
};

// update a team, by id
teamController.update = function (req, res, next) {
    // add the new logo location only if a file was included in the req
    req.file ? req.body.teamLogo = req.file.location : null;

    // query and update a team whose id matches and that is owned by the user
    Team.findOneAndUpdate({'_id': req.params.teamId, 'ownerId': req.user._id}, req.body)
        .then(() => {
            res.redirect('/teams')
        })
        .catch((err) => {
            next(err);
        });
};

// delete a team then redirect the user to the teams page
teamController.delete = function (req, res, next) {
    // query for first (and only) team whose id matches and that the user owns
    Team.findOneAndRemove({'_id': req.params.teamId, 'ownerId': req.user._id})
        .then(() => {
            res.redirect('/teams')
        })
        .catch((err) => {
            next(err);
        });
};

module.exports = teamController;