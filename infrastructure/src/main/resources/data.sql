--Movies

INSERT INTO movie_prices (id, category, price) VALUES (1, 'basic', 1);
INSERT INTO movie_prices (id, category, price) VALUES (2, 'premium', 3);

INSERT INTO movie_types (id, name, renting_days, bonus_points) VALUES (1, 'New releases', 1, 2);
INSERT INTO movie_types (id, name, renting_days, bonus_points) VALUES (2, 'Regular films', 3, 1);
INSERT INTO movie_types (id, name, renting_days, bonus_points) VALUES (3, 'Old films', 5, 1);

INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (1, 'Jumaji. Next level', 1, 2);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (2, 'Bad boys for life', 1, 1);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (3, 'Dolittle', 1, 2);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (4, 'The Irishman', 2, 2);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (5, 'Star wars', 2, 1);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (6, 'Forest Gump', 3, 1);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (7, 'The dark knight', 3, 1);
INSERT INTO movies (id, name, movie_type_id, movie_price_id) VALUES (8, 'Iron man', 3, 2);

--Customers

INSERT INTO customers (id, name, bonus_points) VALUES (1, 'Spiderman', 0);
INSERT INTO customers (id, name, bonus_points) VALUES (2, 'Batman', 0);

