package handlers

import (
	"github.com/gorilla/mux"
)

func Router() *mux.Router {
	r := mux.NewRouter()
	r.HandleFunc("/api/v1/catalog", catalog)
	r.HandleFunc("/healthz", healthz)
	return r
}