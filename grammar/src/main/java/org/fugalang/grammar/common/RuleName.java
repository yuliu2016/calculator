package org.fugalang.grammar.common;

import org.fugalang.grammar.util.StringUtil;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RuleName {
    private final String snakeCase;
    private final int[] suffixArray;
    private final boolean isSequence;

    public RuleName(String snakeCase, int[] suffixArray, boolean isSequence) {
        assert snakeCase != null;
        assert suffixArray != null;
        this.snakeCase = snakeCase;
        this.suffixArray = suffixArray;
        this.isSequence = isSequence;
    }

    public String getSnakeCase() {
        return snakeCase;
    }

    public String getRuleNameFull() {
        String suffix = (suffixArray.length == 0 ? "" : IntStream
                .of(suffixArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(":", ":", "")));
        return snakeCase + suffix;
    }

    public String getRuleNameSymbolic() {
        String suffix = (suffixArray.length == 0 ? "" : IntStream
                .of(suffixArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining("_", "_", "")));
        return snakeCase + suffix;
    }

    public String getPascalCase() {
        return StringUtil.convertCase(snakeCase);
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
        return new RuleName(snakeCase, newArray, isSequence);
    }

    public RuleName asSequence() {
        return new RuleName(snakeCase, suffixArray, true);
    }

    public static RuleName of(String ruleName) {
        return new RuleName(ruleName, new int[]{}, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleName ruleName1 = (RuleName) o;

        return snakeCase.equals(ruleName1.snakeCase);
    }

    public boolean compareExact(RuleName o) {
        if (this == o) return true;
        return snakeCase.equals(o.snakeCase) &&
                isSequence == o.isSequence &&
                Arrays.equals(suffixArray, o.suffixArray);
    }

    public boolean hasSuffix() {
        return suffixArray.length > 0;
    }

    @Override
    public int hashCode() {
        return snakeCase.hashCode();
    }

    @Override
    public String toString() {
        return getRuleNameFull();
    }
}
