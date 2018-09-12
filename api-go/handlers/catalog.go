package handlers

import (
	"fmt"
	"net/http"
)

func catalog(w http.ResponseWriter, _ *http.Request) {
	fmt.Fprint(w, "Hello! Your request was proceeded.")
}
