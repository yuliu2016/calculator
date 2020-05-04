package org.fugalang.core.pprint;

import java.util.List;

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
                builder.addUnquoted("()");
            } else if (component instanceof List) {
                builder.addElem(new ListStringElem((List<?>) component));
            } else if (component instanceof TreeStringElem) {
                builder.addElem((TreeStringElem) component);
            } else {
                builder.addString(component.toString());
            }
        }
    }
}
