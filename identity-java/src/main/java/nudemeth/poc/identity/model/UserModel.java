package nudemeth.poc.identity.model;

import java.util.UUID;

public class UserModel {
    private UUID id;
    private String email;
    private String login;
    private String name;

    public UserModel(String login, String name, String email) {
        this.login = login;
        this.name = name;
        this.email = email;
    }

    public UserModel(UUID id, String login, String name, String email) {
        this(login, name, email);
        this.id = id;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    
}