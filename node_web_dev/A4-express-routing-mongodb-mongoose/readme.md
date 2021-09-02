# Assignment #4 - Express Routing, MongoDB, and Mongoose

The description of this assignment can be found in Canvas at [Assignment #4](https://canvas.harvard.edu/courses/35096/assignments/202461) (Spring 2018)

You should build your application in this repo cloned for you in Github Classroom. You'll submit your project and github URLs in Canvas.

# Assignment 4 Structure Requirements
- [x] A startup file located at bin/www as shown in the course
- [x] A package.json that contains all project dependencies
- [x] A package.json that contains a start script for production (npm start)
- [x] A package.json that contains a start script for development that uses nodemon and the debugger
- [x] .gitignore listing node_modules
- [x] Directories (folders) in the project to include /views, /routes, /models.
- [x] Your routes must be based on route paths as shown in the course examples.
- [x] Your program must use a template engine to generate its dynamic HTML pages
- [x] Clean up express generated code by removing any generated JS or template files that your project does not use.
- [] Your code should adhere to the Coding Standards and Style for the course

# Assignment 4 Functionality Requirements
- [x] Your application should provide an HTML user interface that lets user(s) manage information stored in the database.
- [x] Your site user should be able to list resources, create a new resource, update existing ones, or delete them.
- [x] Your application must use MongoDB (Atlas is recommended) to store data.
- [x] You must use mongoose, with at least one Schema.
- [x] It must use at least one HTML form
- [x] the logic of collecting and displaying your form data should be implemented with server-side code
- [x] application should have a clear, easy-to-use interface.


# Project Info
The purpose of this project right now is to be able to create a user account and teams. Right now...
- A user can register/signup
- A registered user can login
- A registered user can logout
- A user can create a team
- A user can view a list of teams they've created
- After a team is created, the creator of that team can change a teams information
- After a team is created, the creator of that team can can change a teams picture/logo which is stored in an S3 bucket
- After a team is created, the creator of that team can delete a team (although is done via a GET route)

# Just some notes on my assignments code...
- I didnt change the bin/www file or anything like that since i wanted it to be like production code and i though everything generated in bin was pretty good
- I got the much of the design pattern between my routes and controller from watching a recording of Mikes section meeting, which puts most of my logic in the controller rather than the routes like Larry had done  in the module
- There are two big Models/items in this app. Accounts for auth, and Teams which is the item built to the assignments spec
- I implemented passport local auth the way it was recommended by passport. In their design the routes werent really RESTful so many auth routes live at base routes like /login, /logout, etc...
- I created a utilities module which acts as an index for other utilities files in the utilities folder (similar to the piazza recommendation), utilities involve uploading an image (middleware) and checking if a user has a session 
- I didnt find much opportunity to comment my templates really since most of it is pretty standard pug/html and is fairly straight forward, however, i tried my best to comment all my other code a lot
- added dotenv and all my secrets are loaded that way
- I also used a superset theme of the bootstrap framework, so that sped up development but to use it i had to add a SCSS transpiler, which you'll find in the app.js file. 
- Much of whats in /public is related to this 3rd party theme.
- Images aren't stored in /public/images like Larry did it. Instead i have multer sending them to a Digital Ocean version of an S3 bucket and node is mutating the stored URL referencing that image accordingly
- Sessions are also stored in mongo, not locally, which probably wont be noticeable from the users perspective but thought id mention it. So the app should be entirely stateless now.  