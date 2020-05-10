package org.fugalang.core.parser.context;

public class ErrorFormatter {
    public static String format(String message, int lineno, String line, int col) {
        return "Line " + lineno + ":\n    " + line + "\n" +
                " ".repeat(col + 4) + "^\nError: " + message;
    }
}
