package com.ahmadsaleh.bitcoinkeys.usecases

import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag
import spock.lang.Specification
import spock.lang.Unroll

class CalculateAddressUseCaseTest extends Specification {

    @Unroll
    def "given a private key and a valid password, when calculating public address, then return correct address"() {
        setup:
        def calculateAddressUseCase = new CalculateAddressUseCase()
        def privateKeyBag = new EncryptedPrivateKeyBag(encryptedPrivate, password.toCharArray())

        when:
        def resultedAddress = calculateAddressUseCase.execute(privateKeyBag)

        then:
        expectedAddress.equals resultedAddress

        where:
        encryptedPrivate                                             | password   || expectedAddress
        "6PRKXaLiorxj4GcVhBJgeGsnyTZpgP1NK2eho5iLeaZNH9qQ9AbryDQv7y" | "koko"     || "14WoeshsqeXKV9UyMRKY2EURZo7SH5niFK"
        "6PRVdERwxRUpGPK3ie141jgU4Q14Udg3cH5VHu2e7qCHW4qtiZzekmM89a" | "test"     || "1FQS5tvmyRQJvGnX7EhyVXgh8zmtJxAwU1"
        "6PRMYC6ANFExFDUPFGhQZseV5BcZqm9UT7pvcup2BUntnG9D9Peaw6iPQ2" | "arwa"     || "1D3k2dBMdzJdefNv3LbsP29A9STQ8VzLFG"
        "6PRTmUR5DFZJa5vyVyxzaK1FKKWrDUkHb5EErsnsMznxeJM8DoJBKsr1s3" | "P@ssw0rd" || "1FyGTLCUud9t71en9s3kQuxunfPrYUaCsX"
    }

    @Unroll
    def "given a private key and an invalid password, when calculating public address, then InvalidPasswordException should be thrown"() {
        setup:
        def calculateAddressUseCase = new CalculateAddressUseCase()
        def privateKeyBag = new EncryptedPrivateKeyBag(encryptedPrivate, password.toCharArray())

        when:
        calculateAddressUseCase.execute(privateKeyBag)

        then:
        thrown(DecryptPrivateUseCase.InvalidPasswordException.class)

        where:
        encryptedPrivate                                             | password
        "6PRKXaLiorxj4GcVhBJgeGsnyTZpgP1NK2eho5iLeaZNH9qQ9AbryDQv7y" | "test"
        "6PRVdERwxRUpGPK3ie141jgU4Q14Udg3cH5VHu2e7qCHW4qtiZzekmM89a" | "testz"
        "6PRMYC6ANFExFDUPFGhQZseV5BcZqm9UT7pvcup2BUntnG9D9Peaw6iPQ2" | " arwa"
        "6PRTmUR5DFZJa5vyVyxzaK1FKKWrDUkHb5EErsnsMznxeJM8DoJBKsr1s3" | ""
    }

    @Unroll
    def "given an invalid private key and a password, when calculating public address, then DecryptionFailureException should be thrown"() {
        setup:
        def calculateAddressUseCase = new CalculateAddressUseCase()
        def privateKeyBag = new EncryptedPrivateKeyBag(encryptedPrivate, password.toCharArray())

        when:
        calculateAddressUseCase.execute(privateKeyBag)

        then:
        thrown(DecryptPrivateUseCase.DecryptionFailureException.class)

        where:
        encryptedPrivate                                             | password
        "v7y"                                                        | "koko"
        ""                                                           | "test"
        "7PRMYC6ANFExFDUPFGhQZseV5BcZqm9UT7pvcup2BUntnG9D9Peaw6iPQ2" | "arwa"
        "6PRTmUR5DFZJa5vyVyxzaK1FKKWrDUkHb5EErsnsMznxeJM8DoJBKsr1s0" | "P@ssw0rd"
    }
}
