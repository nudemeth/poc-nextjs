org=localhost
domain=localhost

openssl genpkey -algorithm RSA -out "/data/$domain".key
openssl req -x509 -key "/data/$domain".key -out "/data/$domain".crt \
    -subj "/CN=*.$domain/O=$org" \
    -config "/data/openssl.cnf" \
    -extensions x509_ext