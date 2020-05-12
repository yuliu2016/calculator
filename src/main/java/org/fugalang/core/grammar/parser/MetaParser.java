package org.fugalang.core.grammar.parser;

import org.fugalang.core.grammar.psi.*;
import org.fugalang.core.grammar.token.MetaToken;
import org.fugalang.core.grammar.token.MetaTokenType;
import org.fugalang.core.parser.ElementType;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MetaParser {
    public static Rules parseRules(List<MetaToken> tokens) {
        var visitor = new MetaTokenVisitor(tokens);
        return parseRules(visitor);
    }

    public static Rules parseRules(MetaTokenVisitor visitor) {
        List<SingleRule> rules = new ArrayList<>();

        while (true) {
            visitor.markLookahead();

            var rule = parseRule(visitor);

            if (rule == null) {
                visitor.abortLookahead();
                break;
            } else {
                visitor.commitLookahead();
                rules.add(rule);
            }
        }

        return new Rules(rules);
    }

    public static SingleRule parseRule(MetaTokenVisitor visitor) {
        var name = parseTokenStr(visitor);
        if (name == null) return null;
        if (!parseTokenType(visitor, MetaTokenType.COL)) return null;
        var orRule = parseOrRule(visitor);
        if (orRule == null) return null;
        if (!parseTokenType(visitor, MetaTokenType.NEWLINE)) return null;
        return new SingleRule(name, orRule);
    }

    public static OrRule parseOrRule(MetaTokenVisitor visitor) {
        var andRule = parseAndRule(visitor);
        if (andRule == null) return null;

        List<AndRule> andRules = new ArrayList<>();
        while (true) {
            visitor.markLookahead();

            var pipe = parseTokenType(visitor, MetaTokenType.OR);
            var rule = parseAndRule(visitor);

            if (!pipe || rule == null) {
                visitor.abortLookahead();
                break;
            } else {
                visitor.commitLookahead();
                andRules.add(rule);
            }
        }
        return new OrRule(andRule, andRules);
    }

    public static AndRule parseAndRule(MetaTokenVisitor visitor) {
        var repeatRule = parseRepeatRule(visitor);
        if (repeatRule == null) return null;

        List<RepeatRule> repeatRules = new ArrayList<>();
        while (true) {
            visitor.markLookahead();

            var rule = parseRepeatRule(visitor);

            if (rule == null) {
                visitor.abortLookahead();
                break;
            } else {
                visitor.commitLookahead();
                repeatRules.add(rule);
            }
        }
        return new AndRule(repeatRule, repeatRules);
    }

    public static RepeatRule parseRepeatRule(MetaTokenVisitor visitor) {
        var subRule = parseSubRule(visitor);
        if (subRule == null) return null;

        var star = parseTokenType(visitor, MetaTokenType.STAR);
        var plus = parseTokenType(visitor, MetaTokenType.PLUS);

        return new RepeatRule(subRule, star, plus);
    }

    public static SubRule parseSubRule(MetaTokenVisitor visitor) {
        visitor.markLookahead();
        OrRule orRule;

        if (parseTokenType(visitor, MetaTokenType.LPAR) &&
                (orRule = parseOrRule(visitor)) != null &&
                parseTokenType(visitor, MetaTokenType.RPAR)) {
            visitor.commitLookahead();
            return new SubRule(orRule, null, null);
        }
        visitor.abortLookahead();
        visitor.markLookahead();

        if (parseTokenType(visitor, MetaTokenType.LSQB) &&
                (orRule = parseOrRule(visitor)) != null &&
                parseTokenType(visitor, MetaTokenType.RSQB)) {
            visitor.commitLookahead();
            return new SubRule(null, orRule, null);
        }
        visitor.abortLookahead();

        var tok = parseTokenStr(visitor);
        if (tok == null) return null;

        return new SubRule(null, null, tok);
    }

    public static String parseTokenStr(MetaTokenVisitor visitor) {
        var tok = visitor.getAndAdd();
        if (tok == null || tok.type != MetaTokenType.TOK || tok.value == null) {
            visitor.backtrack();
            return null;
        }
        return tok.value;
    }

    public static boolean parseTokenType(MetaTokenVisitor visitor, ElementType type) {
        var tok = visitor.getAndAdd();
        var result = tok != null && tok.type == type;
        if (!result) {
            visitor.backtrack();
        }
        return result;
    }
}
