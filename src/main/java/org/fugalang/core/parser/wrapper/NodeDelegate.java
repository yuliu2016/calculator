package org.fugalang.core.parser.wrapper;

import org.fugalang.core.parser.TreeStringBuilder;

@Deprecated
public interface NodeDelegate {
    void addChoice(Object choice);

    void addChoice(boolean value, String literal);

    void addRequired(boolean value, String literal);

    void addRequired(Object value);

    void addOptional(boolean value, String literal);

    void addOptional(Object value);

    String toReprString();

    void buildString(TreeStringBuilder builder);

    void didBuildRule();
}
