public abstract class Expression {
    public abstract Expression diff(Variable x);
}

class Value extends Expression {
    private int value;

    public Value(int v) { value = v; }

    public Expression diff(Variable x) {
        return new Value(0);
    }
    public String toString( ) {
        return "" + value;
    }
}

class Variable extends Expression {
    private String id;
    static final private Value zero = new Value(0);
    static final private Value one  = new Value(1);

    public Variable(String s) { id = s; }

    public Expression diff(Variable x) {
        return id.equals(x.id) ? one : zero;
    }
    public String toString( ) {
        return id;
    }
}

abstract class Binary extends Expression {
    protected Expression left, right;

    protected Binary(Expression u, Expression v) {
        left = u; right = v;
    }
}

class Add extends Binary {
    public Add(Expression u, Expression v) {
        super(u, v);
    }

    public Expression diff(Variable x) {
        return new Add(left.diff(x), right.diff(x));
    }
    public String toString( ) {
        return "(" + left + ") + (" + right + ")";
    }
}
