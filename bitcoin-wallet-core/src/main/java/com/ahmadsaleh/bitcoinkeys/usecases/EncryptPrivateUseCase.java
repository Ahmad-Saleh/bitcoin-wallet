package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.Bip38;
import com.ahmadsaleh.bitcoinkeys.ByteArrayUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptPrivateUseCase implements UseCase<PrivateKeyBag, String> {

    @Override
    public String execute(PrivateKeyBag privateKeyBag) {
        try {
            byte[] passphrase = ByteArrayUtils.toBytes(privateKeyBag.getPassword());
            return Bip38.encryptNoEC(passphrase, privateKeyBag.getPrivateKey(), false);
        } catch (IOException e) {
            throw new IllegalStateException("Error during keys conversion!", e);
        } catch (org.bitcoinj.core.AddressFormatException | GeneralSecurityException e) {
            throw new KeyEncryptionException("Error while encrypting private key!", e);
        }
    }

    public static class KeyEncryptionException extends RuntimeException {
        public KeyEncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
