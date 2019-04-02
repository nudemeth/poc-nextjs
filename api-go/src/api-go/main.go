package main

import (
	"api-go/handlers"
	"context"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
)

func main() {
	log.Print("Starting the server")

	port := getEnvValue("SERVICE_PORT", "8000")
	catalogBaseURL := getEnvValue("CATALOG_BASE_URL", "http://localhost:5000")
	identityBaseURL := getEnvValue("IDENTITY_BASE_URL", "http://localhost:5100")

	stop := make(chan os.Signal, 1)
	signal.Notify(stop, os.Interrupt, syscall.SIGTERM)

	handler := handlers.Config{CatalogBaseURL: catalogBaseURL, IdentityBaseURL: identityBaseURL}
	router := handler.CreateRouter()
	srv := &http.Server{
		Addr:    ":" + port,
		Handler: router,
	}

	go func() {
		log.Fatal(srv.ListenAndServe())
	}()

	log.Print("The service is ready to listen and serve...")

	killSignal := <-stop
	switch killSignal {
	case os.Interrupt:
		log.Print("Got SIGINT...")
	case syscall.SIGTERM:
		log.Print("Get SIGTERM...")
	}

	log.Print("The service is shutting down...")
	srv.Shutdown(context.Background())
	log.Print("The service has been gracefully shut down.")
}

func getEnvValue(key string, defaultValue string) string {
	value := os.Getenv(key)
	if len(value) == 0 {
		log.Printf("%s is not set in environment variable. Default to: %s", key, defaultValue)
		return defaultValue
	}
	return value
}
