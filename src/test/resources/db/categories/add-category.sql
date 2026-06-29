DELETE FROM books_categories;
DELETE FROM categories;

INSERT INTO categories (id, name, description, is_deleted)
VALUES (1, 'Programming', 'Programming books', 0);