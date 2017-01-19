import java.util.Date;

class BoundedBuffer {

    public static void main(String[] arg) {
	Buffer buffer = new Buffer(5);
	Producer producer1 = new Producer(buffer, 1);
	producer1.start();
        Producer producer2 = new Producer(buffer, 2);
        producer2.start();
	Consumer consumer1 = new Consumer(buffer, 1);
	consumer1.start();
        Consumer consumer2 = new Consumer(buffer, 2);
        consumer2.start();
    }
} // BoundedBuffer

class Buffer {
    private String[] buf;
    private int in = -1;
    private int out = -1;
    private int count = 0;

    public Buffer(int s) {
	buf = new String[s];
    }

    public synchronized void put(String s) {
	while (count >= buf.length)  
	    try{ wait(); } catch(InterruptedException e) { };
	count++;
	buf[++in % buf.length] = s;
	notify();
    }

    public synchronized String get() {
	while (count == 0)
	    try{ wait(); } catch(InterruptedException e) { };
	count--;
	String s = buf[++out % buf.length];
	notify();
	return s;
    }
} // Buffer

class Consumer extends Thread {
    private Buffer buffer;
    private int whoAmI;

    public Consumer(Buffer b, int w) { buffer = b; whoAmI = w; }

    public void run() {
	try {
	    for (int i = 0; i<20; i++) {
		System.out.println(buffer.get() + " Consumer" + whoAmI);
		Thread.sleep(3000);
	    }
	} catch(InterruptedException e) { };
    }
} // Consumer

class Producer extends Thread {
    private Buffer buffer;
    private int whoAmI;

    public Producer(Buffer b, int w) { buffer = b; whoAmI = w; }

    public void run() {
	try {
	    for (int i = 0; i<20; i++) {
		buffer.put("Producer" + whoAmI + " " + new Date());
	    }
	} catch(InterruptedException e) { };
    }
} // Producer

