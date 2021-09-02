/* Good to go for final */
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

/**
 * create Post Subdoc schema
 * a post represents a scheduled reddit post that may or may not
 * have already been posted via an API chron job. A post is a sub
 * document of a 'profile' item/schema
 */
const schema = new Schema({
  body: { type: String, required: false },
  link: { type: String, required: false },
  status: { type: String, required: true },
  subreddit: { type: String, required: true },
  targetPostTimeReadable: { type: String, required: true },
  targetPostTimeUTC: { type: String, required: true },
  title: { type: String, required: true },
  type: { type: String, required: true },
  createdBy: { type: String, required: true },
  profileId: { type: String, required: true},
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

module.exports = schema;