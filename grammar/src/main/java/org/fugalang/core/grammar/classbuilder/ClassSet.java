package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.PackageOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassSet {

    private final PackageOutput packageOutput;

    private final List<ClassComponents> classes;

    // represents the working top-level class
    private ClassComponents currentClass;

    public ClassSet(PackageOutput packageOutput) {
        this.packageOutput = packageOutput;
        classes = new ArrayList<>();
    }

    public List<ClassComponents> getClasses() {
        return classes;
    }

    public ClassBuilder createRootClass(ClassName className) {
        var dupError = false;
        for (var cls : classes) {
            if (cls.getClassName().equals(className.getType())) {
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

        var rootClassBuilder = new ClassBuilder(packageOutput.getPackageName(),
                className.getType(), className.getRuleName());

        currentClass = new ClassComponents(rootClassBuilder);
        classes.add(currentClass);

        return rootClassBuilder;
    }

    public void markRootClassDone() {
        if (currentClass == null) {
            throw new IllegalStateException("No root class being worked on");
        }
        currentClass = null;
    }

    public ClassBuilder createComponentClass(ClassName className) {

        if (currentClass == null) {
            throw new IllegalStateException("No root class to add component to");
        }

        var current = currentClass;

        var dupError = false;
        for (var builder : current.componentClasses) {
            if (builder.getClassName().equals(className.getType())) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (var builder : current.componentClasses) {
                System.out.println(builder.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate inner class: " + className);
        }

        var builder = new ClassBuilder(packageOutput.getPackageName(),
                className.getType(), className.getRuleName());

        current.componentClasses.add(builder);

        return builder;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void writeToFiles() {
        try {
            var rootFile = packageOutput.getFilePath().toFile();

            if (!rootFile.isDirectory()) {
                rootFile.mkdirs();
            }

            var fileList = rootFile.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    file.delete();
                }
            }

            for (var aClass : classes) {
                var code = aClass.generateClassCode()
                        .replace("\n", System.lineSeparator());

                Files.writeString(Paths.get(packageOutput.getFilePath().toString(),
                        aClass.getClassName() + ".java"), code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
