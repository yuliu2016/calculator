package org.fugalang.grammar.main;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.TokenType;
import org.fugalang.grammar.common.TokenEntry;
import org.fugalang.grammar.common.TokenMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TokenMapImpl implements TokenMap {

    private static final List<String> nonLiteralTypes = TokenType
            .ELEMENT_TYPES
            .stream()
            .filter(x -> !x.isLiteral())
            .map(ElementType::getName)
            .collect(Collectors.toList());

    private static final int nTypes = nonLiteralTypes.size();

    private static final List<Operator> operators = Arrays.asList(Operator.values());

    private static final int nOperators = operators.size();

    private static int operatorIndexOf(String s) {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).getCode().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public TokenEntry lookupOrThrow(String s) {

        int typeIdx = nonLiteralTypes.indexOf(s);
        if (typeIdx >= 0) {
            return new TokenEntry(1 + typeIdx, false, s.toLowerCase(), s);
        }

        int opIdx = operatorIndexOf(s);
        if (opIdx >= 0) {
            var op = operators.get(opIdx);
            var lower = op.name().toLowerCase();
            return new TokenEntry(1 + nTypes + opIdx, true, lower, op.getCode());
        }

        int kwIdx = Keyword.ALL_KEYWORDS.indexOf(s);
        if (kwIdx >= 0) {
            var lower = s.toLowerCase();
            return new TokenEntry(1 + nTypes + nOperators + kwIdx, true, lower, s);
        }

        return null;
    }
}
