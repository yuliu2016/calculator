package org.fugalang.core.parser;

import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ConjunctionRule implements TreeStringElem {

    private ComputedRule computedRule = null;

    protected abstract void buildRule();

    protected void addRequired(String name, boolean value) {
        computedRule.addRequired(name, value);
    }

    protected void addRequired(String name, Object value) {
        computedRule.addRequired(name, value);
    }

    protected void addOptional(String name, Object value) {
        computedRule.addOptional(name, value);
    }

    public void validate() {
        if (computedRule == null) {
            computedRule = new ComputedRule();
        }
    }

    public int length() {
        validate();
        return computedRule.getLength();
    }

    public Object getValue(int index) {
        validate();
        return computedRule.getComponents().get(index);
    }

    public boolean hasValue(int index) {
        return getValue(index) != null;
    }

    public <T> T getValue(int index, Class<T> cls) {
        return cls.cast(getValue(index));
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

    private String getName() {
        return getClass().getSimpleName();
    }

    private static void computeRuleFail(String msg) {
        throw new IllegalArgumentException(msg);
    }

    private class ComputedRule {
        private final List<Object> components = new ArrayList<>();
        private int hashCode;
        private boolean hashed;
        private String str = null;
        private int length;

        public ComputedRule() {
            ConjunctionRule.this.buildRule();
        }

        private void addRequired(String name, boolean value) {
            if (!value) {
                computeRuleFail(name + " is a required boolean field for " +
                        getName() + ", but it is false");
            }
            components.add(name);
            length++;
        }

        private void addRequired(String name, Object value) {
            if (value == null) {
                computeRuleFail(name + " is a required field for " +
                        getName() + ", but it is null");
            }
            components.add(value);
            length++;
        }

        @SuppressWarnings("unused")
        private void addOptional(String name, Object value) {
            components.add(value);
            if (value != null) {
                length++;
            }
        }

        private boolean fastEquals(Object obj) {
            return obj.hashCode() == getHashCode();
        }

        private String simpleString() {
            if (str == null) {
                str = components.stream().map(Object::toString)
                        .collect(Collectors.joining(" ", "(" + getName(), ")"));
            }
            return str;
        }

        public int getHashCode() {
            if (!hashed) {
                hashCode = components.hashCode();
                hashed = true;
            }
            return hashCode;
        }

        public int getLength() {
            return length;
        }

        public List<Object> getComponents() {
            return components;
        }

        private void buildString(TreeStringBuilder builder) {
            builder.setName(getName());

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
