package pasman.model.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

/**
 * Created by Max on 29.08.2016.
 */
public class Cryptography {
    private static final Logger logger = LoggerFactory.getLogger(Cryptography.class);


    public static String[] encrypt(String key, String value){
        String[] strings = new String[0];
        try {
            logger.debug("Start encrypt wih pass: " + key);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING");//CBF
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            AlgorithmParameters params = cipher.getParameters();

            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.getEncoder().encodeToString(encrypted));
            strings = new String[2];
            strings[0] = Base64.getEncoder().encodeToString(encrypted);
            strings[1] = Base64.getEncoder().encodeToString(iv);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * Decryption function
     *
     * @param key        - key for decrypt
     * @param initVector - initialization vector
     * @param encrypted  - encrypted String in Base64 format
     * @return {@code String obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */

    public static String decrypt(String key, String initVector, String encrypted) {
        logger.debug("Start decrypt wih pass: " + key);
        try {
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(initVector.getBytes("UTF-8")));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static void main(String[] args) {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        String[] strings = encrypt(key,"1 World 33");
        System.out.println(decrypt(key ,strings[1],strings[0]));

        System.out.print("Hsx: " + bytesToHex("1".getBytes()));
        try {
            System.out.println("to SHA256: " + hash256("1"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

