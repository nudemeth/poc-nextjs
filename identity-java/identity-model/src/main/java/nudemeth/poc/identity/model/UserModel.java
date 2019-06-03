package nudemeth.poc.identity.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserModel {
    @Getter
    private UUID id;
    @Getter @Setter @NonNull
    private String login;
    @Getter @Setter
    private String issuer;
    @Getter @Setter
    private String token;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private boolean isEmailConfirmed;
}