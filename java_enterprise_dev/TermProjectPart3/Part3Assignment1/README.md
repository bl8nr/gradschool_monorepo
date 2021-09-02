#Readme Checklist
#### Term Project Part 3 Assignment 1

#### Data Requirements
   -[x] App must use MySql
   -[x] App must contain test-data.sql file
        -[x] test-data.sql must contain at least 12 books
   -[x] Book Data Model must looks like below
        -[x] ID INT(11)
        -[x] CATEGORY VARCHAR(100)
        -[x] ISBN VARCHAR(10)
        -[x] TITLE VARCHAR(200)
        -[x] PUBLISHER VARCHAR(100)
        -[x] PRICE DECIMAL(4,2)
#### Book Application Directory Structure
   -[x] Must match whats reflected in the book for Java Config
   -[x] webapp directory contains
        -[x] WEB-INF contains
            -[x] i18n
            -[x] layouts
            -[x] views
        -[x] styles contains
            -[x] standard.css
            -[x] custom theme jquery UI stuff
            -[x] bootstrap
        -[x] scripts contains
            -[x] jQuery
            -[x] jQueryUI
        -[x] jpgrid
        -[x] images
   -[x] resources contains
        -[x] standard.properties
        -[x] sql contains
            -[x] schema.sql
            -[x] test-data.sql
   -[x] Config contains
        -[x] Web config
        -[x] Data config
   -[x] Entities contains
        -[x] Book entity
   -[x] Init contains
        -[x] Web initializer
   -[x] repository contains
        -[x] BookRepository
   -[x] service contains
        -[x] BookService interface
        -[x] BookService implementation
   -[x] util contains
        -[x] BookGrid utility
        -[x] Message utility
        -[x] UrlUtil utility
   -[x] web contains
        -[x] BookController

#### Book Application Domain Model
   -[x] domain model must contain one class
        -[x] of Book
        -[x] which corresponds to the Book table

#### Book Application Service Layer and Repository
   -[x] One interface, BookService.java
   -[x] it's implementation, BookServiceImpl.java
   -[x] BookService declares methods which allow
        -[x] find all books
        -[x] find a book by its ID
        -[x] save a book
        -[x] delete by id
   -[x] BookServiceImpl implements all methods declared in BookService interface
   -[x] implementation should handle pagination and sorting
   -[x] methods above should support transactional access to them 
   
#### Configuring the Book Application
   -[x] Configure the application using Java configuration
   -[x] Configure it in a similar fashion as to whats in the textbook
   
#### Creating the Basic View of the Book Application
   -[x] should display all books created by the test data script
   -[x] book list view should live in WEB-INF/views/books/list.jspx
   
#### Enabling Internationalization
   -[x] Select 2 other languages to translate app into
        -[x] French
        -[x] Spanish
   -[x] externalize application UI text into properties files
        -[x] located in /WEB-INF/i18n
        -[x] for french
        -[x] for spanish
        -[x] for english (default)
   -[x] externalize messages UI text into properties files
        -[x] located in /WEB-INF/i18n
        -[x] for french
        -[x] for spanish
        -[x] for english (default)
   -[x] add i18n configuration to the java config
   -[x] activate i18n on a "lang" url param
   -[x] Add tags to view to load the i18n messages
   -[x] Change all the page titles and labels to use i18n
   -[x] Change all the messages to use i18n
   -[x] add footer links for Spanish and French that link home with "lang" param
   
#### Theming and Templating
   -[x] implement theming according to the textbook
   -[x] but only implement one theme
   
#### Implementing the Show Book View
   -[x] implement controller show() method
   -[x] implement show view (/views/books/show.jspx)
   -[x] modify view definition file (/views/books/views.xml)
   -[x] implement edit book link linking to the edit book route

#### Implementing the Edit Book View
   -[x] implement controller updateForm() method
   -[x] implement controller update() method
   -[x] implement edit book view (/views/books/edit.jspx)
   -[x] modify the view definition file (/views/books/views.xml)
   
#### Implementing the Add Book View
   -[x] implement controller createForm() method
   -[x] implement controller create() method
   -[x] use the edit book view (/views/books/edit/jspx)
   -[x] modify the view definition file (/views/books/views.xml)
   
#### Enabling JSR-349 Bean Validation
   -[x] price should not be null
   -[x] isbn should not be empty
   -[x] title should not be empty
   -[x] category should not be empty
   -[x] add other validations to match the ER diagram
        -[x] category must not be larger than 100 char
        -[x] isbn must not be larger than 10 char
        -[x] title must not be larger than 200 char
        -[x] publisher must not be larger than 100 char
   
#### Enable Pagination on the Server Side
   -[x] Add spring data repo pagination support
   -[x] Add method to BookService ( Page<Book> findAllByPage(Pageable pageable) )
   -[x] implement listGrid() in the book controller class
   -[x] data grid should sort by
        -[x] Category
        -[x] Title
        -[x] Publisher
        -[x] Price
        -[x] ISBN
   -[x] data grid should be paginated