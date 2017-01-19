// Fig 18.11 p 544
public class MyStack {
    private class Node {
        /*@ spec_public @*/ int  val;
        /*@ spec_public @*/ Node next;
        Node(int v, Node n) {
            val = v; next = n;
        }
    }
    /*@ public model Node S;
        private represents S <- theStack;
        public invariant n == this.size();
    @*/
    private /*@ spec_public @*/ Node theStack = null;
    private /*@ spec_public @*/ int n = 0;
    
    /*@ requires n > 0;
        ensures \result==\old(S).val && S==\old(S).next;
    @*/
    public /*@ pure @*/ int pop( ) { 
        int result = theStack.val;
        theStack = theStack.next; 
        n = n-1;
        return result; 
    }
    //@ ensures S.next==\old(S) && S.val==v;
    public /*@ pure @*/ void push(int v) { 
        theStack = new Node(v, theStack);
        n = n+1;     
    }
    /*@ requires n > 0;
        ensures \result==S.val && S == \old(S);
    @*/
    public /*@ pure @*/ int top() {
        return theStack.val;   
    }
    //@ ensures \result == (S == null);
    public /*@ pure @*/ boolean isEmpty() {
        return theStack == null;
    }
    //@ ensures \result == n;
    public /*@ pure @*/ int size() {
        int count;
        Node p = theStack;
        for (count=0; p!=null; count++)
            p = p.next;
        return count;
    }
}
