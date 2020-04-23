package org.fugalang.core.token;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep track of a list of tokens
 */
public class TokenSequence {
    public final List<Token> tokens = new ArrayList<>();
    public final Visitor visitor;

    // used to lookup newline characters from the last i value
    // -1 because the first token might be from 0
    public int last_token_index = -1;

    // Assume starting on the first line
    // line_number is not a property of _Visitor because it is not
    // aware of newline characters
    public int line_number = 1;

    // column of the line; 1-indexed, but starts at 0 before any characters
    // are processed when adding tokens to the sequence
    public int column = 0;

    public TokenSequence(String code) {
        visitor = new Visitor(code);
    }

    public void resetState() {
        visitor.resetState();
        tokens.clear();
        last_token_index = -1;
        line_number = 1;
        column = 0;
    }


    /**
     * Pop the newline when the opcode can be simplified
     *
     * @param n the number of characters to skip backwards
     *          when attempting to pop the newline token
     */
    public void popNewline(int n) {
        popDelimiter(TokenType.NEWLINE, n);
    }

    /**
     * Pop a delimiter newline when the opcode can be simplified
     *
     * @param typeToPop the type of token to look for
     * @param n the number of characters to skip backwards
     *          when attempting to pop the newline token
     */
    public void popDelimiter(TokenType typeToPop, int n) {
        // Fix: empty token sequence causes n < 1
        if (tokens.isEmpty()) {
            return;
        }

        if (n < 1) {
            throw new TokenException("Cannot pop " + typeToPop
                    + " past the end of the string!");
        }

        // Fix: trying to pop the space when there are no sufficient tokens
        if (tokens.size() < n) {
            return;
        }

        int index = tokens.size() - n;

        if (tokens.get(index).type == typeToPop) {
            tokens.remove(index);
        }
    }

    /**
     * Trim the spaces at the start and end of the sequence
     */
    public void trim() {
        // end of sequence
        popNewline(1);
        // start of sequence
        popNewline(tokens.size());
    }

    /**
     * Add a token to the sequence with line and column
     */
    public void add(TokenType token_type, Object token_value) {

        // i must be increased from the last call to this function
        // otherwise breaks contract
        if (visitor.i == last_token_index) {
            throw new TokenException("Cannot add token: Index stayed the same");
        }

        // iterate through the section of the string that
        // has been added to the token to search for newlines

        // Fix: indexing needs to start at last_token_index + 1
        for (int i = last_token_index + 1; i <= visitor.i; i++) {
            if (CharTest.isNewline(visitor.code.charAt(i))) {

                // Increment line number before the check, in case of the early exit
                line_number++;

                if (i == visitor.i) {
                    // Fix: exit early to ensure that newlines are on the right line
                    var token = new Token(line_number - 1, column + 1, token_type, token_value);
                    tokens.add(token);
                    last_token_index = visitor.i;
                    column = 0;

                    return;
                }

                column = 0;
            } else {
                column++;
            }
        }

        last_token_index = visitor.i;
        var token = new Token(line_number, column, token_type, token_value);

        tokens.add(token);
    }
}
