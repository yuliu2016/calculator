package org.fugalang.core.pprint;

import java.util.List;

public interface TreeStringBuilder {
    TreeStringBuilder setName(String name);

    TreeStringBuilder addString(String token);

    TreeStringBuilder addElem(TreeStringElem elem);

    @SuppressWarnings("UnusedReturnValue")
    TreeStringBuilder addElems(List<? extends TreeStringElem> elems);
}
