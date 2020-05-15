package org.fugalang.core.token;

import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.LexingContext;

import java.util.Iterator;

public class SimpleLexer implements Iterator<ParserElement> {

    public static Iterator<ParserElement> of(LexingContext context) {
        return new SimpleLexer(context);
    }

    private final TokenState state = new TokenState();
    private final LexingContext context;
    private ParserElement nextElement;

    private SimpleLexer(LexingContext context) {
        this.context = context;
        computeNext();
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public ParserElement next() {
        var thisElement = nextElement;
        computeNext();
        return thisElement;
    }

    private void computeNext() {
        nextElement = context.hasRemaining() ?
                Tokenizer.loop(context, state)
                : null;
    }
}
