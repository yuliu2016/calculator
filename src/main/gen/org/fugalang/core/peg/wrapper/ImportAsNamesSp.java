package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * import_as_names_sp (allow_whitespace=true):
 * *   | '(' import_as_names [','] ')'
 */
public final class ImportAsNamesSp extends NodeWrapper {

    public ImportAsNamesSp(ParseTreeNode node) {
        super(node);
    }

    public ImportAsNames importAsNames() {
        return new ImportAsNames(get(1));
    }

    public boolean isComma() {
        return is(2);
    }
}
