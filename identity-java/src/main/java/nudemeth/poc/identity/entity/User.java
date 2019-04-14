package nudemeth.poc.identity.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    private String Name;
    private String UserName;
    private String Email;

    /**
     * @return the name
     */
    public String getName() {
        return Name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.Email = email;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.UserName = userName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.Name = name;
    }
}