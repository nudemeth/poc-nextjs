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