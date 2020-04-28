package org.fugalang.core.grammar.gen;

public class CaseUtil {
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

    public static String capitalizeFirstCharOnly(String word){
        return word.isEmpty() ? word : word.substring(0, 1).toUpperCase() +
                word.substring(1);
    }

    public static String capitalizeLow(String word){
        return word.isEmpty() ? word : word.substring(0, 1).toUpperCase() +
                word.substring(1).toLowerCase();
    }

    public static String decap(String word) {
        return word.isEmpty() ? word : word.substring(0, 1).toLowerCase() +
                word.substring(1);
    }
}
