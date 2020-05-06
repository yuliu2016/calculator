package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.util.ParserStringUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassName {
    private final String realClassName;
    private final List<String> genericWrappers;
    private final String typeStr;
    private final String printName;

    private ClassName(String realClassName, List<String> genericWrappers, String printName) {
        this.realClassName = realClassName;
        this.genericWrappers = genericWrappers;
        this.printName = printName;
        typeStr = computeType();
    }

    private String computeType() {
        if (genericWrappers.isEmpty()) {
            return realClassName;
        }

        StringBuilder sb = new StringBuilder();

        for (String genericWrapper : genericWrappers) {
            sb.append(genericWrapper);
            sb.append("<");
        }

        // assuming classes start with upper-case,
        // this deals with booleans
        sb.append(ParserStringUtil.capitalizeFirstCharOnly(realClassName));

        sb.append(">".repeat(genericWrappers.size()));
        return sb.toString();
    }

    public String asType() {
        return typeStr;
    }

    public String getRealClassName() {
        return realClassName;
    }

    public String asPrintName() {
        if (printName == null) {
            throw new RuntimeException("Cannot get a null print name");
        }
        return printName;
    }

    public ClassName wrapIn(String genericWrapper) {
        List<String> wrappers = new ArrayList<>();
        wrappers.add(genericWrapper);
        wrappers.addAll(genericWrappers);
        return new ClassName(realClassName, wrappers, printName);
    }

    public ClassName suffix(Object suffix) {
        return new ClassName(
                realClassName + suffix.toString(),
                List.copyOf(genericWrappers),
                printName == null ? null : printName + ":" + suffix.toString()
        );
    }

    public String decapName() {
        return ParserStringUtil.decap(typeStr);
    }

    @Override
    public String toString() {
        return typeStr;
    }

    public static ClassName of(String realClassName) {
        return ClassName.of(realClassName, null);
    }

    public static ClassName of(String realClassName, String printName) {
        return new ClassName(realClassName, List.of(), printName);
    }
}
