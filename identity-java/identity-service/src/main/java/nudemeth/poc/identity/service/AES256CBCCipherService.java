package nudemeth.poc.identity.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256CBCCipherService implements CipherService {

    private final String key;
    private final String iv;
    private final String salt;

    public AES256CBCCipherService(String key, String iv, String salt) {
        this.key = key;
        this.iv = iv;
        this.salt = salt;
    }

    @Override
    public String encrypt(String message) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
        } catch (GeneralSecurityException ex) {

        } catch (UnsupportedEncodingException ex) {

        }
        return null;
    }

    @Override
    public String decrypt(String message) {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
        } catch (GeneralSecurityException ex) {

        } catch (UnsupportedEncodingException ex) {

        }
        return null;
    }

}