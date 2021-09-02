const express = require('express');
const teams = require('../controllers/teamController');
const utilities = require('../utilities/utilities');
const router = express.Router();

// read a list of teams
router.get('/', teams.list);

// create a single team
router.get('/new', function(req, res, next) {
  res.render('teams/newTeam');
});
router.post('/create', teams.create);

// read a single team
router.get('/:teamId', teams.read);

// update a single team, use middleware to save image to s3 if image exists
router.post('/update/:teamId', utilities.uploadImage.single('image'), teams.update);

// delete a single team
router.get('/delete/:teamId', teams.delete);

module.exports = router;
