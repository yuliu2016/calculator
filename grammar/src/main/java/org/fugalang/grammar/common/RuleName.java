package org.fugalang.grammar.common;

import org.fugalang.grammar.util.StringUtil;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record RuleName(String snakeCase, String returnType, int[] suffixArray) {
    public RuleName {
        assert snakeCase != null;
        assert suffixArray != null;
    }

    private String suffixJoin(String delimiter) {
        return (suffixArray.length == 0 ? "" : IntStream
                .of(suffixArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(delimiter, delimiter, "")));
    }

    public String fullName() {
        return snakeCase + suffixJoin(":");
    }

    public String symbolicName() {
        return snakeCase + suffixJoin("_");
    }

    public String pascalCase() {
        return StringUtil.convertCase(symbolicName());
    }

    public String camelCase() {
        return StringUtil.decap(pascalCase());
    }

    public RuleName withSuffix(int suffix) {
        int currentLen = suffixArray.length;
        int[] newArray = Arrays.copyOf(suffixArray, currentLen + 1);
        newArray[currentLen] = suffix;
        return new RuleName(snakeCase, returnType, newArray);
    }

    public static RuleName of(String ruleName, String returnType) {
        return new RuleName(ruleName, returnType, new int[]{});
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
                Arrays.equals(suffixArray, o.suffixArray);
    }

    @Override
    public int hashCode() {
        return snakeCase.hashCode();
    }

    @Override
    public String toString() {
        return fullName();
    }
}
