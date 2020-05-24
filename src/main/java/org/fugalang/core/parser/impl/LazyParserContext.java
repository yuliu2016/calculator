package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.LexingContext;
import org.fugalang.core.parser.ParserContext;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.util.LazyArrayList;

import java.util.Iterator;

public class LazyParserContext implements ParserContext {

    private final LazyArrayList<ParserElement> elements;
    private final boolean debug;
    private final LexingContext lexingContext;

    private LazyParserContext(Iterator<ParserElement> elements, LexingContext lexingContext, boolean debug) {
        this.elements = new LazyArrayList<>(elements);
        this.debug = debug;
        this.lexingContext = lexingContext;
    }

    public static ParserContext of(Iterator<ParserElement> elements, LexingContext lexingContext, boolean debug) {
        return new LazyParserContext(elements, lexingContext, debug);
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
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void log(String message) {
        if (debug) System.out.println(message);
        else System.out.println("Debug off: " + message);
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