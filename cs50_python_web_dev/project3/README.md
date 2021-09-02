# Project 3 - Brett Bloethner
Web Programming with Python and JavaScript

## Project Description
The purpose of this project is to be a recreation of the Boston pizza shop website https://www.pinocchiospizza.net/. The website includes a modest ordering system where people can order items off the sites menu.

## File Descriptions
- I added file descriptions for each file in my project that was mutated from the original
- accounts
    - templates
        - signup.html: custom template for the signup page
    - urls.py: spec out auth URL routes and link signup route to custom view
    - views.py: core logic for the Accounts app for where Django Auth didnt do the job required of the app
- orders
    - templates
        - index.html: home page template of pizza banner image
        - menu.html: menu page template that displays MenuItem cards for a given MenuCategory
        - ticket.html: template that displays the contents of a single Ticket/order or the Shopping Cart
        - tickets.html: template that displays all of the current users submitted Tickets
    - templatetags
        - get_range.py: simple template tag allowing a template for loop to repeat x (int) number of times
    - admin.py: configures the django admin UI for the Orders app
    - models.py: specs out the database model designs for the orders app
    - url.py: create URL routes and associate them to methods in the view.py file
    - views.py: core logic for the Orders app
- pizza
    - settings.py: configure settings for the entire project and its apps
    - urls.py: map out the base endpoints for /, /admin and /accounts
- static
    - orders
        - pizza_hero_image.jpeg: image used on the home/index page for the orders app
        - styles.css: css styles file for the app
- templates
    - registration
        - login.html: template for the login page, placed here to satisfy Django auth template default location expectation
    - layout.html: template that houses the html head as well as the site header and navbar, placed here to allow access by Orders and Accounts app both 
- db.sqlite3: database storage file
- README.md: this file

## Notes for Grading
- Admin account
    - username: admin
    - password: n0t@g0oDp4s$w0rd
- User account
    - username: user
    - password: n0t@g0oDp4s$w0rd
- Tickets are used to store customer orders where a Ticket can be complete or incomplete as well as submitted or not submitted. A not submitted Ticket is basically a shopping cart. I configured the Django admin UI to split the Tickets into "Tickets" and "Shopping Carts" based on the isSubmitted value of the Ticket instance
- Adding items to the menu is done by adding a Menu Item to the database and assigning it to a Menu Category
- Adding toppings or customizations is done by adding a Menu Item Modifier to the database and adding that Menu Item Modifier to each appropriate Menu Items Supported Modifiers
- I think my app design has a few weaknesses that I would reconsider redesigning if I were to do it again...
    - Make a shopping cart a different database table that way I dont have to make a new Ticket for a new shopping cart each time a Shopping Cart is "checked out"
    - Rethink the way Ticket subtotals are calculated. Right now i'm simply recalculating it wherever appropriate hoping it updates when/where Ticket Order Items are mutated. A middleware pattern where the price is recalculated on all Ticket updates might have been optimal.
    - I would also reconsider the layout design, the card based menu items didnt work out as well as I hoped they would
- Some Menu Items will fail to delete in the Admin UI, this is done on purpose. Menu Items cannot be deleted if they're already used in a Ticket. All Tickets with that Menu Item must be deleted first before deleting the Menu Item.

## Personal Touch
The personal touch for my project is the ability to look at past orders as a customer. To do this, click the Order History button in the top left while logged in. Here you will see a table of your previous orders where you can click an order's details link to see more info about that order. I also added an order complete boolean and the admins can go in and check off an order as 'complete', which the user can also see in order details.

## Sources & References
below are sources I used to help implement some of the design patterns in my app...
- Banner Image - https://raster-static.postmates.com/?url=com.postmates.img.prod.s3.amazonaws.com/150c07cd-a6ff-42a7-aa39-e2d894693970/orig.jpg&quality=90&w=1500&h=900&mode=crop&format=jpg&v=4
- Using UUID as primary key - https://books.agiliq.com/projects/django-orm-cookbook/en/latest/uuid.html
- Django Authentication Signup - https://wsvincent.com/django-user-authentication-tutorial-signup/
- Django Authentication Login Logout - https://wsvincent.com/django-user-authentication-tutorial-login-and-logout/
- Django Authentication - https://djangosnippets.org/snippets/1357/
- Django Serving Static Files - https://stackoverflow.com/questions/12809416/django-static-files-404
- Bootstrap Theme - theme https://getbootstrap.com/docs/4.3/examples/blog
- Adding fields for Django signup form - https://simpleisbetterthancomplex.com/tutorial/2017/02/18/how-to-create-user-sign-up-view.html
- Automatically authenticating user after signup - https://teamtreehouse.com/community/django-authentication-login-automatically-after-signup

## Assignment Checklist
#### Menu
- [x] Application must support all the menu items on pinnochios pizza website
- [x] Models must efficiently reflect all of the possible menu combinations on the site
- [x] Models must be added to the orders/models.py file
- [x] Make necessary migration files
- [x] Apply migrations

#### Adding Items
- [x] Site admins must be able to add menu items via Django Admin
- [x] Site admins must be able to update menu items via Django Admin
- [x] Site admins must be able to delete menu items via Django Admin
- [x] Add all the current menu items to the menu using the Django Admin page

#### Registration, Login, Logout
- [x] Site users should be able to register using
    - [x] Username
    - [x] Password
    - [x] First Name
    - [x] Last Name
    - [x] Email Address
- [x] Customers should then be able to login and log out of the website

#### Shopping Cart
- [x] Once logged in, customers should see the menu
- [x] User should be able to add menu items to their cart (along with toppings adn extras) to their shopping cart
- [x] Contents of the shopping cart should be saved
    - [x] Even if the user closes the window
    - [x] Even if the user logs out then logs in again
    
#### Placing an Order
- [x] Once theres at least oen item in the car, users should be able to place an order
    - [x] User should be asked to confirm the items in the cart
    - [x] User should be asked to confirm the total before placing the order

#### Viewing Orders
- [x] Site admins should have access to a page where they can view any orders already placed
    
#### README.md
- [x] Add a project description
- [x] Add a file list and descriptions for each file
- [x] Add grading notes and project notes
- [x] Add assignment checklist
- [x] Confirm any necessary dependencies are in requirements.txt
