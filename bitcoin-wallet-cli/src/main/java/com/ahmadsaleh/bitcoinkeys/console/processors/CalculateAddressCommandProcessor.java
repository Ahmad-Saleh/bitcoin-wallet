package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.utils.Color;
import com.ahmadsaleh.bitcoinkeys.console.utils.ConsoleUtils;
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

            System.out.printf("bitcoin public address: %s\n", publicAddress);
        } catch (DecryptPrivateUseCase.DecryptionFailureException e) {
            System.err.println("failed to decrypt key. Key and/or password are invalid");
        }
    }

    @Override
    public String getCommand() {
        return "calculate";
    }

    @Override
    public void printHelp() {
        System.out.print(Color.CYAN);
        System.out.print("calculate -key ");
        System.out.print(Color.RESET);
        System.out.print(Color.YELLOW);
        System.out.print("[encrypted private key]");
        System.out.print(Color.RESET);
        System.out.print(": calculates the bitcoin public key from an encrypted private key (BIP-38).\n\t\texample: ");
        System.out.print(Color.CYAN);
        System.out.print("calculate -key 6PRSTTzmeteWVaaUq4368sz5NqLJdA7HUsps335aJKCPdoWb4sBdAweTA9");
        System.out.print(Color.RESET);
        System.out.println();
    }
}
