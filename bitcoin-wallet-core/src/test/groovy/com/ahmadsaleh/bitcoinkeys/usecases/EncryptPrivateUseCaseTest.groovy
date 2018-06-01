package com.ahmadsaleh.bitcoinkeys.usecases

import com.ahmadsaleh.bitcoinkeys.ECDSAEncryptionUtils
import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils
import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag
import org.bitcoinj.core.ECKey
import org.bitcoinj.params.MainNetParams
import spock.lang.Specification
import spock.lang.Unroll

class EncryptPrivateUseCaseTest extends Specification {

    @Unroll
    def "given a private key and a password, when encrypt keys then decrypt, then original and result should match"() {
        setup:
        def encryptPrivateUseCase = new EncryptPrivateUseCase()

        when:
        def originalPrivateKey = ECKey.fromPrivate(privateKey, false).getPrivateKeyEncoded(MainNetParams.get()).toString()
        def encryptedKey = encryptPrivateUseCase.execute(new PrivateKeyBag(privateKey, password.toCharArray()))
        def decryptedKey = new DecryptPrivateUseCase().execute(new EncryptedPrivateKeyBag(encryptedKey, password.toCharArray()))

        then:
        new String(decryptedKey).equals originalPrivateKey

        where:
        privateKey                                                                           | password
        KeysConversionUtils.asByteArray(ECDSAEncryptionUtils.generateKeyPair().getPrivate()) | "this is a password"
        KeysConversionUtils.asByteArray(ECDSAEncryptionUtils.generateKeyPair().getPrivate()) | "12njMM"
        KeysConversionUtils.asByteArray(ECDSAEncryptionUtils.generateKeyPair().getPrivate()) | "arwa"
        KeysConversionUtils.asByteArray(ECDSAEncryptionUtils.generateKeyPair().getPrivate()) | "P@ssw0rd"
    }
}