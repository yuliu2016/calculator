package org.fugalang.core.parser.context;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.ParserElement;

import java.util.List;
import java.util.function.Supplier;

public class SimpleContext implements ParserContext {

    private final List<ParserElement> elements;
    private final boolean debug;

    public SimpleContext(List<ParserElement> elements, boolean debug) {
        this.elements = elements;
        this.debug = debug;
    }

    @Override
    public void logError(ParserElement tok, String message) {
        throw new SyntaxError("Invalid syntax: lines from " +
                tok.getLineStart() + " to " + tok.getLineEnd() + " and columns from " +
                tok.getColumnStart() + " to " + tok.getColumnEnd());
    }

    @Override
    public void logWarning(ParserElement tok, String message) {
        if (debug) System.out.println("Invalid syntax: lines from " +
                tok.getLineStart() + " to " + tok.getLineEnd() + " and columns from " +
                tok.getColumnStart() + " to " + tok.getColumnEnd());
    }

    @Override
    public void log(String message) {
        if (debug) System.out.println(message);
    }

    @Override
    public void log(Supplier<String> message) {
        if (debug) System.out.println(message.get());
    }

    @Override
    public boolean didFinish(int index) {
        return index >= elements.size();
    }

    @Override
    public ParserElement getElem(int index) {
        return elements.get(index);
    }
}
