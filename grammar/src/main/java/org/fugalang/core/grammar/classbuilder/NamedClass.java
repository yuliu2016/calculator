package org.fugalang.core.grammar.classbuilder;

import java.util.ArrayList;
import java.util.List;

public class NamedClass {
    public final ClassBuilder rootClass;
    public final List<ClassBuilder> components;

    public NamedClass(ClassBuilder rootClass) {
        this.rootClass = rootClass;
        components = new ArrayList<>();
    }

    public String generateClassCode() {
        return rootClass.generateClassCode(components);
    }

    public String getClassName() {
        return rootClass.getClassName();
    }

    public void generateParser(StringBuilder sb) {
        rootClass.generateParsingFunction(sb, true);
        for (ClassBuilder component : components) {
            component.generateParsingFunction(sb, false);
        }
    }

    public void generateRules(StringBuilder sb) {
        rootClass.generateRuleDeclaration(sb);
        for (ClassBuilder component : components) {
            component.generateRuleDeclaration(sb);
        }
    }
}
