package org.fugalang.core.pprint;

import org.fugalang.core.token.Token;

import java.util.List;

import static org.fugalang.core.pprint.ConsoleUtil.formatLeftWithSpaces;
import static org.fugalang.core.pprint.ConsoleUtil.formatRightWithZeroes;
import static org.fugalang.core.token.TokenType.*;

public class TokenPPrint {
    public static String format(List<Token> tokens) {
        var sb = new StringBuilder();
        var last_line = -1;

        if (tokens.isEmpty()) {
            return "[Empty Token List]\n";
        }

        for (var token : tokens) {
            var line = token.line;
            var type = token.type;
            var value = token.value;

            if (line != last_line) {
                // show the line number in bold
                var line_no = formatRightWithZeroes(String.valueOf(line), 3);
                var wrapped_line = ConsoleColor.wrap(ConsoleColor.BOLD, "L" + line_no);

                sb.append(wrapped_line);
            } else {
                // 5 spaces for the line number
                sb.append("    ");
            }
            last_line = line;

            // Fix: Add column indicators
            var col = formatRightWithZeroes(String.valueOf(token.column), 2);
            sb.append(ConsoleColor.WHITE).append(":").append(col).append(ConsoleColor.END).append("  ");

            var type_padded = formatLeftWithSpaces(type.name(), 9);

            if (DELIMITERS.contains(type)) {
                // type is a delimiter; fade out the token
                sb.append(ConsoleColor.wrap(ConsoleColor.WHITE, type_padded));
                sb.append("\n");
                continue;
            }

            String value_formatted;
            if (type == STRING) {
                value_formatted = ConsoleColor.wrap(ConsoleColor.GREEN, "\"" + value + "\"");
            } else {
                value_formatted = " " + value;
            }

            if (type == KEYWORD) {
                value_formatted = ConsoleColor.wrap(ConsoleColor.BRIGHT_BLUE, value_formatted);
            } else if (SYMBOLS.contains(type)) {
                value_formatted = ConsoleColor.wrap(ConsoleColor.MAGENTA, value_formatted);
            } else if (NUMBER_LITERALS.contains(type)) {
                value_formatted = ConsoleColor.wrap(ConsoleColor.BLUE, value_formatted);
            }

            sb.append(type_padded).append("  ").append(value_formatted).append("\n");
        }

        return sb.toString();
    }
}
