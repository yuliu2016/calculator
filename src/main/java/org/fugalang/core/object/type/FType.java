package org.fugalang.core.object.type;

@SuppressWarnings("unused")
public interface FType<T> {

    // Customizing attribute acces
    Object getattr(T a, Object o);
    Object setattr(T a, Object o, Object v);
    Object delattr(T a, Object o);

    // Sequences/Maps
    Object length(T a);
    Object get(T a, Object o);
    Object set(T a, Object o, Object v);
    Object del(T a, Object o);
    Object iterator(T a);
    Object reversed(T a);

    // Comparision Operators
    Object compare_op(T a, Object o, int compare_op);

    // Unary Operators
    Object unary_op(T a, int unary_op);

    // Binary Operators
    Object binary_op(T a, Object o, int binary_op);

    // Number Operators (right hand operand)
    Object rh_binary_op(T a, Object b, int binary_op);

    // Binary Operators (in-place)
    Object inplace_binary_op(T a, Object b, int binary_op);

    // Context managers
    Object context_enter(T a);
    Object context_exit(T a, Object o);

    FMetaType meta();
}
