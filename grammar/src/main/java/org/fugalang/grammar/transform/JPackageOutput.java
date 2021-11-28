package org.fugalang.grammar.transform;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JPackageOutput {
    private final Path filePath;
    private final String packageName;
    private final String language;

    public JPackageOutput(String basePath, String relPath, String packageName, String language) {
        this.packageName = packageName;
        this.filePath = Paths.get(basePath, relPath,
                packageName.replace(".", "/"));
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getWrapperPackage() {
        return packageName + ".wrapper";
    }

    public Path getWrapperPath() {
        return Paths.get(filePath.toString(), "wrapper");
    }

    public Path getParserPath() {
        return Paths.get(filePath.toString(), "parser");
    }

    public String getParserPackage() {
        return packageName + ".parser";
    }

    public Path getVisitorPath() {
        return Paths.get(filePath.toString(), "visitor");
    }

    public String getVisitorPackage() {
        return packageName + ".visitor";
    }
}
