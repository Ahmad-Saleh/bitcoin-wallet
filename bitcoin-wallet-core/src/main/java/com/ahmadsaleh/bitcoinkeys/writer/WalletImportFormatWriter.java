package com.ahmadsaleh.bitcoinkeys.writer;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import org.bitcoinj.core.Base58;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.PrivateKey;

import static com.ahmadsaleh.bitcoinkeys.ByteArrayUtils.addToStart;
import static com.ahmadsaleh.bitcoinkeys.ByteArrayUtils.copyOfRange;
import static com.ahmadsaleh.bitcoinkeys.HashingUtils.doubleSha256Hash;
import static com.google.common.primitives.Bytes.concat;

/**
 * Created by Ahmad Y. Saleh on 7/20/17.
 */
public class WalletImportFormatWriter extends BufferedWriter {

    private static final byte MAIN_BITCOIN_NETWORK_VERSION = (byte) 0x80;

    public WalletImportFormatWriter(Writer writer) {
        super(writer);
    }

    public void write(PrivateKey privateKey) throws IOException {
        byte[] versioned = addToStart(KeysConversionUtils.asByteArray(privateKey), MAIN_BITCOIN_NETWORK_VERSION);
        byte[] hashed = doubleSha256Hash(versioned);
        byte[] checkSum = copyOfRange(hashed, 0, 4);
        byte[] wif = concat(versioned, checkSum);
        String base58Wif = Base58.encode(wif);
        write(base58Wif);
    }
}
