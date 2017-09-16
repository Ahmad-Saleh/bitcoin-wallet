package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;
import net.bither.bitherj.crypto.ECKey;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CalculateAddressUseCase implements UseCase<PrivateKeyBag, String> {

    @Override
    public String exeute(PrivateKeyBag privateKeyBag) {
        CharSequence decrypt = new DecryptPrivateUseCase().exeute(privateKeyBag);
        return calculateAddress(decrypt);
    }

    private String calculateAddress(CharSequence walletImportFormat) {
        PrivateKey privateKey = KeysConversionUtils.toPrivateKey(walletImportFormat.toString());
        byte[] publicKeyBytes = ECKey.publicKeyFromPrivate(new BigInteger(1, KeysConversionUtils.asByteArray(privateKey)), false);
        PublicKey publicKey = KeysConversionUtils.asPublicKey(publicKeyBytes);
        return KeysConversionUtils.toBitcoinAddress(publicKey);
    }
}