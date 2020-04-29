package org.fugalang.core.grammar.pgen;

// single_rule: 'TOK' ':' 'or_rule' 'NEWLINE'
public class SingleRule {
    public final String token;
    public final boolean isTokenColon;
    public final OrRule orRule;
    public final boolean isTokenNewline;

    public SingleRule(
            String token,
            boolean isTokenColon,
            OrRule orRule,
            boolean isTokenNewline
    ) {
        this.token = token;
        this.isTokenColon = isTokenColon;
        this.orRule = orRule;
        this.isTokenNewline = isTokenNewline;
    }
}
