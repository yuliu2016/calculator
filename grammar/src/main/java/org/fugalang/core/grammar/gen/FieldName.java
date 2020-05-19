package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.ClassName;
import org.fugalang.core.grammar.pgen.AndRule;
import org.fugalang.core.grammar.pgen.OrRule;
import org.fugalang.core.grammar.pgen.RepeatRule;

import static org.fugalang.core.grammar.util.ParserStringUtil.*;

public class FieldName {
    public static String getSmartName(ClassName className, AndRule andRule, TokenConverter converter) {
        if (isSingle(andRule.repeatRule()) && andRule
                .repeatRules()
                .stream()
                .map(AndRule.AndRule2::repeatRule)
                .allMatch(FieldName::isSingle)) {
            StringBuilder sb1 = null;
            StringBuilder sb2 = null;
            for (RepeatRule rule : PEGCompat.allRepeatRules(andRule)) {
                var subRuleString = PEGCompat.getSubruleString(rule.subRule());
                if (isWord(subRuleString)) {
                    if (sb1 == null) sb1 = new StringBuilder();
                    sb1.append(convertCase(subRuleString));
                } else {
                    if (sb2 == null) sb2 = new StringBuilder();
                    sb2.append(converter.checkToken(subRuleString).orElseThrow().getFieldName());
                }
            }
            if (sb1 != null) {
                return decap(sb1.toString());
            }
            if (sb2 != null) {
                return decap(sb2.toString());
            }
            throw new IllegalArgumentException();
        }
        return className.decapName();
    }

    private static boolean isSingle(RepeatRule repeatRule) {
        if (repeatRule.hasTimesOrPlus()) {
            return false;
        }
        var sub = repeatRule.subRule();
        return sub.hasString() || sub.hasName();
    }

    public static String getSmartName(ClassName className, OrRule orRule, TokenConverter converter) {
        var andList = orRule.orRule2s();
        if (andList.isEmpty()) {
            return getSmartName(className, orRule.andRule(), converter);
        }
        if (andList.size() == 1) {
            return getSmartName(className, orRule.andRule(), converter) +
                    "Or" + capitalizeFirstChar(getSmartName(className, andList.get(0).andRule(), converter));
        }
        return className.decapName();
    }

    private static boolean isWord(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if (!(c == '_' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }
}
