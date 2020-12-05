package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.utils.Color;
import com.ahmadsaleh.bitcoinkeys.console.utils.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.DecryptPrivateUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag;

import java.util.List;

public class DecryptCommandProcessor implements CommandProcessor {
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
            System.out.printf("plain private key: %s", new String(new DecryptPrivateUseCase().execute(encryptedPrivateKeyBag)));
        } catch (DecryptPrivateUseCase.DecryptionFailureException e) {
            System.err.println("failed to decrypt key. Key and/or password are invalid");
        }
    }

    @Override
    public String getCommand() {
        return "decrypt";
    }

    @Override
    public void printHelp() {
        System.out.print(Color.CYAN);
        System.out.print("decrypt -key ");
        System.out.print(Color.RESET);
        System.out.print(Color.YELLOW);
        System.out.print("[encrypted private key]");
        System.out.print(Color.RESET);
        System.out.print(": decrypts a private key that is encrypted using BIP-38.\n\t\texample: ");
        System.out.print(Color.CYAN);
        System.out.print("decrypt -key 6PRSTTzmeteWVaaUq4368sz5NqLJdA7HUsps335aJKCPdoWb4sBdAweTA9");
        System.out.print(Color.RESET);
        System.out.println();
    }
}
