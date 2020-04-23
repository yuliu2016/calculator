package org.fugalang.core.grammar.parser;

import org.fugalang.core.grammar.psi.*;
import org.fugalang.core.grammar.token.MgToken;
import org.fugalang.core.grammar.token.MgTokenType;

import java.util.ArrayList;
import java.util.List;

public class MgParser {
    public static Rules parseRules(List<MgToken> tokens) {
        var visitor = new MgTokenVisitor(tokens);
        return parseRules(visitor);
    }

    public static Rules parseRules(MgTokenVisitor visitor) {
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

    public static SingleRule parseRule(MgTokenVisitor visitor) {
        var name = parseTokenStr(visitor);
        if (name == null) return null;
        if (!parseTokenType(visitor, MgTokenType.COL)) return null;
        var orRule = parseOrRule(visitor);
        if (orRule == null) return null;
        if (!parseTokenType(visitor, MgTokenType.NEWLINE)) return null;
        return new SingleRule(name, orRule);
    }

    public static OrRule parseOrRule(MgTokenVisitor visitor) {
        var andRule = parseAndRule(visitor);
        if (andRule == null) return null;

        List<AndRule> andRules = new ArrayList<>();
        while (true) {
            visitor.markLookahead();

            var pipe = parseTokenType(visitor, MgTokenType.OR);
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

    public static AndRule parseAndRule(MgTokenVisitor visitor) {
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

    public static RepeatRule parseRepeatRule(MgTokenVisitor visitor) {
        var subRule = parseSubRule(visitor);
        if (subRule == null) return null;

        var star = parseTokenType(visitor, MgTokenType.STAR);
        var plus = parseTokenType(visitor, MgTokenType.PLUS);

        return new RepeatRule(subRule, star, plus);
    }

    public static SubRule parseSubRule(MgTokenVisitor visitor) {
        visitor.markLookahead();
        OrRule orRule;

        if (parseTokenType(visitor, MgTokenType.LPAR) &&
                (orRule = parseOrRule(visitor)) != null &&
                parseTokenType(visitor, MgTokenType.RPAR)) {
            visitor.commitLookahead();
            return new SubRule(orRule, null, null);
        }
        visitor.abortLookahead();
        visitor.markLookahead();

        if (parseTokenType(visitor, MgTokenType.LSQB) &&
                (orRule = parseOrRule(visitor)) != null &&
                parseTokenType(visitor, MgTokenType.RSQB)) {
            visitor.commitLookahead();
            return new SubRule(null, orRule, null);
        }
        visitor.abortLookahead();

        var tok = parseTokenStr(visitor);
        if (tok == null) return null;

        return new SubRule(null, null, tok);
    }

    public static String parseTokenStr(MgTokenVisitor visitor) {
        var tok = visitor.getAndAdd();
        if (tok == null || tok.type != MgTokenType.TOK || tok.value == null) {
            visitor.backtrack();
            return null;
        }
        return tok.value;
    }

    public static boolean parseTokenType(MgTokenVisitor visitor, MgTokenType type) {
        var tok = visitor.getAndAdd();
        var result = tok != null && tok.type == type;
        if (!result) {
            visitor.backtrack();
        }
        return result;
    }
}
