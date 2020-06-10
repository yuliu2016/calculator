package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.parser.ParserRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Frame {

    final int position;
    final ParserRule rule;
    final Map<ParserRule, Memo> memo_at_pos;

    boolean isTest = false;

    // nodes in this frame
    List<ParseTreeNode> nodes = new ArrayList<>();

    // used for tracking collection sub-frames
    List<ParseTreeNode> collection = null;

    // used for tracking left recursion;
    List<ParseTreeNode> left_recursion_nodes = null;

    public Frame(int position, ParserRule rule, Map<ParserRule, Memo> memo_at_pos) {
        this.position = position;
        this.rule = rule;
        this.memo_at_pos = memo_at_pos;
    }

    @Override
    public String toString() {
        return "ParseTreeFrame";
    }
}
