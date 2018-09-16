package main

import (
	"api-go/handlers"
	"log"
	"net/http"
	"os"
)

func main() {
	log.Print("Starting the server")

	router := handlers.Router()
	port := os.Getenv("SERVICE_PORT")

	if port == "" {
		port = "8000"
		log.Printf("SERVICE_PORT is not set in environment. Default to: %s", port)
	}

	log.Print("The server is ready to listen and serve")
	log.Fatal(http.ListenAndServe(":"+port, router))
}
