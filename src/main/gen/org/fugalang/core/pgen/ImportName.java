package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * import_name: 'import' 'dotted_as_names'
 */
public final class ImportName extends NodeWrapper {

    public ImportName(ParseTreeNode node) {
        super(ParserRules.IMPORT_NAME, node);
    }

    public DottedAsNames dottedAsNames() {
        return get(1, DottedAsNames.class);
    }
}
