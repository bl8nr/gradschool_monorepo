DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE Books (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(13) UNIQUE NOT NULL,
    title VARCHAR,
    author VARCHAR,
    year INTEGER
);

CREATE INDEX idx_books_isbn ON books(isbn);

CREATE TABLE Reviews (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    book_isbn VARCHAR(13) REFERENCES books(isbn),
    review VARCHAR(5000) NOT NULL
);