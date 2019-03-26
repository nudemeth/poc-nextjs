package api

import (
	"io/ioutil"
	"log"
	"net/http"
)

type CatalogService interface {
	GetCatalog(url string, userAgent string) ([]byte, error)
}

type IdentityService interface {
	GetIdentity(url string, userAgent string) ([]byte, error)
}

type Service struct {
	Client  *http.Client
	BaseURL string
}

func (service *Service) GetCatalog(url string, userAgent string) ([]byte, error) {
	req, err := http.NewRequest("GET", service.BaseURL+url, nil)
	req.Header.Add("User-Agent", userAgent)

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Catalog", url, err.Error())
		return nil, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Catalog", url, err.Error())
		return nil, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Catalog", url)
	body, err := ioutil.ReadAll(res.Body)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Catalog", url, err.Error())
		return nil, err
	}

	return body, nil
}

func (service *Service) GetIdentity(url string, header http.Header) ([]byte, error) {
	req, err := http.NewRequest("GET", service.BaseURL+url, nil)
	req.Header = header

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Identity", url)
	body, err := ioutil.ReadAll(res.Body)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, err
	}

	return body, nil
}

func (service *Service) GetGitHubToken(code string) ([]byte, error) {
	clientID := "f4b44543204f5b40deec"
	clientSecret := "9bc72fae341b431a1ff000d6ef12c7fcf45fc4de"
	url := "https://github.com/login/oauth/access_token?client_id=" + clientID + "&client_secret=" + clientSecret + "&code=" + code
	req, err := http.NewRequest("POST", url, nil)
	req.Header.Add("Accept", "application/json")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Identity", url)
	body, err := ioutil.ReadAll(res.Body)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, err
	}

	return body, nil
}
