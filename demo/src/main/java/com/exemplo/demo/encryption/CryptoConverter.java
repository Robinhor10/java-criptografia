package com.exemplo.demo.encryption;

import jakarta.persistence.AttributeConverter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CryptoConverter implements AttributeConverter<String, String> {

    private static final String SECRET_KEY = "mysuperSecretKey";
    private static final int SALT_LENGTH = 16;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            byte[] salt = generateSalt();
            byte[] encrypted = encrypt(attribute, salt);
            return java.util.Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            byte[] decodedData = Base64.getDecoder().decode(dbData);
            return decrypt(decodedData);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }


    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] encrypt(String data, byte[] salt) throws Exception {
        // Usar PBKDF2 para gerar a chave de criptografia
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data.getBytes("UTF-8"));
    }

    private String encryptWithSalt(String data) throws Exception {
        byte[] salt = generateSalt();
        byte[] encryptedData = encrypt(data, salt);

        // Concatenar o salt e os dados criptografados e codificar em Base64
        byte[] combined = new byte[salt.length + encryptedData.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(encryptedData, 0, combined, salt.length, encryptedData.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    private String decrypt(byte[] encryptedData) throws Exception {
        // Extrair o salt dos primeiros 16 bytes
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(encryptedData, 0, salt, 0, SALT_LENGTH);

        // Extrair os dados criptografados restantes
        byte[] actualEncryptedData = new byte[encryptedData.length - SALT_LENGTH];
        System.arraycopy(encryptedData, SALT_LENGTH, actualEncryptedData, 0, actualEncryptedData.length);

        // Gerar a chave a partir do salt e da senha secreta
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Descriptografar os dados
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(actualEncryptedData);

        // Retornar o texto original
        return new String(decryptedData, "UTF-8");
    }

}
