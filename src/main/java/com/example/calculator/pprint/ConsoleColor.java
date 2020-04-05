package com.example.calculator.pprint;

@SuppressWarnings("unused")
public class ConsoleColor {
    public static final String BACK_BLACK = "\033[40m";
    public static final String BACK_WHITE = "\033[47m";
    public static final String WHITE = "\033[37m";
    public static final String BOLD = "\033[1m";
    public static final String GREEN = "\033[32m";
    public static final String BLUE = "\033[34m";
    public static final String BRIGHT_BLUE = "\033[34;1m";
    public static final String MAGENTA = "\033[35m";
    public static final String RED = "\033[31m";
    public static final String END = "\033[0m";

    public static String wrap(String c, String s) {
        return c + s + END;
    }
}
