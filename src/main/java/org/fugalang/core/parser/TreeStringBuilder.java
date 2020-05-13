package org.fugalang.core.parser;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface TreeStringBuilder {
    TreeStringBuilder setName(String name);

    TreeStringBuilder addString(String token);

    TreeStringBuilder addElem(TreeStringElem elem);

    @Deprecated
    default TreeStringBuilder addElems(List<? extends TreeStringElem> elems) {
        for (var elem : elems) {
            addElem(elem);
        }
        return this;
    }

    TreeStringBuilder setOpenBracket(String openBracket);

    TreeStringBuilder setCloseBracket(String closeBracket);

    TreeStringBuilder setDelimiter(String delimiter);
}
