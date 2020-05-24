package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParseTreeNode;

public class MemoValue {
    private final int endPos;
    private final ParseTreeNode result;

    public MemoValue(int endPos, ParseTreeNode result) {
        this.endPos = endPos;
        this.result = result;
    }

    public boolean hasResult() {
        return result != null;
    }

    public ParseTreeNode getResult() {
        return result;
    }

    public int getEndPos() {
        return endPos;
    }

    @Override
    public String toString() {
        return hasResult() ? "succ" : "fail";
    }
}
