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
		url := getUrl(issuer, code)
		res, err = service.GetIdentityToken(url)
	} else {
		//res, err = service.GetIdentity(req.URL.Path, req.Header)
		w.WriteHeader(http.StatusNotFound)
		return
	}

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Write(res)
}

func getUrl(issuer string, code string) string {
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
