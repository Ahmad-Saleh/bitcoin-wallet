package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.utils.Color;
import com.ahmadsaleh.bitcoinkeys.console.utils.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.EncryptPrivateUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;

import java.util.List;

public class EncryptCommandProcessor implements CommandProcessor {
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
            char[] password = ConsoleUtils.requestNewPassword();

            byte[] privateKey = KeysConversionUtils.fromWalletImportFormat(keyOption.getArguments());
            PrivateKeyBag privateKeyBag = new PrivateKeyBag(privateKey, password);
            System.out.printf("encrypted private key: %s", new EncryptPrivateUseCase().execute(privateKeyBag));
        } catch (EncryptPrivateUseCase.KeyEncryptionException e) {
            System.err.println("failed to encrypt key. Key and/or password are invalid");
        }
    }

    @Override
    public String getCommand() {
        return "encrypt";
    }

    @Override
    public void printHelp() {
        System.out.print(Color.CYAN);
        System.out.print("encrypt -key ");
        System.out.print(Color.RESET);
        System.out.print(Color.YELLOW);
        System.out.print("[private key]");
        System.out.print(Color.RESET);
        System.out.print(": encrypts a bitcoin private key using BIP-38.\n\t\texample: ");
        System.out.print(Color.CYAN);
        System.out.print("encrypt -key 5J3SDGJty2TfAm6yKe8vPAPwF7EVsG4SpZMUNbtPhg4mTdAAaDP");
        System.out.print(Color.RESET);
        System.out.println();
    }
}
