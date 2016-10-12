class Buffer {
    private Integer[ ] buf;
    private int in = -1;
    private int out = -1;
    private int count = 0;

    public Buffer(int s) {
	buf = new Integer[s];
    }

    public synchronized void put(int s) {
	while (count >= buf.length)  
	    try{ wait(); } catch(InterruptedException e) { };
	count++;
	buf[++in % buf.length] = new Integer(s);
	notify();
    }

    public synchronized int get() {
	while (count == 0)
	    try{ wait(); } catch(InterruptedException e) { };
	count--;
	Integer s = buf[++out % buf.length];
	notify();
	return s.intValue();
    }
} // Buffer


class Sieve implements Runnable {
    Buffer in;

    public Sieve(Buffer b) { in = b; }

    public void run( ) {
        int p = in.get( );
        if (p < 0) return;
        System.out.println(p);
        Buffer out = new Buffer(5);
        Thread t = new Thread(new Sieve(out));
        t.start();
        while (true) {
            int num = in.get();
            if (num < 0) {
                out.put(num);
                return;
            }
            if (num % p != 0)
                out.put(num);
        }
    }

    public static void main (String[] arg) {
        if (arg.length < 1) {
            System.out.println("Usage: java Sieve number");
            System.exit(1);
        }
        try {
            int N = Integer.parseInt(arg[0]);
            Buffer out = new Buffer(5);
            Thread t = new Thread(new Sieve(out));
            t.start();
            for (int i = 2; i <= N; i++)
                out.put(i);
            out.put(-1);
        } catch (NumberFormatException e) {
            System.out.println("Illegal number: " + arg[0]);
            System.exit(1);
        }
    }
}
