FROM nginx:1.14.0-alpine

RUN /bin/ash -c 'mkdir /data'

COPY ./create-self-signed.sh /data/create-self-signed.sh
COPY ./openssl.cnf /data/openssl.cnf

RUN /bin/ash -c 'apk add openssl'
RUN /bin/ash -c '/data/create-self-signed.sh'
RUN /bin/ash -c 'apk del openssl'

COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80 443