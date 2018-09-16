package handlers

import (
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestRouter(t *testing.T) {
	r := Router()
	ts := httptest.NewServer(r)
	defer ts.Close()

	res, err := http.Get(ts.URL + "/api/v1/catalog")
	if err != nil {
		t.Fatal(err)
	}
	if res.StatusCode != http.StatusOK {
		t.Errorf("Status code for /api/v1/catalog is wrong. Actual: %d, Expected: %d.", res.StatusCode, http.StatusOK)
	}

}
