package org.fugalang.core.grammar.classbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassSet {
    private final Path path;
    private final String packageName;

    private final List<ClassBuilder> builders;

    public ClassSet(Path path, String packageName) {
        this.path = path;
        this.packageName = packageName;
        builders = new ArrayList<>();
    }

    public Path getPath() {
        return path;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<ClassBuilder> getBuilders() {
        return builders;
    }

    public ClassBuilder create(String className) {

        var dupError = false;
        for (ClassBuilder builder : builders) {
            if (builder.getClassName().equals(className)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (ClassBuilder builder : builders) {
                System.out.println(builder.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate class: " + className);
        }

        var builder = new ClassBuilder(packageName, className);
        builders.add(builder);
        return builder;
    }

    public void writeToFiles() {
        var fileList = path.toFile().listFiles();
        if (fileList != null) {
            for(File file: fileList) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
        try {
            for (ClassBuilder builder : builders) {
                Files.writeString(Paths.get(path.toString(),
                        builder.getClassName() + ".java"), builder.generateClassCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
