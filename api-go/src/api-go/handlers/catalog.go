package handlers

import (
	"net/http"

	"api-go/api"
)

func catalog(w http.ResponseWriter, req *http.Request, service *api.Service) {
	res, status, err := service.GetCatalog(req.URL.Path, req.UserAgent())

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.WriteHeader(status)
	w.Write(res)
}
