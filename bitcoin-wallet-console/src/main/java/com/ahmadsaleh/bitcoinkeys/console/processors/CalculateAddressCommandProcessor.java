package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.ConsoleUtils;
import com.ahmadsaleh.bitcoinkeys.usecases.CalculateAddressUseCase;
import com.ahmadsaleh.bitcoinkeys.usecases.to.PrivateKeyBag;
import net.bither.bitherj.crypto.SecureCharSequence;

import java.util.List;

public class CalculateAddressCommandProcessor implements CommandProcessor {

    @Override
    public void process(List<CommandOption> options) {
        if (options.size() != 1) {
            System.err.printf("Invalid command, expected one option '-private'\n");
            return;
        }
        CommandOption keyOption = options.get(0);
        if (!keyOption.getOption().equals("private")) {
            System.err.printf("Invalid option. expected '-private' but found '-%s'\n", keyOption.getOption());
            return;
        }

        CharSequence password = ConsoleUtils.requestPassword();
        CalculateAddressUseCase calculateAddressUseCase = new CalculateAddressUseCase();
        String publicAddress = calculateAddressUseCase.exeute(
                new PrivateKeyBag.Builder()
                .encryptedPrivateKey(keyOption.getArguments())
                .password(new SecureCharSequence(password)).build());

        System.out.printf("address: %s\n", publicAddress);
    }

    @Override
    public String getCommand() {
        return "calculate";
    }

}
