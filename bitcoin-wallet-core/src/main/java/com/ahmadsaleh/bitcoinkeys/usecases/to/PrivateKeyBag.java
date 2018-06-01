package com.ahmadsaleh.bitcoinkeys.usecases.to;

public class PrivateKeyBag {

    private byte[] privateKey;
    private char[] password;

    public PrivateKeyBag(byte[] privateKey, char[] password) {
        this.privateKey = privateKey;
        this.password = password;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPlainPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }


}
