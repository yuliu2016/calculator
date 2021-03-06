package org.fugalang.grammar.token;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.token.CharTest;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MetaLexer {
    public final List<MetaToken> tokens = new ArrayList<>();

    private final Visitor visitor;

    public MetaLexer(String code) {
        visitor = new Visitor(code);
    }

    private void addToken(ElementType type, String s) {
        tokens.add(new MetaToken(type, s));
    }

    /**
     * tokenize all spacing as either NEWLINE or SPACE
     * and ignore all the comments
     */
    private boolean tokenizeSpace() {

        var in_comment = CharTest.isSingleComment(visitor.p1);

        if (!(in_comment || CharTest.isSpace(visitor.p1))) {
            return false;
        }

        var newline = CharTest.isNewline(visitor.p1);

        // the space character visitor
        var j = visitor.i + 1;

        while (j < visitor.size) {

            var p1 = visitor.code.charAt(j);

            // check that it's still in a commenting state, or
            // the next character is a comment
            if (!(in_comment ||
                    CharTest.isSpace(p1) ||
                    CharTest.isNewline(p1) ||
                    CharTest.isSingleComment(p1)))
                break;

            if (CharTest.isNewline(p1)) {
                in_comment = false;
                newline = true;
            } else if (CharTest.isSingleComment(p1)) {
                in_comment = true;
            } // else it's a comment character

            j++;
        }

        if (newline && !tokens.isEmpty()) {
            addToken(MetaTokenType.NEWLINE, "NEWLINE");
        }

        // this line must be after add_token for line no to be correct
        visitor.i = j;

        return true;
    }

    /**
     * Tokenize all normal strings
     */
    private boolean tokenizeString() {
        if (!(visitor.p1 == '"' || visitor.p1 == '\'')) {
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
                throw new MetaGrammarError("String not closed; Unexpected EOL");
            }

            j++;
        }

        addToken(MetaTokenType.TOK, visitor.code.substring(visitor.i + 1, j));

        // this line must be after add_token for line no to be correct
        // add an extra one here to account for closing char
        visitor.i = j + 1;

        return true;
    }

    private boolean tokenizeSymbolOrWord() {
        if (!isMGSymbol(visitor.p1)) {
            return false;
        }

        // check for symbols
        var j = visitor.i + 1;

        while (j < visitor.size && isMGSymbol(visitor.code.charAt(j))) {
            j++;
        }

        var symbol = visitor.code.substring(visitor.i, j);

        // Since some literals and all keywords have the same rules as symbols
        // just add them here

        addToken(MetaTokenType.TOK, symbol);

        // this line must be after add_token for line no to be correct
        visitor.i = j;

        return true;
    }

    public static boolean isMGSymbol(char ch) {
        return Character.isLetter(ch) || CharTest.isUnderscore(ch);
    }

    private boolean tokenizeOperators() {
        if (visitor.p1 == '(') {
            addToken(MetaTokenType.LPAR, "(");
        } else if (visitor.p1 == ')') {
            addToken(MetaTokenType.RPAR, ")");
        } else if (visitor.p1 == '[') {
            addToken(MetaTokenType.LSQB, "[");
        } else if (visitor.p1 == ']') {
            addToken(MetaTokenType.RSQB, "]");
        } else if (visitor.p1 == '*') {
            addToken(MetaTokenType.STAR, "*");
        } else if (visitor.p1 == '+') {
            addToken(MetaTokenType.PLUS, "+");
        } else if (visitor.p1 == ':') {
            addToken(MetaTokenType.COL, ":");
        } else if (visitor.p1 == '|') {
            addToken(MetaTokenType.OR, "|");
        } else {
            return false;
        }
        visitor.i++;
        return true;
    }

    public List<MetaToken> tokenize() {
        visitor.resetState();

        while (visitor.hasRemaining()) {
            visitor.updateAllPeeks();
            if (!(tokenizeSpace() ||
                    tokenizeString() ||
                    tokenizeSymbolOrWord() ||
                    tokenizeOperators())) {
                System.out.println(visitor.code.substring(0, visitor.i));
                throw new MetaGrammarError("Unknown Syntax");
            }
        }

        return tokens;
    }
}
