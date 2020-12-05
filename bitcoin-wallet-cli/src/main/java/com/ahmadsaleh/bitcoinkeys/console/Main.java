package com.ahmadsaleh.bitcoinkeys.console;

import com.ahmadsaleh.bitcoinkeys.console.utils.Color;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ahmad Y. Saleh on 7/22/17.
 */
public class Main {

    public static void main(String[] args) {
        printIntro();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String command = scanner.nextLine();
                String function = getFunction(command);
                List<CommandOption> optionList = parseOptions(command);
                CommandProcessorFactory.getFactory().getProcessor(function).process(optionList);
            } catch (InvalidCommandStructureException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static String getFunction(String command) {
        String[] splitted = StringUtils.split(command);
        return splitted.length > 0 ? splitted[0] : "";
    }

    private static List<CommandOption> parseOptions(String command) {
        List<CommandOption> optionList = new ArrayList<>();
        String[] splitted = StringUtils.split(command);
        if (splitted.length > 1 && !splitted[1].startsWith("-")) {
            throw new InvalidCommandStructureException("Invalid command, options should start with '-'");
        }
        CommandOption option = null;
        for (int i = 1; i < splitted.length; i++) {
            String part = splitted[i];
            if (part.startsWith("-")) {
                option = new CommandOption();
                optionList.add(option);
                option.setOption(part.substring(1, part.length()));
            }
            option.setArguments(part);
        }
        return optionList;
    }

    private static void printIntro() {
        System.out.print(Color.MAGENTA);
        System.out.println("  ___   _   _                _           __      __         _   _         _   \n" +
                " | _ ) (_) | |_   __   ___  (_)  _ _     \\ \\    / /  __ _  | | | |  ___  | |_ \n" +
                " | _ \\ | | |  _| / _| / _ \\ | | | ' \\     \\ \\/\\/ /  / _` | | | | | / -_) |  _|\n" +
                " |___/ |_|  \\__| \\__| \\___/ |_| |_||_|     \\_/\\_/   \\__,_| |_| |_| \\___|  \\__|\n");

        System.out.print(Color.RESET);
        System.out.println("\nAvailable commands:\n");
        CommandProcessorFactory.getFactory().getAllProcessors().stream().forEach(p -> p.printHelp());
    }

    private static class InvalidCommandStructureException extends RuntimeException {

        public InvalidCommandStructureException(String message) {
            super(message);
        }
    }
}
