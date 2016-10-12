// Fig 18.12 page 545
public class myStackTest {
public static void main(String[] args) {
   MyStack s = new MyStack();
   int val;
   for (int i=0; i<args.length; i++) 
      s.push(Integer.parseInt(args[i]));
   System.out.println("Stack size = " + s.size());
   System.out.print("Stack contents =");
   for (int i=1; i<=n; i++) {
      System.out.print(" " + s.top( ));
      s.pop( );
   }
   System.out.println( );
   System.out.println("Is Stack empty? " + s.isEmpty( ));
}
}
