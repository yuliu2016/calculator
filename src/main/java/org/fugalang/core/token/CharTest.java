package org.fugalang.core.token;

public class CharTest {
    public static boolean isNewline(char ch) {
        return ch == '\n' || ch == '\r';
    }

    public static boolean isCRLF(String ch) {
        return "\r\n".equals(ch);
    }

    public static boolean isCRLF(char ch1, char ch2) {
        return ch1 == '\r' && ch2 == '\n';
    }

    public static boolean isAlpha(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    public static boolean isSymbol(char ch) {
        return isAlpha(ch) || isNumeric(ch) || isUnderscore(ch);
    }

    public static boolean isSpace(char ch) {
        return Character.isWhitespace(ch);
    }

    public static boolean isUnderscore(char ch) {
        return ch == '_';
    }

    public static boolean isSingleComment(char ch) {
        return ch == '#';
    }

    public static boolean isStringQuote(char ch) {
        return ch == '\"' || ch == '\'';
    }

    public static boolean isNumeric(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isHexLead(String ch) {
        return "0x".equals(ch);
    }

    public static boolean isOctLead(String ch) {
        return "0o".equals(ch);
    }

    public static boolean isBinLead(String ch) {
        return "0b".equals(ch);
    }

    public static boolean isFloatingPoint(char ch) {
        return ch == '.';
    }

    public static boolean isComplexDelimiter(char ch) {
        return ch == 'j' || ch == 'J';
    }

    public static boolean isExponentDelimiter(char ch) {
        return ch == 'e' || ch == 'E';
    }

    public static boolean isExponentSign(char ch) {
        return ch == '+' || ch == '-';
    }

    public static boolean isAnyHex(char ch) {
        return (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f') || isNumeric(ch);
    }

    public static boolean isAnyBin(char ch) {
        return ch == '0' || ch == '1';
    }

    public static boolean isAnyOct(char ch) {
        return ch >= '0' && ch <= '7';
    }
}
