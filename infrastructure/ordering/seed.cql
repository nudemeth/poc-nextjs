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
   order_items map<TEXT, FROZEN <tuple<INT, DOUBLE, TEXT>>>, -- {product_name, {units, unit_price, picture_url}}
   PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS ordering.order_by_buyer_id (
   buyer_id UUID,
   order_id UUID,
   order_date TIMESTAMP,
   status_name TEXT,
   total INT,
   PRIMARY KEY (buyer_id, order_id),
) WITH CLUSTERING ORDER BY (order_id DESC);

CREATE TABLE IF NOT EXISTS ordering.card_type (
    name TEXT,
    PRIMARY KEY (name)
);

INSERT INTO ordering.card_type (name) VALUES('VISA');
INSERT INTO ordering.card_type (name) VALUES('MasterCard');
INSERT INTO ordering.card_type (name) VALUES('Amex');
INSERT INTO ordering.card_type (name) VALUES('JCB');

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, order_items)
VALUES (6bc6cfae-b04e-4b53-ba23-1a1b7260b121, toTimestamp(now()), 'order 1 desc','Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Paid', {'Item 1': (1, 199, '/pic/Item 1.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 6bc6cfae-b04e-4b53-ba23-1a1b7260b121, toTimestamp(now()), 'Paid', 1);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, order_items)
VALUES (cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, toTimestamp(now()), 'order 2 desc','Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Distributed', {'Item 2': (2, 500, '/pic/Item 2.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (65430366-f534-49e2-97bf-d54b91545345, cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, toTimestamp(now()), 'Distributed', 1);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, order_items)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, toTimestamp(now()), 'order 3 desc','Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', {'Item 3': (3, 999, '/pic/Item 3.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, 40426a32-5ea6-4e86-b826-30abb6629a26, toTimestamp(now()), 'Packaging', 1);

INSERT INTO ordering.order_by_id (order_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, order_items)
VALUES (d0ce2e17-ce35-4454-989e-441cee2ac389, toTimestamp(now()), 'order 4 desc','Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', {'Item 4': (4, 2000, '/pic/Item 4.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389, toTimestamp(now()), 'Packaging', 1);