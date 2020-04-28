package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.psi.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParserGenerator {
    private final Map<String, OrRule> ruleMap;
    private final TokenValidator validator;

    public ParserGenerator(Rules rules, TokenValidator validator) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.rules) {
            ruleMap.put(rule.name, rule.orRule);
        }
        this.validator = validator;
        validate();
    }

    public void validate() {
        for (OrRule rule : ruleMap.values()) {
            validateOrRule(rule);
        }
    }

    private void validateOrRule(OrRule rule) {
        validateAndRule(rule.andRule);
        for (AndRule andRule : rule.andRules) {
            validateAndRule(andRule);
        }
    }

    private void validateAndRule(AndRule andRule) {
        validateRepeatRule(andRule.repeatRule);
        for (RepeatRule repeatRule : andRule.repeatRules) {
            validateRepeatRule(repeatRule);
        }
    }

    private void validateRepeatRule(RepeatRule rule) {
        assert !(rule.tokenPlus && rule.tokenStar);
        validateSubRule(rule.subRule);
    }

    private void validateSubRule(SubRule rule) {
        if (rule.groupedOrRule != null) {
            validateOrRule(rule.groupedOrRule);
        } else if (rule.optionalOrRule != null) {
            validateOrRule(rule.optionalOrRule);
        } else {
            if (!ruleMap.containsKey(rule.token) && !validator.isValidToken(rule.token)) {
                throw new IllegalStateException("'" + rule.token + "' doesn't exist!!!");
            }
        }
    }

    public String generate(String pkg) {
        return String.format("// Auto Generated Parser\n\npackage %s;\n\npublic class FugaParser {\n%s}",
                pkg, generateClasses());
    }

    public String generateClasses() {
        StringBuilder sb = new StringBuilder();

        for (var entry : ruleMap.entrySet()) {
            var clzName = CaseUtil.convertCase(entry.getKey());
            List<Field> fields = new ArrayList<>();
            sb.append(String.format("\n    public static class %s {\n\n    ", clzName));
            appendOrRuleFields(fields, entry.getValue());
            for (var f : fields) {
                sb.append(f.asFieldDeclaration());
                sb.append("\n");
            }
            sb.append("}\n");
        }

        return sb.toString();
    }

    private static void appendOrRuleFields(List<Field> fields, OrRule orRule) {

    }

    private static class Field {
        private final String type;
        private final String name;

        public Field(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String asFieldDeclaration() {
            return String.format("        public %s %s;", type, name);
        }

        public String asConstructorArg() {
            return String.format("%s %s", type, name);
        }

        public String asConstructorStmt() {
            return String.format("this.%s = %s;", name, name);
        }
    }
}
