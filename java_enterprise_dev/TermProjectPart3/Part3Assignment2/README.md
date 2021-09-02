#Readme Checklist
#### Term Project Part 3 Assignment 2

-[x] Project extends Term Project Part 3 Assignment 1
    -[x] Data Requirements
    -[x] Book Application Directory Structure
    -[x] Book Application Domain Model
    -[x] Book Application Service Layer and Repository 
    -[x] Configuring the Book Application
    -[x] Creating the Basic View of the Book Application
    -[x] Theming and Templating
    -[x] Implementing the Show Book View
    -[x] Implementing the Edit Book View
    -[x] Implementing the Add Book View
    -[x] Enabling JSR-349 Bean Validation
    -[x] Enable Pagination on the Server Side
  
#### Assignment 2 Additions
-[x] Implement auth according to the textbooks Java config example
    -[x] Create a security config java config
    -[x] Add security config to the webinitializer root config classes
    -[x] Create security app initializer
-[x] Add login functionality to the Book Application
    -[x] Modify header.jspx to
        -[x] detect that user is logged in and display username and logout link
    -[x] Modify menu.jspx to
        -[x] display new book link only if a user is logged in (has the role ROLE_USER granted)
    -[x] If failed login then login form is redisplayed with the "Login failed, please try again" message
    -[x] Login fail URL is /security/loginfail
    -[x] Implement a SecurityController class
        -[x] handles all URLs prefixed with "security"
        -[x] contains loginFail() method that puts the login failed message in and redirects to home
    -[x] Modify show.jspx so that the edit book button doesnt show if not logged in 
-[x] a non-authenticated user should not be able to access any of the "books?form" pages for book creation/editing/ or deletion
        
