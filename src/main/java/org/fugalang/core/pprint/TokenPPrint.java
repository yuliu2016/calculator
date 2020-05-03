package org.fugalang.core.pprint;

import org.fugalang.core.parser.ParserElement;

import java.util.List;

import static org.fugalang.core.pprint.ConsoleUtil.formatLeftWithSpaces;
import static org.fugalang.core.pprint.ConsoleUtil.formatRightWithZeroes;
import static org.fugalang.core.token.TokenType.*;

public class TokenPPrint {
    public static String format(List<? extends ParserElement> tokens) {
        var sb = new StringBuilder();
        var last_line = -1;

        if (tokens.isEmpty()) {
            return "[Empty Token List]\n";
        }

        for (var token : tokens) {
            var line = token.getLineStart();
            var type = token.getType();
            var value = token.getValue();

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
            var col = formatRightWithZeroes(String.valueOf(token.getColumnStart()), 2);
            sb.append(ConsoleColor.WHITE).append(":").append(col).append(ConsoleColor.END).append("  ");

            var type_padded = formatLeftWithSpaces(type.getName(), 9);

            if (type == NEWLINE) {
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
            } else if (type == NAME) {
                value_formatted = ConsoleColor.wrap(ConsoleColor.MAGENTA, value_formatted);
            } else if (type == NUMBER) {
                value_formatted = ConsoleColor.wrap(ConsoleColor.BLUE, value_formatted);
            }

            sb.append(type_padded).append("  ").append(value_formatted).append("\n");
        }

        return sb.toString();
    }
}
