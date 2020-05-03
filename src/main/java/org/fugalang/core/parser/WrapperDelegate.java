package org.fugalang.core.parser;

import org.fugalang.core.pprint.TreeStringBuilder;

class WrapperDelegate implements NodeDelegate {
    private final ParserRule rule;

    public WrapperDelegate(ParserRule rule) {
        this.rule = rule;
    }

    @Override
    public void addChoice(Object choice) {

    }

    @Override
    public void addChoice(boolean value) {

    }

    @Override
    public void addRequired(boolean value) {

    }

    @Override
    public void addRequired(Object value) {

    }

    @Override
    public void addOptional(Object value) {

    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public void buildString(TreeStringBuilder builder) {

    }
}
