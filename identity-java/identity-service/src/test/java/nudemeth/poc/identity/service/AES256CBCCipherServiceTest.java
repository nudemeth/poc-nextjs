package nudemeth.poc.identity.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AES256CBCCipherServiceTest {

    @Test
    public void encrypt_message_encrypted() {
        String key = "this is key";
        String iv = "this is init vtr";
        String salt = "this is salt";
        String message = "Message to encrypt";
        AES256CBCCipherService service = new AES256CBCCipherService(key, iv, salt);
        String actual = service.encrypt(message);
        String expected = "YmJVnpV08OB7a2lDCGhipWkzOXTO/Jt/T5pnW5pvxnk=";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void decrypt_message_decrypted() {
        String key = "this is key";
        String iv = "this is init vtr";
        String salt = "this is salt";
        String message = "YmJVnpV08OB7a2lDCGhipWkzOXTO/Jt/T5pnW5pvxnk=";
        AES256CBCCipherService service = new AES256CBCCipherService(key, iv, salt);
        String actual = service.decrypt(message);
        String expected = "Message to encrypt";

        Assert.assertEquals(expected, actual);
    }

}