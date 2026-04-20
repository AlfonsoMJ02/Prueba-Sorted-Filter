package com.digis01.AMorenoPruebTecnicaSortedFilter.Util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {

    private static final String Algoritmo = "AES";
    private static final byte[] KEY = "12345678901234567890123456789012".getBytes();

    public static String encrypt(String data) {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY, Algoritmo);
            Cipher cipher = Cipher.getInstance(Algoritmo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            throw new RuntimeException("Error al encriptar", ex);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY, Algoritmo);
            Cipher cipher = Cipher.getInstance(Algoritmo);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted);
        } catch (Exception ex) {
            throw new RuntimeException("Error al desencriptar", ex);
        }
    }
}
