package org.fugalang.core.grammar;

/**
 * A syntax error generated from the tokenizer/compiler
 */
@SuppressWarnings("unused")
public class MetaGrammarError extends RuntimeException {
    public MetaGrammarError() {
    }

    public MetaGrammarError(String message) {
        super(message);
    }

    public MetaGrammarError(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaGrammarError(Throwable cause) {
        super(cause);
    }

}
