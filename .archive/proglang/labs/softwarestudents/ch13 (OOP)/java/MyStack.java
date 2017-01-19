public class MyStack {
    protected class Node {
        public Object  val;
        public Node next;
        public Node(Object v, Node n) {
            val = v; next = n;
        }
    }
    private Node theStack;
    
    public MyStack( ) { theStack = null;  }
    
    public boolean empty( ) { return theStack == null;  }
    
    public Object top( ) { return theStack.val; }

    public Object pop( ) {
        Object result = theStack.val;
        theStack = theStack.next;
        return result;
    }
    
    public void push(Object v) { 
        theStack = new Node(v, theStack); 
    }
}
