package com.hexicloud.portaldb.util.encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EncryptionUtil {
    @Value("${pwdencrypt.key}")
    public static final String ENC_KEY = "~!@oApTI0o.o0987";

    public EncryptionUtil() {
        super();
    }

    public static void main(String[] args) throws Exception {
        //        byte [] encrpted = encrypt ("1AumMaaDurga1");
        //        System.err.println (decrypt(encrpted));

//        String encrptedTxt = encryptString("1AumMaaDurga1");
//        System.err.println("----->>>>>" + encrptedTxt);
//        System.err.println("----AUM->" + decryptString("ZQwjtH/KJJDrWFe6b3WxLA=="));

    }

    public static byte[] encrypt(String plainText) throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidKeyException,IllegalBlockSizeException,BadPaddingException{

        byte[] encrypted = getCypher(Cipher.ENCRYPT_MODE).doFinal(plainText.getBytes());
        System.err.println(new String(encrypted));
        return encrypted;
    }

    public static String decrypt(byte[] encryptedText) throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidKeyException,IllegalBlockSizeException,BadPaddingException {
        byte[] decryptedByte = getCypher(Cipher.DECRYPT_MODE).doFinal(encryptedText);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public static Cipher getCypher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException,
                                                    InvalidKeyException {
        // Create key and cipher
        Key aesKey = new SecretKeySpec(ENC_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // encrypt the text
        cipher.init(mode, aesKey);
        return cipher;
    }

    public static String encryptString(String plainText) throws Exception {
        byte[] encrypted = getCypher(Cipher.ENCRYPT_MODE).doFinal(plainText.getBytes());
//        System.err.println(new String(encrypted));
        byte[] enc64 = Base64.encodeBase64(encrypted);
        String encryptedText = new String(enc64);
        return encryptedText;
    }

    public static String decryptString(String encryptedText) throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidKeyException,IllegalBlockSizeException,BadPaddingException {
        byte[] encryptedTextByte = Base64.decodeBase64(encryptedText.getBytes());
        byte[] decryptedByte = getCypher(Cipher.DECRYPT_MODE).doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
}

