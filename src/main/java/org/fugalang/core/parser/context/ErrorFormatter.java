package org.fugalang.core.parser.context;

public class ErrorFormatter {
    public static String format(String message, int lineno, String line, int col) {
        return "File <repl>, line " + lineno + ":\n    " + line + "\n" +
                " ".repeat(col + 4) + "^\nError: " + message;
    }

    public static String format(String message, int lineno, String line, int col1, int col2) {
        return "File <repl>, line " + lineno + ":\n    " + line + "\n" +
                " ".repeat(col1 + 4) +
                "^".repeat(col2 - col1) +
                "\nError: " + message;
    }
}
