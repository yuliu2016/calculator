package org.fugalang.core.token;

import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.context.LazyArrayList;
import org.fugalang.core.parser.context.LexingContext;
import org.fugalang.core.parser.context.LexingVisitor;

import java.util.List;

import static org.fugalang.core.token.CharTest.*;
import static org.fugalang.core.token.TokenType.*;

public class Tokenizer {

    /**
     * tokenize all spacing as NEWLINE
     * and ignore all the comments
     */
    private static boolean tokenizeSpace(LexingContext context, TokenState state) {

        var in_comment = isSingleComment(context.p1());

        if (!(in_comment || isSpace(context.p1()))) {
            return false;
        }

        // make sure that newline is added if it's the first char

        // Fix: add CRLF for the first line
        var newline = isNewline(context.p1()) || isCRLF(context.p2());

        // the space character visitor
        var sv = context.copyAndAdvance(1);

        while (sv.hasRemaining()) {

            // check that it's still in a commenting state, or
            // the next character is a comment
            if (!(in_comment ||
                    isSpace(sv.p1()) ||
                    isSingleComment(sv.p1())))
                break;

            if (isCRLF(sv.p2())) {
                in_comment = false;
                newline = true;

                // need to add an extra character to account for newline

                // Fix: increase only one index because there is another one
                // at the end of this loop.
                sv.advance();
            } else if (isNewline(sv.p1())) {
                in_comment = false;
                newline = true;
            } else if (isSingleComment(sv.p1())) {
                in_comment = true;
            }  // else it's a comment character

            sv.advance();
        }

        if (newline) {
            state.setToken(context.createElement(NEWLINE, context.index(), sv.index()));
        } else {
            state.setToken(null);
        }

        // this line must be after add_token for line no to be correct
        context.setIndex(sv.index());

        return true;
    }

    private static boolean tokenizeSymbolOrWord(LexingContext context, TokenState state) {
        if (!isSymbol(context.p1())) {
            return false;
        }

        // check for symbols
        var j = context.index() + 1;

        while (j < context.length() && isSymbol(context.charAt(j))) {
            j++;
        }

        var symbol = context.substring(context.index(), j);

        // Since some literals and all keywords have the same rules as symbols
        // just add them here

        if (Keyword.ALL_KEYWORDS.contains(symbol)) {
            state.setToken(context.createElement(KEYWORD, context.index(), j));
        } else {
            state.setToken(context.createElement(NAME, context.index(), j));
        }

        // this line must be after add_token for line no to be correct
        context.setIndex(j);

        return true;
    }

    /**
     * Tokenize a three-char operator
     */
    private static boolean tokenizeTripleOperator(LexingContext context, TokenState state) {
        if (context.p3() != null && Operator.TRIPLE_OPERATOR_MAP.containsKey(context.p3())) {
            var i = context.index();
            state.setToken(context.createElement(OPERATOR, i, i + 3));
            context.advance(3);
            return true;
        }
        return false;
    }

    /**
     * Tokenize a two-char operator
     */
    private static boolean tokenizeDoubleOperator(LexingContext context, TokenState state) {
        if (context.p2() != null && Operator.DOUBLE_OPERATOR_MAP.containsKey(context.p2())) {
            var i = context.index();
            state.setToken(context.createElement(OPERATOR, i, i + 2));
            context.advance(2);
            return true;
        }
        return false;
    }

    /**
     * Tokenize a one-char operator
     */
    private static boolean tokenizeSingleOperator(LexingContext context, TokenState state) {
        // Fix: wrap the p1 char into a string in order to look it up on the map
        var ch = String.valueOf(context.p1());
        if (Operator.SINGLE_OPERATOR_MAP.containsKey(ch)) {
            var i = context.index();
            state.setToken(context.createElement(OPERATOR, i, i + 1));
            context.advance();
            return true;
        }
        return false;
    }

    /**
     * Tokenize all normal strings
     */
    private static boolean tokenizeString(LexingContext context, TokenState state) {
        if (!isStringQuote(context.p1())) {
            return false;
        }

        // Fix: make this work if isStringQuote returns true
        // for more than one character.
        // The resulting string must close with the same char
        char open_char = context.p1();

        // string requires an extra character, so must be accounted
        // for in the indexing

        var j = context.index() + 1;

        var closed = false;

        while (j < context.length()) {
            var ch = context.charAt(j);
            if (ch == open_char) {
                closed = true;
                break;
            }

            // Fix: Only allow normal strings to span a single line
            if ((j < context.length() - 1 &&
                    isCRLF(context.substring(j, j + 2)))) {
                break;
            }
            if (isNewline(ch)) {
                break;
            }

            j++;
        }

        if (!closed) {
            context.syntaxError("String not closed; Unexpected EOL",
                    j - context.index());
        }

        state.setToken(context.createElement(STRING, context.index() + 1, j));

        // this line must be after add_token for line no to be correct
        // add an extra one here to account for closing char
        context.setIndex(j + 1);

        return true;
    }

    /**
     * Tokenize a hexadecimal number
     * <p>
     * hex_number: '0x' ('_' | hex_digit)* hex_digit
     */
    private static boolean tokenizeHex(LexingContext context, TokenState state) {
        // add 2 because hex has a 2-char lead
        var j = context.index() + 2;

        while (j < context.length()) {
            var ch = context.charAt(j);
            if (!isUnderscore(ch) && !isAnyHex(ch)) {
                break;
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == context.index() + 2) {
            context.syntaxError("Error parsing hex: Nothing after leading literal", 2);
        }

        if (isUnderscore(context.charAt(context.index() + 2)) ||
                isUnderscore(context.charAt(j - 1))) {
            context.syntaxError("Invalid hex literal", j - context.index());
        }

        state.setToken(context.createElement(NUMBER, context.index(), j));
        context.setIndex(j);

        return true;
    }

    /**
     * Tokenize a binary number
     */
    private static boolean tokenizeBin(LexingContext context, TokenState state) {
        // add 2 because bin has a 2-char lead
        var j = context.index() + 2;

        while (j < context.length()) {
            var ch = context.charAt(j);
            if (!isUnderscore(ch) && !isAnyBin(ch)) {
                if (isNumeric(ch)) {
                    context.syntaxError("Invalid digit '" + ch + "' in binary literal",
                            j - context.index());
                }
                break;
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == context.index() + 2) {
            context.syntaxError("Error parsing bin: Nothing after leading literal", 2);
        }

        if (isUnderscore(context.charAt(context.index() + 2)) ||
                isUnderscore(context.charAt(j - 1))) {
            context.syntaxError("Invalid bin literal", j - context.index());
        }

        state.setToken(context.createElement(NUMBER, context.index(), j));
        context.setIndex(j);

        return true;
    }

    /**
     * Tokenize a octal number
     */
    private static boolean tokenizeOct(LexingContext context, TokenState state) {
        // add 2 because oct has a 2-char lead
        var j = context.index() + 2;

        while (j < context.length()) {
            var ch = context.charAt(j);
            if (!isUnderscore(ch) && !isAnyOct(ch)) {
                if (isNumeric(ch)) {
                    context.syntaxError("Invalid digit '" + ch + "' in octal literal",
                            j - context.index());
                }
                break;
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == context.index() + 2) {
            context.syntaxError("Error parsing oct: Nothing after leading literal", 2);
        }

        if (isUnderscore(context.charAt(context.index() + 2)) ||
                isUnderscore(context.charAt(j - 1))) {
            context.syntaxError("Invalid oct literal", j - context.index());
        }

        state.setToken(context.createElement(NUMBER, context.index(), j));
        context.setIndex(j);

        return true;
    }

    private static int tokenizeDecimalSequence(LexingContext context, int i) {
        int j = i;
        while (j < context.length()) {
            var ch = context.charAt(j);
            if (!isUnderscore(ch) && !isNumeric(ch)) {
                break;
            }
            j++;
        }
        if (j == i || isUnderscore(context.charAt(i)) ||
                isUnderscore(context.charAt(j - 1))) {
            context.syntaxError("Invalid decimal literal",
                    j - context.index());
        }
        return j;
    }

    /**
     * dec_small: dec_digit | dec_digit ('_' | dec_digit)* dec_digit
     * dec_big: dec_small ['.' [dec_small]]
     * exponent: ('e' | 'E') ['+' | '-'] dec_small
     * dec_number: dec_big [exponent] ['j']
     */
    private static boolean tokenizeDecimalNumber(LexingContext context, TokenState state) {

        var is_floating_point = false;
        var j = context.index();

        if (!isNumeric(context.p1())) {
            return false;
        }
        j = tokenizeDecimalSequence(context, j);
        if (j < context.length() && isFloatingPoint(context.charAt(j))) {
            is_floating_point = true;
            j++;
            j = tokenizeDecimalSequence(context, j);
        }

        if (j < context.length() && isExponentDelimiter(context.charAt(j))) {
            j++;
            if (j < context.length() && isExponentSign(context.charAt(j))) j++;

            j = tokenizeDecimalSequence(context, j);
        }

        if (j < context.length() && isComplexDelimiter(context.charAt(j))) j++;

        var s = context.substring(context.index(), j);

        if (!is_floating_point && s.startsWith("0") && !s.replace("0", "").isEmpty()) {
            context.syntaxError("Integer with leading zero; use 0o for octal numbers", 0);
        }

        state.setToken(context.createElement(NUMBER, context.index(), j));

        context.setIndex(j);
        return true;
    }

    /**
     * Tokenize a numerical value that starts with a digit
     * <p>
     * Note that this doesn't account for negative values;
     * negatives are represented as an OPERATOR.
     * <p>
     * Rules for number parsing:
     * <p>
     * number: hex_number | oct_number | bin_number | dec_number
     * <p>
     * hex_number: '0x' ('_' | hex_digit)* hex_digit
     * oct_number: '0o' ('_' | oct_digit)* oct_digit
     * bin_number: '0b' ('_' | bin_digit)* bin_digit
     * <p>
     * hex_digit: dec_digit | 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'A' | 'B' | 'C' | 'D' | 'E' | 'F'
     * dec_digit: oct_digit | '8' | '9'
     * oct_digit: bin_digit | '2' | '3' | '4' | '5' | '6' | '7'
     * bin_digit: '0' | '1'
     * <p>
     * dec_small: dec_digit | dec_digit ('_' | dec_digit)* dec_digit
     * dec_big: dec_small ['.' [dec_small]]
     * exponent: ('e' | 'E') ['-'] dec_digit+
     * dec_number: dec_big [exponent] ['j']
     */
    private static boolean tokenizeNumber(LexingContext context, TokenState state) {

        // delegate to other methods if literal is in another base
        if (isHexLead(context.p2())) {
            return tokenizeHex(context, state);
        } else if (isBinLead(context.p2())) {
            return tokenizeBin(context, state);
        } else if (isOctLead(context.p2())) {
            return tokenizeOct(context, state);
        } else {
            return tokenizeDecimalNumber(context, state);
        }
    }

    static ParserElement loop(LexingContext c, TokenState s) {
        s.clearToken();
        while (c.hasRemaining()) {
            var result = tokenizeSpace(c, s);
            result = result || tokenizeNumber(c, s);
            result = result || tokenizeString(c, s);
            result = result || tokenizeSymbolOrWord(c, s);
            result = result || tokenizeTripleOperator(c, s);
            result = result || tokenizeDoubleOperator(c, s);
            result = result || tokenizeSingleOperator(c, s);

            if (!result) {
                c.syntaxError("Unknown Syntax", 0);
            }

            if (s.getToken() != null) {
                break;
            }
        }
        return s.getToken();
    }

    @Deprecated
    private final String code;

    @Deprecated
    public Tokenizer(String code) {
        this.code = code;
    }

    @Deprecated
    public List<ParserElement> tokenizeAll() {
        return new LazyArrayList<>(SimpleLexer.of(LexingVisitor.of(code))).getInnerList();
    }
}
