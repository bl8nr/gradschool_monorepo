import application
from flask import Blueprint, request, flash, redirect, url_for, session, g
from werkzeug.security import generate_password_hash, check_password_hash

# create a blueprint for the auth pages and endpoints
bp = Blueprint("auth", __name__)

# before an app request gets processed, load the user session and data if there is one
@bp.before_app_request
def load_user_from_session():
    id = session.get("user_id")

    # if there is no user...
    if id is None:
        # set user to none, there is no session in existance
        g.user = None;
    else:
        # get the user by session's user_id and store it globally for use around the app
        g.user = application.db.execute("SELECT * FROM users WHERE id=(:id)", {"id": id}).fetchone()

        # finish and close DB connection
        application.db.commit()

# this endpoint allows for user registration, its requested by a form on the index page
@bp.route("/register", methods=["POST"])
def register():

    # get the name and email and password from the requests form values
    # salt and hash the password before we use it for anything
    name = request.form.get("name")
    email = request.form.get("email")
    password = generate_password_hash(request.form.get("password"))

    # if a user already exists under provided email address, send user to index page and display an error
    user = application.db.execute("SELECT * FROM users WHERE email=(:email)", {"email": email}).fetchone()
    if user is not None:
        flash("Failed to create account, email is already in use.")
        return redirect(url_for("index"))

    # if password complexity is not met then return an error

    # save the new user into the database
    application.db.execute("INSERT INTO users (name, email, password) VALUES (:name, :email, :password)",
               {"name": name, "email": email, "password": password})

    # create a new session for then new user
    user = application.db.execute("SELECT * FROM users WHERE email=(:email)", {"email": email}).fetchone()
    session.clear()
    session["user_id"] = user["id"]

    # finish and close DB connection
    application.db.commit()

    # send the authenticated new user to the main books page
    return redirect(url_for("books.books"))

# this endpoint allows for user login (session creation), its requested by a form on the index page
@bp.route("/login", methods=["POST"])
def login():

    # get the email and password from the requests form values
    formEmail = request.form.get("email")
    formPassword = request.form.get("password")

    # get the user by email address from the database
    user = application.db.execute("SELECT * FROM users WHERE email=(:email)", {"email": formEmail}).fetchone()

    # finish and close DB connection
    application.db.commit()

    # if the user is not found or the password is incorrect, refresh page and flash error
    if user is None or check_password_hash(user.password, formPassword) is False:
        flash("Login Failed: Invalid username or password.")
        return redirect(url_for("index"))

    # clear the session and store the user id in the session for later (creating a session)
    session.clear()
    session["user_id"] = user["id"]

    # send the authenticated user to the main books page
    return redirect(url_for("books.books"))

# this endpoint allows for user logout (session destruction), its requested by a button in the nav bar
@bp.route("/logout", methods=["GET"])
def logout():

    # clear out the user's session
    session.clear()

    # send the user back to the home index page
    return redirect(url_for("index"))
