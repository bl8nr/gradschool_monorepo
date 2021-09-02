from flask import Blueprint, jsonify, abort
import application

# create a blueprint for the api endpoints
bp = Blueprint("api", __name__)

@bp.route("/api/<isbn>", methods=["GET"])
def isbn(isbn):

    # try and get the book by ISBN
    book = application.db.execute("SELECT * FROM books WHERE isbn=(:isbn)", {"isbn": isbn}).fetchone()

    # if there are no book results for the provided isbn, then return an error
    if book is None:
        print("Failed to find a book at the API route")
        return abort(404)

    # get any reviews for this book by getting all reviews with matching book isbn
    reviews = application.db.execute("SELECT * FROM reviews WHERE book_isbn=(:isbn)", {"isbn": isbn}).fetchall()

    # finish and close DB connection
    application.db.commit()

    # create the response object we'll send back to the requester
    isbnDict = {
        "title": book.title,
        "author": book.author,
        "year": book.year,
        "review_count": len(reviews),
        "reviews": [],
        "isbn": book.isbn
    }

    # for every book review related to this book...
    for review in reviews:

        # create a review object
        reviewDict = {
            "id": review.id,
            "user_id": review.user_id,
            "book_isbn": review.book_isbn,
            "review": review.review
        }

        # and add the review object to the response object
        isbnDict['reviews'].append(reviewDict)

    # convert the response object (book info and reviews) to JSON and respond with it
    return jsonify(isbnDict)
