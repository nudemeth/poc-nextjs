package nudemeth.poc.identity.service;

public interface CipherService {
    String encrypt(String message);
    String decrypt(String message);
}