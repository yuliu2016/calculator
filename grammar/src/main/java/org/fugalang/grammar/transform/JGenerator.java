package org.fugalang.grammar.transform;

import org.fugalang.grammar.common.NamedRule;
import org.fugalang.grammar.common.RuleSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JGenerator {

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


    public static void generateFiles(RuleSet ruleSet, JPackageOutput packageOutput) {
        try {
            generate(ruleSet, packageOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generate(RuleSet ruleSet, JPackageOutput packageOutput) throws IOException {
        setupDir(packageOutput.getWrapperPath());

        for (var rule : ruleSet.namedRules()) {
            var code = fixLineSep(JTransform
                    .generateWrapper(rule, packageOutput.getWrapperPackage()));
            Files.writeString(Paths.get(packageOutput.getWrapperPath().toString(),
                    rule.getRoot().ruleName().pascalCase() + ".java"), code);
        }

        var lang = packageOutput.getLanguage();
        setupDir(packageOutput.getParserPath());

        var parserBase = packageOutput.getParserPath().toString();
        var parserPath = Paths.get(parserBase, lang + "Parser.java");
        var parserClassCode = fixLineSep(generateParserClass(ruleSet, packageOutput));
        Files.writeString(parserPath, parserClassCode);

        var parserRulePath = Paths.get(parserBase, lang + "Rules.java");
        var ruleClassCode = fixLineSep(generateRuleClass(ruleSet, packageOutput));
        Files.writeString(parserRulePath, ruleClassCode);

        setupDir(packageOutput.getVisitorPath());

        var visitorBase = packageOutput.getVisitorPath().toString();
        var visitorPath = Paths.get(visitorBase, lang + "Visitor.java");
        var visitorCode = fixLineSep(generateVisitorClass(ruleSet, packageOutput));
        Files.writeString(visitorPath, visitorCode);
    }

    private static String generateParserClass(RuleSet ruleSet, JPackageOutput packageOutput) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getParserPackage()).append(";\n\n");
        sb.append("import org.fugalang.core.parser.ParseTree;\n");
        sb.append("""
                        import org.fugalang.core.token.TokenType;

                        import static\s""")
                .append(packageOutput.getParserPackage());
        sb.append(".");
        sb.append(packageOutput.getLanguage());
        sb.append("""
                Rules.*;

                @SuppressWarnings("UnusedReturnValue")
                public class\s""");
        sb.append(packageOutput.getLanguage());
        sb.append("Parser {\n");
        for (var rule : ruleSet.namedRules()) {
            JTransform.generateParser(sb, rule);
        }
        sb.append("}\n");
        return sb.toString();
    }

    private static String generateRuleClass(RuleSet ruleSet, JPackageOutput packageOutput) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getParserPackage()).append(";\n\n");
        sb.append("import org.fugalang.core.parser.ParserRule;\n\n");

        var imports = ruleSet.namedRules().stream()
                .anyMatch(namedRule -> namedRule.getRoot().leftRecursive()) ?
                "import static org.fugalang.core.parser.ParserRule.*;\n\n" :
                """
                        import static org.fugalang.core.parser.ParserRule.and_rule;
                        import static org.fugalang.core.parser.ParserRule.or_rule;

                        """;

        sb.append(imports)
                .append("public class ");
        sb.append(packageOutput.getLanguage());
        sb.append("Rules {\n");

        for (NamedRule rule : ruleSet.namedRules()) {
            JTransform.generateRule(sb, rule);
        }

        sb.append("}\n");
        return sb.toString();
    }

    private static String generateVisitorClass(RuleSet ruleSet, JPackageOutput packageOutput) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getVisitorPackage()).append(";\n\n");
        sb.append("import ")
                .append(packageOutput.getWrapperPackage());
        sb.append(".*;\n\n@SuppressWarnings(\"unused\")\npublic interface ");
        sb.append(packageOutput.getLanguage());
        sb.append("Visitor<T> {\n");

        for (NamedRule rule : ruleSet.namedRules()) {
            JTransform.generateVisitor(sb, rule);
        }

        sb.append("}\n");
        return sb.toString();
    }
}
