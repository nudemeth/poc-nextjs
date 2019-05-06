package nudemeth.poc.identity.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class UserModel {
    @Getter
    private UUID id;
    @Getter @Setter @NonNull
    private String login;
    @Getter @Setter @NonNull
    private String name;
    @Getter @Setter @NonNull
    private String email;

    //The generated constructor code has some problems when using Jackon annotaion with Lombok annotation. Need to do it manually.
    public UserModel(@JsonProperty("login") String login, @JsonProperty("name") String name, @JsonProperty("email") String email) {
        this(null, login, name, email);
    }

    public UserModel(@JsonProperty("id") UUID id, @JsonProperty("login") String login, @JsonProperty("name") String name, @JsonProperty("email") String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
    }
}