package org.fugalang.core.parser;

import org.fugalang.core.pprint.ParseTreePPrint;
import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

public abstract class DisjunctionRule implements TreeStringElem {

    private ComputedRule computedRule = null;

    protected abstract void buildRule();

    protected void setExplicitName(String ruleName){
        computedRule.setRuleName(ruleName, true);
    }

    protected void setImpliedName(String ruleName){
        computedRule.setRuleName(ruleName, false);
    }

    protected void addChoice(String name, Object choice) {
        computedRule.addChoice(name, choice);
    }

    protected void addChoice(String name, boolean value) {
        computedRule.addChoice(name, value);
    }

    public void validate() {
        if (computedRule == null) {
            computedRule = new ComputedRule();
        }
    }

    public int getIndex() {
        validate();
        return computedRule.getChosenIndex();
    }

    public Object getValue() {
        validate();
        return computedRule.getChosenComponent();
    }

    public <T> T getValue(Class<T> cls) {
        return cls.cast(getValue());
    }

    @Override
    public String toString() {
        validate();
        return computedRule.simpleString();
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        validate();
        computedRule.buildString(builder);
    }

    public String prettyFormat() {
        return ParseTreePPrint.format(this, 2);
    }

    @Override
    public final int hashCode() {
        validate();
        return computedRule.getHashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof ConjunctionRule) {
            validate();
            return computedRule.fastEquals(obj);
        }
        return false;
    }

    private static void computeRuleFail(String msg) {
        throw new IllegalArgumentException(msg);
    }

    private class ComputedRule {
        private boolean hashed;
        private int hashCode;
        private String str = null;
        private int index = 0;
        private int chosenIndex = -1;
        private Object chosenComponent;
        private String chosenName;
        private String ruleName;
        private boolean isExplicitName;

        public ComputedRule() {
            DisjunctionRule.this.buildRule();

            if (chosenIndex < 0) {
                computeRuleFail("Rule" + ruleName + " must have at least one valid choice");
            }
        }

        private void setRuleName(String ruleName, boolean isExplicitName) {
            this.ruleName = ruleName;
            this.isExplicitName = isExplicitName;
        }

        private void addChoice(String name, boolean value) {
            if (value) {
                if (chosenIndex > 0) {
                    computeRuleFail(name + "must be mutually exclusive for rule " + ruleName +
                            "; currently " + chosenName + " at index " + chosenIndex +
                            " has already been chosen");
                }
                chosenIndex = index;
                chosenComponent = name;
                chosenName = name;
            }
            index++;
        }

        private void addChoice(String name, Object value) {
            if (value != null) {
                if (chosenIndex > 0) {
                    computeRuleFail(name + "must be mutually exclusive for rule " + ruleName +
                            "; currently " + chosenName + " at index " + chosenIndex +
                            " has already been chosen");
                }
                chosenIndex = index;
                chosenComponent = name;
                chosenName = name;
            }
            index++;
        }

        private boolean fastEquals(Object obj) {
            return obj.hashCode() == getHashCode();
        }

        private String simpleString() {
            if (str == null) {
                var maybeName = (isExplicitName ? ruleName : "") + "#" + chosenIndex;
                str = "(" + maybeName + " " + chosenComponent + ")";
            }
            return str;
        }

        public int getChosenIndex() {
            return chosenIndex;
        }

        public int getHashCode() {
            if (!hashed) {
                hashCode = chosenComponent.hashCode();
                hashed = true;
            }
            return hashCode;
        }

        public Object getChosenComponent() {
            return chosenComponent;
        }

        public void buildString(TreeStringBuilder builder) {

            if (isExplicitName) {
                builder.setName(ruleName + "#" + chosenIndex);
            }

            if (chosenComponent instanceof TreeStringElem) {
                builder.addElem((TreeStringElem) chosenComponent);
            } else {
                builder.addString(chosenComponent.toString());
            }
        }
    }
}
