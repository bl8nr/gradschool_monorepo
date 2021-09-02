/* Good to go for final */
const Profile = require('../models/profile');

class ProfileService {

  /**
   * return Profiles[] by ownerId
   */
	static list(loggedInUserId) {
		return Profile.find({ ownerId: loggedInUserId })
			.then((profiles) => {
				return profiles;
			})
	}

  /**
   * return one Profile by ID
   */
	static read(profileId) {
		return Profile.findById(profileId)
			.then((profile) => {
				return profile;
			})
	}

  /**
   * create a Profile then return that Profile
   */
	static create(userId, authData, accountInfo) {
		const profileObject = {
			authorization: {
				originalState: authData.originalState,
				originalCode: authData.originalCode,
				accessToken: authData.accessToken,
				tokenType: authData.tokenType,
				expiresIn: authData.expiresIn,
				issuedAt: new Date().getTime() / 1000,
				scope: authData.scope,
				refreshToken: authData.refreshToken
			},
			accountInfo: accountInfo,
			ownerId: userId
		};

		const profile = new Profile(profileObject);
		return profile.save();
	}

  /**
   * delete a Profile then return the deleted Profile
   */
	static delete(profileId) {
		return Profile.findByIdAndRemove(profileId)
			.then((deletedProfile) => {
				return deletedProfile;
			});
	}
}

module.exports = ProfileService;