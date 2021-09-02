const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// create new Schema for member item
const schema = new Schema({
	userId: { type: String, required: true },
	displayFirstName: { type: String, required: false },
	displayLastName: { type: String, required: false },
	jobTitle: { type: String, required: false },
	signature: { type: String, required: false },
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
module.exports = schema;