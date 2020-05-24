package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParserRule;

public class MemoKey {
    private final int position;
    private final ParserRule parserRule;

    public MemoKey(int position, ParserRule parserRule) {
        this.position = position;
        this.parserRule = parserRule;
    }

    public int getPosition() {
        return position;
    }

    public ParserRule getParserRule() {
        return parserRule;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MemoKey)) {
            return false;
        }
        MemoKey that = (MemoKey) o;
        return position == that.position && parserRule == that.parserRule;
    }

    @Override
    public int hashCode() {
        return 31 * parserRule.hashCode() + position;
    }
}
