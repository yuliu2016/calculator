package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.ParseStringUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassName {
    private final String realClassName;
    private final List<String> genericWrappers;

    private ClassName(String realClassName, List<String> genericWrappers) {
        this.realClassName = realClassName;
        this.genericWrappers = genericWrappers;
    }

    public String asType() {
        StringBuilder sb = new StringBuilder();

        for (String genericWrapper : genericWrappers) {
            sb.append(genericWrapper);
            sb.append("<");
        }

        // assuming classes start with upper-case,
        // this deals with booleans

        if (genericWrappers.isEmpty()) {
            sb.append(realClassName);
        } else {
            sb.append(ParseStringUtil.capitalizeFirstCharOnly(realClassName));
        }

        sb.append(">".repeat(genericWrappers.size()));

        return sb.toString();
    }

    public ClassName wrap(String genericWrapper) {
        List<String> wrappers = new ArrayList<>();
        wrappers.add(genericWrapper);
        wrappers.addAll(genericWrappers);
        return new ClassName(realClassName, wrappers);
    }

    public ClassName suffix(Object suffix) {
        return new ClassName(realClassName + suffix, List.copyOf(genericWrappers));
    }

    public static ClassName of(String realClassName) {
        return new ClassName(realClassName, List.of());
    }
}
