package com.ahmadsaleh.bitcoinkeys.console.processors;

import com.ahmadsaleh.bitcoinkeys.console.CommandOption;
import com.ahmadsaleh.bitcoinkeys.console.CommandProcessor;
import com.ahmadsaleh.bitcoinkeys.console.utils.Color;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class ExitCommandProcessor implements CommandProcessor {

    @Override
    public void process(List<CommandOption> options) {
        if(!CollectionUtils.isEmpty(options)){
            System.out.println("invalid command! no options are expected");
            return;
        }
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public String getCommand() {
        return "exit";
    }

    @Override
    public void printHelp() {
        System.out.print(Color.CYAN);
        System.out.print("exit");
        System.out.print(Color.RESET);
        System.out.print(": Exit the application.");
        System.out.println();
    }
}
