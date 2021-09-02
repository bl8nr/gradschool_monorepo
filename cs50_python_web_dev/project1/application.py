import os
from flask import Flask, render_template, redirect, url_for, g
from flask_session import Session
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
import auth
import api
import books

app = Flask(__name__)

# Check for environment variable
if not os.getenv("DATABASE_URL"):
    raise RuntimeError("DATABASE_URL is not set")

# Configure session storage to use filesystem
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Set up database connection
engine = create_engine(os.getenv("DATABASE_URL"))
db = scoped_session(sessionmaker(bind=engine))

# this route renders the home page which contains login and register forms
@app.route("/")
def index():

    # if there is a user session, ie the user is logged in, send them to the books page
    if g.user is not None:
        return redirect(url_for("books.books"))

    # render the index home page
    return render_template('index.html')

# register the blueprints for the auth, books, and api routes/pages
app.register_blueprint(auth.bp)
app.register_blueprint(books.bp)
app.register_blueprint(api.bp)

