package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * file_input:
 * *   | [stmt_list] ENDMARKER
 */
public final class FileInput extends NodeWrapper {

    public FileInput(ParseTreeNode node) {
        super(node);
    }

    public StmtList stmtList() {
        return new StmtList(get(0));
    }

    public boolean hasStmtList() {
        return has(0);
    }

    public String endmarker() {
        return get(1, TokenType.ENDMARKER);
    }
}
