package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * iterator:
 * *   | iter_for* 'for' targetlist [iter_if]
 */
public final class Iterator extends NodeWrapper {

    public Iterator(ParseTreeNode node) {
        super(node);
    }

    public List<IterFor> iterFors() {
        return getList(0, IterFor::new);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(2));
    }

    public IterIf iterIf() {
        return new IterIf(get(3));
    }

    public boolean hasIterIf() {
        return has(3);
    }
}
