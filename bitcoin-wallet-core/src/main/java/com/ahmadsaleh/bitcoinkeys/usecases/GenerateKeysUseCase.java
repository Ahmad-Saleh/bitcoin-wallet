package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.ECDSAEncryptionUtils;
import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.BitcoinKeyPair;
import com.ahmadsaleh.bitcoinkeys.writer.WalletImportFormatWriter;
import net.bither.bitherj.crypto.SecureCharSequence;
import net.bither.bitherj.crypto.bip38.Bip38;
import net.bither.bitherj.exception.AddressFormatException;

import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.PrivateKey;

public class GenerateKeysUseCase implements UseCase<SecureCharSequence, BitcoinKeyPair> {

    @Override
    public BitcoinKeyPair exeute(SecureCharSequence password) {
        try {
            KeyPair keyPair = ECDSAEncryptionUtils.generateKeyPair();
            String bitcoinAddress = KeysConversionUtils.toBitcoinAddress(keyPair.getPublic());
            String wifPrivateKey = toWalletImportFormat(keyPair.getPrivate());
            String encryptedPrivate = Bip38.encryptNoEcMultiply(password, wifPrivateKey);
            return new BitcoinKeyPair(encryptedPrivate, bitcoinAddress);
        } catch (IOException e) {
            throw new IllegalStateException("Error during keys conversion!", e);
        } catch (InterruptedException | AddressFormatException e) {
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
