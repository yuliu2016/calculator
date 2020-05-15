package org.fugalang.core.grammar.token;

/**
 * A syntax error generated from the tokenizer/compiler
 */
@Deprecated
public class MetaGrammarError extends RuntimeException {

    public MetaGrammarError(String message) {
        super(message);
    }
}
