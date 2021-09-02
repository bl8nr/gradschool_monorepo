// shipitfile.js
module.exports = shipit => {
	// Load shipit-deploy tasks
	require('shipit-deploy')(shipit);

	const currentPath = `~/replyas/current`;

	shipit.initConfig({
		default: {
			deployTo: '/home/deploy/replyas',
			repositoryUrl: 'https://github.com/HarvardDCENode/assignment-5-rest-apis-brettbl.git',
			ignores: ['.git', 'node_modules', '.env'],
			keepReleases: 2,
		},
		production: {
			servers: 'deploy@staging.replyas.com',
		},
	});

	// this task runs an NPM install remotely to install dependencies
	shipit.blTask("install", function () {
		return shipit.remote(`cd ${currentPath} && npm install`);
	});

	// stop the server while the new code is being deployed
	shipit.blTask("stop_server", function () {
		return shipit.remote(`pm2 stop all && pm2 delete all`);
	});

	// pm2 start the fresh code
	shipit.blTask("start_server", function () {
		return shipit.remote(`cd ${currentPath} && pm2 start npm -- start`);
	});

	// copy the environment var config files from local to the server
	shipit.blTask("install_prod_env", function () {
		return shipit.copyToRemote('prod.env', `${currentPath}/current.env`);
	});

	shipit.on("deployed", function () {
		// after the asset deployment is complete, transfer the env file and start the server
		shipit.start("install", "install_prod_env", "stop_server", "start_server");
	});
};