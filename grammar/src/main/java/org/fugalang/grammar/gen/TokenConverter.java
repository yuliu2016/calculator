package org.fugalang.grammar.gen;

public interface TokenConverter {
    ConvertedValue checkToken(String tok);

    String getTokenTypeClass();

    String getTokenTypeShort();
}
