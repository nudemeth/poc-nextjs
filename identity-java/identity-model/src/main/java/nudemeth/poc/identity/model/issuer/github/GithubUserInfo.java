package nudemeth.poc.identity.model.issuer.github;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nudemeth.poc.identity.model.issuer.IssuerUserInfo;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GithubUserInfo implements IssuerUserInfo {
    
    @Getter @Setter
    private String login;
}