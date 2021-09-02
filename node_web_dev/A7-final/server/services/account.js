/* Good to go for final */
const Account = require('../models/account');

class AccountService {

  /**
   * create one Account
   */
	static create(accountData, accountPassword) {
		return Account.register(new Account(accountData), accountPassword)
			.then((account) => {
				return account;
			});
	}

  /**
   * read one Account by ID
   */
	static read(id) {
		return Account.findById(id)
			.then((account) => {
				return account;
			});
	}

  /**
   * updated one Account by ID
   */
	static update(id, accountData) {
		return Account.findById(id)
			.then((account) => {
				account.profile = accountData;
				account.save();
				return account;
			});
	}

	// TODO: delete an account by id
}

module.exports = AccountService;