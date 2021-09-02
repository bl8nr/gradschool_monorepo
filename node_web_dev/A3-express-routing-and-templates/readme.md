# Assignment #3 - ExpressJS, Routing and Templates

The description of this assignment can be found in Canvas at [Assignment #3](https://canvas.harvard.edu/courses/35096/assignments/200749) (Spring 2018)

You should build your application in this repo cloned for you in Github Classroom. You'll submit your project and github URLs in Canvas.  

TODO -  Just another checklist of stuff todo/todone, not an extensive or detailed list:
- [x] Add a standard NodeJS git ignore
- [x] Create a route (/workspaces GET) to list collection of workspaces
- [x] Create a route (/workspaces POST) to add workspace item to collection of workspaces
- [x] Create a route (/workspaces PUT) to update the entire collection in bulk - Give it a 501 for now
- [x] Create a route (/workspaces DELETE) to delete the entire collection in bulk - Give it a 501 for now
- [x] Create a route (/workspaces/:id GET)to view a single log item
- [x] Create a route (/workspaces/:id PUT)to update a single log item - Give it a 501 for now
- [x] Create a route (/workspaces/:id DELETE)to delete a single log item - Give it a 501 for now
- [x] Remove the users route and template
- [x] Remove 'cookie-parser', painful since i know ill need it later. But not for this assignment
- [x] Comment the existing express code so we know im not just leaving code in there because it came generated
- [x] Add bootstrap framework
- [x] Add a seeds json file to load some data into app locals on start
- [x] Add .pug template for the layout/navbar and add relevant buttons/link etc...
- [x] Modify index/home pug temaplte and route to allow it to view all workspaces in a table
- [x] Add .pug viewing a single Workspace, add button to go back to all workspaces
- [x] Add favicon

Assignment 3 Requirements Check List
- [x] A startup file located at bin/www as shown in the course
A package.json that contains:
  - [x] all project dependencies
  - [x] a start script for production (npm start)
  - [x] a start script for development that uses nodemon and the debugger
- [x] .gitignore listing node_modules (you may have other entries in .gitignore as well, but node_modules must be present)
- [x] Directories (folders) in the project to include /views, /routes.   
- [x] Your program must use a template engine to generate its HTML pages - you can use Pug/Jade, EJS, Handlebars, or any other express template you choose.
- [x] You may use Express Generator (described on the last page of week 6) to help get your project started.
- [x] You must clean it up by removing any generated JS or template files that your project does not use.   
- [x] Your code should adhere to the Coding Standards and Style for the course
- [x] Your application will generate a website that contains at least two pages.
- [x] The purpose of the site and how to use it should be clear to a casual user.
- [x] An HTML form on your website with at least two input fields.
- [x] The result of submitting the form should affect the content shown on the home page.
- [x] use app.locals to store data while your application is running
- [x] the logic of collecting and displaying your form data should be implemented with server-side code.
