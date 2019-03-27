package handlers

import (
	"api-go/api"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"
)

func TestIdentityRouter(t *testing.T) {
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(`{"status":"OK"}`))
	}))
	defer server.Close()

	os.Setenv("GITHUB_CLIENT_ID", "testclientid")
	os.Setenv("GITHUB_SECRET", "testclientsecret")
	os.Setenv("GITHUB_TOKEN_URL", server.URL+"?client_id=%s&client_secret=%s&code=%s")

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	req := httptest.NewRequest("GET", "/token/github?code=1234567890", nil)
	identity(w, req, service)

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
