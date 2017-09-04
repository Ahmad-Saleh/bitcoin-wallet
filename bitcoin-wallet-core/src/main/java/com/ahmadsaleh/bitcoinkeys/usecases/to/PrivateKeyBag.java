package com.ahmadsaleh.bitcoinkeys.usecases.to;

import net.bither.bitherj.crypto.SecureCharSequence;

public class PrivateKeyBag {

    private String encryptedPrivateKey;
    private SecureCharSequence password;

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

    public static class Builder {

        private String encryptedPrivateKey;
        private SecureCharSequence password;

        public Builder encryptedPrivateKey(String encryptedPrivateKey) {
            this.encryptedPrivateKey = encryptedPrivateKey;
            return this;
        }

        public Builder password(SecureCharSequence password) {
            this.password = password;
            return this;
        }

        public PrivateKeyBag build() {
            PrivateKeyBag privateKeyBag = new PrivateKeyBag();
            privateKeyBag.setEncryptedPrivateKey(encryptedPrivateKey);
            privateKeyBag.setPassword(password);
            return privateKeyBag;
        }
    }
}
