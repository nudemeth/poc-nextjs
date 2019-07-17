package nudemeth.poc.identity.config;

public class CipherConfig {
    
    private String iv;
    private String key;
    private String salt;

    public CipherConfig(String iv, String key, String salt) {
        this.iv = iv;
        this.key = key;
        this.salt = salt;
    }

    public String getIv() {
        return iv;
    }
    public void setIv(String iv) {
        this.iv = iv;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
}