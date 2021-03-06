package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.BIP38PrivateKey;
import org.bitcoinj.params.MainNetParams;

public class DecryptPrivateUseCase implements UseCase<EncryptedPrivateKeyBag, char[]> {

    @Override
    public char[] execute(EncryptedPrivateKeyBag encryptedPrivateKeyBag) {
        try {
            /**
             * bitcoinj uses Strings for passphrases, which is a security risk
             * (see https://github.com/bitcoinj/bitcoinj/issues/1456)
             */
            BIP38PrivateKey bip38PrivateKey = new BIP38PrivateKey(MainNetParams.get(), encryptedPrivateKeyBag.getEncryptedPrivateKey());
            ECKey ecKey = bip38PrivateKey.decrypt(new String(encryptedPrivateKeyBag.getPassword()));
            return ecKey.getPrivateKeyEncoded(MainNetParams.get()).toString().toCharArray();
        } catch (AddressFormatException e) {
            throw new DecryptionFailureException("failed to decrypt key", e);
        } catch (BIP38PrivateKey.BadPassphraseException e) {
            throw new InvalidPasswordException("failed to decrypt key", e);
        }
    }

    public static class DecryptionFailureException extends RuntimeException {
        public DecryptionFailureException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
