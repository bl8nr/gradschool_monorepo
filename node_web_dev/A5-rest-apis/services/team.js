const Team = require('../models/team');

class TeamService {

	// return all teams that the session user is a member of
	static list(userId) {
		return Team.find({ 'members': { $elemMatch: { 'userId': userId } } })
			.then((teams) => {
				return teams;
			});
	}

	// read a single team
	static read(id) {
		return Team.findById(id)
			.then((team) => {
				return team;
			});
	}

	// create a single team
	static create(obj) {
		const team = new Team(obj);

		// create member on behalf of the owner/team creator
		const member = {
			userId: team.ownerId,
			displayFirstName: '',
			displayLastName: '',
			jobTitle: 'Team Administrator',
			signature: ''
		};

		// add the team creator as a member to the team
		team.members.push(member);
		return team.save();
	}

	// update a single team
	static update(id, data) {
		return Team.findById(id)
			.then((team) => {
				team.set(data);
				team.save();
				return team;
			});
	}

	// delete a single team
	static delete(id) {
		return Team.remove({_id: id})
			.then((deletedTeam) => {
				return deletedTeam;
			});
	}

}

// export TeamService
module.exports = TeamService;