package handlers

import (
	"api-go/api"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"
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
	if strings.Index(req.URL.Path, "/users/token") > -1 && req.Method == "GET" {
		id := path.Base(req.URL.Path)
		res, status, err = service.GetUserToken(id)
	} else if strings.Index(req.URL.Path, "/users/login") > -1 && req.Method == "GET" {
		login := path.Base(req.URL.Path)
		issuer := req.URL.Query().Get("issuer")
		res, status, err = service.GetUser("/api/v1/identity/users/login", login, issuer)
	} else if strings.Index(req.URL.Path, "/token") > -1 && req.Method == "GET" {
		issuer := req.URL.Query().Get("issuer")
		code := req.URL.Query().Get("code")
		url := getTokenURL(issuer, code)
		res, status, err = service.GetIdentityToken(url)
	} else if strings.Index(req.URL.Path, "/userinfo") > -1 && req.Method == "GET" {
		issuer := req.URL.Query().Get("issuer")
		token := req.URL.Query().Get("token")
		url := getUserInfoURL(issuer)
		res, status, err = service.GetIdentityUserInfo(url, token)
	} else if strings.Index(req.URL.Path, "/users/login") > -1 && req.Method == "PUT" && req.Body != nil {
		login := path.Base(req.URL.Path)
		issuer := req.URL.Query().Get("issuer")
		decoder := json.NewDecoder(req.Body)
		var user UserModel
		err := decoder.Decode(&user)

		if err == nil {
			issuer := issuer
			token := user.Token
			login := login
			res, status, err = service.CreateOrUpdateUser(issuer, token, login)
		}

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
