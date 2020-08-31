package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.peg.wrapper.*;
import org.fugalang.grammar.util.GrammarRepr;
import org.fugalang.grammar.util.PEGUtil;
import org.fugalang.grammar.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class RuleSetBuilder {

    // make arguments clear
    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    private final List<Rule> rules;
    private final Map<String, TokenEntry> tokenMap;
    private final Map<String, RuleName> ruleNameMap = new LinkedHashMap<>();
    private final RuleSet ruleSet;

    public RuleSetBuilder(
            Grammar grammar,
            Map<String, TokenEntry> tokenMap) {
        this.rules = grammar.rules();
        this.tokenMap = tokenMap;
        this.ruleSet = new RuleSet(tokenMap);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static RuleSet generateRuleSet(Grammar grammar, Map<String, TokenEntry> tokenMap) {
        var builder = new RuleSetBuilder(grammar, tokenMap);
        builder.generateRuleSet();
        return builder.ruleSet;
    }

    private void generateRuleSet() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var rule : rules) {
            var ruleName = rule.name();
            ruleNameMap.put(ruleName, RuleName.of(ruleName));
        }

        for (var rule : rules) {
            var left_recursive = PEGUtil.isLeftRecursive(rule.name(), rule.ruleSuite().altList());

            var ruleName = ruleNameMap.get(rule.name());

            // use a root named rule to reduce files
            UnitRule unit = ruleSet.createNamedRule(ruleName, left_recursive);

            var ruleRepr = GrammarRepr.INSTANCE.visitRule(rule);
            unit.setGrammarString(ruleRepr);
            unit.setRuleType(RuleType.Disjunction);

            addAltList(ruleName, unit, rule.ruleSuite().altList());

            // protect against not initializing result
            unit.guardMatchEmptyString();

            // for checking invariant state
            ruleSet.namedRuleDone();
        }
    }


    private void addAltList(
            RuleName ruleName,
            UnitRule unit,
            AltList altList
    ) {
        if (altList.altList2s().isEmpty()) {
            // only one rule - can propagate fields of this rule
            // but need to change the type here
            unit.setRuleType(RuleType.Conjunction);
            addSequence(ruleName, unit, altList.sequence(), REQUIRED);
        } else {

            // For counting component sequences
            int sequenceCount = 1;

            // must create new sub rules for AND rules that have more than one
            // REPEAT rule
            for (var sequence : PEGUtil.allSequences(altList)) {

                // add sequence count to the name, even if not used, because
                // otherwise could result in the same rule names later,
                // when there are two branches
                var newRuleName = ruleName.suffix(sequenceCount);
                sequenceCount++;

                if (sequence.primaries().size() == 1) {
                    // only one repeat rule - can propagate fields of this unit
                    addSequence(newRuleName, unit, sequence, REQUIRED);
                } else {
                    // need to make a new unit for this, because
                    // a list can't hold multiple-ly typed objects
                    var subUnit = ruleSet.createUnnamedSubRule(newRuleName);

                    var ruleRepr = GrammarRepr.INSTANCE.visitSequence(sequence);
                    subUnit.setGrammarString(ruleRepr);
                    subUnit.setRuleType(RuleType.Conjunction);

                    var smartName = getSmartName(newRuleName, sequence);

                    // Add a field to the rule set
                    // The reason to do this first is that if adding the rule fails,
                    // this field can still show that this point was reached
                    addField(newRuleName,
                            unit,
                            smartName,
                            Modifier.Once,
                            REQUIRED,
                            ResultSource.ofUnitRule(newRuleName),
                            null);

                    addSequence(newRuleName, subUnit, sequence, REQUIRED);
                }
            }
        }
    }

    private void addSequence(
            RuleName ruleName,
            UnitRule unit,
            Sequence sequence,
            boolean isOptional
    ) {
        if (sequence.primaries().size() == 1) {
            addPrimary(ruleName, unit, sequence.primaries().get(0), isOptional);
        } else {
            // don't need to check for subrules - every Primary
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int primaryCount = 1;

            for (var primary : sequence.primaries()) {
                var ruleNameWithCount = ruleName.suffix(primaryCount);
                primaryCount++;

                addPrimary(ruleNameWithCount, unit, primary, isOptional);
            }
        }
    }

    private void addPrimary(
            RuleName ruleName,
            UnitRule unit,
            Primary primary,
            boolean isOptional
    ) {
        var item = PEGUtil.getModifierItem(primary);
        var modifier = PEGUtil.getModifier(primary);

        TokenEntry delimiter;
        if (primary.hasDelimited()) {
            delimiter = tokenMap.get(primary.delimited().string());
            if (!delimiter.isLiteral()) throw new RuntimeException("Delimiter must be literal");
        } else {
            delimiter = null;
        }

        switch (PEGUtil.getRuleType(item)) {
            case Group:
                addAltListAsComponent(ruleName, unit, item.group().altList(),
                        modifier, REQUIRED, delimiter);
                break;
            case Optional:
                addAltListAsComponent(ruleName, unit,
                        item.optional().altList(), modifier, OPTIONAL, delimiter);
                break;
            case Token:
                addToken(unit, modifier, PEGUtil.getItemString(item), isOptional, delimiter);
                break;
        }
    }

    private void addAltListAsComponent(
            RuleName ruleName,
            UnitRule unit,
            AltList altList,
            Modifier modifier,
            boolean isOptional,
            TokenEntry delimiter
    ) {
        // maybe this can just be added to this unit rule
        // but maybe there needs to be a separate sub-rule

        if (altList.altList2s().isEmpty() && altList.sequence().primaries().size() == 1 &&
                modifier == Modifier.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addSequence(ruleName, unit, altList.sequence(), isOptional);
        } else {

            var component_cb = ruleSet.createUnnamedSubRule(ruleName);
            var rule_repr = GrammarRepr.INSTANCE.visitAltList(altList);
            component_cb.setGrammarString(rule_repr);
            component_cb.setRuleType(RuleType.Disjunction);

            var smart_name = getSmartName(ruleName, altList);

            // Add a field to the rule set
            // The reason to do this first is that if adding the rule fails,
            // this field can still show that this point was reached
            addField(ruleName,
                    unit,
                    smart_name,
                    modifier,
                    isOptional,
                    ResultSource.ofUnitRule(ruleName),
                    delimiter);

            addAltList(ruleName, component_cb, altList);
        }
    }

    private void addToken(
            UnitRule unit,
            Modifier modifier,
            String token,
            boolean isOptional,
            TokenEntry delimiter
    ) {

        if (ruleNameMap.containsKey(token)) {
            // it is referring to another named rule.
            var ruleName = ruleNameMap.get(token);

            // fix - need to add repeat rules here
            addField(ruleName,
                    unit,
                    ruleName.getSnakeCase(),
                    modifier,
                    isOptional,
                    ResultSource.ofUnitRule(ruleName),
                    delimiter);
        } else {

            // not another named rule generated by the parser
            // add a token value instead

            var tokenEntry = tokenMap.get(token);
            var ruleName = unit.getRuleName();

            if (tokenEntry.isLiteral()) {
                var fieldName = "is_" + tokenEntry.getNameSnakeCase();
                var resultSource = ResultSource.ofTokenLiteral(tokenEntry);

                addField(ruleName,
                        unit,
                        fieldName,
                        modifier,
                        isOptional,
                        resultSource,
                        delimiter);
            } else {
                var fieldName = tokenEntry.getNameSnakeCase();
                unit.setContainsTokenType(true);
                var resultSource = ResultSource.ofTokenType(tokenEntry);

                addField(ruleName,
                        unit,
                        fieldName,
                        modifier,
                        isOptional,
                        resultSource,
                        delimiter);
            }
        }
    }

    private void addField(
            RuleName ruleName,
            UnitRule unit,
            String fieldName,
            Modifier modifier,
            boolean isOptional,
            ResultSource resultSource,
            TokenEntry delimiter
    ) {
        RuleName newRuleName;
        String newFieldName;
        FieldType fieldType;
        switch (modifier) {
            case TestTrue:
                newFieldName = fieldName;
                newRuleName = ruleName;
                fieldType = FieldType.RequireTrue;
                break;
            case TestFalse:
                newFieldName = fieldName;
                newRuleName = ruleName;
                fieldType = FieldType.RequireFalse;
                break;
            case OnceOrMore:
                unit.setContainsList(true);
                newRuleName = ruleName.asSequence();
                newFieldName = StringUtil.pluralize(fieldName);
                fieldType = FieldType.RequiredList;
                break;
            case NoneOrMore:
                unit.setContainsList(true);
                newRuleName = ruleName.asSequence();
                newFieldName = StringUtil.pluralize(fieldName);
                fieldType = FieldType.OptionalList;
                break;
            case Once:
                newRuleName = ruleName;
                newFieldName = fieldName;
                fieldType = isOptional ? FieldType.Optional : FieldType.Required;
                break;
            default:
                throw new IllegalArgumentException();
        }

        var field = new UnitField(
                newRuleName,
                newFieldName,
                fieldType,
                resultSource,
                delimiter);

        unit.addField(field);
    }

    public String getSmartName(RuleName ruleName, Sequence sequence) {
        var primaries = sequence.primaries();
        if (primaries.size() <= 3 &&
                primaries.stream().allMatch(PEGUtil::isSingle)) {

            StringJoiner joiner = new StringJoiner("_");
            for (var primary : primaries) {
                var itemString = PEGUtil.getItemString(PEGUtil.getModifierItem(primary));
                if (itemString == null) throw new IllegalStateException();
                if (StringUtil.isWord(itemString)) {
                    // make lowercase in case it's a token type
                    joiner.add(itemString.toLowerCase());
                } else {
                    joiner.add(tokenMap.get(itemString).getNameSnakeCase());
                }
            }
            return StringUtil.decap(joiner.toString());
        }
        return ruleName.getSnakeCase();
    }


    public String getSmartName(RuleName ruleName, AltList altList) {
        var andList = altList.altList2s();
        if (andList.isEmpty()) {
            return getSmartName(ruleName, altList.sequence());
        }
        if (andList.size() == 1) {
            return getSmartName(ruleName, altList.sequence()) +
                    "_or_" + getSmartName(ruleName, andList.get(0).sequence()
            );
        }
        return ruleName.getSnakeCase();
    }
}
