package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag;

import java.security.PrivateKey;
import java.security.PublicKey;

public class CalculateAddressUseCase implements UseCase<EncryptedPrivateKeyBag, String> {

    @Override
    public String execute(EncryptedPrivateKeyBag encryptedPrivateKeyBag) {
        char[] decrypt = new DecryptPrivateUseCase().execute(encryptedPrivateKeyBag);
        return calculateAddress(decrypt);
    }

    private String calculateAddress(char[] walletImportFormat) {
        PrivateKey privateKey = KeysConversionUtils.toPrivateKey(walletImportFormat);
        byte[] publicKeyBytes = KeysConversionUtils.publicKeyFromPrivate(privateKey, false);
        PublicKey publicKey = KeysConversionUtils.asPublicKey(publicKeyBytes);
        return KeysConversionUtils.toBitcoinAddress(publicKey);
    }
}