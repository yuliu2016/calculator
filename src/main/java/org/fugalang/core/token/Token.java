package org.fugalang.core.token;

public class Token {
    public final int line;
    public final int column;
    public final TokenType type;
    public final Object value;

    public Token(int line, int column, TokenType type, Object value) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "line=" + line +
                ", column=" + column +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    // A literal of None, to be passed as the value of Token
    public static final Object NoneValue = new Object() {
        @Override
        public String toString() {
            return "None";
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    };
}
