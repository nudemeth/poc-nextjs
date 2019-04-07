package handlers

import (
	"api-go/api"
	"net/http"

	"github.com/gorilla/mux"
)

type Config struct {
	CatalogBaseURL  string
	IdentityBaseURL string
}

func (config *Config) CreateRouter() *mux.Router {
	r := mux.NewRouter()
	catalogService := api.Service{Client: &http.Client{}, BaseURL: config.CatalogBaseURL}
	identityService := api.Service{Client: &http.Client{}, BaseURL: config.IdentityBaseURL}

	r.PathPrefix("/api/v1/catalog").HandlerFunc(wrapper(&catalogService, catalog))
	r.PathPrefix("/api/v1/identity").HandlerFunc(wrapper(&identityService, identity))
	r.HandleFunc("/healthz", healthz)
	return r
}

func wrapper(service *api.Service, handle func(http.ResponseWriter, *http.Request, *api.Service)) func(http.ResponseWriter, *http.Request) {
	return func(writer http.ResponseWriter, req *http.Request) {
		handle(writer, req, service)
	}
}
