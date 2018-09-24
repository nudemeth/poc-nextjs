package api

import (
	"io/ioutil"
	"log"
	"net/http"
)

/*type API struct {
	Client *http.Client
	Req    *http.Request
}

func (api *API) Request() ([]byte, error) {
	resp, err := api.Client.Do(api.Req)

	if err != nil {
		return nil, err
	}

	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)

	return body, err
}*/

type CatalogService interface {
	GetCatalog(url string, userAgent string) ([]byte, error)
}

type Service struct {
	Client  *http.Client
	BaseURL string
}

func (service *Service) GetCatalog(url string, userAgent string) ([]byte, error) {
	req, err := http.NewRequest("GET", service.BaseURL+url, nil)
	req.Header.Add("User-Agent", userAgent)
	req.Header.Add("Accept", "application/json")

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
