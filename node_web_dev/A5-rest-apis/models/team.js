const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// import member schema since its a subdoc of team
const memberSchema = require('./member');

// create new Schema for team item
const schema = new Schema({
	teamName: { type: String, required: true },
	organizationName: { type: String },
	adminEmail: { type: String, required: true },
	ownerId: { type: String, required: true },
	teamDescription: { type: String, required: false },
	members: [ memberSchema ],
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

// export module with associated name and schema
module.exports = mongoose.model('Team', schema);