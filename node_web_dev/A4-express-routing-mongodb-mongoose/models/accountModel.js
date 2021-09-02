const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const passportLocalMongoose = require('passport-local-mongoose');

// create a schema for the accounts/users
const Account = new Schema({
  email: String,
  username: String,
  password: String
});

// add passportLocalMongoose plugin to have passport local use the Account index
Account.plugin(passportLocalMongoose, { usernameLowerCase: true });

// export module with associated name and schema
module.exports = mongoose.model('Account', Account);
