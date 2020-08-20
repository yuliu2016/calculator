package org.fugalang.grammar.common;

import java.util.ArrayList;
import java.util.List;

public class NamedRule {
    private final UnitRule rootClass;
    private final List<UnitRule> components;

    public NamedRule(UnitRule root) {
        this.rootClass = root;
        components = new ArrayList<>();
    }

    public UnitRule getRoot() {
        return rootClass;
    }

    public List<UnitRule> getComponents() {
        return components;
    }
}
