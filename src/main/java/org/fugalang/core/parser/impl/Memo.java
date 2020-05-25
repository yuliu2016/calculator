package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParseTreeNode;

public class Memo {
    private final int endPos;
    private final ParseTreeNode result;

    public Memo(int endPos, ParseTreeNode result) {
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
