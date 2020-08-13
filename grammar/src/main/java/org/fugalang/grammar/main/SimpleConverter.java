package org.fugalang.grammar.main;

import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.TokenType;
import org.fugalang.grammar.gen.ConvertedValue;
import org.fugalang.grammar.gen.TokenConverter;
import org.fugalang.grammar.util.ParserStringUtil;

public class SimpleConverter implements TokenConverter {
    @Override
    public ConvertedValue checkToken(String s) {

        if (Keyword.ALL_KEYWORDS.contains(s)) {
            return new ConvertedValue("boolean", s, s);
        }
        if (Operator.CODE_TO_NAME.containsKey(s)) {
            var name = ParserStringUtil.convertCase(Operator.CODE_TO_NAME.get(s));
            return new ConvertedValue("boolean", name, s);
        }

        if (TokenType.NAMES.contains(s)) {
            return new ConvertedValue("String", s.toLowerCase(), s);
        }

        return null;
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
