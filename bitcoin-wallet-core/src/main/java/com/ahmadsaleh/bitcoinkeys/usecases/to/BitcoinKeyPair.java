package com.ahmadsaleh.bitcoinkeys.usecases.to;

public class BitcoinKeyPair {

    private String encryptedPrivate;
    private String publicBitcoinAddress;

    public BitcoinKeyPair(String wifEncryptedPrivate, String publicAddress) {
        this.encryptedPrivate = wifEncryptedPrivate;
        this.publicBitcoinAddress = publicAddress;
    }

    public String getEncryptedPrivate() {
        return encryptedPrivate;
    }

    public void setEncryptedPrivate(String encryptedPrivate) {
        this.encryptedPrivate = encryptedPrivate;
    }

    public String getPublicBitcoinAddress() {
        return publicBitcoinAddress;
    }

    public void setPublicBitcoinAddress(String publicBitcoinAddress) {
        this.publicBitcoinAddress = publicBitcoinAddress;
    }
}
