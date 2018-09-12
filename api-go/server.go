package main

import (
	"fmt"
	"log"
	"net/http"
	//"github.com/nudemeth/poc-microservices/api-go/handlers"
)

func main() {
	log.Print("Starting the server")

	http.HandleFunc("/api", func(w http.ResponseWriter, _ *http.Request) {
		fmt.Fprint(w, "Hello world!")
	})

	log.Print("The server is ready to listen and serve")

	log.Fatal(http.ListenAndServe(":8000", nil))

}
