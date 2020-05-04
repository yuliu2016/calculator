package org.fugalang.core.pprint;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface TreeStringBuilder {
    TreeStringBuilder setName(String name);

    TreeStringBuilder addString(String token);

    TreeStringBuilder addUnquoted(String token);

    TreeStringBuilder addElem(TreeStringElem elem);

    TreeStringBuilder addElems(List<? extends TreeStringElem> elems);

    TreeStringBuilder setOpenBracket(String openBracket);

    TreeStringBuilder setCloseBracket(String closeBracket);

    TreeStringBuilder setDelimiter(String delimiter);
}
