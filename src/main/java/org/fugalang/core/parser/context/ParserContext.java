package org.fugalang.core.parser.context;

import org.fugalang.core.parser.ParserElement;

import java.util.function.Supplier;

public interface ParserContext {
    void logError(ParserElement element, String message);

    void logWarning(ParserElement element, String message);

    void log(String message);

    void log(Supplier<String> message);

    boolean didFinish(int index);

    ParserElement getElem(int index);
}
