FROM golang:1.11 as builder

WORKDIR /go/src
COPY ./src/api-go/ ./api-go/

RUN go get -d ./...
RUN go test -v ./api-go/...
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o api-gateway api-go

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /go/src/api-gateway ./api-gateway

CMD ["./api-gateway"]