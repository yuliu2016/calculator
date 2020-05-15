package org.fugalang.core.parser.context;

import org.fugalang.core.parser.ParserContext;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.parser.ParserElement;

import java.util.List;
import java.util.function.Supplier;

@Deprecated(forRemoval = true)
public class SimpleContext implements ParserContext {

    private final List<? extends ParserElement> elements;
    private final boolean debug;

    public SimpleContext(List<? extends ParserElement> elements, boolean debug) {
        this.elements = elements;
        this.debug = debug;
    }

    @Override
    public void errorForElem(int index, String message) {
        var tok = getElem(index);
        throw new SyntaxError(message + ": token = '" + tok.getValue() + "'");
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
