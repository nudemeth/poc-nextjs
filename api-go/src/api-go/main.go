package main

import (
	"log"
	"net/http"

	"api-go/handlers"
)

func main() {
	log.Print("Starting the server")

	router := handlers.Router()

	log.Print("The server is ready to listen and serve")
	log.Fatal(http.ListenAndServe(":8000", router))
}
