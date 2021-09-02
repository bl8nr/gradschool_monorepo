const Account = require('../models/account');

class AccountService {

	// create a single account
	static create(accountData, accountPassword) {
		return Account.register(new Account(accountData), accountPassword)
			.then((account) => {
				return account;
			});
	}

	// read a single account by id
	static read(id) {
		return Account.findById(id)
			.then((account) => {
				return account;
			});
	}

	// update a single account by id
	static update(id, accountData) {
		return Account.findById(id)
			.then((account) => {
				account.profile = accountData;
				account.save();
				return account;
			});
	}

	//TODO: delete an account by id
}

// export AccountService
module.exports = AccountService;