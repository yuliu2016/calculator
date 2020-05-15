package org.fugalang.core.pprint;

import org.fugalang.core.parser.TreeStringBuilder;
import org.fugalang.core.parser.TreeStringElem;

import java.util.List;

@Deprecated
public class ListStringElem implements TreeStringElem {

    private final List<?> components;

    public ListStringElem(List<?> components) {
        this.components = components;
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        builder.setOpenBracket("[");
        builder.setCloseBracket("]");
        builder.setDelimiter(",");

        for (Object component : components) {
            if (component == null) {
                continue;
            }
            if (component instanceof List) {
                var list = (List<?>) component;
                if (!list.isEmpty()) {
                    builder.addElem(new ListStringElem((List<?>) component));
                }
            } else if (component instanceof TreeStringElem) {
                builder.addElem((TreeStringElem) component);
            } else {
                builder.addString(component.toString());
            }
        }
    }
}
