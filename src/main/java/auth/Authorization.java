package auth;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Authorization {
    private String user;
    private String passwordHash;

    public Authorization(String user, String passwordHash) {
        this.user = user;
        this.passwordHash = passwordHash;
    }

    public boolean getEncrypt(byte [] crypthedPasswordBytes) throws Exception {
        SecretKeySpec key = new SecretKeySpec("44EncryptedPhrase".getBytes(), "AES");

        Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedPassword = decryptCipher.doFinal(crypthedPasswordBytes);
        System.out.print("Phrase decrypt: ");
        for (int i = 0; i < decryptedPassword.length; i++) {
            System.out.print((char) decryptedPassword[i]);
        }

        String checkPhrase = decryptedPassword.toString();

        if (checkPhrase.equals("takeIt")) {
            return true;
        } else {
            return false;
        }
    }

    public String getCrypt(String keyWord) throws Exception {
        Cipher cryptCipher = Cipher.getInstance("AES");

        SecretKeySpec key = new SecretKeySpec("44EncryptedPhrase".getBytes(), "AES");

        cryptCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] crypthedPasswordBytes = cryptCipher.doFinal(keyWord.getBytes());
        System.out.print("Phrase encrypt: ");
        for (int i = 0; i < crypthedPasswordBytes.length; i++) {
            System.out.print(crypthedPasswordBytes[i]);
        }

        System.out.println(crypthedPasswordBytes);

        return crypthedPasswordBytes.toString();
    }
}
