package handlers

import (
	"api-go/api"
	"fmt"
	"log"
	"net/http"
	"os"
	"strings"
)

func identity(w http.ResponseWriter, req *http.Request, service *api.Service) {
	var res []byte
	var err error
	var status int
	if strings.Index(req.URL.Path, "/token") > -1 && req.Method == "GET" {
		issuer := req.URL.Query().Get("issuer")
		code := req.URL.Query().Get("code")
		url := getTokenURL(issuer, code)
		res, status, err = service.GetIdentityToken(url)
	} else if strings.Index(req.URL.Path, "/userinfo") > -1 && req.Method == "GET" {
		issuer := req.URL.Query().Get("issuer")
		token := req.URL.Query().Get("token")
		url := getUserInfoURL(issuer)
		res, status, err = service.GetIdentityUserInfo(url, token)
	} else if strings.Index(req.URL.Path, "/user") > -1 && req.Method == "POST" {
		issuer := req.FormValue("issuer")
		token := req.FormValue("token")
		login := req.FormValue("login")
		res, status, err = service.CreateUser("/api/v1/identity/users/login", issuer, token, login)
	} else if strings.Index(req.URL.Path, "/user") > -1 && req.Method == "GET" {
		login := req.URL.Query().Get("login")
		issuer := req.URL.Query().Get("issuer")
		res, status, err = service.GetUser("/api/v1/identity/users", login, issuer)
	} else {
		log.Printf("Cannot map route: service=%s, URI=%s", "Identity", req.URL.Path)
		w.WriteHeader(http.StatusNotFound)
		return
	}

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.WriteHeader(status)
	w.Write(res)
}

func getTokenURL(issuer string, code string) string {
	var url string
	switch strings.ToLower(issuer) {
	case "github":
		tokenURL := os.Getenv("GITHUB_TOKEN_URL")
		url = fmt.Sprintf(tokenURL, code)
	}

	return url
}

func getUserInfoURL(issuer string) string {
	var url string
	switch strings.ToLower(issuer) {
	case "github":
		url = os.Getenv("GITHUB_USER_INFO_URL")
	}

	return url
}
