package com.ahmadsaleh.bitcoinkeys.writer;

import com.ahmadsaleh.bitcoinkeys.Base58;
import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.PublicKey;

import static com.ahmadsaleh.bitcoinkeys.ByteArrayUtils.addToStart;
import static com.ahmadsaleh.bitcoinkeys.ByteArrayUtils.copyOfRange;
import static com.ahmadsaleh.bitcoinkeys.HashingUtils.*;
import static com.google.common.primitives.Bytes.concat;

/**
 * Created by Ahmad Y. Saleh on 7/20/17.
 */
public class BitcoinAddressWriter extends BufferedWriter {

    private static final byte MAIN_BITCOIN_NETWORK_VERSION = 0x00;

    public BitcoinAddressWriter(Writer writer) {
        super(writer);
    }

    public void write(PublicKey publicKey) throws IOException {
        byte[] sha256Hash = sha256Hash(KeysConversionUtils.asByteArray(publicKey));
        byte[] ripemd160Hash = ripemd160Hash(sha256Hash);
        byte[] versioned = addToStart(ripemd160Hash, MAIN_BITCOIN_NETWORK_VERSION);
        byte[] hashed = doubleSha256Hash(versioned);
        byte[] checkSum = copyOfRange(hashed, 0, 4);
        byte[] address = concat(versioned, checkSum);
        String base58Address = Base58.encode(address);
        write(base58Address);
    }
}
