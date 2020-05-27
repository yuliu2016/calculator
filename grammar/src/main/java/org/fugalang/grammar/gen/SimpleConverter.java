package org.fugalang.grammar.gen;

import org.fugalang.grammar.util.ParserStringUtil;
import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.TokenType;

import java.util.Optional;

public class SimpleConverter implements TokenConverter {
    @Override
    public Optional<ConvertedValue> checkToken(String s) {

        if (Keyword.ALL_KEYWORDS.contains(s)) {
            return Optional.of(new ConvertedValue("boolean", s, s));
        }
        if (Operator.CODE_TO_NAME.containsKey(s)) {
            var name = ParserStringUtil.convertCase(Operator.CODE_TO_NAME.get(s));
            return Optional.of(new ConvertedValue("boolean", name, s));
        }

        if (TokenType.NAMES.contains(s)) {
            return Optional.of(new ConvertedValue("String", s.toLowerCase(), s));
        }

        return Optional.empty();

    }

    @Override
    public String getTokenTypeClass() {
        return "org.fugalang.core.token.TokenType";
    }

    @Override
    public String getTokenTypeShort() {
        return "TokenType";
    }
}