package org.fugalang.core.token;

import org.fugalang.core.grammar.SyntaxError;

import java.util.List;

import static org.fugalang.core.token.TokenType.*;

public class Tokenizer {
    public final TokenSequence sequence;
    public final Visitor visitor;

    public Tokenizer(String code) {
        sequence = new TokenSequence(code);
        visitor = sequence.visitor;
    }

    /**
     * tokenize all spacing as either NEWLINE or SPACE
     * and ignore all the comments
     */
    private boolean tokenizeSpace() {

        var in_comment = CharTest.isSingleComment(visitor.p1);

        // Use an int instead of boolean because it needs a
        // counter to keep track of nested multi-line comments

        // no need to check if p2 is null -- it just returns False
        var multi_comment_level = CharTest.isOpenMultiComment(visitor.p2) ? 1 : 0;

        if (!(in_comment || multi_comment_level > 0 || CharTest.isSpace(visitor.p1))) {
            return false;
        }

        // invariant condition:
        // in_comment and in_multi_line_comment are mutually exclusive

        // make sure that newline is added if it's the first char

        // Fix: add CRLF for the first line
        var newline = CharTest.isNewline(visitor.p1) || CharTest.isCRLF(visitor.p2);

        // the space character visitor
        var sv = visitor.copyAndAdvance(1);

        if (multi_comment_level > 0) {
            // because the parsing needs to start an extra char later
            // for a multi-line comment
            sv.i++;
        }

        while (sv.hasRemaining()) {
            sv.updateAllPeeks();

            // check that it's still in a commenting state, or
            // the next character is a comment
            if (!(in_comment ||
                    multi_comment_level > 0 ||
                    CharTest.isSpace(sv.p1) ||
                    CharTest.isSingleComment(sv.p1) ||
                    CharTest.isOpenMultiComment(sv.p2)))
                break;

            // Fix: doc string with spaces before it will trigger multi-line
            // comments instead of a doc str
            if (CharTest.isOpenDocStr(sv.p3) && multi_comment_level == 0)
                break;

            if (CharTest.isCRLF(sv.p2)) {
                in_comment = false;
                newline = true;

                // need to add an extra character to account for newline

                // Fix: increase only one index because there is another one
                // at the end of this loop.
                sv.i++;
            } else if (CharTest.isNewline(sv.p1)) {
                in_comment = false;
                newline = true;
            } else if (CharTest.isSingleComment(sv.p1) && multi_comment_level == 0) {
                // ^ Fix (condition 2): only allow single-line comments
                // when not inside another multi-line comment
                in_comment = true;
            } else if (CharTest.isOpenMultiComment(sv.p2)) {
                multi_comment_level++;
                // since peek2 is not null, this does not break indexing
                sv.i++;
            } else if (CharTest.isCloseMultiComment(sv.p2)) {
                multi_comment_level--;
                // since peek2 is not null, this does not break indexing
                sv.i++;
            } // else it's a comment character

            sv.i++;
        }

        // This is the case when in_multi_line_comment is not 0
        // when the while loop has iterated through the entire piece of code
        if (multi_comment_level > 0) {
            throw new SyntaxError("Multi-line comment not closed; Unexpected EOF");
        }

        if (newline) {
            sequence.add(NEWLINE, Token.NoneValue);
        }

        // this line must be after add_token for line no to be correct
        visitor.i = sv.i;

        return true;
    }

    private boolean tokenizeSymbolOrWord() {
        if (!CharTest.isSymbol(visitor.p1)) {
            return false;
        }

        // check for symbols
        var j = visitor.i + 1;

        while (j < visitor.size && CharTest.isSymbol(visitor.code.charAt(j))) {
            j++;
        }

        var symbol = visitor.code.substring(visitor.i, j);

        // Since some literals and all keywords have the same rules as symbols
        // just add them here

        if (CharTest.isNone(symbol)) {
            sequence.add(NONE, Token.NoneValue);
        } else if (CharTest.isTrue(symbol)) {
            sequence.add(BOOLEAN, Boolean.TRUE);
        } else if (CharTest.isFalse(symbol)) {
            sequence.add(BOOLEAN, Boolean.FALSE);
        } else if (Keyword.ALL_KEYWORDS.contains(symbol)) {
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
            sequence.add(OPERATOR, Operator.TRIPLE_OPERATOR_MAP.get(visitor.p3));
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
            sequence.add(OPERATOR, Operator.DOUBLE_OPERATOR_MAP.get(visitor.p2));
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
            sequence.add(OPERATOR, Operator.SINGLE_OPERATOR_MAP.get(ch));
            visitor.i++;
            return true;
        }
        return false;
    }

    /**
     * Tokenize all normal strings
     */
    private boolean tokenizeString() {
        if (!CharTest.isStringQuote(visitor.p1)) {
            return false;
        }

        // Fix: make this work if isStringQuote returns true
        // for more than one character.
        // The resulting string must close with the same char
        char open_char = visitor.p1;

        // string requires an extra character, so must be accounted
        // for in the indexing

        var j = visitor.i + 1;

        while (j < visitor.size - 1) {
            var ch = visitor.code.charAt(j);
            if (ch == open_char) {
                break;
            }

            // Fix: Only allow normal strings to span a single line
            if (CharTest.isNewline(ch) || CharTest.isCRLF(visitor.code.substring(j, j + 1))) {
                throw new SyntaxError("String not closed; Unexpected EOL");
            }

            j++;
        }

        sequence.add(STRING, visitor.code.substring(visitor.i + 1, j));

        // this line must be after add_token for line no to be correct
        // add an extra one here to account for closing char
        visitor.i = j + 1;

        return true;
    }

    /**
     * Add a integer to the sequence, to be parsed from a string
     *
     * @param s     the integer
     * @param radix the radix
     */
    private void addInteger(String s, int radix) {
        sequence.add(NUMBER, Integer.parseInt(s, radix));
    }

    /**
     * Tokenize a hexadecimal number
     */
    private boolean tokenizeHex() {
        // add 2 because hex has a 2-char lead
        var j = visitor.i + 2;

        var sb = new StringBuilder();

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!CharTest.isUnderscore(ch)) {
                if (CharTest.isAnyHex(ch)) {
                    sb.append(ch);
                } else {
                    break;
                }
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == visitor.i + 2) {
            throw new SyntaxError("Error parsing hex: EOF after leading literal");
        }

        addInteger(sb.toString(), 16);

        visitor.i = j;
        return true;
    }

    /**
     * Tokenize a binary number
     */
    private boolean tokenizeBin() {
        // add 2 because bin has a 2-char lead
        var j = visitor.i + 2;

        var sb = new StringBuilder();

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!CharTest.isUnderscore(ch)) {
                if (CharTest.isAnyBin(ch)) {
                    sb.append(ch);
                } else {
                    break;
                }
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == visitor.i + 2) {
            throw new SyntaxError("Error parsing bin: EOF after leading literal");
        }

        addInteger(sb.toString(), 2);

        visitor.i = j;
        return true;
    }

    /**
     * Tokenize a octal number
     */
    private boolean tokenizeOct() {
        // add 2 because oct has a 2-char lead
        var j = visitor.i + 2;

        var sb = new StringBuilder();

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (!CharTest.isUnderscore(ch)) {
                if (CharTest.isAnyOct(ch)) {
                    sb.append(ch);
                } else {
                    break;
                }
            }
            j++;
        }

        // Fix: EOF after leading literal
        if (j == visitor.i + 2) {
            throw new SyntaxError("Error parsing oct: EOF after leading literal");
        }

        addInteger(sb.toString(), 8);

        visitor.i = j;
        return true;
    }

    /**
     * Tokenize a numerical value that starts with a digit
     * <p>
     * Note that this doesn't account for negative values;
     * negatives are represented as an OPERATOR
     */
    private boolean tokenizeNumber() {
        if (!CharTest.isNumeric(visitor.p1)) {
            return false;
        }

        var leading_zero = CharTest.isZero(visitor.p1);

        if (leading_zero) {
            // delegate to other methods if literal is in another base
            if (CharTest.isHexLead(visitor.p2)) {
                return tokenizeHex();
            } else if (CharTest.isBinLead(visitor.p2)) {
                return tokenizeBin();
            } else if (CharTest.isOctLead(visitor.p2)) {
                return tokenizeOct();
            }
        }

        var is_float = false;
        var is_complex = false;

        var sb = new StringBuilder();

        // there is already a digit in here
        sb.append(visitor.p1);

        var j = visitor.i + 1;

        while (j < visitor.size) {
            var ch = visitor.code.charAt(j);
            if (CharTest.isFloatingPoint(ch)) {
                if (is_float) {
                    // Fix: A second floating point is no longer a number

                    break;
                } else {
                    is_float = true;
                    sb.append(".");
                }
            } else if (CharTest.isNumeric(ch)) {
                sb.append(ch);
            } else if (CharTest.isComplexDelimiter(ch)) {
                is_complex = true;
                // the number literal includes an extra char
                // that need to be skipped
                j++;
                break;
            } else {
                // not a part of the number
                break;
            }
            j++;
        }

        var s = sb.toString();

        // Fix: change the order to complex, then float, since
        // a complex literal may also be a float

        if (is_complex) {
            sequence.add(NUMBER, Double.parseDouble(s));
        } else if (is_float) {
            sequence.add(NUMBER, Double.parseDouble(s));
        } else {
            // Fix: the special case of 0 should not throw a syntax error
            if (leading_zero && s.length() > 1) {
                throw new SyntaxError("Integer with leading zero");
            } else {
                addInteger(s, 10);
            }
        }

        visitor.i = j;
        return true;
    }

    public List<Token> tokenizeAll() {
        sequence.resetState();

        while (visitor.hasRemaining()) {
            visitor.updateAllPeeks();

            // Fix: brackets cosing at the wrong call
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
        sequence.trim();

        return sequence.tokens;
    }
}
