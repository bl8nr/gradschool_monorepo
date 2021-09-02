const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const passportLocalMongoose = require('passport-local-mongoose');

// create new schema for account item
const Account = new Schema({
	username: { type: String, required: true },
	profile: {
		firstName: { type: String, required: true },
		lastName: { type: String, required : true },
		displayName: { type: String, required: true },
		email: { type: String, required: true },
		bio: { type: String, required: false },
		company: { type: String, required: false },
		profilePicture: { type: String, required: false }
	},
	password: String,
});

// add passport local plugin to account model
Account.plugin(passportLocalMongoose);

// export account schema
module.exports = mongoose.model('Account', Account);