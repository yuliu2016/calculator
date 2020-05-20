package org.fugalang.core.grammar.classbuilder;

import java.util.ArrayList;
import java.util.List;

public class ClassComponents {
    public final ClassBuilder rootClass;
    public final List<ClassBuilder> componentClasses;

    public ClassComponents(ClassBuilder rootClass) {
        this.rootClass = rootClass;
        componentClasses = new ArrayList<>();
    }

    public String generateClassCode() {
        return rootClass.generateClassCode(componentClasses);
    }

    public String getClassName() {
        return rootClass.getClassName();
    }

    @Override
    public String toString() {
        return "ClassWithComponents{" +
                "rootClass=" + rootClass +
                ", componentClasses=" + componentClasses +
                '}';
    }
}
