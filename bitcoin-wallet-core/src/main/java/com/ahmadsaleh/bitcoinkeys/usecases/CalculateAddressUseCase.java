package com.ahmadsaleh.bitcoinkeys.usecases;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointUtil;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CalculateAddressUseCase implements UseCase<PrivateKeyBag, String> {

    @Override
    public String exeute(PrivateKeyBag privateKeyBag) {
        char[] decrypt = new DecryptPrivateUseCase().exeute(privateKeyBag);
        return calculateAddress(decrypt);
    }

    private String calculateAddress(char[] walletImportFormat) {
        PrivateKey privateKey = KeysConversionUtils.toPrivateKey(walletImportFormat);
        byte[] publicKeyBytes = KeysConversionUtils.publicKeyFromPrivate(privateKey, false);
        PublicKey publicKey = KeysConversionUtils.asPublicKey(publicKeyBytes);
        return KeysConversionUtils.toBitcoinAddress(publicKey);
    }
}