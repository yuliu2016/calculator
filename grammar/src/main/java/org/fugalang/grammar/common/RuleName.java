package org.fugalang.grammar.common;

import org.fugalang.grammar.util.StringUtil;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RuleName {
    private final String ruleNameStr;
    private final int[] suffixArray;
    private final boolean isSequence;

    public RuleName(String ruleNameStr, int[] suffixArray, boolean isSequence) {
        assert ruleNameStr != null;
        assert suffixArray != null;
        this.ruleNameStr = ruleNameStr;
        this.suffixArray = suffixArray;
        this.isSequence = isSequence;
    }

    public String getRuleNameStr() {
        return ruleNameStr;
    }

    public String getRuleNameFull() {
        return ruleNameStr + IntStream
                .of(suffixArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(":", ":", ""));
    }

    public String getPascalCase() {
        return StringUtil.convertCase(ruleNameStr);
    }

    public String getCamelCase() {
        return StringUtil.decap(getPascalCase());
    }

    public boolean isSequence() {
        return isSequence;
    }

    public RuleName suffix(int suffix) {
        int currentLen = suffixArray.length;
        int[] newArray = Arrays.copyOf(suffixArray, currentLen + 1);
        newArray[currentLen] = suffix;
        return new RuleName(ruleNameStr, newArray, isSequence);
    }

    public RuleName asSequence() {
        return new RuleName(ruleNameStr, suffixArray, true);
    }

    public static RuleName of(String ruleName) {
        return new RuleName(ruleName, new int[]{}, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleName ruleName1 = (RuleName) o;

        return ruleNameStr.equals(ruleName1.ruleNameStr);
    }

    @Override
    public int hashCode() {
        return ruleNameStr.hashCode();
    }
}
