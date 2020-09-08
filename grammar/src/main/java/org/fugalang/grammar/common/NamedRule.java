package org.fugalang.grammar.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NamedRule {
    private final UnitRule rootClass;
    private final List<UnitRule> components;
    private final Map<String, String> args;

    public NamedRule(UnitRule root, Map<String, String> args) {
        this.rootClass = root;
        this.args = args;
        components = new ArrayList<>();
    }

    public UnitRule getRoot() {
        return rootClass;
    }

    public List<UnitRule> getComponents() {
        return components;
    }

    public Map<String, String> getArgs() {
        return args;
    }
}
