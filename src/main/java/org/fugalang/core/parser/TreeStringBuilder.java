package org.fugalang.core.parser;

@SuppressWarnings("UnusedReturnValue")
public interface TreeStringBuilder {
    TreeStringBuilder setName(String name);

    TreeStringBuilder addString(String token);

    TreeStringBuilder addElem(TreeStringElem elem);

    TreeStringBuilder setOpenBracket(String openBracket);

    TreeStringBuilder setCloseBracket(String closeBracket);

    TreeStringBuilder setDelimiter(String delimiter);
}
