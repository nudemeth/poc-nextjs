package handlers

import (
	"api-go/api"
	"fmt"
	"net/http"
	"os"
	"strings"
)

func identity(w http.ResponseWriter, req *http.Request, service *api.Service) {
	var res []byte
	var err error
	if strings.HasPrefix(req.URL.Path, "/token") {
		issuer := strings.Replace(req.URL.Path, "/token/", "", 1)
		code := req.URL.Query().Get("code")
		url := getTokenURL(issuer, code)
		res, err = service.GetIdentityToken(url)
	} else if strings.HasPrefix(req.URL.Path, "/userinfo") {
		issuer := strings.Replace(req.URL.Path, "/userinfo/", "", 1)
		token := req.URL.Query().Get("token")
		url := getUserInfoURL(issuer)
		res, err = service.GetIdentityUserInfo(url, token)
	} else {
		w.WriteHeader(http.StatusNotFound)
		return
	}

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Write(res)
}

func getTokenURL(issuer string, code string) string {
	var url string
	switch strings.ToLower(issuer) {
	case "github":
		clientID := os.Getenv("GITHUB_CLIENT_ID")
		clientSecret := os.Getenv("GITHUB_SECRET")
		tokenURL := os.Getenv("GITHUB_TOKEN_URL")
		url = fmt.Sprintf(tokenURL, clientID, clientSecret, code)
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
