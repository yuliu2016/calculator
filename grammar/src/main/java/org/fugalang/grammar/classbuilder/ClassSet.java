package org.fugalang.grammar.classbuilder;

import org.fugalang.grammar.gen.PackageOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassSet {

    private final PackageOutput packageOutput;

    private final List<NamedClass> classes;

    // represents the working top-level class
    private NamedClass currentClass;

    public ClassSet(PackageOutput packageOutput) {
        this.packageOutput = packageOutput;
        classes = new ArrayList<>();
    }

    public List<NamedClass> getClasses() {
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

        var rootClassBuilder = new ClassBuilder(packageOutput, className);

        currentClass = new NamedClass(rootClassBuilder);
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
        for (var builder : current.components) {
            if (builder.getClassName().equals(className.getType())) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (var builder : current.components) {
                System.out.println(builder.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate inner class: " + className);
        }

        var builder = new ClassBuilder(packageOutput, className);

        current.components.add(builder);

        return builder;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void setupDir(Path path) {
        var rootFile = path.toFile();

        if (!rootFile.isDirectory()) {
            rootFile.mkdirs();
        }

        var fileList = rootFile.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                file.delete();
            }
        }
    }

    private static String fixLineSep(String s) {
        return s.replace("\n", System.lineSeparator());
    }

    public void writeToFiles() {
        try {
            setupDir(packageOutput.getFilePath());

            for (var aClass : classes) {
                var code = fixLineSep(aClass.generateClassCode());
                Files.writeString(Paths.get(packageOutput.getFilePath().toString(),
                        aClass.getClassName() + ".java"), code);
            }

            setupDir(packageOutput.getParserPath());

            var parserBase = packageOutput.getParserPath().toString();
            var parserPath = Paths.get(parserBase, packageOutput.getLanguage() + "Parser.java");
            var parserClassCode = fixLineSep(generateParserClass());
            Files.writeString(parserPath, parserClassCode);

            var parserRulePath = Paths.get(parserBase, packageOutput.getLanguage() + "Rules.java");
            var ruleClassCode = fixLineSep(generateRuleClass());
            Files.writeString(parserRulePath, ruleClassCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateParserClass() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getParserPackage()).append(";\n\n");
        sb.append("import org.fugalang.core.parser.ParseTree;\n");
        sb.append("import org.fugalang.core.token.TokenType;\n\n" + "import static ")
                .append(packageOutput.getParserPackage());
        sb.append(".");
        sb.append(packageOutput.getLanguage());
        sb.append("Rules.*;\n\n" +
                "@SuppressWarnings(\"UnusedReturnValue\")\n" +
                "public class ");
        sb.append(packageOutput.getLanguage());
        sb.append("Parser {\n");
        for (NamedClass namedClass : classes) {
            namedClass.generateParser(sb);
        }
        sb.append("}\n");
        return sb.toString();
    }

    private String generateRuleClass() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getParserPackage()).append(";\n\n");
        sb.append("import org.fugalang.core.parser.ParserRule;\n\n");
        sb.append("import static org.fugalang.core.parser.ParserRule.and_rule;\n" +
                "import static org.fugalang.core.parser.ParserRule.or_rule;\n\n" +
                "public class ");
        sb.append(packageOutput.getLanguage());
        sb.append("Rules {\n");
        for (NamedClass namedClass : classes) {
            namedClass.generateRules(sb);
        }
        sb.append("}\n");
        return sb.toString();
    }
}
