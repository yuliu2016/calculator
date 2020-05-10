package org.fugalang.core.token;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.ParserElement;

import java.util.Iterator;
import java.util.List;

import static org.fugalang.core.token.CharTest.*;
import static org.fugalang.core.token.TokenType.*;

public class Tokenizer {
    public final TokenSequence sequence;
    public final Visitor visitor;

    public Tokenizer(String code) {
        sequence = new TokenSequence(code);
        visitor = sequence.visitor;
    }

    /**
     * tokenize all spacing as NEWLINE
     * and ignore all the comments
     */
    private boolean tokenizeSpace() {

        var in_comment = isSingleComment(visitor.p1);

        if (!(in_comment || isSpace(visitor.p1))) {
            return false;
        }

        // make sure that newline is added if it's the first char

        // Fix: add CRLF for the first line
        var newline = isNewline(visitor.p1) || isCRLF(visitor.p2);

        // the space character visitor
        var sv = visitor.copyAndAdvance(1);

        while (sv.hasRemaining()) {
            sv.updateAllPeeks();

            // check that it's still in a commenting state, or
            // the next character is a comment
            if (!(in_comment ||
                    isSpace(sv.p1) ||
                    isSingleComment(sv.p1)))
                break;

            if (isCRLF(sv.p2)) {
                in_comment = false;
                newline = true;

                // need to add an extra character to account for newline

                // Fix: increase only one index because there is another one
                // at the end of this loop.
                sv.i++;
            } else if (isNewline(sv.p1)) {
                in_comment = false;
                newline = true;
            } else if (isSingleComment(sv.p1)) {
                in_comment = true;
            }  // else it's a comment character

            sv.i++;
        }

        if (newline) {
            sequence.add(NEWLINE, "\n");
        }

        // this line must be after add_token for line no to be correct
        visitor.i = sv.i;

        return true;
    }

    private boolean tokenizeSymbolOrWord() {
        if (!isSymbol(visitor.p1)) {
            return false;
        }

        // check for symbols
        var j = visitor.i + 1;

        while (j < visitor.size && isSymbol(visitor.code.charAt(j))) {
            j++;
        }

        var symbol = visitor.code.substring(visitor.i, j);

        // Since some literals and all keywords have the same rules as symbols
        // just add them here

        if (Keyword.ALL_KEYWORDS.contains(symbol)) {
            sequence.add(KEYWORD, symbol);
        } else {
            sequence.add(NAME, symbol);
        }

        // this line must be after add_token for line no to be correct
        visitor.i = j;

        return true;
    }

    /**
     * Tokenize a three-char operator
     */
    private boolean tokenizeTripleOperator() {
        if (visitor.p3 != null && Operator.TRIPLE_OPERATOR_MAP.containsKey(visitor.p3)) {
            sequence.add(OPERATOR, visitor.p3);
            visitor.i += 3;
            return true;
        }
        return false;
    }

    /**
     * Tokenize a two-char operator
     */
    private boolean tokenizeDoubleOperator() {
        if (visitor.p2 != null && Operator.DOUBLE_OPERATOR_MAP.containsKey(visitor.p2)) {
            sequence.add(OPERATOR, visitor.p2);
            visitor.i += 2;
            return true;
        }
        return false;
    }

    /**
     * Tokenize a one-char operator
     */
    private boolean tokenizeSingleOperator() {
        // Fix: wrap the p1 char into a string in order to look it up on the map
        var ch = String.valueOf(visitor.p1);
        if (Operator.SINGLE_OPERATOR_MAP.containsKey(ch)) {
            sequence.add(OPERATOR, String.valueOf(visitor.p1));
            visitor.i++;
            return true;
        }
        return false;
    }

    /**
     * Tokenize all normal strings
     */
    private boolean tokenizeString() {
        if (!isStringQuote(visitor.p1)) {
            return false;
        }

        // Fix: make this work if isStringQuote returns true
        // for more than one character.
        // The resulting string must close with the same char
        char open_char = visitor.p1;

        // string requires an extra character, so must be accounted
        // for in the indexing

        var j = visitor.i + 1;

        var closed = false;

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (ch == open_char) {
                closed = true;
                break;
            }

            // Fix: Only allow normal strings to span a single line
            if ((j < visitor.size - 1 &&
                    isCRLF(visitor.code.substring(j, j + 2)))) {
                break;
            }
            if (isNewline(ch)) {
                break;
            }

            j++;
        }

        if (!closed) {
            throw new SyntaxError("String not closed; Unexpected EOL");
        }

        sequence.add(STRING, visitor.code.substring(visitor.i + 1, j));

        // this line must be after add_token for line no to be correct
        // add an extra one here to account for closing char
        visitor.i = j + 1;

        return true;
    }

    /**
     * Tokenize a hexadecimal number
     * <p>
     * hex_number: '0x' ('_' | hex_digit)* hex_digit
     */
    private boolean tokenizeHex() {
        // add 2 because hex has a 2-char lead
        var j = visitor.i + 2;

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!isUnderscore(ch) && !isAnyHex(ch)) {
                break;
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == visitor.i + 2) {
            throw new SyntaxError("Error parsing hex: EOF after leading literal");
        }

        if (isUnderscore(visitor.code.charAt(visitor.i + 2)) ||
                isUnderscore(visitor.code.charAt(j - 1))) {
            throw new SyntaxError("Invalid hex literal");
        }

        sequence.add(NUMBER, visitor.code.substring(visitor.i, j));

        visitor.i = j;
        return true;
    }

    /**
     * Tokenize a binary number
     */
    private boolean tokenizeBin() {
        // add 2 because bin has a 2-char lead
        var j = visitor.i + 2;

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!isUnderscore(ch) && !isAnyBin(ch)) {
                if (isNumeric(ch)) {
                    throw new SyntaxError("Invalid digit '" + ch + "' in binary literal");
                }
                break;
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == visitor.i + 2) {
            throw new SyntaxError("Error parsing bin: EOF after leading literal");
        }

        if (isUnderscore(visitor.code.charAt(visitor.i + 2)) ||
                isUnderscore(visitor.code.charAt(j - 1))) {
            throw new SyntaxError("Invalid bin literal");
        }

        sequence.add(NUMBER, visitor.code.substring(visitor.i, j));

        visitor.i = j;
        return true;
    }

    /**
     * Tokenize a octal number
     */
    private boolean tokenizeOct() {
        // add 2 because oct has a 2-char lead
        var j = visitor.i + 2;

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!isUnderscore(ch) && !isAnyOct(ch)) {
                if (isNumeric(ch)) {
                    throw new SyntaxError("Invalid digit '" + ch + "' in octal literal");
                }
                break;
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == visitor.i + 2) {
            throw new SyntaxError("Error parsing oct: EOF after leading literal");
        }

        if (isUnderscore(visitor.code.charAt(visitor.i + 2)) ||
                isUnderscore(visitor.code.charAt(j - 1))) {
            throw new SyntaxError("Invalid oct literal");
        }

        sequence.add(NUMBER, visitor.code.substring(visitor.i, j));

        visitor.i = j;
        return true;
    }

    private int tokenizeDecimalSequence(int i) {
        int j = i;
        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!isUnderscore(ch) && !isNumeric(ch)) {
                break;
            }
            j++;
        }
        if (j == i || isUnderscore(visitor.code.charAt(i)) ||
                isUnderscore(visitor.code.charAt(j - 1))) {
            throw new SyntaxError("Invalid decimal literal");
        }
        return j;
    }

    /**
     * dec_small: dec_digit | dec_digit ('_' | dec_digit)* dec_digit
     * dec_big: dec_small ['.' [dec_small]]
     * exponent: ('e' | 'E') ['+' | '-'] dec_small
     * dec_number: dec_big [exponent] ['j']
     */
    private boolean tokenizeDecimalNumber() {

        var is_floating_point = false;
        var j = visitor.i;

        if (!isNumeric(visitor.p1)) {
            return false;
        }
        j = tokenizeDecimalSequence(j);
        if (j < visitor.size && isFloatingPoint(visitor.code.charAt(j))) {
            is_floating_point = true;
            j++;
            j = tokenizeDecimalSequence(j);
        }

        if (j < visitor.size && isExponentDelimiter(visitor.code.charAt(j))) {
            j++;
            if (j < visitor.size && isExponentSign(visitor.code.charAt(j))) j++;

            j = tokenizeDecimalSequence(j);
        }

        if (j < visitor.size && isComplexDelimiter(visitor.code.charAt(j))) j++;

        var s = visitor.code.substring(visitor.i, j);

        if (!is_floating_point && s.startsWith("0") && !s.replace("0", "").isEmpty()) {
            throw new SyntaxError("Integer with leading zero; use 0o for octal numbers");
        }
        sequence.add(NUMBER, s);

        visitor.i = j;
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
    private boolean tokenizeNumber() {

        // delegate to other methods if literal is in another base
        if (isHexLead(visitor.p2)) {
            return tokenizeHex();
        } else if (isBinLead(visitor.p2)) {
            return tokenizeBin();
        } else if (isOctLead(visitor.p2)) {
            return tokenizeOct();
        } else {
            return tokenizeDecimalNumber();
        }
    }

    public List<ParserElement> tokenizeAll() {
        sequence.resetState();

        while (visitor.hasRemaining()) {
            visitor.updateAllPeeks();

            if (!(tokenizeSpace() ||
                    tokenizeNumber() ||
                    tokenizeString() ||
                    tokenizeSymbolOrWord() ||
                    tokenizeTripleOperator() ||
                    tokenizeDoubleOperator() ||
                    tokenizeSingleOperator()
            )) {
                throw new SyntaxError("Unknown Syntax");
            }
        }

        return sequence.tokens;
    }
}
