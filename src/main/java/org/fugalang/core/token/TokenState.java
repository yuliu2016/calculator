package org.fugalang.core.token;

import org.fugalang.core.parser.ParserElement;

public class TokenState {
    private ParserElement token;

    public ParserElement getToken() {
        return token;
    }

    public void setToken(ParserElement token) {
        if (this.token != null) {
            throw new IllegalStateException("Token not cleared");
        }
        this.token = token;
    }

    public void clearToken() {
        token = null;
    }
}
