package com.example.calculator.pprint;

import com.example.calculator.token.Operator;
import com.example.calculator.token.Token;

import java.util.List;

import static com.example.calculator.pprint.ConsoleColor.*;
import static com.example.calculator.pprint.ConsoleUtil.formatLeftWithSpaces;
import static com.example.calculator.pprint.ConsoleUtil.formatRightWithZeroes;
import static com.example.calculator.token.TokenType.*;

public class TokenPPrint {
    public static String format(List<Token> tokens) {
        var sb = new StringBuilder();
        var last_line = -1;

        for (var token: tokens) {
            var line = token.line;
            var type = token.type;
            var value = token.value;

            if (line != last_line) {
                // show the line number in bold
                var wrapped_line = wrap(BOLD, "L" +
                        formatRightWithZeroes(String.valueOf(line), 3) + "  ");

                sb.append(wrapped_line);
            } else {
                // 5 spaces for the line number
                sb.append("      ");
            }
            last_line = line;

            var type_padded = formatLeftWithSpaces(type.name(), 9);

            if (DELIMITERS.contains(type)) {
                // type is a delimiter; fade out the token
                sb.append(wrap(WHITE, type_padded));
                sb.append("\n");
                continue;
            }

            var value_formatted = switch (type) {
                case STRING, DOC_STR -> wrap(GREEN, "\"" + value.toString() + "\"");
                case OPERATOR -> " " + ((Operator) value).getCode();
                case COMPLEX -> " " + value.toString() + "j";
                default -> " " + value.toString();
            };

            if (type == KEYWORD) {
                value_formatted = wrap(BRIGHT_BLUE, value_formatted);
            } else if (SYMBOLS.contains(type)) {
                value_formatted = wrap(MAGENTA, value_formatted);
            } else if (NUMBER_LITERALS.contains(type)) {
                value_formatted = wrap(BLUE, value_formatted);
            }

            sb.append(type_padded).append("  ").append(value_formatted).append("\n");
        }

        return sb.toString();
    }
}
