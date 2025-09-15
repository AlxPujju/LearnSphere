package com.divyanshu.backend.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;




import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;


@Service
public class EncryptionService {
    private  static  final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] FIXED_IV = "1234567890abcdef".getBytes(); // 16 bytes


    private static IvParameterSpec getFixedIv() {
        return new IvParameterSpec(FIXED_IV);
    }
    @Value("${data_ky}")
    private  String ENCRYPTION_KEY;
    private byte[] normalizeKey(String key) {
        byte[] keyBytes = key.getBytes();
        int[] validKeyLengths = {16, 24, 32};
        int targetLength = 16; // Default to 128 bits


        for (int len : validKeyLengths) {
            if (keyBytes.length <= len) {
                targetLength = len;
                break;
            }
        }


        byte[] normalizedKey = new byte[targetLength];
        System.arraycopy(keyBytes, 0, normalizedKey, 0, Math.min(keyBytes.length, targetLength));
        return normalizedKey;
    }


    public  void encryptDoc(File inputFile, File outputFile) throws Exception {
        SecretKey secretKey = new SecretKeySpec(normalizeKey(ENCRYPTION_KEY), "AES");
        IvParameterSpec ivParameterSpec = getFixedIv();




        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);


        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {


            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
    }


    public  void decryptDoc(File inputFile, File outputFile) throws Exception {
        SecretKey secretKey = new SecretKeySpec(normalizeKey(ENCRYPTION_KEY), "AES");
        IvParameterSpec ivParameterSpec = getFixedIv();


        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);


        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {


            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
    }








}


