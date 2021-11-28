package org.fugalang.grammar.gen;

@Deprecated
public interface TokenConverter {
    ConvertedValue checkToken(String tok);

    String getTokenTypeClass();

    String getTokenTypeShort();
}
