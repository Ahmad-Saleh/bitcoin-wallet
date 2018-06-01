package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.KeysConversionUtils;
import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.DecryptPrivateUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.EncryptPrivateUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.EncryptedPrivateKeyBag;
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
}
