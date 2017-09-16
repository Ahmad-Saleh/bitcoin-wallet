package com.ahmadsaleh.bitcoinkeys.usecases

import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag
import net.bither.bitherj.crypto.SecureCharSequence
import spock.lang.Specification
import spock.lang.Unroll

import java.security.SecureRandom

class GenerateKeysUseCaseTest extends Specification {

    @Unroll
    def "given a password, when generating keys, then matching public/private keys should be returned"() {
        setup:
        def generateKeysUseCase = new GenerateKeysUseCase()

        when:
        def bitcoinKeyPair = generateKeysUseCase.exeute(password)

        def calculatedPublic = new CalculateAddressUseCase().exeute(new PrivateKeyBag(bitcoinKeyPair.getEncryptedPrivate(), password))
        then:
        calculatedPublic.equals bitcoinKeyPair.publicBitcoinAddress

        where:
        password << [new SecureCharSequence("this is a password")
                     , new SecureCharSequence("12njMM")
                     , new SecureCharSequence("arwa")
                     , new SecureCharSequence("P@ssw0rd")
                     , generateRandomPassword()
                     , generateRandomPassword()
                     , generateRandomPassword()]
    }

    SecureCharSequence generateRandomPassword() {
        def bytes = new byte[20]
        new SecureRandom().nextBytes(bytes)
        new SecureCharSequence(new String(bytes, "UTF-8"))
    }
}
