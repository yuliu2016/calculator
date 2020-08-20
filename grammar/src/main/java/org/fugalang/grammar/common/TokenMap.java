package org.fugalang.grammar.common;

@FunctionalInterface
public interface TokenMap {
    TokenEntry lookupOrThrow(String nameOrLiteral);
}
