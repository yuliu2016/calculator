package org.fugalang.grammar.common;


@SuppressWarnings("unused")
public class GrammarException extends RuntimeException {
    public GrammarException(String message) {
        super(message);
    }

    public GrammarException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrammarException(Throwable cause) {
        super(cause);
    }
}
