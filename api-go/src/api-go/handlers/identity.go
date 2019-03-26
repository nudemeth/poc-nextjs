package handlers

import (
	"api-go/api"
	"net/http"
	"strings"
)

func identity(w http.ResponseWriter, req *http.Request, service *api.Service) {
	var res []byte
	var err error
	if strings.HasPrefix(req.URL.Path, "token") {
		code := req.URL.Query().Get("code")
		res, err = service.GetGitHubToken(code)
	} else {
		res, err = service.GetIdentity(req.URL.Path, req.Header)
	}

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Write(res)
}
