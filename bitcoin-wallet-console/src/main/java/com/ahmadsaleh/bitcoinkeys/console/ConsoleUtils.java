package com.ahmadsaleh.bitcoinkeys.console;


import java.util.Arrays;

public class ConsoleUtils {

    public static char[] requestNewPassword() {
        char[] password;
        char[] passwordConfirm;
        do {
            password = System.console().readPassword("Enter password: ");
            passwordConfirm = System.console().readPassword("Confirm password: ");
            if (!Arrays.equals(password, passwordConfirm)) {
                System.err.println("Password and confirmation do not match!");
            }
        } while (!Arrays.equals(password, passwordConfirm));
        return password;
    }

    public static char[] requestPassword() {
        return System.console().readPassword("Enter password: ");
    }
}
