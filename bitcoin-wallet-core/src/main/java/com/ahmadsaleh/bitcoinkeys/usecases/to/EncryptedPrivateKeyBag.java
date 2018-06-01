package com.ahmadsaleh.bitcoinkeys.usecases.to;

public class EncryptedPrivateKeyBag {

    private String encryptedPrivateKey;
    private char[] password;

    public EncryptedPrivateKeyBag(String encryptedPrivateKey, char[] password) {
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.password = password;
    }

    public String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }

    public void setEncryptedPrivateKey(String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }


}
