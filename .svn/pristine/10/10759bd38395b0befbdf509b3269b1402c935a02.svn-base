package powerservice.core.util.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AES {
    /**
     * AES 방식의 암호화
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String key) throws Exception {

        // use key coss2
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] encrypted = cipher.doFinal(message.getBytes());
        return Hex.encodeHexString(encrypted);
    }

    /**
     * AES 방식의 복호화
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypted, String key) throws Exception {

        // use key coss2
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(Hex.decodeHex(encrypted.toCharArray()));
        String originalString = new String(original);
        return originalString;
    }


}
