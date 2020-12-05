package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.utils.Color;
import com.ahmadsaleh.bitcoinkeys.console.utils.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.GenerateKeysUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.BitcoinKeyPair;

import java.util.List;

public class GenerateCommandProcessor implements CommandProcessor {

    @Override
    public void process(List<CommandOption> options) {
        char[] password = ConsoleUtils.requestNewPassword();
        BitcoinKeyPair keyPair = new GenerateKeysUseCase().execute(password);
        System.out.printf("bitcoin public address: %s\n", keyPair.getPublicBitcoinAddress());
        System.out.printf("encrypted private: %s\n", keyPair.getEncryptedPrivate());
    }

    @Override
    public String getCommand() {
        return "generate";
    }

    @Override
    public void printHelp() {
        System.out.print(Color.CYAN);
        System.out.print("generate");
        System.out.print(Color.RESET);
        System.out.print(": generates a bitcoin key pair.");
        System.out.println();
    }
}
