package handlers

import (
	"api-go/api"
	"bytes"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"
)

func TestIdentityToken(t *testing.T) {
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(`{"status":"OK"}`))
	}))
	defer server.Close()

	os.Setenv("GITHUB_TOKEN_URL", server.URL+"?client_id=testclientid&client_secret=testclientsecret&code=%s")

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	req := httptest.NewRequest("GET", "/token?issuer=github&code=1234567890", nil)
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

func TestIdentityUserInfo(t *testing.T) {
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(`{"status":"OK"}`))
	}))
	defer server.Close()

	os.Setenv("GITHUB_USER_INFO_URL", server.URL)

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	req := httptest.NewRequest("GET", "/userinfo?issuer=github&token=1234567890", nil)
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

func TestCreateUser(t *testing.T) {
	mockResp := `{"id":"123457890"}`
	mockIssuer := "testIssuer"
	mockToken := "testToken"
	mockLogin := "testlogin"
	w := httptest.NewRecorder()
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(mockResp))
	}))
	defer server.Close()

	os.Setenv("GITHUB_USER_INFO_URL", server.URL)

	data := map[string]string{
		"issuer": mockIssuer,
		"token":  mockToken,
		"login":  mockLogin}
	jsonValue, err := json.Marshal(data)

	service := &api.Service{Client: server.Client(), BaseURL: server.URL}
	req := httptest.NewRequest("POST", "/users", bytes.NewBuffer(jsonValue))
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
