package org.fugalang.core.parser;

public interface ParserContext {
    void errorForElem(int index, String message);

    boolean didFinish(int index);

    ParserElement getElem(int index);
}
