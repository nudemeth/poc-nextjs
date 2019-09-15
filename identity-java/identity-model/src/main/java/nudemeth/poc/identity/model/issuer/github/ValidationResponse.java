package nudemeth.poc.identity.model.issuer.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationResponse {
    private int id;

    @JsonProperty("id")
    public int getId() {
        return this.id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
}