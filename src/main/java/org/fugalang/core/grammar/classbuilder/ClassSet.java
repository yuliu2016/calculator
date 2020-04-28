package org.fugalang.core.grammar.classbuilder;

import java.nio.file.Path;
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
        var builder = new ClassBuilder(packageName, className);
        builders.add(builder);
        return builder;
    }
}
