package org.fugalang.core.parser;

import org.fugalang.core.pprint.TreeStringBuilder;

public interface NodeDelegate {
    void addChoice(Object choice);

    void addChoice(boolean value, String literal);

    void addRequired(boolean value, String literal);

    void addRequired(Object value);

    void addOptional(boolean value, String literal);

    void addOptional(Object value);

    String simpleString();

    void buildString(TreeStringBuilder builder);

    void didBuildRule();
}
