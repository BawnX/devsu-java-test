package com.hexagonal.client.domain.models.valueObjects;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Password {
    private final String secret;
    private final String encryptedPassword;

    public Password(String secret, String encryptedPassword) {
        this.secret = secret;
        this.encryptedPassword = encryptedPassword;
    }

    public static Password CreateWithoutKey(String password) {
        SecretKey secret = generateSecretKey();
        String encrypted = encryptPassword(password, secret);
        return new Password(secretKeyToString(secret), encrypted) {
        };
    }

    public static Password CreateWithKey(String secretPassword, String secretKey) {
        return new Password(secretKey, secretPassword) {
        };
    }

    public String getPassword() {
        SecretKey secretKey = stringToSecretKey(this.secret);
        return decryptPassword(this.encryptedPassword, secretKey);
    }

    public String getSecret() {
        return secret;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    private static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret");
        }
    }

    private static String encryptPassword(String password, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedPasswordBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException
                | NoSuchPaddingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error encrypting password");
        }
    }

    private static String decryptPassword(String encryptedPassword, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedPasswordBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedPasswordBytes, "UTF-8");
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException
                | NoSuchPaddingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error decrypting password ", e);
        }
    }

    public static String secretKeyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey stringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
