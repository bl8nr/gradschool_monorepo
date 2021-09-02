# final-project - CryptoTracker

# Description
The purpose of this project is to give the user an easy way to view simple metrics on todays most popular cryptocurrencies. The user can also create a watchlist to keep track of their favorite crypto and share crypto pages with friends via an email message to them via the web app.

# Notes for Graders
- i used Python, Django, JS and the SQLite database. I didnt make as much of a use of SQLite as I would have liked to since
it turns out i only really needed one custom model beyond Djangos user model.
- Much of the JS is in the coin.html file and controls the charting for the coin metrics
- You can create you own user account or use the test account below
    - username: test
    - password: Testing123!
- the share/email form works but sometimes sendgrid takes a while to send the emails so it may take 10+ seconds for it to send the button to change. Ideally i would use some sort of queuing system for this.
- sharing via email should work (it worked for me in testing) but i know there are a lot of variables when it comes to email, like spam filters, so if you dont see a message in your inbox it may in your spam
- Sharing a coin page and adding a coin to your watchlist are located as buttons in the top right of any cryptocurrency coin page
- ive already added bitcoin and ethereum to the testuser watchlist
- the chart mode can be changed using the selector below the chart (price, volume, market cap)
- there's a dependency for this project (pycoingecko, the crypto API) please install it via requirements.txt before running!
- i used a variant of the Bootstrap framework for much of the html styling, the theme itself has actually been deprecated recently but i originally got it at the link below
    - https://themes.getbootstrap.com/

## Sources & References
below are sources I used to help implement some of the design patterns in my app (similar to many of the patterns in my pizza app)...
- Using UUID as primary key - https://books.agiliq.com/projects/django-orm-cookbook/en/latest/uuid.html
- Django Authentication Signup - https://wsvincent.com/django-user-authentication-tutorial-signup/
- Django Authentication Login Logout - https://wsvincent.com/django-user-authentication-tutorial-login-and-logout/
- Django Authentication - https://djangosnippets.org/snippets/1357/
- Django Serving Static Files - https://stackoverflow.com/questions/12809416/django-static-files-404
- Bootstrap Theme - https://themes.getbootstrap.com/
- Automatically authenticating user - https://teamtreehouse.com/community/django-authentication-login-automatically-after-signup

# File Descriptions
- cryptotracker
    - accounts
        - templates
            - signup.html: the template page for the user signup
        - urls.py: contains the routes for all of the accounts pages (login, logout, signup)
        - views.py: contains the logic for all of the accounts pages additional to the Django logic (login, logout, signup)
    - cryptotracker
        - settings.py: contains the project settings (auth and sendgrid settings)
        - urls.py: contains the top level project routing (routes to /account app and /tracker app)
    - static
        - tracker
            - css
                - application.css: bootstrap theme framework css file
                - styles.css: custom css file
                - toolkit-light.css: another bootstrap theme framework css file
            - fonts
                - toolkit-entypo.eot: bootstrap theme framework font file
                - toolkit-entypo.ttf: bootstrap theme framework font file
                - toolkit-entypo.woff: bootstrap theme framework font file
                - toolkit-entypo.woff2: bootstrap theme framework font file
            - img: folder for statically hosted images (empty right now)
            - js
                - application.js: bootstrap theme framework javascript file
                - chart.js: bootstrap theme framework javascript charting file
                - jquery.min.js: bootstrap theme framework jquery framework minified file
                - tablesorter.min.js: bootstrap theme framework javascript file for table sorting
                - tether.min.js: bootstrap theme framework javascript dependency file
                - toolkit.js: bootstrap theme framework javascript file
    - templates
        - registration
            - login.html: the template page for the user login
        - layout.html: the top level template that structures the site layout and includes the <head> tag and body block
    - tracker
        - templates
            - coin.html: the template for the page where you view the metrics for a coin
            - tracker.html: the template for the page where you view the coin master list
        - admin.py: configuration for the admin UI (where Watchedcoin is added to admin)
        - models.py: the file that specs out the objects in the DB (where Watchedcoin is specd out)
        - urls.py: contains all the router configs for the /tracker based routes (watchlist, coin, chart, share)
        - views.py: contains all the controller logic for the /tracker based views (watchlist, coin, chart, share)
    - requirements.txt: contains added dependencies for the project
- README.md: this file

Thanks for a great class!!! :D
