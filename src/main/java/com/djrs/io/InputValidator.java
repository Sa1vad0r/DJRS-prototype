package com.djrs.io;

import java.util.Scanner;

/**
 * Provides utility methods for validating and reading input values from the console. Includes overloaded numeric range
 * checks for {@code short}, {@code int}, {@code float}, and {@code double} types, along with input readers that
 * continue prompting until valid data is provided.
 */
public class InputValidator {

    /**
     * Shared scanner used to read input from standard input across all read methods in this class.
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Checks whether a short value falls within the given inclusive range.
     *
     * @param number the value to check
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @return {@code true} if the value is within the range, {@code false} otherwise
     */
    public static boolean isNumberBetween(short number, short from, short to) {
        return (number >= from && number <= to);
    }

    /**
     * Checks whether an int value falls within the given inclusive range.
     *
     * @param number the value to check
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @return {@code true} if the value is within the range, {@code false} otherwise
     */
    public static boolean isNumberBetween(int number , int from, int to) {
        return (number >= from && number <= to);
    }

    /**
     * Checks whether a float value falls within the given inclusive range.
     *
     * @param number the value to check
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @return {@code true} if the value is within the range, {@code false} otherwise
     */
    public static boolean isNumberBetween(float number, float from, float to) {
        return (number >= from && number <= to);
    }

    /**
     * Checks whether a double value falls within the given inclusive range.
     *
     * @param number the value to check
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @return {@code true} if the value is within the range, {@code false} otherwise
     */
    public static boolean isNumberBetween(double number, double from, double to) {
        return (number >= from && number <= to);
    }

    /**
     * Reads a single character from standard input.
     *
     * @param errorMessage the message to display if the input is invalid
     * @return the first character of the entered line
     */
    public static char readChar(String errorMessage) {
        return scanner.nextLine().charAt(0);
    }

    /**
     * Repeatedly prompts for input until a valid integer is entered.
     *
     * @param errorMessage the message to display when the input is not a valid integer
     * @return the integer entered by the user
     */
    public static int readIntNumber(String errorMessage) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ignore) {}
            System.out.println(errorMessage); // why not in catch?
        }
    }

    /**
     * Repeatedly prompts for input until a valid integer is entered and falls within the specified range.
     *
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @param errorMessage the message to display when the input is not a valid integer or falls outside the range
     * @return the integer entered by the user, guaranteed to be within the specified range
     */
    public static int readIntNumberBetween(int from, int to, String errorMessage) {
        int number = readIntNumber(errorMessage);

        while (!isNumberBetween(number, from, to))
        {
            System.out.println(errorMessage);
            number = readIntNumber(errorMessage);
        }
        return number;
    }

    /**
     * Repeatedly prompts for input until a valid double is entered.
     *
     * @param errorMessage the message to display when the input is not a valid double
     * @return the double entered by the user
     */
    public static double readDoubleNumber(String errorMessage) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException ignore) {}
            System.out.println(errorMessage); // why not in catch?
        }
    }

    /**
     * Repeatedly prompts for input until a valid double is entered and falls within the specified range.
     *
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @param errorMessage the message to display when the input is not a valid double or falls outside the range
     * @return the double entered by the user, guaranteed to be within the specified range
     */
    public static double readDoubleNumberBetween(double from, double to, String errorMessage) {
        double number = readDoubleNumber(errorMessage);

        while (!isNumberBetween(number, from, to)) {
            System.out.println(errorMessage);
            number = readDoubleNumber(errorMessage);
        }
        return number;
    }

    /**
     * Repeatedly prompts for input until a valid short is entered.
     *
     * @param errorMessage the message to display when the input is not a valid short
     * @return the short entered by the user
     */
    public static short readShortNumber(String errorMessage) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Short.parseShort(line);
            } catch (NumberFormatException ignore) {}
            System.out.println(errorMessage); // why not in catch?
        }
    }

    /**
     * Repeatedly prompts for input until a valid float is entered.
     *
     * @param errorMessage the message to display when the input is not a valid float
     * @return the float entered by the user
     */
    public static float readFloatNumber(String errorMessage) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Float.parseFloat(line);
            } catch (NumberFormatException ignore) {}
            System.out.println(errorMessage); // why not in catch?
        }
    }

    /**
     * Repeatedly prompts for input until a valid short is entered and falls within the specified range.
     *
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @param errorMessage the message to display when the input is not a valid short or falls outside the range
     * @return the short entered by the user, guaranteed to be within the specified range
     */
    public static short readShortNumberBetween(short from, short to, String errorMessage) {
        short number = readShortNumber(errorMessage);

        while (!isNumberBetween(number, from, to))
        {
            System.out.println(errorMessage);
            number = readShortNumber(errorMessage);
        }
        return number;
    }

    /**
     * Repeatedly prompts for input until a valid float is entered and falls within the specified range.
     *
     * @param from the lower bound of the range, inclusive
     * @param to the upper bound of the range, inclusive
     * @param errorMessage the message to display when the input is not a valid float or falls outside the range
     * @return the float entered by the user, guaranteed to be within the specified range
     */
    public static float readFloatNumberBetween(float from, float to, String errorMessage) {
        float number = readFloatNumber(errorMessage);

        while (!isNumberBetween(number, from, to))
        {
            System.out.println(errorMessage);
            number = readFloatNumber(errorMessage);
        }
        return number;
    }

    /**
     * Reads a single line of text from standard input.
     *
     * @return the trimmed text entered by the user
     */
    public static String readString() {
        return scanner.nextLine().trim();
    }

    /**
     * Repeatedly prompts for input until a recognizable boolean value is entered.
     * Accepts {@code "true"}, {@code "false"}, {@code "yes"}, {@code "no"}, and {@code "y"}, {@code "n"} (case-insensitive).
     *
     * @return {@code true} if the input represents a truthy value, {@code false} otherwise
     */
    public static boolean readBooleanValue() {
        while (true) {
            String line = scanner.nextLine().trim();

            if (Boolean.parseBoolean(line)) { return true; }

            if (line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("y")) {
                return true;
            } if (line.equalsIgnoreCase("no") || line.equalsIgnoreCase("false")
                    || line.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Invalid input. Type yes/no (y/n) or true/false. Has Helipad?: "); // why not in catch?
        }
    }
}
