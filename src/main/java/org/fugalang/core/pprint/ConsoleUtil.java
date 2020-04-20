package org.fugalang.core.pprint;

public class ConsoleUtil {
    public static String formatRightWithZeroes(String s, int d) {
        return new String(new char[d - s.length()]).replace("\0", "0") + s;
    }

    public static String formatLeftWithSpaces(String s, int d) {
        return s + new String(new char[d - s.length()]).replace("\0", " ");
    }
}
