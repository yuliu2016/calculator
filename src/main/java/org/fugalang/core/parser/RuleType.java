package org.fugalang.core.parser;

public enum RuleType {
    Disjunction("org.fugalang.core.parser.DisjunctionRule"),
    Conjunction("org.fugalang.core.parser.ConjunctionRule");

    private final String superClass;

    RuleType(String superClass) {
        this.superClass = superClass;
    }

    public String getSuperClass() {
        return superClass;
    }

    public String getSuperClassShort() {
        var split = superClass.split("\\.");
        return split[split.length - 1];
    }
}
