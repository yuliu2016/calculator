package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * file_input:
 * *   | (NEWLINE | stmt)* ENDMARKER
 */
public final class FileInput extends NodeWrapper {

    public FileInput(ParseTreeNode node) {
        super(node);
    }

    public List<FileInput1> newlineOrStmts() {
        return getList(0, FileInput1::new);
    }

    public String endmarker() {
        return get(1, TokenType.ENDMARKER);
    }

    /**
     * NEWLINE | stmt
     */
    public static final class FileInput1 extends NodeWrapper {

        public FileInput1(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0);
        }

        public Stmt stmt() {
            return new Stmt(get(1));
        }

        public boolean hasStmt() {
            return has(1);
        }
    }
}
