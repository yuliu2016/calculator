package org.fugalang.grammar.classbuilder;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class NamedClass {
    private final ClassBuilder rootClass;
    private final List<ClassBuilder> components;

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

    public void generateVisitor(StringBuilder sb) {
        rootClass.generateVisitor(sb);
    }

    public boolean isLeftRecursive() {
        return rootClass.isLeftRecursive();
    }

    public ClassBuilder getRootClass() {
        return rootClass;
    }

    public List<ClassBuilder> getComponents() {
        return components;
    }
}
