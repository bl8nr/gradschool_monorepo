const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// create new Schema for team item
const schema = new Schema({
  teamName: { type: String, required: true },
  organizationName: { type: String, required: true },
  billingEmail: { type: String, required: true },
  ownerId: { type: String, required: true },
  ownerUsername: { type: String, required: true },
  teamDescription: { type: String, required: false },
  teamLogo: { type: String, required: false },
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
