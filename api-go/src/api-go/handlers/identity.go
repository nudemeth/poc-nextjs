package handlers

import (
	"api-go/api"
	"log"
	"net/http"
	"path"
	"strings"
)

type UserModel struct {
	ID               string `json:"id"`
	Login            string `json:"login"`
	Issuer           string `json:"issuer"`
	Token            string `json:"token"`
	Name             string `json:"name"`
	Email            string `json:"email"`
	IsEmailConfirmed bool   `json:"isEmailConfirmed"`
}

func identity(w http.ResponseWriter, req *http.Request, service *api.Service) {
	var res []byte
	var err error
	var status int
	if strings.Index(req.URL.Path, "/users") > -1 && strings.HasSuffix(req.URL.Path, "/token") && req.Method == "GET" {
		id := path.Base(strings.Replace(req.URL.Path, "/token", "", 1))
		res, status, err = service.GetUserToken(id)
	} else if strings.Index(req.URL.Path, "/users/login") > -1 && req.Method == "GET" {
		login := path.Base(req.URL.Path)
		issuer := req.URL.Query().Get("issuer")
		res, status, err = service.GetUser("/api/v1/identity/users/login", login, issuer)
	} else if strings.Index(req.URL.Path, "/users/issuer/") > -1 && strings.Index(req.URL.Path, "/code/") > -1 && req.Method == "PUT" {
		// /users/issuer/{issuer}/code/{code}
		segments := strings.Split(req.URL.Path, "/")
		issuer, code := segments[3], segments[5]
		res, status, err = service.CreateOrUpdateIssuerUser(issuer, code)
	} else {
		log.Printf("Cannot map route: service=%s, URI=%s", "Identity", req.URL.Path)
		w.WriteHeader(http.StatusNotFound)
		return
	}

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	w.Write(res)
}
