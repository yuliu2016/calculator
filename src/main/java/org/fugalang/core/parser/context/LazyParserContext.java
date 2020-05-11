package org.fugalang.core.parser.context;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.ParserElement;

import java.util.Iterator;
import java.util.function.Supplier;

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

        var msg = "Line " + lineno + ":\n    " + line + "\n" +
                " ".repeat(tok.getColumnStart() + 4) +
                "^".repeat(tok.getColumnEnd() - tok.getColumnStart()) +
                "\nError: " + message;

        throw new SyntaxError(msg);
    }

    @Override
    public void log(Supplier<String> message) {
        if (debug) System.out.println(message.get());
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