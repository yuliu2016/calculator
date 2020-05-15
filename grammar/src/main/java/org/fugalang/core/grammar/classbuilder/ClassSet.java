package org.fugalang.core.grammar.classbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassSet {

    public static class ClassWithComponents {
        public final ClassBuilder rootClass;
        public final List<ClassBuilder> componentClasses;

        public ClassWithComponents(ClassBuilder rootClass) {
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

    private final Path path;
    private final String packageName;

    private final List<ClassWithComponents> classes;

    // represents the working top-level class
    private ClassWithComponents currentClassWithComp;

    public ClassSet(Path path, String packageName) {
        this.path = path;
        this.packageName = packageName;
        classes = new ArrayList<>();
    }

    public List<ClassWithComponents> getClasses() {
        return classes;
    }

    public ClassBuilder createRootClass(ClassName className) {
        var dupError = false;
        for (var cls : classes) {
            if (cls.getClassName().equals(className.asType())) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (var cls : classes) {
                System.out.println(cls.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate class: " + className);
        }

        var rootClassBuilder = new ClassBuilder(packageName,
                className.asType(), className.asPrintName());

        currentClassWithComp = new ClassWithComponents(rootClassBuilder);
        classes.add(currentClassWithComp);

        return rootClassBuilder;
    }

    public void markRootClassDone() {
        if (currentClassWithComp == null) {
            throw new IllegalStateException("No root class being worked on");
        }
        currentClassWithComp = null;
    }

    public ClassBuilder createComponentClass(ClassName className) {

        if (currentClassWithComp == null) {
            throw new IllegalStateException("No root class to add component to");
        }

        var currentClass = currentClassWithComp;

        var dupError = false;
        for (ClassBuilder builder : currentClass.componentClasses) {
            if (builder.getClassName().equals(className.asType())) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (ClassBuilder builder : currentClass.componentClasses) {
                System.out.println(builder.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate inner class: " + className);
        }

        var builder = new ClassBuilder(packageName, className.asType(), className.asPrintName());

        currentClass.componentClasses.add(builder);

        return builder;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void writeToFiles() {
        try {
            var rootFile = path.toFile();

            if (!rootFile.isDirectory()) {
                rootFile.mkdirs();
            }

            var fileList = path.toFile().listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    file.delete();
                }
            }

            for (var aClass : classes) {
                var code = aClass.generateClassCode()
                        .replace("\n", System.lineSeparator());

                Files.writeString(Paths.get(path.toString(),
                        aClass.getClassName() + ".java"), code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
