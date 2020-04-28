package org.fugalang.core.grammar.gen;

@FunctionalInterface
public interface TokenValidator {
    boolean isValidToken(String tok);
}
