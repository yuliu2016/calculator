package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * import_as_names: ','.import_as_name+
 */
public final class ImportAsNames extends NodeWrapper {

    public ImportAsNames(ParseTreeNode node) {
        super(node);
    }

    public List<ImportAsName> importAsNames() {
        return getList(0, ImportAsName::new);
    }
}
