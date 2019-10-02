package api

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
)

type CatalogService interface {
	GetCatalog(url string, userAgent string) ([]byte, int, error)
}

type IdentityService interface {
	GetUser(path string, login string, issuer string) ([]byte, int, error)
	CreateOrUpdateIssuerUser(issuer string, code string) ([]byte, int, error)
	GetUserToken(id string) ([]byte, int, error)
}

type Service struct {
	Client  *http.Client
	BaseURL string
}

func (service *Service) GetCatalog(url string, userAgent string) ([]byte, int, error) {
	req, err := http.NewRequest("GET", service.BaseURL+url, nil)
	req.Header.Add("User-Agent", userAgent)

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Catalog", url, err.Error())
		return nil, 500, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Catalog", url, err.Error())
		return nil, 500, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Catalog", url)
	body, err := ioutil.ReadAll(res.Body)
	code := res.StatusCode

	if err != nil {
		log.Printf("Error occur when reading response: service=%s, URI=%s\n%s", "Catalog", url, err.Error())
		return nil, 500, err
	}

	return body, code, nil
}

func (service *Service) GetUser(path string, login string, issuer string) ([]byte, int, error) {
	pathWithParams := fmt.Sprintf(path+"/%s", login)
	req, err := http.NewRequest("GET", service.BaseURL+pathWithParams, nil)
	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Accept", "application/json")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", path, err.Error())
		return nil, 500, err
	}

	queryString := req.URL.Query()
	queryString.Add("issuer", issuer)
	req.URL.RawQuery = queryString.Encode()

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", pathWithParams, err.Error())
		return nil, 500, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Identity", path)
	body, err := ioutil.ReadAll(res.Body)
	code := res.StatusCode

	if err != nil {
		log.Printf("Error occur when reading response: service=%s, URI=%s\n%s", "Identity", path, err.Error())
		return nil, 500, err
	}

	return body, code, nil
}

func (service *Service) CreateOrUpdateIssuerUser(issuer string, code string) ([]byte, int, error) {
	url := fmt.Sprintf("/users/issuer/%s/code/%s", issuer, code)

	req, err := http.NewRequest("PUT", service.BaseURL+url, nil)
	req.Header.Add("Accept", "application/json")
	req.Header.Add("Content-Type", "application/json")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	log.Printf("Status: service=%s, URI=%s, code=%d", "Identity", url, res.StatusCode)
	body, err := ioutil.ReadAll(res.Body)
	statusCode := res.StatusCode

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	return body, statusCode, nil
}

func (service *Service) GetUserToken(id string) ([]byte, int, error) {
	path := "/api/v1/identity/users/%s/token"
	pathWithParams := fmt.Sprintf(path, id)

	req, err := http.NewRequest("GET", service.BaseURL+pathWithParams, nil)
	req.Header.Add("Accept", "application/json")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", path, err.Error())
		return nil, 500, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", path, err.Error())
		return nil, 500, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Identity", path)
	body, err := ioutil.ReadAll(res.Body)
	code := res.StatusCode

	if err != nil {
		log.Printf("Error occur when reading response: service=%s, URI=%s\n%s", "Identity", path, err.Error())
		return nil, 500, err
	}

	return body, code, nil
}
