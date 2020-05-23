package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.parser.ParserRule;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeFrame {

    private final int position;
    private final int level;
    private final ParserRule rule;

    private final List<ParseTreeNode> nodes = new ArrayList<>();

    List<ParseTreeNode> collection = null;

    public ParseTreeFrame(int position, int level, ParserRule rule) {
        this.position = position;
        this.level = level;
        this.rule = rule;
    }

    public int position() {
        return position;
    }

    public int getLevel() {
        return level;
    }

    public ParserRule getRule() {
        return rule;
    }

    public List<ParseTreeNode> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "ParseTreeFrame";
    }
}