package org.fugalang.grammar.classbuilder;

import org.fugalang.grammar.util.ParserStringUtil;

public class ClassName {
    private final String realClassName;
    private final boolean isList;
    private final String typeStr;
    private final String ruleName;

    private ClassName(String realClassName, boolean isList, String ruleName) {
        this.realClassName = realClassName;
        this.isList = isList;
        this.ruleName = ruleName;
        typeStr = computeType();
    }

    private String computeType() {
        if (isList) {
            return "List<" + ParserStringUtil.capitalizeFirstChar(realClassName) + ">";
        }
        return realClassName;
    }

    public String getType() {
        return typeStr;
    }

    public String getRealClassName() {
        return realClassName;
    }

    public String getRuleName() {
        if (ruleName == null) {
            throw new RuntimeException("No Rule Name");
        }
        return ruleName;
    }

    public ClassName suffix(int suffix) {
        return new ClassName(
                realClassName + suffix, isList,
                ruleName == null ? null : ruleName + ":" + suffix
        );
    }

    public ClassName asList() {
        return new ClassName(realClassName, true, ruleName);
    }

    public String decapName() {
        return ParserStringUtil.decap(typeStr);
    }

    @Override
    public String toString() {
        return typeStr;
    }

    public static ClassName of(String realClassName, String ruleName) {
        return new ClassName(realClassName, false, ruleName);
    }
}
