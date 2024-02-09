--1 Script per creare la tabella "catalogue_authors"
CREATE TABLE catalogue_authors (
    author_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    birth_city VARCHAR(255) NOT NULL,
    death_day DATE,
    CONSTRAINT registry_author_pkey PRIMARY KEY (author_id)
);

--2 Script per creare la tabella "catalogue_books"
CREATE TABLE catalogue_books (
    book_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    author_id UUID NOT NULL,
    price numeric NOT NULL,
    CONSTRAINT registry_books_pkey PRIMARY KEY (book_id),
    FOREIGN KEY (author_id) REFERENCES catalogue_authors(author_id)
);


--3 Script per creare la tabella "catalogue_categories"
CREATE TABLE catalogue_categories (
    category_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(50) NOT null,
    CONSTRAINT registry_categories_pkey PRIMARY KEY (category_id)
);


--4 Script per creare la tabella di join "book_category" per la relazione many-to-many
CREATE TABLE book_category (
    book_id UUID,
    category_id UUID,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES catalogue_books(book_id),
    FOREIGN KEY (category_id) REFERENCES catalogue_categories(category_id)
);


-- NB-> ALTER TABLE da eseguire al capitolo CH08-FEIGN-CLIENT
ALTER TABLE public.catalogue_books ADD quantity int4 NULL;

