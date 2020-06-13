package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.LexingContext;
import org.fugalang.core.parser.ParserContext;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.util.LazyArrayList;

import java.util.Iterator;

public class LazyParserContext implements ParserContext {

    private final LazyArrayList<ParserElement> elements;
    private final LexingContext lexingContext;

    private LazyParserContext(Iterator<ParserElement> elements, LexingContext lexingContext) {
        this.elements = new LazyArrayList<>(elements);
        this.lexingContext = lexingContext;
    }

    public static ParserContext of(Iterator<ParserElement> elements, LexingContext lexingContext) {
        return new LazyParserContext(elements, lexingContext);
    }

    @Override
    public void errorForElem(int index, String message) {
        ParserElement tok;

        if (elements.didFinish(index)) {
            tok = elements.getLast();
        } else {
            tok = elements.get(index);
        }

        var lineno = tok.getLineStart();
        var line = lexingContext.getLine(lineno);

        var msg = ErrorFormatter.format(message, lineno, line, tok.getColumnStart(), tok.getColumnEnd());

        throw new SyntaxError(msg);
    }

    @Override
    public boolean didFinish(int index) {
        return elements.didFinish(index);
    }

    @Override
    public ParserElement getElem(int index) {
        return elements.get(index);
    }
}