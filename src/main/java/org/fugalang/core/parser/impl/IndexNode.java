package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.*;

import java.util.Collections;
import java.util.List;

class IndexNode implements ParseTreeNode {

    static final ParseTreeNode NULL = new IndexNode(null, false,
            false, null, null);

    private final List<ParseTreeNode> children;

    private final boolean isPresent;
    private final boolean isCollection;
    private final ParserRule rule;
    private final ParserElement element;

    public IndexNode(List<ParseTreeNode> children,
                     boolean isPresent,
                     boolean isCollection,
                     ParserRule rule,
                     ParserElement element) {
        this.children = children;
        this.isPresent = isPresent;
        this.isCollection = isCollection;
        this.rule = rule;
        this.element = element;
    }

    @Override
    public ParseTreeNode getItem(int index) {
        // Fixes the OR problem
        if (children == null || index >= children.size()) {
            return NULL;
        }
        return children.get(index);
    }

    @Override
    public boolean isPresent() {
        return isPresent;
    }

    @Override
    public void failIfAbsent() {
        if (!isPresent) {
            throw new ParserException("This node does not contain a value");
        }
    }

    @Override
    public void failIfAbsent(ParserRule rule) {
        failIfAbsent();
        if (this.rule != rule) {
            throw new ParserException("Expecting rule " + rule +
                    ", but the rule of this node is " + this.rule);
        }
    }

    @Override
    public void failIfAbsent(ElementType type) {
        failIfAbsent();
        if (element.getType() != type) {
            throw new ParserException("Expecting type " + type +
                    ", but the type of this node is " + element.getType());
        }
    }

    @Override
    public Iterable<ParseTreeNode> asCollection() {
        if (!isPresent || !isCollection) {
            return Collections.emptyList();
        }
        return children;
    }

    @Override
    public int sizeOfChildren() {
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean asBoolean() {
        if (rule != null) {
            throw new ParserException("Cannot be a rule and a boolean");
        }
        return isPresent;
    }

    @Override
    public String asString() {
        if (rule != null) {
            throw new ParserException("Cannot be a rule and a string");
        }
        return element.getValue();
    }

    @Override
    public boolean isLeaf() {
        return isPresent && element != null && children == null;
    }

    @Override
    public String toString() {
        return "N{" +
                (rule == null ? "" : "R=" + rule + ", ") +
                "P=" + isPresent +
                (element == null ? "" : ", E=" + element) +
                (children == null ? "" : ", C=" + children) +
                '}';
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        if (!isPresent) {
            throw new ParserException("Cannot build string with an empty node");
        }

        if (isCollection) {
            builder.setOpenBracket("[");
            builder.setCloseBracket("]");
            builder.setDelimiter(",");
            addChildren(builder);
        } else {
            if (rule == null) {
                throw new ParserException("Cannot build string without a rule");
            }
            if (rule.getRuleType() == RuleType.Disjunction) {
                int chosenIndex = 0;
                for (ParseTreeNode node : children) {
                    if (node.isPresent()) break;
                    chosenIndex++;
                }
                var chosenComponent = children.get(chosenIndex);

                builder.setName(rule.getRuleName() + "#" + chosenIndex);
                addNode(chosenComponent, builder);
            } else if (rule.getRuleType() == RuleType.Conjunction) {
                builder.setName(rule.getRuleName());
                addChildren(builder);
            }
        }
    }

    private void addNode(ParseTreeNode node, TreeStringBuilder builder) {
        // this accounts for empty lists
        if (node != null && node.isPresent()) {
            // need to make sure that for a leaf, it doesn't add another set of brackets
            if (node.isLeaf()) {
                builder.addString(node.asString());
            } else {
                builder.addElem(node);
            }
        }
    }

    private void addChildren(TreeStringBuilder builder) {
        for (var node : children) {
            addNode(node, builder);
        }
    }
}
