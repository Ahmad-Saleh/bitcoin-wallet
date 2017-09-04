package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;
import net.bither.bitherj.crypto.ECKey;
import net.bither.bitherj.crypto.bip38.Bip38;
import net.bither.bitherj.exception.AddressFormatException;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CalculateAddressUseCase implements UseCase<PrivateKeyBag, String> {

    @Override
    public String exeute(PrivateKeyBag privateKeyBag) {
        try {
            CharSequence decrypt = Bip38.decrypt(privateKeyBag.getEncryptedPrivateKey(), privateKeyBag.getPassword());
            if (decrypt == null) {
                throw new IllegalStateException("failed to decrypt key, seems to be an invalid password!");
            }
            return calculateAddress(decrypt.toString());
        } catch (InterruptedException | AddressFormatException e) {
            throw new IllegalStateException("Error while decrypting key!", e);
        }
    }

    private String calculateAddress(String walletImportFormat) {
        PrivateKey privateKey = KeysConversionUtils.toPrivateKey(walletImportFormat);
        byte[] publicKeyBytes = ECKey.publicKeyFromPrivate(new BigInteger(1, KeysConversionUtils.asByteArray(privateKey)), false);
        PublicKey publicKey = KeysConversionUtils.asPublicKey(publicKeyBytes);
        return KeysConversionUtils.toBitcoinAddress(publicKey);
    }
}