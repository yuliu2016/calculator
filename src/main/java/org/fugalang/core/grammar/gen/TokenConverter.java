package org.fugalang.core.grammar.gen;

@FunctionalInterface
public interface TokenConverter {
    ConvertedValue convert(String s);
}
