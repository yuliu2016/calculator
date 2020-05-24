package org.fugalang.core.parser;

public interface ParserContext {
    void errorForElem(int index, String message);

    void log(String message);

    boolean didFinish(int index);

    ParserElement getElem(int index);

    default boolean isDebug() {
        return false;
    }
}
