package com.ahmadsaleh.bitcoinkeys.usecases.to;

import net.bither.bitherj.crypto.SecureCharSequence;

public class PrivateKeyBag {

    private String encryptedPrivateKey;
    private SecureCharSequence password;

    public PrivateKeyBag(String encryptedPrivateKey, SecureCharSequence password) {
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.password = password;
    }

    public String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }

    public void setEncryptedPrivateKey(String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public SecureCharSequence getPassword() {
        return password;
    }

    public void setPassword(SecureCharSequence password) {
        this.password = password;
    }


}
