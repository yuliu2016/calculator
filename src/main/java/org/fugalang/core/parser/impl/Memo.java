package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParseTreeNode;

public record Memo(int endPos, ParseTreeNode result) {

    public boolean hasResult() {
        return result != null;
    }

    @Override
    public String toString() {
        return hasResult() ? "succ" : "fail";
    }
}
