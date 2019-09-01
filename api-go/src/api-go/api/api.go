package api

import (
	"bytes"
	"encoding/json"
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
	GetIdentityToken(url string) ([]byte, int, error)
	GetIdentityUserInfo(url string, token string) ([]byte, int, error)
	CreateOrUpdateUser(path string, issuer string, token string, login string) ([]byte, int, error)
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

func (service *Service) GetIdentityToken(url string) ([]byte, int, error) {
	req, err := http.NewRequest("POST", url, nil)
	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Accept", "application/json")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Identity", url)
	body, err := ioutil.ReadAll(res.Body)
	code := res.StatusCode

	if err != nil {
		log.Printf("Error occur when reading response: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	return body, code, nil
}

func (service *Service) GetIdentityUserInfo(url string, token string) ([]byte, int, error) {
	req, err := http.NewRequest("GET", url, nil)
	req.Header.Add("Accept", "application/json")
	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Authorization", fmt.Sprintf("token %s", token))
	req.Header.Add("User-Agent", "poc-microservice-dev")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	defer res.Body.Close()

	log.Printf("Redirect to: service=%s, URI=%s", "Identity", url)
	body, err := ioutil.ReadAll(res.Body)
	code := res.StatusCode

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", url, err.Error())
		return nil, 500, err
	}

	return body, code, nil
}

func (service *Service) CreateOrUpdateUser(issuer string, token string, login string) ([]byte, int, error) {
	createOrUpdateUserPath := fmt.Sprintf("/api/v1/identity/users/login/%s?issuer=%s", login, issuer)
	data := map[string]string{
		"issuer": issuer,
		"token":  token,
		"login":  login}
	jsonValue, err := json.Marshal(data)

	if err != nil {
		log.Printf("Error occur when parsing request: service=%s, URI=%s\n%s", "Identity", createOrUpdateUserPath, err.Error())
	}

	req, err := http.NewRequest("POST", service.BaseURL+createOrUpdateUserPath, bytes.NewBuffer(jsonValue))
	req.Header.Add("Accept", "application/json")
	req.Header.Add("Content-Type", "application/json")

	if err != nil {
		log.Printf("Error occur when creating request: service=%s, URI=%s\n%s", "Identity", createOrUpdateUserPath, err.Error())
		return nil, 500, err
	}

	res, err := service.Client.Do(req)

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", createOrUpdateUserPath, err.Error())
		return nil, 500, err
	}

	log.Printf("Status: service=%s, URI=%s, code=%d", "Identity", createOrUpdateUserPath, res.StatusCode)
	body, err := ioutil.ReadAll(res.Body)
	code := res.StatusCode

	if err != nil {
		log.Printf("Error occur when requesting: service=%s, URI=%s\n%s", "Identity", createOrUpdateUserPath, err.Error())
		return nil, 500, err
	}

	return body, code, nil
}

func (service *Service) GetUserToken(id string) ([]byte, int, error) {
	path := "/api/v1/identity/token/user"
	pathWithParams := fmt.Sprintf(path+"/%s", id)

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
