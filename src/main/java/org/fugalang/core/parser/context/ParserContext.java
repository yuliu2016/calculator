package org.fugalang.core.parser.context;

import org.fugalang.core.parser.ParserElement;

import java.util.function.Supplier;

public interface ParserContext {
    void errorForElem(int index, String message);

    void log(Supplier<String> message);

    boolean didFinish(int index);

    ParserElement getElem(int index);
}
