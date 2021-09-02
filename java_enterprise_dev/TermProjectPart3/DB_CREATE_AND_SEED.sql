DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK (
       ID INT(11) NOT NULL AUTO_INCREMENT
     , CATEGORY VARCHAR(100) NOT NULL
     , ISBN VARCHAR(10) NOT NULL
     , TITLE VARCHAR(200) NOT NULL
     , PUBLISHER VARCHAR(100)
     , PRICE DECIMAL(4,2)
     , PRIMARY KEY (ID)
);

insert into book (title, publisher, category, isbn, price) values ('Daughters of the Night Sky', 'Lake Union Publishing', 'Military Fiction', '154204586X', 7.48);
insert into book (title, publisher, category, isbn, price) values ('The Tuscan Child', 'Lake Union Publishing', 'Historical', '1503951812', 6.99);
insert into book (title, publisher, category, isbn, price) values ('History: A Very Short Introduction', 'Oxford University Press', 'Textbooks', '1503951812', 6.34);
insert into book (title, publisher, category, isbn, price) values ('The Second Treatise on Civil Government', 'Prometheus Books', 'Politics and Social Science', '0879753374', 10.91);
insert into book (title, publisher, category, isbn, price) values ('Just Mercy: A Story of Justice and Redemption', 'Spiegel & Grau', 'Politics and Social Science', 'B00JYWVYLY', 2.99);
insert into book (title, publisher, category, isbn, price) values ('Algorithms to Live By: The Computer Science of Human Decisions', 'Brilliance Audio', 'Textbooks', 'B01D24NAL6', 14.69);
insert into book (title, publisher, category, isbn, price) values ('Never Split the Difference: Negotiating As If Your Life Depended On It', 'HarperBusiness', 'Business & Money', 'B014DUR7L2', 19.99);
insert into book (title, publisher, category, isbn, price) values ('Pivot, Disrupt, Transform: How Leaders Beat the Odds and Survive', 'Diversion Books', 'Business & Money', 'B07G7G6SSK', 16.50);
insert into book (title, publisher, category, isbn, price) values ('The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change', 'Mango', 'Business & Money', 'B01069X4H0', 6.17);
insert into book (title, publisher, category, isbn, price) values ('Principles: Life and Work', 'Simon & Schuster', 'Business & Money', 'B071CTK28D', 14.99);
insert into book (title, publisher, category, isbn, price) values ('Ship of Fools: How a Selfish Ruling Class Is Bringing America to the Brink of Revolution', 'Free Press', 'Politics and Social Science', '1501183664', 13.53);
insert into book (title, publisher, category, isbn, price) values ('Sapiens: A Brief History of Humankind', 'Harper Perennial', 'Historical', '0062316110', 14.23);
insert into book (title, publisher, category, isbn, price) values ('The Silver Music Box', 'AmazonCrossing', 'Military Fiction', 'B07196BC8V', 1.99);