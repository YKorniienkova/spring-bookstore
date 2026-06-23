INSERT INTO categories (id, name, description, is_deleted)
VALUES (1, 'Programming', 'Programming books', 0);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (1, 'Java', 'Author', '9781234567897', 50.00, 'Java book', 'img.jpg', 0);

INSERT INTO books_categories (book_id, category_id)
VALUES (1, 1);