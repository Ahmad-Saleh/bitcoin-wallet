package com.ahmadsaleh.bitcoinkeys;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by Ahmad Y. Saleh on 7/21/17.
 */
public final class ByteArrayUtils {

    private ByteArrayUtils() {
        throw new UnsupportedOperationException("utility class is not supposed to be used this way!");
    }

    public static byte[] addToStart(byte[] data, byte b) {
        byte[] result = new byte[data.length + 1];
        System.arraycopy(data, 0, result, 1, data.length);
        result[0] = b;
        return result;
    }

    public static byte[] copyOfRange(byte[] data, int from, int to) {
        return Arrays.copyOfRange(data, from, to);
    }

    /**
     * The regular {@link BigInteger#toByteArray()} method isn't quite what we often
     * need: it appends a leading zero to indicate that the number is positive and may need padding.
     */
    public static byte[] bigIntegerToBytes(BigInteger b, int numBytes) {
        if (b == null) {
            return null;
        }
        byte[] bytes = new byte[numBytes];
        byte[] biBytes = b.toByteArray();
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    public static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }
}
