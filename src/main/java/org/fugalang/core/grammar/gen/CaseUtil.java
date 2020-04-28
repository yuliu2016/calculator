package org.fugalang.core.grammar.gen;

public class CaseUtil {
    public static String convertCase(String s) {
        var words = s.split("_");
        var sb = new StringBuilder();
        for (var word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)));
            sb.append(word.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
