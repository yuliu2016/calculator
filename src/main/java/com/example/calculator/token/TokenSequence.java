package com.example.calculator.token;

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

    // column of the line; 1-indexed
    public int column = 1;

    public TokenSequence(String code) {
        visitor = new Visitor(code);
    }

    public void resetState() {
        visitor.resetState();
        tokens.clear();
        last_token_index = -1;
        line_number = 1;
        column = 1;
    }

    /**
     * Pop the space when the opcode can be simplified
     *
     * @param n the number of characters to skip backwards
     *          when attempting to pop the space token
     */
    public void popSpace(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        // Fix: trying to pop the space when there are no sufficient tokens
        if (tokens.size() < n) {
            return;
        }

        int index = tokens.size() - n;

        if (tokens.get(index).type == TokenType.SPACE) {
            tokens.remove(index);
        }
    }


    /**
     * Pop the newline when the opcode can be simplified
     *
     * @param n the number of characters to skip backwards
     *          when attempting to pop the newline token
     */
    public void popNewline(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        // Fix: trying to pop the space when there are no sufficient tokens
        if (tokens.size() < n) {
            return;
        }

        int index = tokens.size() - n;

        if (tokens.get(index).type == TokenType.NEWLINE) {
            tokens.remove(index);
        }
    }

    /**
     * Trim the spaces at the start and end of the sequence
     */
    public void trim() {
        // end of sequence
        popSpace(1);
        popNewline(1);

        // start of sequence
        popSpace(tokens.size());
        popSpace(tokens.size());
    }

    /**
     * Add a token to the sequence with line and column
     */
    public void add(TokenType token_type, Object token_value) {

        // i must be increased from the last call to this function
        // otherwise breaks contract
        if (visitor.i == last_token_index) {
            throw new IllegalStateException("Cannot add token: Index stayed the same");
        }

        // iterate through the section of the string that
        // has been added to the token to search for newlines

        // Fix: indexing needs to start at last_token_index + 1
        for (int i = last_token_index + 1; i < visitor.i; i++) {
            if (CharTest.isNewline(visitor.code.charAt(i))) {
                line_number++;
                column = 0;
            }
            column++;
        }

        last_token_index = visitor.i;
        var token = new Token(line_number, visitor.i, token_type, token_value);

        tokens.add(token);
    }
}
