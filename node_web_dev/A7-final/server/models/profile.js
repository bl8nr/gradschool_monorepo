/* Good to go for final */
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

/**
 * import Subdocument schemas
 */
const postSchema = require('./post');

/**
 * create Profile schema
 * a profile represents a user account connected via OAuth,
 * is contains OAuth token data, other obscure profile/account
 * data from reddit and it's the parent to Post items
 */
const schema = new Schema({
	authorization: {
		originalState: { type: String, required: true },
		originalCode: { type: String, required: true },
		accessToken: { type: String, required: true },
		tokenType: { type: String, required: true },
		expiresIn: { type: String, required: true },
		issuedAt: { type: String, required: true },
		scope: { type: String, required: true },
		refreshToken: { type: String, required: true}
	},
	accountInfo: { type: {} , required: true },
	name: { type: String, required: false },
	notes: { type: String, required: false },
	ownerId: { type: String, required: true },
	members: { type: [String], required: true },
  posts: [ postSchema ],
	createdAt: { type: Date },
	updatedAt: { type: Date }
});

// set-reset the createdAt or updatedAt value
schema.pre('save', function(next){
	if (!this.createdAt) {
		this.createdAt = new Date();
	} else {
		this.updatedAt = new Date();
	}
	next();
});

module.exports = mongoose.model('Profile', schema);