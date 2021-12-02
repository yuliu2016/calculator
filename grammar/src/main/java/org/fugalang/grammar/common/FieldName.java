package org.fugalang.grammar.common;

import org.fugalang.grammar.util.StringUtil;

public record FieldName(
        String snakeCase,
        boolean isPlural,
        int suffixCount
) {
    public static FieldName of(String name) {
        return new FieldName(name, false, -1);
    }

    public FieldName pluralize() {
        return new FieldName(snakeCase, true, suffixCount);
    }

    public FieldName withSuffix(int suffix) {
        return new FieldName(snakeCase, false, suffix);
    }

    public String pluralized() {
        return isPlural ? StringUtil.pluralize(snakeCase) : snakeCase;
    }

    public String fullSnakeCase() {
        return pluralized() + (suffixCount == -1 ? "" : "_" + suffixCount);
    }

    public String snakeCaseUnconflicted() {
        return "_" + fullSnakeCase();
    }

    public String fullCamelCase() {
        return StringUtil.convertCase(fullSnakeCase());
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}
