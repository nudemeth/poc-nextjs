CREATE ROLE IF NOT EXISTS sa
WITH SUPERUSER = TRUE
AND LOGIN = TRUE
AND PASSWORD = 'P@ssw0rd';

LOGIN sa 'P@ssw0rd';

DROP ROLE cassandra;

CREATE KEYSPACE IF NOT EXISTS ordering 
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 1
    };

CREATE TABLE IF NOT EXISTS ordering.order_by_id (
   order_id UUID,
   order_date TIMESTAMP,
   description TEXT,
   address_city TEXT,
   address_country TEXT,
   address_state TEXT,
   address_street TEXT,
   address_zip_code TEXT,
   status_name TEXT,
   product_name TEXT,
   units INT,
   unit_price DOUBLE,
   picture_url TEXT,
   PRIMARY KEY (order_id, product_name)
) WITH CLUSTERING ORDER BY (product_name);

CREATE TABLE IF NOT EXISTS ordering.order_by_buyer_id (
   buyer_id UUID,
   order_id UUID,
   order_date TIMESTAMP,
   address_city TEXT,
   address_country TEXT,
   address_state TEXT,
   address_street TEXT,
   address_zip_code TEXT,
   status_name TEXT,
   product_name TEXT,
   units INT,
   unit_price DOUBLE,
   PRIMARY KEY (buyer_id, order_id),
) WITH CLUSTERING ORDER BY (order_id DESC);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price, picture_url)
VALUES (6bc6cfae-b04e-4b53-ba23-1a1b7260b121, toTimestamp(now()), 'order 1 desc','Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Paid', 'Item 1', 1, 199, '/pic/Item 1.png');
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price)
VALUES (2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 6bc6cfae-b04e-4b53-ba23-1a1b7260b121, toTimestamp(now()), 'Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Paid', 'Item 1', 1, 199);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price, picture_url)
VALUES (cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, toTimestamp(now()), 'order 2 desc','Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Distributed', 'Item 2', 2, 500, '/pic/Item 2.png');
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price)
VALUES (65430366-f534-49e2-97bf-d54b91545345, cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, toTimestamp(now()), 'Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Distributed', 'Item 2', 2, 500);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price, picture_url)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, toTimestamp(now()), 'order 3 desc','Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', 'Item 3', 3, 999, '/pic/Item 3.png');
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, 40426a32-5ea6-4e86-b826-30abb6629a26, toTimestamp(now()), 'Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', 'Item 3', 3, 999);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price, picture_url)
VALUES (d0ce2e17-ce35-4454-989e-441cee2ac389, toTimestamp(now()), 'order 4 desc','Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', 'Item 4', 4, 2000, '/pic/Item 4.png');
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, address_city, address_country, address_state, address_street, address_zip_code, status_name, product_name, units, unit_price)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389, toTimestamp(now()), 'Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', 'Item 4', 4, 2000);