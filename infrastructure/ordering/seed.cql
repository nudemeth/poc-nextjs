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
   buyer_id UUID,
   order_date TIMESTAMP,
   description TEXT,
   address_city TEXT,
   address_country TEXT,
   address_state TEXT,
   address_street TEXT,
   address_zip_code TEXT,
   status_name TEXT,
   payment_method_alias TEXT,
   payment_method_card_number TEXT,
   payment_method_card_security_number TEXT,
   payment_method_card_holder_name TEXT,
   payment_method_card_expiration TIMESTAMP,
   payment_method_card_type TEXT,
   order_items MAP<TEXT, FROZEN <TUPLE<INT, DECIMAL, TEXT>>>, -- {product_name, {units, unit_price, picture_url}}
   PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS ordering.order_by_buyer_id (
   buyer_id UUID,
   order_id UUID,
   order_date TIMESTAMP,
   status_name TEXT,
   total INT,
   PRIMARY KEY (buyer_id, order_id)
) WITH CLUSTERING ORDER BY (order_id DESC);

CREATE TABLE IF NOT EXISTS ordering.card_type (
    name TEXT,
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS ordering.payment_method (
    buyer_id UUID,
    payment_method_id UUID,
    alias TEXT,
    card_number TEXT,
    card_security_number TEXT,
    card_holder_name TEXT,
    card_expiration TIMESTAMP,
    card_type TEXT,
    PRIMARY KEY (buyer_id, payment_method_id)
);

CREATE TABLE IF NOT EXISTS ordering.buyer (
    buyer_id UUID,
    name TEXT,
    payment_methods LIST<FROZEN <TUPLE<TEXT, TEXT, TEXT, TEXT, TIMESTAMP, TEXT>>>,
    PRIMARY KEY (buyer_id)
);

INSERT INTO ordering.card_type (name) VALUES('VISA');
INSERT INTO ordering.card_type (name) VALUES('MasterCard');
INSERT INTO ordering.card_type (name) VALUES('Amex');
INSERT INTO ordering.card_type (name) VALUES('JCB');

INSERT INTO ordering.payment_method (buyer_id, payment_method_id, alias, card_number, card_security_number, card_holder_name, card_expiration, card_type)
VALUES (2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 0914208a-d69d-4f15-a826-b2752abfee44, 'MyVisaCard', '1111 2222 3333 4444', '000', 'NAME SURNAME', '2022-01-01', 'VISA');
INSERT INTO ordering.payment_method (buyer_id, payment_method_id, alias, card_number, card_security_number, card_holder_name, card_expiration, card_type)
VALUES (65430366-f534-49e2-97bf-d54b91545345, a19b770c-4951-4ec7-82cf-6a17e318864d, 'MyJCBCard', '5555 6666 7777 8888', '000', 'NAME2 SURNAME2', '2023-01-01', 'JCB');
INSERT INTO ordering.payment_method (buyer_id, payment_method_id, alias, card_number, card_security_number, card_holder_name, card_expiration, card_type)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, c1535134-bd35-4588-b9ce-60ed426eb4f8, 'MyMasterCard', '1234 5678 1234 5678', '111', 'NAME3 SURNAME3', '2023-01-01', 'MasterCard');


INSERT INTO ordering.order_by_id (order_id, buyer_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, payment_method_alias, payment_method_card_number, payment_method_card_security_number, payment_method_card_holder_name, payment_method_card_expiration, payment_method_card_type, order_items)
VALUES (6bc6cfae-b04e-4b53-ba23-1a1b7260b121, 2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, toTimestamp(now()), 'order 1 desc','Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Paid', 'MyVisaCard', '1111 2222 3333 4444', '000', 'NAME SURNAME', '2022-01-01', 'VISA', {'Item 1': (1, 199, '/pic/Item 1.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 6bc6cfae-b04e-4b53-ba23-1a1b7260b121, toTimestamp(now()), 'Paid', 1);

INSERT INTO ordering.order_by_id (order_id, buyer_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, payment_method_alias, payment_method_card_number, payment_method_card_security_number, payment_method_card_holder_name, payment_method_card_expiration, payment_method_card_type, order_items)
VALUES (cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, 65430366-f534-49e2-97bf-d54b91545345, toTimestamp(now()), 'order 2 desc','Bangkok','Thailand', null, 'Sukhumvit', '10110', 'Distributed', 'MyJCBCard', '5555 6666 7777 8888', '000', 'NAME2 SURNAME2', '2023-01-01', 'JCB', {'Item 2': (2, 500, '/pic/Item 2.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (65430366-f534-49e2-97bf-d54b91545345, cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, toTimestamp(now()), 'Distributed', 1);

INSERT INTO ordering.order_by_id (order_id, buyer_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, payment_method_alias, payment_method_card_number, payment_method_card_security_number, payment_method_card_holder_name, payment_method_card_expiration, payment_method_card_type, order_items)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, 3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, toTimestamp(now()), 'order 3 desc','Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', 'MyMasterCard', '1234 5678 1234 5678', '111', 'NAME3 SURNAME3', '2023-01-01', 'MasterCard', {'Item 3': (3, 999, '/pic/Item 3.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, 40426a32-5ea6-4e86-b826-30abb6629a26, toTimestamp(now()), 'Packaging', 1);

INSERT INTO ordering.order_by_id (order_id, buyer_id, order_date, description, address_city, address_country, address_state, address_street, address_zip_code, status_name, payment_method_alias, payment_method_card_number, payment_method_card_security_number, payment_method_card_holder_name, payment_method_card_expiration, payment_method_card_type, order_items)
VALUES (d0ce2e17-ce35-4454-989e-441cee2ac389, 3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, toTimestamp(now()), 'order 4 desc','Bangkok','Thailand', null, 'Phetkasem', '10160', 'Packaging', 'MyMasterCard', '1234 5678 1234 5678', '111', 'NAME3 SURNAME3', '2023-01-01', 'MasterCard', {'Item 4': (4, 2000, '/pic/Item 4.png')});
INSERT INTO ordering.order_by_buyer_id (buyer_id, order_id, order_date, status_name, total)
VALUES (3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389, toTimestamp(now()), 'Packaging', 1);