package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.CalculateAddressUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.DecryptPrivateUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag;

import java.util.List;

public class CalculateAddressCommandProcessor implements CommandProcessor {

    @Override
    public void process(List<CommandOption> options) {
        if (options.size() != 1) {
            System.err.printf("Invalid command, expected one option '-key'\n");
            return;
        }
        CommandOption keyOption = options.get(0);
        if (!keyOption.getOption().equals("key")) {
            System.err.printf("Invalid option. expected '-key' but found '-%s'\n", keyOption.getOption());
            return;
        }

        try {
            char[] password = ConsoleUtils.requestPassword();
            EncryptedPrivateKeyBag encryptedPrivateKeyBag = new EncryptedPrivateKeyBag(keyOption.getArguments(), password);
            String publicAddress = new CalculateAddressUseCase().execute(encryptedPrivateKeyBag);

            System.out.printf("address: %s\n", publicAddress);
        } catch (DecryptPrivateUseCase.DecryptionFailureException e) {
            System.err.println("failed to decrypt key. Key and/or password are invalid");
        }
    }

    @Override
    public String getCommand() {
        return "calculate";
    }

}
