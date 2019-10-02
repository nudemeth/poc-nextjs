package handlers

import (
	"api-go/api"
	"fmt"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"
)

func TestGetUser(t *testing.T) {
	mockResp := `{"id":"123457890", "login":"testlogin", "issuer":"github", "token":"11111111", "name":null, "email":null, "isEmailConfirmed":false}`
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(mockResp))
	}))
	defer server.Close()

	os.Setenv("GITHUB_USER_INFO_URL", server.URL)

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	req := httptest.NewRequest("GET", "/users/login", nil)
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

	if actual, expected := string(text), mockResp; actual != expected {
		t.Errorf("The response text is wrong. Actual: %s, Expected: %s", actual, expected)
	}
}

func TestCreateOrUpdateIssuerUser(t *testing.T) {
	mockResp := `{"123457890"}`
	mockIssuer := "testIssuer"
	mockCode := "testCode"
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(mockResp))
	}))
	defer server.Close()

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	fullPath := fmt.Sprintf("/users/issuer/%s/code/%s", mockIssuer, mockCode)
	req := httptest.NewRequest("PUT", fullPath, nil)
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

	if actual, expected := string(text), mockResp; actual != expected {
		t.Errorf("The response text is wrong. Actual: %s, Expected: %s", actual, expected)
	}
}
