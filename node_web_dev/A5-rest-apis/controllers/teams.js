const teamService = require('../services/team');

// create the teams controller object
const teamsController = {};

// get a list of teams the session has access to
teamsController.list = function(req, res, next) {
	teamService.list(req.user.id)
		.then((teams) => {
			res.status(200);
			res.send(JSON.stringify(teams));
		})
		.catch((err) => {
			res.status(err.status || 500);
			res.json(`Error - ${err.message}`);
		});
};

// get one team by id, return team or 404
// TODO: Protect this route by session/user id
teamsController.read = function(req, res, next) {
	teamService.read(req.params.teamId)
		.then((team) => {
			// send 200 and team if team exists else send a 404
			res.status(team ? 200 : 404);
			res.send(JSON.stringify(team));
		})
		.catch((err) => {
			res.status(err.status || 500);
			res.json(`Error - ${err.message}`);
		});
};

// create one team, return the team
teamsController.create = function(req, res, next) {
	const teamData = {
		teamName: req.body.teamName,
		organizationName: req.body.organizationName,
		adminEmail: req.body.adminEmail,
		ownerId: req.user.id,
		teamDescription: req.body.teamDescription
	};

	teamService.create(teamData)
		.then((team) => {
			res.status(201);
			res.send(JSON.stringify(team));
		})
		.catch((err) => {
			res.status(err.status || 500);
			res.json(`Error - ${err.message}`);
		});
};

// update one team by id, return the team
// TODO: protect this route by session/user id
teamsController.update = function(req, res, next) {
	const teamData = {
		teamName: req.body.teamName,
		organizationName: req.body.organizationName,
		adminEmail: req.body.adminEmail,
		teamDescription: req.body.teamDescription
	};

	teamService.update(req.params.teamId, teamData)
		.then((team) => {
			res.status(200);
			res.send(JSON.stringify(team));
		})
		.catch((err) => {
			if (err.message === "Cannot read property 'set' of null") {
				res.status(404).json('Not Found').end();
			} else {
				res.status(500).json('Internal Server Error').end();
			}
		});
};

// delete one team by id, return the team
// TODO: protect this route by session/user id
teamsController.delete = function(req, res, next) {
	teamService.delete(req.params.teamId)
		.then((team) => {
			res.status(200);
			res.send(JSON.stringify(team));
		})
		.catch((err) => {
			res.status(err.status || 500);
			res.json(`Error - ${err.message}`);
		});
};

// export teamsController fns
module.exports = teamsController;