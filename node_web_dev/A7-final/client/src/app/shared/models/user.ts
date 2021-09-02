/**
 * User class/model/type
 */
export class User {
    private _id: String;
    private _username: String;

    profile: UserProfile;

    constructor(userData) {
        this._id = userData._id;
        this._username = userData.username;
        this.profile = userData.profile;
    }

    get id(): String {
        return this._id;
    }

    get username(): String {
        return this._username;
    }

}

/**
 * user profile type/model
 */
export interface UserProfile {
    firstName: String;
    lastName: String;
    displayName: String;
    email: String;
    bio: String;
    company: String;
    profilePicture: String;
}
