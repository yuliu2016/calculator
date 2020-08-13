package org.fugalang.grammar.cgen;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule that has a name, corresponding to
 * {@link org.fugalang.grammar.classbuilder.NamedClass}
 * <p>
 * having multiple {@link CpegFrame}s
 */
public class CpegNamedRule {
    private final CpegFrame rootClass;
    private final List<CpegFrame> components;

    public CpegNamedRule(CpegFrame root) {
        this.rootClass = root;
        components = new ArrayList<>();
    }

    public CpegFrame getRoot() {
        return rootClass;
    }

    public List<CpegFrame> getComponents() {
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
