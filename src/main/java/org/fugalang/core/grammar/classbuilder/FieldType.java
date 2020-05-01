package org.fugalang.core.grammar.classbuilder;

public enum FieldType {
    Simple,
    Optional,
    Repeated;

    public static FieldType fromBiState(boolean isOptional, boolean isRepeated) {
        if (isOptional) {
            if (isRepeated) {
                throw new IllegalArgumentException(
                        "ClassField: Cannot be optional and repeated at the same time");
            } else {
                return Optional;
            }
        }
        return isRepeated ? Repeated : Simple;
    }
}
