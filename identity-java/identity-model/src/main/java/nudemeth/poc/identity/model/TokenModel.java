package nudemeth.poc.identity.model;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TokenModel {
    @Getter @NonNull
    private UUID id;
    @Getter @Setter @NonNull
    private String token;
}