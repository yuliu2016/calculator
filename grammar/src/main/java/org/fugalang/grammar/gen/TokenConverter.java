package org.fugalang.grammar.gen;

import java.util.Optional;

public interface TokenConverter {
    Optional<ConvertedValue> checkToken(String tok);

    String getTokenTypeClass();

    String getTokenTypeShort();
}
