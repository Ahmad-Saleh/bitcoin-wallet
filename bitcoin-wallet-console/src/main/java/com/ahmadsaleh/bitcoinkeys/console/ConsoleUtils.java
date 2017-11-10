package com.ahmadsaleh.bitcoinkeys.console;


import java.util.Arrays;
import java.util.Scanner;

public class ConsoleUtils {

    public static char[] requestNewPassword() {
        char[] password;
        char[] passwordConfirm;
        do {
            password = readPassword("Enter password: ");
            passwordConfirm = readPassword("Confirm password: ");
            if (!Arrays.equals(password, passwordConfirm)) {
                System.err.println("Password and confirmation do not match!");
            }
        } while (!Arrays.equals(password, passwordConfirm));
        return password;
    }

    public static char[] requestPassword() {
        return readPassword("Enter password: ");
    }

    private static char[] readPassword(String message) {
        if (System.console() == null) {
            System.out.println(message + " (password will be visible)");
            return new Scanner(System.in).nextLine().toCharArray();
        } else {
            return System.console().readPassword(message);
        }
    }
}
