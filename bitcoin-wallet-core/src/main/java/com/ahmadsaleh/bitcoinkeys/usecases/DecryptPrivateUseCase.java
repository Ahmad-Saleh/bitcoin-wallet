package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;
import net.bither.bitherj.crypto.SecureCharSequence;
import net.bither.bitherj.crypto.bip38.Bip38;
import net.bither.bitherj.exception.AddressFormatException;

public class DecryptPrivateUseCase implements UseCase<PrivateKeyBag, SecureCharSequence> {

    @Override
    public SecureCharSequence exeute(PrivateKeyBag privateKeyBag) {
        try {
            SecureCharSequence decrypt = Bip38.decrypt(privateKeyBag.getEncryptedPrivateKey(), privateKeyBag.getPassword());
            if (decrypt == null) {
                throw new DecryptionFailureException("failed to decrypt key");
            }
            return decrypt;
        } catch (InterruptedException | AddressFormatException e) {
            throw new DecryptionFailureException("failed to decrypt key");
        }
    }

    public static class DecryptionFailureException extends RuntimeException {
        public DecryptionFailureException(String message) {
            super(message);
        }
    }
}
