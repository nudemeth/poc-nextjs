package handlers

import (
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestIdentityRouter(t *testing.T) {
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(`{"status":"OK"}`))
	}))
	defer server.Close()

	handler := Config{IdentityBaseURL: server.URL}
	r := handler.CreateRouter()
	ts := httptest.NewServer(r)
	defer ts.Close()

	res, err := http.Get(ts.URL + "/api/v1/identity")
	if err != nil {
		t.Fatal(err)
	}
	if res.StatusCode != http.StatusOK {
		t.Errorf("Status code for /api/v1/identity is wrong. Actual: %d, Expected: %d.", res.StatusCode, http.StatusOK)
	}
}
