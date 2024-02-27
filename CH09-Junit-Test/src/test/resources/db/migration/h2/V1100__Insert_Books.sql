-- Inserimento nella tabella "catalogue_authors" per test TEST_GETBOOKBYREALID
INSERT INTO catalogue_authors (author_id, name, surname, birthday, birth_city, death_day)
VALUES
  ('1c1c4465-4a9e-4c8d-98e2-1e7db2f65b5c', 'TEST_GETBOOKBYREALID', 'TEST_GETBOOKBYREALID', '1990-01-01', 'TEST_GETBOOKBYREALID', NULL);

-- Inserimento nella tabella "catalogue_categories" per test TEST_GETBOOKBYREALID
INSERT INTO catalogue_categories (category_id, name, description, type)
VALUES
  ('2b2b5574-b5e0-4f5a-8b5b-8789b67b24a5', 'TEST_GETBOOKBYREALID', 'TEST_GETBOOKBYREALID', 'THRILLER');

-- Inserimento nella tabella "catalogue_categories" per test TEST_POST_OK
INSERT INTO catalogue_categories (category_id, name, description, type)
VALUES
  ('2ca129e6-9b17-457f-a716-5d6ad5cf44e0', 'TEST_POST_OK', 'TEST_POST_OK', 'THRILLER');

-- Inserimento nella tabella "catalogue_books" per test TEST_GETBOOKBYREALID
INSERT INTO catalogue_books (book_id, title, author_id, price)
VALUES
  ('3a3a6682-2854-4b12-9f9b-1023c7f497d4', 'TEST_GETBOOKBYREALID', '1c1c4465-4a9e-4c8d-98e2-1e7db2f65b5c', 19.99);

-- Inserimento nella tabella "book_category" per la relazione many-to-many per test TEST_GETBOOKBYREALID
INSERT INTO book_category (book_id, category_id)
VALUES
('3a3a6682-2854-4b12-9f9b-1023c7f497d4', '2b2b5574-b5e0-4f5a-8b5b-8789b67b24a5');



-- Inserimento nella tabella "catalogue_categories" per test DELETE_OK
INSERT INTO catalogue_categories (category_id, name, description, type)
VALUES
  ('0d0f0959-8735-47c3-b9d3-84e63a4f7900', 'DELETE_OK', 'DELETE_OK', 'THRILLER');

-- Inserimento nella tabella "catalogue_books" per test DELETE_OK
INSERT INTO catalogue_books (book_id, title, author_id, price)
VALUES
  ('2c6145b2-08ec-4d04-9def-f0ccb6fd04aa', 'DELETE_OK', '1c1c4465-4a9e-4c8d-98e2-1e7db2f65b5c', 19.99);

-- Inserimento nella tabella "book_category" per la relazione many-to-many per test DELETE_OK
INSERT INTO book_category (book_id, category_id)
VALUES
('2c6145b2-08ec-4d04-9def-f0ccb6fd04aa', '0d0f0959-8735-47c3-b9d3-84e63a4f7900');
