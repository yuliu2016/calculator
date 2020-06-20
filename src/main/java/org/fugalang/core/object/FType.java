package org.fugalang.core.object;

@SuppressWarnings("unused")
public interface FType<T> {

    // Customizing attribute acces
    Object __getattr__(T a, Object o);
    Object __setattr__(T a, Object o, Object v);
    Object __delattr__(T a, Object o);
    Object __dir__(T a, Object o);

    // Sequences
    Object __len__(T a);
    Object __getitem__(T a, Object o);
    Object __setitem__(T a, Object o, Object v);
    Object __delitem__(T a, Object o);
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
}
