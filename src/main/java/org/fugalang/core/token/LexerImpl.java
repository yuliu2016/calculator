package org.fugalang.core.token;

import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.context.LexingContext;

import java.util.Iterator;

public class LexerImpl implements Iterator<ParserElement> {

    private final LexingContext context;
    private ParserElement next;

    public LexerImpl(LexingContext context) {
        this.context = context;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public ParserElement next() {
        return next;
    }
}
