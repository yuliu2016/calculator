package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.ClassBuilder;
import org.fugalang.core.grammar.classbuilder.ClassSet;
import org.fugalang.core.grammar.psi.*;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParserGenerator {
    private final Map<String, OrRule> ruleMap;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final ClassSet classSet;

    public ParserGenerator(
            Rules rules,
            TokenConverter converter,
            Path path,
            String packageName
    ) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.rules) {
            ruleMap.put(rule.name, rule.orRule);
        }
        this.converter = converter;
        validate();

        classSet = new ClassSet(path, packageName);
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
            if (!ruleMap.containsKey(rule.token) && !converter.didResolveToken(rule.token)) {
                throw new IllegalStateException("'" + rule.token + "' doesn't exist!!!");
            }
        }
    }

    public void generate(boolean toFiles) {
        classSet.getBuilders().clear();
        generateClasses();
        for (ClassBuilder builder : classSet.getBuilders()) {
            System.out.println(builder.generateClassCode());
        }
        if (toFiles) {
            classSet.writeToFiles();
        }
    }

    public void generateClasses() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var entry : ruleMap.entrySet()) {
            classNameMap.put(entry.getKey(), CaseUtil.convertCase(entry.getKey()));
        }

        for (var entry : ruleMap.entrySet()) {
            var className = classNameMap.get(entry.getKey());
            addOrRule(className, entry.getValue());
        }
    }

    private void addOrRule(String className, OrRule orRule) {
        ClassBuilder cb = classSet.create(className);
        cb.setHeaderComments(orRule.toSimpleString());

        int cnt = addAndRule(className, cb, orRule.andRule, 1);
        for (AndRule andRule : orRule.andRules) {
            cnt = addAndRule(className, cb, andRule, cnt);
        }
    }

    private int addAndRule(String className,
                            ClassBuilder cb, AndRule andRule, int cnt) {
        if (andRule.repeatRules.isEmpty()) {
            cnt = addFields(className, cb, andRule.repeatRule, cnt);
            // only one in the sequence
        } else {
            String classWithCount = className + cnt;
            ClassBuilder cb2 = classSet.create(classWithCount);
            for (RepeatRule repeatRule : andRule.repeatRules) {
                cnt = addFields(classWithCount, cb2, repeatRule, cnt);
            }
            cnt++;
        }

        return cnt;
    }

    private int addFields(String className, ClassBuilder cb, RepeatRule repeatRule, int cnt) {

        if(repeatRule.subRule.token != null) {
            if (classNameMap.containsKey(repeatRule.subRule.token)) {
                var nextCls = classNameMap.get(repeatRule.subRule.token);
                cb.addField(nextCls, CaseUtil.decap(nextCls));
            } else {
                var cv = converter.checkToken(repeatRule.subRule.token);
                if (cv.isPresent()) {
                    var cv2 = cv.orElseThrow();
                    var clsName = cv2.getClassName();

                    if (clsName.equals("boolean")) {
                        cb.addField("boolean", CaseUtil.prefixCap("isToken", cv2.getFieldName()));
                    } else {
                        cb.addField(cv2.getClassName(), cv2.getFieldName());
                    }
                }
            }

        }

        return cnt;
    }

    private boolean isAtomic(OrRule orRule) {
        return orRule.andRules.isEmpty() && isAtomic(orRule.andRule);
    }

    private boolean isAtomic(AndRule andRule) {
        return andRule.repeatRules.isEmpty() && isAtomic(andRule.repeatRule);
    }

    private boolean isAtomic(RepeatRule repeatRule) {
        // repeat rules are always atomic
        return isAtomic(repeatRule.subRule);
    }

    private boolean isAtomic(SubRule subRule) {
        return (subRule.groupedOrRule != null && isAtomic(subRule.groupedOrRule)) ||
                (subRule.optionalOrRule != null && isAtomic(subRule.optionalOrRule)) ||
                subRule.token != null;
    }
}
