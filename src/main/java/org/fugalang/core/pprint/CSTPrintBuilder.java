package org.fugalang.core.pprint;

import java.util.List;

public interface CSTPrintBuilder {
    CSTPrintBuilder setName(String name);

    CSTPrintBuilder addString(String token);

    CSTPrintBuilder addElem(CSTPrintElem elem);

    @SuppressWarnings("UnusedReturnValue")
    CSTPrintBuilder addElems(List<? extends CSTPrintElem> elems);
}
