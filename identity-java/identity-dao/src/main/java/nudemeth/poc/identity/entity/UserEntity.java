package nudemeth.poc.identity.entity;

import java.util.UUID;

import javax.persistence.Column;
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
    @Column(name = "user_id")
    @Getter
    private UUID id;
    @Column(unique = true, nullable = false)
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
    @Column(name = "email_confirmed", nullable = false)
    @Getter @Setter
    private boolean isEmailConfirmed;
}