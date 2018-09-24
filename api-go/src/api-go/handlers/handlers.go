package handlers

import (
	"api-go/api"
	"net/http"

	"github.com/gorilla/mux"
)

func Router() *mux.Router {
	r := mux.NewRouter()
	service := api.Service{Client: &http.Client{}, BaseURL: "http://localhost:5000"}

	r.PathPrefix("/api/v1/catalog").HandlerFunc(wrapper(&service, catalog))
	r.HandleFunc("/healthz", healthz)
	return r
}

func wrapper(service *api.Service, handle func(http.ResponseWriter, *http.Request, *api.Service)) func(http.ResponseWriter, *http.Request) {
	return func(writer http.ResponseWriter, req *http.Request) {
		handle(writer, req, service)
	}
}
