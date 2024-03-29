package org.fugalang.grammar.transform;

import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CTransform {

    private static void error(String message) {
        throw new GrammarException(message);
    }

    public static String generateParser(GrammarSpec spec) {
        return new CTransform(spec).generate();
    }
    
    private final GrammarSpec spec;
    private StringBuilder sb2;
    private List<Integer> previousHashes;

    public CTransform(GrammarSpec spec) {
        this.spec = spec;
    }
    
    private String generate() {
        sb2 = new StringBuilder();
        previousHashes = new ArrayList<>();
        for (NamedDirective directive : spec.directives()) {
            execute(directive.name(), directive.argument());
        }
        return sb2.toString();
    }
    
    private CTransform emit(Object ob) {
        sb2.append(ob);
        return this;
    }
    
    private void execute(String name, Object arg) {
        switch (name) {
            case "include" -> addInclude(arg);
            case "code" -> addCode(arg);
            case "fdecl" -> addForwardDeclarations();
            case "exp" -> addNamedExpansion(arg);
            case "space" -> addSpace(arg);
            case "operators" -> {}
            default -> error("Unknown directive: " + name);
        }
    }

    @SuppressWarnings("unchecked")
    private static String firstStrArg(Object arg) {
        return ((List<String>) arg).get(0);
    }
    
    private void addInclude(Object arg) {
        emit("#include \"%s\"\n".formatted(firstStrArg(arg)));
    }

    private void addCode(Object arg) {
        emit("%s\n".formatted(firstStrArg(arg)));
    }

    private void addSpace(Object arg) {
        var lines = Integer.parseInt(firstStrArg(arg));
        emit("\n".repeat(lines));
    }

    private void addForwardDeclarations() {
        emit("\n\n");
        for (NamedRule namedRule : spec.namedRules()) {
            addUnitRuleDeclaration(namedRule.root());
            for (UnitRule component : namedRule.components()) {
                addUnitRuleDeclaration(component);
            }
        }
        emit("\n");
    }

    private void addUnitRuleDeclaration(UnitRule unit) {
        var rn = unit.ruleName();

        if (rn.returnType() != null &&
                !rn.returnType().endsWith("*") && !unit.isInline()) {
            error("Only pointer types allowed for non-inline rule: " + rn );
        }

        emit("static ").emit(rn.returnTypeOr("void *"))
                .emit(rn.symbolicName());
        emit("();\n");
        for (UnitField field : unit.fields()) {
            if (field.isSingular() || field.isPredicate()) {
                continue;
            }
            emit("static ast_list_t *");
            emit(field.ruleName().symbolicName());
            if (field.delimiter() == null) {
                emit("_loop");
            } else {
                emit("_delimited");
            }
            emit("();\n");
        }
    }
    
    private void addNamedExpansion(Object arg) {
        var namedRule = (NamedRule) arg;
        addUnitRuleBody(namedRule.root(), namedRule.args());
        for (UnitRule component : namedRule.components()) {
            addUnitRuleBody(component, Collections.emptyMap());
        }
    }

    private String ruleNameHash(RuleName ruleName) {
        String sym = ruleName.symbolicName();

        int h = 0;
        for (byte v : sym.getBytes(StandardCharsets.UTF_8)) {
            h = 31 * h + (v & 0xff);
        }

        int hash = Math.floorMod(h, 1000);
        while (previousHashes.contains(hash)) {
            hash++;
        }
        previousHashes.add(hash);
        return String.valueOf(hash);
    }

    
    private void addUnitRuleBody(UnitRule unit, Map<String, String> args) {
        emit("\n");
        emit(StringUtil.inlinedoc(unit.grammarString()));

        var rn = unit.ruleName();
        var returnType = rn.returnTypeOr("void *");
        emit("\nstatic ").emit(returnType)
                .emit(rn.symbolicName());
        emit("() {\n");

        String resultName;
        String hashStr;
        if (unit.isInline()) {
            // not needed
            resultName = "ERROR";
            hashStr = "ERROR";
        } else {
            hashStr = ruleNameHash(rn);
            resultName = "res_" + hashStr;
        }

        if (!unit.isInline()) {
            emit("    if (errorcode()) return 0;\n\n");
        }

        boolean memoize = args.containsKey("memo") || unit.leftRecursive();
        if (memoize) {
            if (unit.isInline())
                error("Inline rules cannot be memoized");
            emit("    memo_t *memo = fetch_memo(").emit(hashStr).emit(", FUNC);\n");
            emit("    if (memo) {\n");
            emit("        return memo->node;\n");
            emit("    }\n\n");
        }

        emit("    size_t _pos = pos();\n");
        if (!unit.isInline()) {
            var resultDeclare = "    " + returnType + resultName;
            emit(resultDeclare);
            emit(unit.leftRecursive() ? " = 0;\n" : ";\n");
        }

        if (unit.leftRecursive()) {
            addLeftRecursiveUnitRuleBody(unit, hashStr);
        } else {
            switch (unit.ruleType()) {
                case Disjunction -> addDisjunctionBody(unit, hashStr);
                case Conjunction -> addConjunctionBody(unit, hashStr);
            }
        }

        if (memoize && !unit.leftRecursive()) {
            emit("    insert_memo(_pos, " + hashStr + ", ").emit(resultName).emit(");\n");
        }

        if (!unit.isInline()) {
            emit("    exit_frame(_pos, ").emit(resultName).emit(", FUNC);\n");
            emit("    return ").emit(resultName).emit(";\n");
        }

        emit("}\n");

        for (UnitField field : unit.fields()) {
            getLoopParser(field);
        }
    }

    private void addLeftRecursiveUnitRuleBody(UnitRule unit,  String hashStr) {
        var rtype = unit.ruleName().returnTypeOr("void *");
        var resultName = "res_" + hashStr;
        var altName = "alt_" + hashStr;

        emit("    ").emit(rtype).emit(altName).emit(";\n");
        emit("    size_t maxpos;\n");
        emit("    ").emit(rtype).emit("max;\n");

        if (unit.isInline()) error("LR rules cannot be inline");

        emit("\n");
        emit("    enter_frame(FUNC);\n\n");

        emit("    do {\n");
        emit("        maxpos = pos();\n");
        emit("        max = ").emit(resultName).emit(";\n");
        emit("        insert_memo(_pos, ").emit(hashStr).emit(", max);\n");
        emit("        restore(_pos);\n\n");
        emit("        if (\n");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            emit("            (").emit(altName).emit(" = ");
            emit(getParserFieldExpr(fields.get(i)));

            if (i == fields.size() - 1) {
                emit(")");
            } else {
                emit(") ||\n");
            }
        }
        emit("\n        ) ").emit(resultName).emit(" = ").emit(altName).emit(";\n\n");
        emit("""
                    } while (pos() > maxpos);
                    
                    restore(maxpos);
                    %s = max;
                    insert_memo(_pos, %s, %s);
                    
                """.formatted(resultName, hashStr, resultName));
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() ||
                (field.resultSource().isTokenLiteral() && field.isRequired()));
    }

    private void addConjunctionBody(UnitRule unit, String hashStr) {
        List<String> fieldNames = new ArrayList<>();

        int j = 0;
        for (UnitField field : unit.fields()) {
            if (!isImportantField(field)) continue;

            var name = field.fieldName().snakeCaseUnconflicted();
            fieldNames.add(name);

            if (unit.resultClause() != null) {
                var template = unit.resultClause().template();
                var varName = "%" + ((char) ('a' + j));
                if (!template.contains(varName)) {
                    j++;
                    continue;
                }
            }
            j++;
            var type = getParserFieldType(field);
            emit("    ").emit(type).emit(name).emit(";\n");
        }

        var resultName = "res_" + hashStr;

        if (!unit.isInline()) {
            emit("\n    enter_frame(FUNC);\n");
        }
        emit("\n    if (\n");

        var fields = unit.fields();
        j = 0;
        for (int i = 0; i < fields.size(); i++) {
            emit("        ");
            emit("(");
            var field = fields.get(i);
            if (isImportantField(field)) {
                var fieldName = field.fieldName().snakeCaseUnconflicted();

                if (unit.resultClause() != null) {
                    var template = unit.resultClause().template();
                    var varName = "%" + ((char) ('a' + j));
                    if (template.contains(varName)) {
                        emit(fieldName);
                        emit(" = ");
                    }
                } else {
                    emit(fieldName);
                    emit(" = ");
                }
                j++;
            }

            emit(getParserFieldExpr(field));

            if (i == fields.size() - 1) {
                emit(")");
            } else {
                emit(") &&\n");
            }
        }

        String templatedResult;
        if (unit.resultClause() == null) {
            templatedResult = "node()";
        } else {
            var resultClause = unit.resultClause().template();
            for (int i = 0; i < fieldNames.size(); i++) {
                var name = fieldNames.get(i);
                var templateName = "%" + ((char) ('a' + i));
                resultClause = resultClause.replace(templateName, name);
            }
            templatedResult = resultClause;
        }

        if (unit.isInline()) {
            emit("\n    ) {\n");
            emit("        return ");
            emit(templatedResult);
            emit(";\n    } else {\n");
            emit("        restore(_pos);\n");
            if (unit.ruleName().returnTypeOr("void *").endsWith("*")) {
                emit("        return NULL;");
            } else {
                emit("        return 0;");
            }
            emit("\n    }\n");
        } else {
            emit("\n    ) {\n");
            emit("        " + resultName + " = ");
            emit(templatedResult);
            emit(";\n    } else {\n");
            emit("        restore(_pos);\n");
            emit("        " + resultName + " = NULL;\n");
            emit("    }\n\n");
        }
    }


    private static String getRawParserFieldType(UnitField field) {
        var kind = field.resultSource().kind();
        return switch (kind) {
            case TokenLiteral, TokenType -> "token_t *";
            case UnitRule -> field.resultSource().asRuleName().returnTypeOr("void *");
        };
    }

    private static String getParserFieldType(UnitField field) {
        String type;
        if (field.isSingular() || field.isPredicate()) {
            type = getRawParserFieldType(field);
        } else type = "ast_list_t *";
        return type;
    }

    private void addDisjunctionBody(UnitRule unit, String hashStr) {

        for (UnitField field : unit.fields()) {
            var template = field.resultClause().template().strip();
            // Direct fallthrough
            if (template.equals("%a")) continue;
            // Still need to AND the result, but don't need separate variable
            if (!template.contains("%a")) continue;

            var type = getParserFieldType(field);
            var name = field.fieldName().snakeCaseUnconflicted();
            emit("    ").emit(type).emit(name).emit(";\n");
        }

        var resultType = unit.ruleName().returnTypeOr("void *");
        var resultName = "res_" + hashStr;
        String altName;
        if (unit.isInline()) {
            altName = "alt";
        } else {
            altName = "alt_" + hashStr;
        }

        emit("    ").emit(resultType).emit(altName).emit(";\n\n");
        if (!unit.isInline()) {
            emit("    enter_frame(FUNC);\n\n");
        }
        emit("    if (");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            var field = fields.get(i);
            var fieldName = field.fieldName().snakeCaseUnconflicted();

            if (field.resultClause() == null)
                error("Result clause required for field " + fieldName);

            var template = field.resultClause().template().strip();

            if (template.equals("%a")) {
                // Fall-through; no extra variables needed
                emit("\n        (").emit(altName).emit(" = ")
                        .emit(getParserFieldExpr(field));
                if (i == fields.size() - 1) {
                    emit(")");
                } else {
                    emit(") ||");
                }
            } else {
                if (i != 0) emit(" ");
                emit("(\n            (");

                if (template.contains("%a")) {
                    // Only assign to variable if it's actually used
                    emit(fieldName).emit(" = ");
                }

                emit(getParserFieldExpr(field)).emit(") &&\n");

                emit("            ");
                emit("(").emit(altName).emit(" = ");

                emit(field.resultClause().template().replace("%a", fieldName));
                if (i == fields.size() - 1) {
                    emit("))");
                } else {
                    emit(")\n        ) ||");
                }
            }

        }


        if (unit.isInline()) {
            emit("\n    ) {\n");
            emit("        return ");
            emit(altName);
            emit(";\n    } else {\n");
            emit("        restore(_pos);\n");
            if (resultType.endsWith("*")) {
                emit("        return NULL;");
            } else {
                emit("        return 0;");
            }
            emit("\n    }\n");
        } else {
            emit("\n    ) {\n");
            emit("        " + resultName + " = ");
            emit(altName);
            emit(";\n    } else {\n");
            emit("        restore(_pos);\n");
            emit("        " + resultName + " = NULL;\n");
            emit("    }\n\n");
        }
    }

    private static String getParserFieldExpr(UnitField field) {
        return switch (field.fieldType()) {
            case RequireTrue -> getTestExpr(field);
            case RequireFalse -> "!" + getTestExpr(field);
            case Required -> getResultExpr(field);
            case Optional -> getResultExpr(field) + ", 1";
            case RequiredList, OptionalList -> getNewLoopExpr(field);
        };
    }

    private void getLoopParser(UnitField field) {
        if (field.isSingular() || field.isPredicate()) {
            return;
        }
        String s = field.fieldType() == FieldType.RequiredList ?
                getRequiredLoopParser(field) : getOptionalLoopParser(field);
        emit(s);
    }

    private static String getRequiredLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field);
        var ruleName = field.ruleName().symbolicName();

        var listName = "list";
        var fname = "_" + field.fieldName().snakeCase();
        var rawType = getRawParserFieldType(field);

        TokenEntry delimiter = field.delimiter();
        if (delimiter == null) {
            return "\nstatic ast_list_t *" + ruleName + "_loop() {\n" +
                    "    " + rawType + fname + " = " + resultExpr + ";\n" +
                    "    if (!" + fname + ") {\n" +
                    "        return 0;\n" +
                    "    }\n\n" +
                    "    ast_list_t *" + listName + " = ast_list_new();\n" +
                    "    do {\n" +
                    "        ast_list_append(" + listName + ", " + fname + ");\n" +
                    "    } while ((" + fname + " = " + resultExpr + "));\n\n" +
                    "    return " + listName + ";\n" +
                    "}\n";
        } else {
            var delimExpr = "consume(" + delimiter.index() + ", \"" + delimiter.literalValue() + "\")";

            return "\nstatic ast_list_t *" + ruleName + "_delimited() {\n" +
                    "    " + rawType + fname + " = " + resultExpr + ";\n" +
                    "    if (!" + fname + ") {\n" +
                    "        return NULL;\n" +
                    "    }\n\n" +
                    "    ast_list_t *" + listName + " = ast_list_new();\n" +
                    "    size_t _pos;\n\n" +
                    "    do {\n" +
                    "        ast_list_append(" + listName + ", " + fname + ");\n" +
                    "        _pos = pos();\n" +
                    "    } while (\n" +
                    "        " + delimExpr + " &&\n" +
                    "        (" + fname + " = " + resultExpr + ")\n" +
                    "    );\n\n" +
                    "    restore(_pos);\n" +
                    "    return " + listName + ";\n" +
                    "}\n";
        }
    }

    private static String getOptionalLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field);
        var rule_name = field.ruleName().symbolicName();

        var listName = "list";
        var fname = "_" + field.fieldName().snakeCase();
        var rawType = getRawParserFieldType(field);

        return "\nstatic ast_list_t *" + rule_name + "_loop() {\n" +
                "    ast_list_t *" + listName + " = ast_list_new();\n" +
                "    " + rawType + fname + ";\n\n" +
                "    while ((" + fname + " = " + resultExpr + ")) {\n" +
                "        ast_list_append(" + listName + ", " + fname + ");\n" +
                "    }\n\n" +
                "    return " + listName + ";\n" +
                "}\n";
    }

    private static String getNewLoopExpr(UnitField field) {
        var name = field.ruleName().symbolicName();
        if (field.delimiter() == null) {
            return name + "_loop()";
        } else {
            return name + "_delimited()";
        }
    }


    private static String getTestExpr(UnitField field) {
        return "test_and_reset(_pos, " + getResultExpr(field) + ")";
    }

    private static String getResultExpr(UnitField field) {
        var rs = field.resultSource();
        String innerName;
        if (rs.isUnitRule()) {
            innerName = rs.asRuleName().symbolicName() + "()";
        } else if (rs.isTokenType() || rs.isTokenLiteral()) {
            var te = rs.asTokenEntry();
            innerName = "consume(" + te.index() + ", \"" + te.literalValue() + "\")";
        } else throw new IllegalArgumentException();
        return innerName;
    }
}
