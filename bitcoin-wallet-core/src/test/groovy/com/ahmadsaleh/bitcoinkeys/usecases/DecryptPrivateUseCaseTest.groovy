package com.ahmadsaleh.bitcoinkeys.usecases

import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag
import spock.lang.Specification
import spock.lang.Unroll

class DecryptPrivateUseCaseTest extends Specification {

    @Unroll
    def "given a private key and a valid password, when decrypting the key, then return correct private"() {
        setup:
        def decryptPrivateUseCase = new DecryptPrivateUseCase()
        def privateKeyBag = new PrivateKeyBag(encryptedPrivate, password.toCharArray())

        when:
        def resultedAddress = decryptPrivateUseCase.exeute(privateKeyBag)

        then:
        expectedPrivate.equals resultedAddress.toString()

        where:
        encryptedPrivate                                             | password             || expectedPrivate
        "6PRTYuoiWHZJvEzBpgVtq2dMS7xVDp1Fj3LqB9yPkn9jGabAFdRmJVqYgp" | "this is a password" || "5KFyXtVZYhTZhiZh6yMBnawwQKwmfwSuffxEfmKAJUyAGdVYZRb"
        "6PRVPxJptipL42JZXRXGTrV6hwLeHffCrPV1yZEBHCuCmyZPeAr2XqTFeC" | "12njMM"             || "5JrD5PPTnidyCyNp5Qf2fHviY69sw8SnmaRb1wcsLwFjqNxVDhG"
        "6PRMYC6ANFExFDUPFGhQZseV5BcZqm9UT7pvcup2BUntnG9D9Peaw6iPQ2" | "arwa"               || "5KVcm2gXWjyngbX2iLM9U1hHnrxZ7bhL4HKJsgDfukztDCFv9Ew"
        "6PRTmUR5DFZJa5vyVyxzaK1FKKWrDUkHb5EErsnsMznxeJM8DoJBKsr1s3" | "P@ssw0rd"           || "5KVyoLVwvsvLgdMLc69rNuUVrxweymbxCKgJpGtCbo4hDBA6ms2"
    }

    @Unroll
    def "given a private key and an invalid password, when decrypting the key, then InvalidPasswordException should be thrown"() {
        setup:
        def decryptPrivateUseCase = new DecryptPrivateUseCase()
        def privateKeyBag = new PrivateKeyBag(encryptedPrivate, password.toCharArray())

        when:
        decryptPrivateUseCase.exeute(privateKeyBag)

        then:
        thrown(DecryptPrivateUseCase.InvalidPasswordException.class)

        where:
        encryptedPrivate                                             | password
        "6PRKXaLiorxj4GcVhBJgeGsnyTZpgP1NK2eho5iLeaZNH9qQ9AbryDQv7y" | "test"
        "6PRVdERwxRUpGPK3ie141jgU4Q14Udg3cH5VHu2e7qCHW4qtiZzekmM89a" | "testz"
        "6PRTYuoiWHZJvEzBpgVtq2dMS7xVDp1Fj3LqB9yPkn9jGabAFdRmJVqYgp" | " arwa"
        "6PRQgsoWUJABaewYS1V3HjJUHcVSLhQh8oV5gDWshbqP145QKXZwkTXw3Y" | "KKOkdf1"
    }

    @Unroll
    def "given an invalid private key and a password, when decrypting the key, then DecryptionFailureException should be thrown"() {
        setup:
        def decryptPrivateUseCase = new DecryptPrivateUseCase()
        def privateKeyBag = new PrivateKeyBag(encryptedPrivate, password.toCharArray())

        when:
        decryptPrivateUseCase.exeute(privateKeyBag)

        then:
        thrown(DecryptPrivateUseCase.DecryptionFailureException.class)

        where:
        encryptedPrivate                                             | password
        "kokoald3eef"                                                | "koko"
        ""                                                           | "test"
        "3PRQgsoWUJABaewYS1V3HjJUHcVSLhQh8oV5gDWshbqP145QKXZwkTXw3Y" | "arwa"
        "6PRTmUR5DFZJa5vyVyxzaK1FKKWrDokHb5EErsnsMznxeJM8DoJBKsr1sC" | "P@ssw0rd"
    }
}
