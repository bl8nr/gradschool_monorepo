
export class User {

    // create private model params
    private _id: String;
    private _username: String;

    // create public model params
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

export interface UserProfile {
    firstName: String;
    lastName: String;
    displayName: String;
    email: String;
    bio: String;
    company: String;
    profilePicture: String;
}
