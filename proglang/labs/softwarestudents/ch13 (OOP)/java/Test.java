public class Test {
    public static void main(String[ ] args) {
        Expression in = new Add(new Variable("x"), new Value(1));
        System.out.println(in.diff(new Variable("x")));
    }
}

