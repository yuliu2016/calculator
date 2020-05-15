package org.fugalang.core.parser;

/**
 * A syntax error generated from the tokenizer/compiler
 */
@SuppressWarnings("unused")
public class SyntaxError extends RuntimeException {
    public SyntaxError() {
    }

    public SyntaxError(String message) {
        super(message);
    }

    public SyntaxError(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxError(Throwable cause) {
        super(cause);
    }

}
