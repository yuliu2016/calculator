package org.fugalang.core.parser;

import java.util.function.Supplier;

public interface ParserContext {
    void errorForElem(int index, String message);

    void log(Supplier<String> message);

    boolean didFinish(int index);

    ParserElement getElem(int index);
}
