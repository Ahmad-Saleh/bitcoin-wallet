package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.Bip38;
import com.ahmadsaleh.bitcoinkeys.ByteArrayUtils;
import com.ahmadsaleh.bitcoinkeys.ECDSAEncryptionUtils;
import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.BitcoinKeyPair;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

public class GenerateKeysUseCase implements UseCase<char[], BitcoinKeyPair> {

    @Override
    public BitcoinKeyPair execute(char[] password) {
        try {
            KeyPair keyPair = ECDSAEncryptionUtils.generateKeyPair();
            String bitcoinAddress = KeysConversionUtils.toBitcoinAddress(keyPair.getPublic());
            byte[] privateKeyBytes = KeysConversionUtils.asByteArray(keyPair.getPrivate());
            String encryptedPrivate = Bip38.encryptNoEC(ByteArrayUtils.toBytes(password), privateKeyBytes, false);

            return new BitcoinKeyPair(encryptedPrivate, bitcoinAddress);
        } catch (IOException e) {
            throw new IllegalStateException("Error during keys conversion!", e);
        } catch (org.bitcoinj.core.AddressFormatException | GeneralSecurityException e) {
            throw new KeyEncryptionException("Error while encrypting private key!", e);
        }
    }

    private class KeyEncryptionException extends RuntimeException {
        public KeyEncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
