# Project 1

Web Programming with Python and JavaScript

## Description
This is a project built to match the project 1 specs for CS-50s Web Programming with Python and Javascript. The file contents are outline below...
- api.py: this file is the controller for all routes starting in /api
- application.py: this file is the entrypoint file and starts the web server, connects to the db and imports the other files/blueprints
- auth.py: this file is responsible for all of the user authentication and endpoints /login, /register and /logout as well as session management
- books.csv: holds a data set of 5000 books to be imported into the database
- create.sql: consists of the psql commands required to execute in order to build a database suitable for this app
- import.py: is a small python application that will take the book data from book.csv and import it into the database books table
- README.md: is this file, a readme file
- requirements.txt: lists the project python dependencies
- templates/layout.html: is the base jinja template which all other templates are built into
- templates/index.html: is the template for the home/index page that houses the register and signup forms
- templates/books/books.html: is the template for the book search and book search results page
- templates/books/book.html: is the template for the single book info display and review form page

## Getting Started
1. Pull the repository
2. Install the project requirements using `pip3 install -r requirements.txt`
3. Set the flask environment variable using `export FLASK_APP=application.py`
4. Set the database url environment variable using `export DATABASE_URL= ***your db url***`
5. Set the GoodReads API key using `export GOODREADS_API_KEY=b4Zqt6eie06KLu9RBj7Q`
6. to debug, set the flask debug environment variable `export FLASK_DEBUG=1`
7. Run the create.sql sql commands to create the necessary tables and index
8. Run `python3 import.py` to import the csv list books.csv into the books table
9. Run `flask run` to run the application

## Database Design
##### Users Table Column List
- id SERIAL PRIMARY KEY
- name VARCHAR(255) NOT NULL
- email VARCHAR(255) UNIQUE NOT NULL
- password VARCHAR NOT NULL

##### Books Table Column List
- id SERIAL PRIMARY KEY
- isbn VARCHAR(13) UNIQUE NOT NULL
- title VARCHAR
- author VARCHAR
- year INTEGER
- NOTE: An index is added to books(isbn)
    
##### Reviews Table Columns List
- id SERIAL as a PRIMARY KEY,
- user_id INTEGER REFERENCES users(id),
- book_isbn VARCHAR(13) REFERENCES books(isbn),
- review VARCHAR(5000) NOT NULL

## Sources
- Flash messaging design from flaskr tutorial (https://github.com/pallets/flask/tree/master/examples/tutorial/flaskr)
- Password hashing and salting design from flaskr tutorial with Werkzeug (https://github.com/pallets/flask/tree/master/examples/tutorial/flaskr)
- User session pre-loading design from flaskr tutorial using flask annotations (https://github.com/pallets/flask/tree/master/examples/tutorial/flaskr)
- File structure using blueprints roughly adapted from flaskr tutorial (https://github.com/pallets/flask/tree/master/examples/tutorial/flaskr)
- password validation (https://www.w3schools.com/howto/howto_js_password_validation.asp)
- standard flask/python gitignore file from (https://www.gitignore.io/api/python)

## Additional Information
I went a little out of my way to try and optimize the way the app was built and even add a few more features. I've bulleted these items below...
- Added router guards on the book pages that way logged our users cannot access them
- Added router guard on the index page that way logged in users are redirected to the books search
- Added a full review list to the API response that was consumers could get the review content instead of only the count
    - this required that I fetch the entire review results rather than using the COUNT sql command
- Password hashing and password salting
- Adherence to rest principals with the routes and html forms and methods (GET, POST, etc...)
- Implementation of flask blueprints to split the controllers into separate files
- Small javascript snippet used for toggling the signup and register form
- I wasn't sure how to use the database connection across files in the application so i attached to the application object, this doesnt seem like to optimal way to accomplish this


## Assignment Checklist
#### Registration
- [x] Users should be able to register for your website
- [x] Registration should require a password
- [x] Registration should require a username (email address)
- [x] Registration should require a name
#### Login
- [x] Users should be able to login to the website using their username and password
#### Logout
- [x] Logged in users should be able to logout
#### Import
- [x] Write a program that imports books from books.csv into the Postgres database
- [x] The program should be in a file titled import.py
- [x] The program should run using 'python3 import.py'
- [x] The program should ignore the headers in books.csv
#### Search
- [x] Users should be taken to a search page after logging in
- [x] Users should be able to search for books via ISBN, Author or Title
- [x] Search should return matches and possible matches
- [x] Search should return a message if there are no matches
- [x] Non-exact search querys should also return matches
#### Book Page
- [x] Users should be able to click on a book on the search page and be taken to a book page
- [x] The book page should show the books details (title, author, publication year, isbn)
- [x] The book page should also show any reviews users have left for the book
#### Review Submission
- [x] On the book page, users should be able to submit reviews
- [x] Users should be able to submit reviews via text page
- [x] Users should not be able to leave a review for a book the have already reviewed
#### Goodreads Review Data
- [x] On the book page, it should show Goodreads API data
- [x] It should show the the average rating and the number of ratings
#### API Access
- [x] A user should be able to make a GET request to /api/<isbn> to get a books info
- [x] Info should include title, author, year, isbn and the sites review count
- [x] The API response should be in the following format 
{
    "title": "Memory",
    "author": "Doug Lloyd",
    "year": 2015,
    "isbn": "1632168146",
    "review_count": 28
}
- [x] If the ISBN isnt in the database, it should return a 404 error
#### Other Requirements
- [x] Only raw SQL commands should be used via SQLAlchemy’s execute method
- [x] SQLAlchemy ORM should not be used
- [x] README.md should include a short write that...
    - [x] describes the project
    - [x] describes whats contained in each file
    - [x] a list of all of the tables in your database and what column names (and data types) are in each column
    - [x] any other additional information the staff should know about your project
- [x] Any python packages added to the project must be added to requirements.txt
