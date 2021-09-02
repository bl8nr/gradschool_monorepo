# Project 2
#### Web Programming with Python and JavaScript
#### Brett Bloethner

### Project Description
This project is a chat application that allows users to have a display name and join and create chat channels. Users can chat in each individual chanel with other users also in that same channel. Users can also change their display name in real time.

### File LIst and Descriptions
- application.py
    - Flask application entry point. This is the only server python file and contains all the server app logic
- README.md
    - Contains project description, checklist and other project information
- requirements.txt
    - Describes the dependencies required by this project
- static/all.css
    - File generated by flask-assets that includes all the SCSS files (currently only one right now though) processed into one CSS file
- static/packed.js
    - File generated by flask-assets that includes all the JS files processed into one JS file
- static/js/chat.js
    - JS file that includes the main client side application logic
- static/js/helpers.js
    - JS file that includes all of the Handlebar helpers created for this project
- static/js/selectors.js
    - JS file that includes all of the JQuery selectors used to target HTML dom elements in this project

### Notes For Grading
For my last project I tried to make it in a more scaleable method, separating different categories of logic into different files based on the objects used. For this project I wanted to try to make the project code as concise as possible while still fully functional with a personal touch feature. After many refactors and design changes, I was pretty happy to see I achieved all the project requirements with less than 300 lines of main JS app code and less than 200 lines of python server code. I built this as a single page app using handlebars. To create a new channel, click the "New Channel" button in the left sidebar.

### Personal Touch
My personal touch for this app was giving the user the ability to change their display name and have that new name reflected in all clients in real time. This is a relatively simple feature to the user but called for a more advanced design (more use of UIDs) than what the apps core functionality required. To change your display name, click the "Change Display Name" link in the top right corner of the app.

### Sources
- Bootstrap Docs for general styling and CSS class usage
    - https://getbootstrap.com/docs/4.0/getting-started/introduction/
- Modals adapted from the Bootstrap modal documentation
    - https://getbootstrap.com/docs/4.0/components/modal/
- Overall layout, navbar and sidebar design adapted from the Bootstrap dashboard example
    - https://getbootstrap.com/docs/4.0/examples/
    - https://getbootstrap.com/docs/4.0/examples/dashboard/
- Used this to help me with form validation
    - https://stackoverflow.com/questions/16707743/html5-validation-when-the-input-type-is-not-submit
- Flask catch all domain redirect
    - https://amir.rachum.com/blog/2016/08/27/flask-redirect/
- Removing styles form text areas
    -  https://stackoverflow.com/questions/17109702/remove-all-stylings-border-glow-from-textarea

### Project Checklist
#### Display Name
- [x] When a user visits the web app for the first time, they should be prompted to type in a display name
- [x] THe users display name should be associated with every message the user sends
- [x] If a user closes the page and returns to the app later, the display name should still be remembered
#### Channel Creation
- [x] Any user should be able to create a new channel
- [x] Names of new channels must not conflict with names of existing channels
#### Channel List
- [x] Users should be able to see a list of all current channels
- [x] Users should be able to select one and the user can then view the channel
#### Messages View
- [x] Once a channel is selected, the user should see any messages that have already been sent in that channel
- [x] The user should only see up to a maximum of 100 messages
- [x] The app should only store the 100 most recent messages per channel in server-side memory
#### Sending Messages
- [x] Once in a channel, users should be able to send text messages to others the channel
- [x] When a user sends a message, their display name and the timestamp of the message should be associated with the message
- [x] All users in a channel should see new messages (with display name and timestamp) appear on their channel page
- [x] Sending and receiving messages should NOT require reloading the page
#### Personal Touch
- [x] Add at least one additional feature to your chat application of your choosing
    - [x] Users have to ability to change their display name in real time and the name updates in everyones app/messages
#### Readme
- [x] Short description
- [x] File list and file descriptions
- [x] Personal touch description
- [x] Add all python packages to requirements.txt