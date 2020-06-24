package org.fugalang.core.object.type;

import org.fugalang.core.object.call.FCallable;

import java.util.Map;

public class FMetaType {

    public String name;
    Map<String, Object> type_vars;

    public FSequenceT seq_t;
    public FAtomT atom_t;
    public FAccessT access_t;
    public FStateT state_t;
    public FCallable call_func;

    public FMetaType(
            String name,
            Map<String, Object> type_vars,
            FSequenceT seq_t,
            FAtomT atom_t,
            FAccessT access_t,
            FStateT state_t,
            FCallable call_func
    ) {
        this.name = name;
        this.type_vars = type_vars;
        this.seq_t = seq_t;
        this.atom_t = atom_t;
        this.access_t = access_t;
        this.state_t = state_t;
        this.call_func = call_func;
    }

    FCallable compare_func(int compare_op) {
        return atom_t == null || atom_t.compare_func == null ?
                null : atom_t.compare_func[compare_op];
    }

    FCallable unary_func(int unary_op) {
        return atom_t == null || atom_t.unary_func == null ?
                null : atom_t.unary_func[unary_op];
    }

    FCallable binary_func(int binary_op) {
        return atom_t == null || atom_t.binary_func == null ?
                null : atom_t.binary_func[binary_op];
    }

    FCallable rh_binary_func(int binary_op) {
        return atom_t == null || atom_t.rh_binary_func == null ?
                null : atom_t.rh_binary_func[binary_op];
    }

    FCallable inplace_binary_func(int binary_op) {
        return atom_t == null || atom_t.inplace_binary_func == null ?
                null : atom_t.inplace_binary_func[binary_op];
    }
}
