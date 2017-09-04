package com.ahmadsaleh.bitcoinkeys.writer

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils
import com.ahmadsaleh.bitcoinkeys.writer.BitcoinAddressWriter
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Ahmad Y. Saleh on 7/20/17.
 */
class BitcoinAddressWriterTest extends Specification {

    @Unroll
    def "given a BitcoinAddressWriter, when writing a PublicKey, then the correct address is generated"() {
        setup:
        def stringWriter = new StringWriter()
        def writer = new BitcoinAddressWriter(stringWriter)

        when:
        def publicKey = KeysConversionUtils.asPublicKey(publicHex);
        writer.write(publicKey)
        writer.flush()

        then:
        bitcoinAddress.equals(stringWriter.toString())

        where:
        publicHex                                                                                                                            || bitcoinAddress
        "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6" || "16UwLL9Risc3QfPqBUvKofHmBQ7wMtjvM"
        "044A3288CEECBA6F4038D009D7B092104E8574ABBD3E6479390D9E8C06579ECFDDDB2C76D7CDAE36F50D9822920F86EC7BABC3824DFFA57538DA397444FC963225" || "18j58qyAkFpWUCbjkikUCaxHetkDGhY9iG"
        "0432FEB99DF66BB986685E4D85453B67035A069491DF6BE0579667D42BB8437CAE6DAF1B6B8B588B391711B026E0BEA1815C90AD0CDDC07A6BE5B810D27A0D130D" || "1MM8ybcnqtWV1TfUqZP8a1axb2X2K9x5gU"
        "048EFF24DE41C11C63564B1BD764792811A9E448B2E7F87BB200FB7012B3AB3B3CB0ED5072BBC41C76A6B7A28920D905D34968F7324811FF13D658D481D78964DA" || "15CuWpqo4q5TH1zr2xNdjmmbrudYKHW4Td"
        "047C2FDC4C13A4BD10BCE11F89B6A9CD43460F9C16447B2670CFFA3C8CE65DFAED9520A3EDC2AC8179456BD0C9FECC946EFB29D535B29FF5297A932717E772C483" || "1Ep8iSEHZRHF2EtopHfww3BRpLS6LjHkrz"
        "04AE24B425E80CE1D312BE458BE6D8B1C1E3A6EF720365B6F3B6A42102D60327494E85872B7CB45426752D1B078357E5963D9148E2F44ECABF209EF245C1B3710A" || "14kTiLHiFKi9bN3MxR76gqkSqKygJAdHdz"
    }
}
