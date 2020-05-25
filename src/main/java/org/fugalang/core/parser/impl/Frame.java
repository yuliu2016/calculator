package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.parser.ParserRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Frame {

    final int position;
    final int level;
    final ParserRule rule;
    final List<ParseTreeNode> nodes = new ArrayList<>();
    final Map<ParserRule, Memo> memo_at_pos;

    // a mutable collection of
    List<ParseTreeNode> collection = null;

    public Frame(int position, int level, ParserRule rule, Map<ParserRule, Memo> memo_at_pos) {
        this.position = position;
        this.level = level;
        this.rule = rule;
        this.memo_at_pos = memo_at_pos;
    }

    @Override
    public String toString() {
        return "ParseTreeFrame";
    }
}
