package handlers

import (
	"api-go/api"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestCatalog(t *testing.T) {
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(`{"status":"OK"}`))
	}))
	defer server.Close()

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	req := httptest.NewRequest("GET", "/", nil)
	catalog(w, req, service)

	resp := w.Result()
	defer resp.Body.Close()

	if actual, expected := resp.StatusCode, http.StatusOK; actual != expected {
		t.Errorf("Status code is wrong. Actual: %d, Expected: %d", actual, expected)
	}

	text, err := ioutil.ReadAll(resp.Body)

	if err != nil {
		t.Fatal(err)
	}

	if actual, expected := string(text), `{"status":"OK"}`; actual != expected {
		t.Errorf("The response text is wrong. Actual: %s, Expected: %s", actual, expected)
	}
}
