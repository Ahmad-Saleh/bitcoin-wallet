package com.ahmadsaleh.bitcoinkeys.usecases

import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag
import spock.lang.Specification
import spock.lang.Unroll

import java.security.SecureRandom

class GenerateKeysUseCaseTest extends Specification {

    @Unroll
    def "given a password, when generating keys, then matching public/private keys should be returned"() {
        setup:
        def generateKeysUseCase = new GenerateKeysUseCase()

        when:
        def bitcoinKeyPair = generateKeysUseCase.exeute(password.toCharArray())

        def calculatedPublic = new CalculateAddressUseCase().exeute(new PrivateKeyBag(bitcoinKeyPair.getEncryptedPrivate(), password.toCharArray()))
        then:
        calculatedPublic.equals bitcoinKeyPair.publicBitcoinAddress

        where:
        password << ["this is a password"
                     , "12njMM"
                     , "arwa"
                     , "P@ssw0rd"
                     , generateRandomPassword()
                     , generateRandomPassword()
                     , generateRandomPassword()]
    }

    String generateRandomPassword() {
        def bytes = new byte[20]
        new SecureRandom().nextBytes(bytes)
        new String(bytes, "UTF-8")
    }
}
