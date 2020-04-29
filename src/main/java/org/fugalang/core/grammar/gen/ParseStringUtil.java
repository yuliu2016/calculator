package org.fugalang.core.grammar.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParseStringUtil {
    public static String convertCase(String s) {
        var words = s.split("_");
        var sb = new StringBuilder();
        for (var word : words) {
            sb.append(capitalizeLow(word));
        }
        return sb.toString();
    }

    public static String prefixCap(String pf, String s) {
        return pf + capitalizeFirstCharOnly(s);
    }

    public static String capitalizeFirstCharOnly(String word) {
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
}
