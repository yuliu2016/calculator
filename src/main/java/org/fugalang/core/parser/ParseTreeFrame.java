package org.fugalang.core.parser;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeFrame implements ParseTreeMarker {

    private final ParseTreeFrame parentFrame;
    private final int position;
    private final int level;
    private final ParserRule rule;

    private final List<ParseTreeNode> nodes = new ArrayList<>();

    List<ParseTreeNode> collection = null;

    public ParseTreeFrame(ParseTreeFrame parentFrame, int position, int level, ParserRule rule) {
        this.parentFrame = parentFrame;
        this.position = position;
        this.level = level;
        this.rule = rule;
    }

    @Override
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

    public ParseTreeFrame getParentFrame() {
        return parentFrame;
    }

    @Override
    public String toString() {
        return "ParseTreeFrame{" +
                "parentFrame=" + parentFrame +
                ", position=" + position +
                ", level=" + level +
                ", rule=" + rule +
                ", nodes=" + nodes +
                ", collection=" + collection +
                '}';
    }
}
