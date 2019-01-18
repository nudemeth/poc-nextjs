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
   id UUID,
   user_id TEXT,
   user_name TEXT,
   street TEXT,
   city TEXT,
   state TEXT,
   country TEXT,
   zip_code TEXT,
   card_type_id INT,
   card_number TEXT,
   card_security_number TEXT,
   card_holder_name TEXT,
   card_expiration TIMESTAMP,
   buyer_id UUID,
   payment_method_id UUID,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ordering.order_by_user_id (
   id UUID,
   user_id TEXT,
   user_name TEXT,
   street TEXT,
   city TEXT,
   state TEXT,
   country TEXT,
   zip_code TEXT,
   card_type_id INT,
   card_number TEXT,
   card_security_number TEXT,
   card_holder_name TEXT,
   card_expiration TIMESTAMP,
   buyer_id UUID,
   payment_method_id UUID,
   PRIMARY KEY (user_id, id)
) WITH CLUSTERING ORDER BY (id DESC);

INSERT INTO ordering.order_by_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (6bc6cfae-b04e-4b53-ba23-1a1b7260b121, '1', 'aaa','a','b', null, 'Thailand', '00000', 1, '1234-5678-1234-5678', '000', 'AAA BBBB', toTimestamp(now()), 2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 65430366-f534-49e2-97bf-d54b91545345);
INSERT INTO ordering.order_by_user_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (6bc6cfae-b04e-4b53-ba23-1a1b7260b121, '1', 'aaa','a','b', null, 'Thailand', '00000', 1, '1234-5678-1234-5678', '000', 'AAA BBBB', toTimestamp(now()), 2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 65430366-f534-49e2-97bf-d54b91545345);

INSERT INTO ordering.order_by_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, '1', 'aaa','a','b', null, 'Thailand', '00000', 1, '1234-5678-1234-5678', '000', 'AAA BBBB', toTimestamp(now()), 2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 65430366-f534-49e2-97bf-d54b91545345);
INSERT INTO ordering.order_by_user_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (cc6a6c9d-0871-4f62-9bd1-f29b81ec8b5e, '1', 'aaa','a','b', null, 'Thailand', '00000', 1, '1234-5678-1234-5678', '000', 'AAA BBBB', toTimestamp(now()), 2f17c7e4-a6b6-4ba0-a1eb-9b4f6d2ce4df, 65430366-f534-49e2-97bf-d54b91545345);

INSERT INTO ordering.order_by_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, '2', 'bbb','c','d', null, 'Thailand', '11111', 1, '9876-5432-9876-5432', '111', 'CCC DDDD', toTimestamp(now()), 3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389);
INSERT INTO ordering.order_by_user_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, '2', 'bbb','c','d', null, 'Thailand', '11111', 1, '9876-5432-9876-5432', '111', 'CCC DDDD', toTimestamp(now()), 3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389);

INSERT INTO ordering.order_by_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, '2', 'bbb','c','d', null, 'Thailand', '11111', 1, '9876-5432-9876-5432', '111', 'CCC DDDD', toTimestamp(now()), 3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389);
INSERT INTO ordering.order_by_user_id (id, user_id, user_name, street, city, state, country, zip_code, card_type_id, card_number, card_security_number, card_holder_name, card_expiration, buyer_id, payment_method_id)
VALUES (40426a32-5ea6-4e86-b826-30abb6629a26, '2', 'bbb','c','d', null, 'Thailand', '11111', 1, '9876-5432-9876-5432', '111', 'CCC DDDD', toTimestamp(now()), 3da1e68a-9ff7-4b85-8e2e-b6440fb43ca5, d0ce2e17-ce35-4454-989e-441cee2ac389);