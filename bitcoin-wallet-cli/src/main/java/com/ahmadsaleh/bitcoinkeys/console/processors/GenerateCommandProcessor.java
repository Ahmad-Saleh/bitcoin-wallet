package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.GenerateKeysUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.BitcoinKeyPair;

import java.util.List;

public class GenerateCommandProcessor implements CommandProcessor {

    @Override
    public void process(List<CommandOption> options) {
        char[] password = ConsoleUtils.requestNewPassword();
        BitcoinKeyPair keyPair = new GenerateKeysUseCase().execute(password);
        System.out.printf("bitcoin address: %s\n", keyPair.getPublicBitcoinAddress());
        System.out.printf("encrypted private: %s\n", keyPair.getEncryptedPrivate());
    }

    @Override
    public String getCommand() {
        return "generate";
    }
}
