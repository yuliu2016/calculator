package org.fugalang.core.token;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetterOrDigit;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class CharTest {
    public static boolean isNewline(char ch) {
        return ch == '\n' || ch == '\r';
    }

    public static boolean isCRLF(String ch) {
        return "\r\n".equals(ch);
    }

    public static boolean isSymbol(char ch) {
        return isLetterOrDigit(ch) || isUnderscore(ch);
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
        return ch == '\"';
    }

    public static boolean isNumeric(char ch) {
        return isDigit(ch);
    }

    public static boolean isZero(char ch) {
        return ch == '0';
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

    public static boolean isExponentSignDelimiter(char ch) {
        return ch == '+' || ch == '-';
    }

    public static boolean isAnyHex(char ch) {
        var charCode = (int) (Character.toUpperCase(ch));
        return (charCode >= 65 && charCode < 71) || isDigit(ch);
    }

    public static boolean isAnyBin(char ch) {
        return ch == '0' || ch == '1';
    }

    public static boolean isAnyOct(char ch) {
        return isDigit(ch) && !(ch == '8' || ch == '9');
    }
}
