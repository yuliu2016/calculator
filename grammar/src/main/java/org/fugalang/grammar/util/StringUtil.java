package org.fugalang.grammar.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {
    public static String convertCase(String s) {
        var words = s.split("_");
        var sb = new StringBuilder();
        for (var word : words) {
            sb.append(capitalizeLow(word));
        }
        return sb.toString();
    }

    public static String prefixCap(String pf, String s) {
        return pf + capitalizeFirstChar(s);
    }

    public static String capitalizeFirstChar(String word) {
        return word.isEmpty() ? word : word.substring(0, 1).toUpperCase() +
                word.substring(1);
    }

    public static String capitalizeLow(String word) {
        return word.isEmpty() ? word : word.substring(0, 1).toUpperCase() +
                word.substring(1).toLowerCase();
    }

    public static String decap(String word) {
        return word.isEmpty() ? word : word.substring(0, 1).toLowerCase() +
                word.substring(1);
    }

    public static String javadoc(String block, int indentation) {
        String idt = " ".repeat(indentation);
        return splitLines(block)
                .stream()
                // add an extra asterisk to save the indentation
                // then add indentation
                .map(ln -> idt + " * " +
                        (ln.startsWith(" ") ? "*" + ln.substring(1) : ln))
                .collect(Collectors.joining(
                        "\n",
                        idt + "/**\n",
                        "\n" + idt + " */\n"
                ));
    }

    public static String indent(String block, int indentation) {
        String idt = " ".repeat(indentation);
        return splitLines(block).stream().map(ln -> ln.isBlank() ? ln : idt + ln)
                .collect(Collectors.joining("\n"));
    }

    public static List<String> splitLines(String block) {
        List<String> lines = new ArrayList<>();

        int i = 0;
        int lastI = 0;

        while (i < block.length()) {
            char ch = block.charAt(i);

            if (ch == '\n' || ch == '\r') {
                lines.add(block.substring(lastI, i));
                lastI = i + 1;
            }

            i++;
        }

        lines.add(block.substring(lastI));

        return lines;
    }

    public static boolean isWord(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if (!(c == '_' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public static String pluralize(String s) {
        if (s.endsWith("y")) {
            return s.substring(0, s.length() - 1) + "ies";
        }
        return s + "s";
    }
}
