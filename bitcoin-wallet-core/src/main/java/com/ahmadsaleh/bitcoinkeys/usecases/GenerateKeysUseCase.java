package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.ByteArrayUtils;
import com.ahmadsaleh.bitcoinkeys.ECDSAEncryptionUtils;
import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.Bip38;
import com.ahmadsaleh.bitcoinkeys.usecases.to.BitcoinKeyPair;
import com.ahmadsaleh.bitcoinkeys.writer.WalletImportFormatWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;

public class GenerateKeysUseCase implements UseCase<char[], BitcoinKeyPair> {

    @Override
    public BitcoinKeyPair exeute(char[] password) {
        try {
            KeyPair keyPair = ECDSAEncryptionUtils.generateKeyPair();
            String bitcoinAddress = KeysConversionUtils.toBitcoinAddress(keyPair.getPublic());
            String wifPrivateKey = toWalletImportFormat(keyPair.getPrivate());

            String encryptedPrivate = Bip38.encryptNoEC(ByteArrayUtils.toBytes(password), wifPrivateKey, false);
            return new BitcoinKeyPair(encryptedPrivate, bitcoinAddress);
        } catch (IOException e) {
            throw new IllegalStateException("Error during keys conversion!", e);
        } catch (org.bitcoinj.core.AddressFormatException | GeneralSecurityException e) {
            throw new KeyEncryptionException("Error while encrypting private key!", e);
        }
    }

    private String toWalletImportFormat(PrivateKey privateKey) throws IOException {
        try (StringWriter stringWriter = new StringWriter();
             WalletImportFormatWriter wifWriter = new WalletImportFormatWriter(stringWriter);) {
            wifWriter.write(privateKey);
            wifWriter.flush();
            return stringWriter.toString();
        }
    }

    private class KeyEncryptionException extends RuntimeException {
        public KeyEncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
