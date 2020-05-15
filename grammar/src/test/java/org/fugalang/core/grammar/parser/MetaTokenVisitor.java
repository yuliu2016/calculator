package org.fugalang.core.grammar.parser;

import org.fugalang.core.grammar.token.MetaToken;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Deprecated
public class MetaTokenVisitor {
    private final List<MetaToken> tokens;
    private int i;

    private final Deque<Integer> lookahead = new ArrayDeque<>();

    public MetaTokenVisitor(List<MetaToken> tokens) {
        this.tokens = tokens;
        this.i = 0;
    }

    public MetaToken getAndAdd() {
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

    public void backtrack() {
        i--;
    }
}
