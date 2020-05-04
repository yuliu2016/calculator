package org.fugalang.core.parser;

import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class WrapperDelegate implements NodeDelegate {
    private final ParserRule rule;

    // string
    private String str = null;
    private int index = 0;

    private boolean didBuildRule = false;

    // disjunction fields
    private int chosenIndex = -1;
    private Object chosenComponent;

    // conjunction fields
    private final List<Object> components = new ArrayList<>();

    public WrapperDelegate(ParserRule rule) {
        this.rule = rule;
    }

    @Override
    public void addChoice(Object choice) {
        if (didBuildRule) {
            computeRuleFail("Node has already been built");
        }
        if (rule.getRuleType() == RuleType.Conjunction) {
            computeRuleFail("Cannot call addChoice with a conjunction rule");
        }

        if (choice != null) {
            if (chosenIndex > 0) {
                computeRuleFail("The value at index " + index + " must be mutually exclusive" +
                        "  with the value at index" + chosenIndex + " for rule " + rule);
            }
            chosenIndex = index;
            chosenComponent = choice;
        }

        index++;
    }

    @Override
    public void addChoice(boolean value, String literal) {
        if (didBuildRule) {
            computeRuleFail("Node has already been built");
        }
        if (rule.getRuleType() == RuleType.Conjunction) {
            computeRuleFail("Cannot call addChoice with a conjunction rule");
        }

        if (value) {
            if (chosenIndex > 0) {
                computeRuleFail("The value at index " + index + " must be mutually exclusive" +
                        " with the value at index " + chosenIndex + " for rule " + rule);
            }
            chosenIndex = index;
            chosenComponent = "'" + literal + "'";
        }

        index++;
    }

    @Override
    public void addRequired(boolean value, String literal) {
        if (didBuildRule) {
            throw new IllegalStateException("Node has already been built");
        }
        if (rule.getRuleType() == RuleType.Disjunction) {
            computeRuleFail("Cannot call addRequired with a disjunction rule");
        }

        if (!value) {
            computeRuleFail("The value at index " + index + " is a required boolean field for " +
                    rule + ", but it is false");
        }

        components.add("'" + literal + "'");
        index++;
    }

    @Override
    public void addRequired(Object value) {
        if (didBuildRule) {
            throw new IllegalStateException("Node has already been built");
        }
        if (rule.getRuleType() == RuleType.Conjunction) {
            computeRuleFail("Cannot call addRequired with a disjunction rule");
        }

        if (value == null) {
            computeRuleFail("The value at index " + index + " is a required field for " +
                    rule + ", but it is null");
        }

        components.add(value);
        index++;
    }

    @Override
    public void addOptional(Object value) {
        if (didBuildRule) {
            throw new IllegalStateException("Node has already been built");
        }
        if (rule.getRuleType() == RuleType.Conjunction) {
            computeRuleFail("Cannot call addOptional with a disjunction rule");
        }

        components.add(value);
        index++;
    }

    @Override
    public String simpleString() {
        if (str != null) {
            return str;
        }
        switch (rule.getRuleType()) {
            case Disjunction -> {
                var maybeName = (rule.isExplicit() ? rule.getRuleName() : "") + "#" + chosenIndex;
                str = "(" + maybeName + " " + chosenComponent + ")";
                return str;
            }
            case Conjunction -> {
                var maybeName = rule.isExplicit() ? rule.getRuleName() + " " : "";
                str = components.stream().map(Object::toString)
                        .collect(Collectors.joining(" ", "(" + maybeName, ")"));
            }
        }
        return str;
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        switch (rule.getRuleType()) {

            case Disjunction -> {
                if (rule.isExplicit()) {
                    builder.setName(rule.getRuleName() + "#" + chosenIndex);
                }

                if (chosenComponent instanceof TreeStringElem) {
                    builder.addElem((TreeStringElem) chosenComponent);
                } else {
                    builder.addString(chosenComponent.toString());
                }
            }
            case Conjunction -> {
                if (rule.isExplicit()) {
                    builder.setName(rule.getRuleName());
                }

                for (Object component : components) {
                    if (component instanceof TreeStringElem) {
                        builder.addElem((TreeStringElem) component);
                    } else {
                        builder.addString(component.toString());
                    }
                }
            }
        }
    }

    private static void computeRuleFail(String msg) {
        System.out.println(msg);
//        throw new IllegalArgumentException(msg);
    }

    @Override
    public void didBuildRule() {
        didBuildRule = true;
    }
}
