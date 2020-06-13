package org.fugalang.core.parser.context;

import org.fugalang.core.parser.ParserContext;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.SyntaxError;

import java.util.List;

@Deprecated(forRemoval = true)
public class SimpleContext implements ParserContext {

    private final List<? extends ParserElement> elements;

    public SimpleContext(List<? extends ParserElement> elements) {
        this.elements = elements;
    }

    @Override
    public void errorForElem(int index, String message) {
        var tok = getElem(index);
        throw new SyntaxError(message + ": token = '" + tok.getValue() + "'");
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
