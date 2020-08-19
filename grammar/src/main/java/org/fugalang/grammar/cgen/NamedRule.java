package org.fugalang.grammar.cgen;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule that has a name, corresponding to
 * {@link org.fugalang.grammar.classbuilder.NamedClass}
 * <p>
 * having multiple {@link UnitRule}s
 */
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

    public boolean isLeftRecursive() {
        return rootClass.isLeftRecursive();
    }

    public void generateParser(StringBuilder sb) {
        rootClass.generateParsingFunction(sb, true);
        for (var component : components) {
            component.generateParsingFunction(sb, false);
        }
    }
}
