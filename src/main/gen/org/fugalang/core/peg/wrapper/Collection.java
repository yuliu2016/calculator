package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * collection: 
 * | tuple_atom
 * | list_iter
 * | list_atom
 * | set_atom
 * | dict_iter
 * | dict_atom
 */
public final class Collection extends NodeWrapper {

    public Collection(ParseTreeNode node) {
        super(node);
    }

    public TupleAtom tupleAtom() {
        return new TupleAtom(get(0));
    }

    public boolean hasTupleAtom() {
        return has(0);
    }

    public ListIter listIter() {
        return new ListIter(get(1));
    }

    public boolean hasListIter() {
        return has(1);
    }

    public ListAtom listAtom() {
        return new ListAtom(get(2));
    }

    public boolean hasListAtom() {
        return has(2);
    }

    public SetAtom setAtom() {
        return new SetAtom(get(3));
    }

    public boolean hasSetAtom() {
        return has(3);
    }

    public DictIter dictIter() {
        return new DictIter(get(4));
    }

    public boolean hasDictIter() {
        return has(4);
    }

    public DictAtom dictAtom() {
        return new DictAtom(get(5));
    }

    public boolean hasDictAtom() {
        return has(5);
    }
}
