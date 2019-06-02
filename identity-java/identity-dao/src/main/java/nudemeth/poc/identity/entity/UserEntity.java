package nudemeth.poc.identity.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user", schema = "public")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}