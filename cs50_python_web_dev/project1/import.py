import csv
import os
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker

# connect to the database using the db url env var
engine = create_engine(os.getenv("DATABASE_URL"))
db = scoped_session(sessionmaker(bind=engine))

# Before running this import program, be sure to run the sql commands in the
# create.sql file. This will remove any existing tables and then create the
# required tables. Running this program will then import all the books in
# books.csv into the psql books table

def main():

    # read the books CSV file into the var list books
    books = csv.reader(open("books.csv"))

    # skip the first row of the csv file, this is the csv headers
    next(books)

    # for each book in books...
    for isbn, title, author, year in books:
        # execute a db query that will add the book to the database book table...
        db.execute("INSERT INTO books (isbn, title, author, year) VALUES (:isbn, :title, :author, :year)",
                   {"isbn": isbn, "title": title, "author": author, "year": year})

        # and output the book info to the console for convenience
        print(f"Added book with ISBN {isbn} (Title: {title}, Author: {author}, Year: {year})")

    # finish db queries by committing
    db.commit()

if __name__ == "__main__":
    main()