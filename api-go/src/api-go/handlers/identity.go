package handlers

import (
	"net/http"

	"api-go/api"
)

func identity(w http.ResponseWriter, req *http.Request, service *api.Service) {

	res, err := service.GetIdentity(req.URL.Path, req.Header)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Write(res)
}
