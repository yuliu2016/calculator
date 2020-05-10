package org.fugalang.core.token;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.context.TokenElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep track of a list of tokens
 */
public class TokenSequence {
    public final List<ParserElement> tokens = new ArrayList<>();
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
     * Add a token to the sequence with line and column
     */
    public void add(ElementType token_type, String token_value) {

        // i must be increased from the last call to this function
        // otherwise breaks contract
        if (visitor.i == last_token_index) {
            throw new SyntaxError("Cannot add token: Index stayed the same");
        }

        var line_start = line_number;
        var col_start = column;

        var line_offset = 0;
        var col_offset = 0;

        // iterate through the section of the string that
        // has been added to the token to search for newlines

        // Fix: indexing needs to start at last_token_index + 1
        for (int i = last_token_index + 1; i <= visitor.i; i++) {
            if (CharTest.isNewline(visitor.code.charAt(i))) {

                // Increment line number before the check, in case of the early exit
                line_number++;

                if (i == visitor.i) {
                    line_offset = -1;
                    col_offset = 1;
                }
                column = 0;
            } else {
                column++;
            }
        }


        var token = new TokenElement(
                token_type,
                token_value,
                last_token_index,
                line_start,
                line_number + line_offset,
                col_start,
                column + col_offset
        );
        tokens.add(token);

        last_token_index = visitor.i;
    }
}
