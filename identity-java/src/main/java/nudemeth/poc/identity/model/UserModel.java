package nudemeth.poc.identity.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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
}