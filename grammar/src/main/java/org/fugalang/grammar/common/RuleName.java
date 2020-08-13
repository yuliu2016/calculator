package org.fugalang.grammar.common;

import org.fugalang.grammar.util.StringUtil;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RuleName {
    private final String ruleName;
    private final int[] suffixArray;
    private final boolean isSequence;

    public RuleName(String ruleName, int[] suffixArray, boolean isSequence) {
        assert ruleName != null;
        assert suffixArray != null;
        this.ruleName = ruleName;
        this.suffixArray = suffixArray;
        this.isSequence = isSequence;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getRuleNameFull() {
        return ruleName + IntStream
                .of(suffixArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(":", ":", ""));
    }

    public String getPascalCase() {
        return StringUtil.convertCase(ruleName);
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
        return new RuleName(ruleName, newArray, isSequence);
    }

    public RuleName asSequence() {
        return new RuleName(ruleName, suffixArray, true);
    }

    public RuleName of(String ruleName) {
        return new RuleName(ruleName, new int[]{}, false);
    }
}
