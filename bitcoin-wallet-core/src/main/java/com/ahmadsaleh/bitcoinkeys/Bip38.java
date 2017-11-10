package com.ahmadsaleh.bitcoinkeys;

import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCrypt;
import org.bitcoinj.core.*;
import org.bitcoinj.core.Base58;
import org.bitcoinj.params.MainNetParams;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

public class Bip38 {

    private static final NetworkParameters MAIN_NET_PARAMS = MainNetParams.get();

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static String encryptNoEC(byte[] passphrase, byte[] privateKeyBytes, boolean isCompressed)
            throws GeneralSecurityException, UnsupportedEncodingException, AddressFormatException {
        ECKey ktmp = ECKey.fromPrivate(privateKeyBytes, isCompressed);
        byte[] keyBytes = ktmp.getPrivKeyBytes();
        ECKey key = ECKey.fromPrivate(new BigInteger(1, keyBytes), isCompressed);
        String address = key.toAddress(MAIN_NET_PARAMS).toString();
        byte[] tmp = address.getBytes("ASCII");
        byte[] hash = HashingUtils.doubleSha256Hash(tmp);
        byte[] addressHash = Arrays.copyOfRange(hash, 0, 4);
        byte[] scryptKey = SCrypt.scrypt(passphrase, addressHash, 16384, 8, 8, 64);
        byte[] derivedHalf1 = Arrays.copyOfRange(scryptKey, 0, 32);
        byte[] derivedHalf2 = Arrays.copyOfRange(scryptKey, 32, 64);

        byte[] k1 = new byte[16];
        byte[] k2 = new byte[16];
        for (int i = 0; i < 16; i++) {
            k1[i] = (byte) (keyBytes[i] ^ derivedHalf1[i]);
            k2[i] = (byte) (keyBytes[i+16] ^ derivedHalf1[i+16]);
        }

        byte[] encryptedHalf1 = aesEncrypt(k1, derivedHalf2);
        byte[] encryptedHalf2 = aesEncrypt(k2, derivedHalf2);

        byte[] header = { 0x01, 0x42, (byte) (isCompressed ? 0xe0 : 0xc0) };
        byte[] encryptedPrivateKey = Bytes.concat(header, addressHash, encryptedHalf1, encryptedHalf2);

        return base58Check(encryptedPrivateKey);
    }

    public static String base58Check(byte [] b) throws NoSuchAlgorithmException {
        byte[] r = new byte[b.length + 4];
        System.arraycopy(b, 0, r, 0, b.length);
        System.arraycopy(HashingUtils.doubleSha256Hash(b), 0, r, b.length, 4);
        return Base58.encode(r);
    }

    public static byte[] aesEncrypt(byte[] plaintext, byte[] key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
        Key aesKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return cipher.doFinal(plaintext);
    }
}