package org.fugalang.core.parser.simple;

import org.fugalang.core.token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class TokenVisitor {
    private final List<Token> tokens;
    private int i;

    private final Deque<Integer> lookahead = new ArrayDeque<>();

    public TokenVisitor(List<Token> tokens) {
        this.tokens = tokens;
        this.i = 0;
    }

    public Token getAndAdd() {
        if (i == tokens.size()) {
            return null;
        }
        return tokens.get(i++);
    }

    public void markLookahead() {
        // save the current i value, so at a future point this can be found again
        lookahead.push(i);
    }

    public void abortLookahead() {
        // revert i to the top of the lookahead stack
        i = lookahead.pop();
    }

    public void commitLookahead() {
        // remove the last saved value without reverting
        lookahead.pop();
    }
}
