package org.fugalang.grammar.cgen;

import org.fugalang.grammar.common.PEGUtil;
import org.fugalang.grammar.gen.PEGBuilder;
import org.fugalang.grammar.gen.TokenConverter;
import org.fugalang.grammar.peg.wrapper.Grammar;
import org.fugalang.grammar.peg.wrapper.Rule;
import org.fugalang.grammar.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The C version of {@link PEGBuilder}
 */
public class CpegBuilder {
    private final List<Rule> rules;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final CpegRuleSet ruleSet;

    public CpegBuilder(
            Grammar grammar,
            TokenConverter converter,
            CpegConfig config) {
        this.rules = grammar.rules();
        this.converter = converter;
        this.ruleSet = new CpegRuleSet(config);
    }

    public void generate(boolean toFiles) {
        if (!ruleSet.getNamedRules().isEmpty()) {
            throw new IllegalStateException("Cannot repeat generation");
        }

        generateRuleSet();

        if (toFiles) {
            ruleSet.writeToFiles();
        }
    }

    private void generateRuleSet() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var rule : rules) {
            classNameMap.put(rule.name(), StringUtil.convertCase(rule.name()));
        }

        for (var rule : rules) {
            var left_recursive = PEGUtil.isLeftRecursive(rule.name(), rule.altList());

            var realClassName = classNameMap.get(rule.name());
//            var className = ClassName.of(realClassName, rule.name());
//
//            // use a root class to reduce files
//            CpegFrame cf = ruleSet.createRootClass(className, left_recursive);
//
//            var rule_repr = Stringifier.INSTANCE.visitRule(rule);
//            cf.setHeaderComments(rule_repr);
//            cf.setRuleType(RuleType.Disjunction);
//
//            addAltList(className, cf, rule.altList());
//
//            // protect against not initializing result
//            cf.guardMatchEmptyString();
//
//            // for checking invariant state
//            classSet.markRootClassDone();
        }
    }
}
