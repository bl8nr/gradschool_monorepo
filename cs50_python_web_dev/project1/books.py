import os
from flask import Blueprint, render_template, request, flash, redirect, url_for, g
import requests
import application

# create a blueprint for the books pages and endpoints
bp = Blueprint("books", __name__)


# this route supports both the main book search page as well as the search results page
# if a 'searchQuery' query parameter exists, then this page will include search results
# for that query in addition to the search bar
@bp.route("/books", methods=["GET"])
def books():

    # if there is no user session, send the user to the main page to login or register
    if g.user is None:
        return redirect(url_for("index"))

    # get the search query out of the query parameters
    searchQuery = request.args.get('searchQuery')

    # if there is no search query, then render the books index page with no search results
    if searchQuery is None:
        return render_template("books/books.html", user=g.user)

    # find books in database via the search query as part of the books isbn, title or author
    bookResults = application.db.execute(
        "SELECT * FROM books WHERE isbn ILIKE (:searchQuery) OR author ILIKE (:searchQuery) OR title ILIKE (:searchQuery)",
        {"searchQuery": '%' + searchQuery + '%'}).fetchall()

    # finish and close DB connection
    application.db.commit()

    # render the books index page with book search results
    return render_template("books/books.html", user=g.user, bookResults=bookResults, searchQuery=searchQuery)


# this route gets one single book by book isbn and renders the single book info page that
# also includes the books reviews
@bp.route("/books/<book_isbn>", methods=["GET"])
def bookByIsbn(book_isbn):

    # if there is no user session, send the user to the main page to login or register
    if g.user is None:
        return redirect(url_for("index"))

    # by default, the user can leave a review
    usercanleavereview = True

    # fetch the book info by the books isbn
    book = application.db.execute("SELECT * FROM books WHERE isbn=(:isbn)", {"isbn": book_isbn}).fetchone()

    # if the book isbn does not exist, return an error
    if book is None:
        flash("Invalid book id.")
        return redirect(url_for("books.books"))

    # get any reviews for this book by getting all reviews with matching book isbn
    reviews = application.db.execute("SELECT * FROM reviews where book_isbn=(:book_isbn)",
                                     {"book_isbn": book_isbn}).fetchall()

    # finish and close DB connection
    application.db.commit()

    # if any review was authored by the current user, make it so that the user cannot leave another review
    for review in reviews:
        if review.user_id == g.user.id:
            usercanleavereview = False

    # fetch review counts information from goodReads API
    goodReads = requests.get("https://www.goodreads.com/book/review_counts.json",
                             params={"key": os.getenv("GOODREADS_API_KEY"), "isbns": book_isbn}).json()['books'][0]

    # render the single book template, providing info for current user, book, reviews and goodReads
    return render_template("books/book.html", book=book, user=g.user, goodReads=goodReads, reviews=reviews,
                           userCanLeaveReview=usercanleavereview)


# this route allows users to post a new book review
@bp.route("/books/<book_isbn>/reviews/", methods=["POST"])
def reviews(book_isbn):

    # if there is no user session, send the user to the main page to login or register
    if g.user is None:
        return redirect(url_for("index"))

    # get the review content from the req form values
    reviewFromForm = request.form.get("review")

    # fetch book by the books isbn, if book doesnt exist, dont allow user to post a review and return a error
    book = application.db.execute("SELECT * FROM books WHERE isbn=(:isbn)", {"isbn": book_isbn}).fetchone()
    if book is None:
        flash("Invalid book id.")
        return redirect(url_for("books.bookByIsbn", book_isbn=book_isbn))

    # fetch review by book isbn, if user has already left a review, dont allow user to post a review and return a error
    reviews = application.db.execute("SELECT * FROM reviews where book_isbn=(:book_isbn)", {"book_isbn": book_isbn}).fetchall()
    for review in reviews:
        if review.user_id == g.user.id:
            flash("Failed to save review. A review by this user already exists.")
            return redirect(url_for("books.bookByIsbn", book_isbn=book_isbn))

    # save the new review in the database
    application.db.execute("INSERT INTO reviews (user_id, book_isbn, review) VALUES (:user_id, :book_isbn, :review)",
                           {"user_id": g.user.id, "book_isbn": book_isbn, "review": reviewFromForm})

    # finish and close DB connection
    application.db.commit()

    # rerender the books info page, which will now show the new review
    return redirect(url_for("books.bookByIsbn", book_isbn=book_isbn))
