package org.fugalang.core.grammar.gen;

import java.util.Optional;

@FunctionalInterface
public interface TokenConverter {
    Optional<ConvertedValue> checkToken(String tok);

    default boolean didResolveToken(String tok) {
        return checkToken(tok).isPresent();
    }
}
