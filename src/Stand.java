import java.util.concurrent.ConcurrentLinkedQueue;

public class Stand {
	private int id ;
	private int availableBikes ;
	private int capacity ;

	// queue of threads waiting for a bike
	private ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<Thread>();

	public Stand (int id, int capacity, int availableBikes) {
		this.id = id;
		this.capacity = capacity;
		this.availableBikes = availableBikes;
	}

	public int getId() {
		synchronized (this)
		{
			return id;
		}
	}

	public int getAvailableBikes() {
		synchronized (this) {
			return availableBikes;
		}
	}

	public int getFreeSlots () {
		synchronized (this) {
			return capacity - availableBikes ;
		}
	}

	public int getCapacity() {
		synchronized (this)
		{
			return capacity;
		}
	}

	public void getBike() throws InterruptedException {
		synchronized (this) {
			Thread currentThread = Thread.currentThread();
			queue.add(currentThread);

			// if there is no bike available or the current thread is not the first in the queue -> wait
			while (this.getAvailableBikes() <= 0 || queue.peek() != currentThread) {
				this.wait();
			}
			availableBikes = availableBikes - 1;
			queue.poll(); // remove the current thread from the queue
			this.notifyAll();
		}
	}

	public void getBikes(int nbBikes) {
		synchronized (this) {
			availableBikes = availableBikes - nbBikes;
			this.notifyAll();
		}
	}

	public void returnBike() throws InterruptedException {
		synchronized (this) {
			Thread currentThread = Thread.currentThread();
			queue.add(currentThread);

			// if there is no free slot or the current thread is not the first in the queue -> wait
			while (this.getFreeSlots() <= 0 || queue.peek() != currentThread) {
				this.wait();
			}
			availableBikes = availableBikes + 1;
			queue.poll();
			this.notifyAll();
		}
	}

	public void returnBikes(int nbBikes) {
		synchronized (this) {
			availableBikes = availableBikes + nbBikes;
			this.notifyAll();
		}
	}
}