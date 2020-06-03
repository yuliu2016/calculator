package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * import_name: 'import' dotted_as_names
 */
public final class ImportName extends NodeWrapper {

    public ImportName(ParseTreeNode node) {
        super(node);
    }

    public DottedAsNames dottedAsNames() {
        return new DottedAsNames(get(1));
    }
}
