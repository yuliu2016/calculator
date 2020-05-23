package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * rules: ['NEWLINE'] 'single_rule'+
 */
public final class Rules extends NodeWrapper {

    public Rules(ParseTreeNode node) {
        super(ParserRules.RULES, node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0);
    }

    public List<SingleRule> singleRules() {
        return getList(1, SingleRule.class);
    }
}
