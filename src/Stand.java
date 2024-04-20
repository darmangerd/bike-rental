import java.util.concurrent.locks.ReentrantLock;

public class Stand {
	private int id ;
	private int availableBikes ;
	private int capacity ;
	// Initialize the lock for synchronization issues
	// The idea is to avoid that two threads access the same stand at the same time
	// So we use the lock on every method that access a property that can be modified by another thread
	private ReentrantLock lock = new ReentrantLock();

	public Stand (int id, int capacity, int availableBikes) {
		this.id = id;
		this.capacity = capacity;
		this.availableBikes = availableBikes;
	}
	
	public int getId() {
		return id;
	}
	
	public int getAvailableBikes() {
		lock.lock();
		try
		{
			return availableBikes;
		}
		finally
		{
			lock.unlock();
		}


	}

	public int getFreeSlots () {
		lock.lock();
		try
		{
			return capacity - availableBikes ;
		}
		finally
		{
			lock.unlock();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public void getBike () {
		lock.lock();
		try
		{
			availableBikes = availableBikes - 1;
		}
		finally
		{
			lock.unlock();
		}

	}

	public void getBikes(int nbBikes) {

		lock.lock();
		try
		{
			availableBikes = availableBikes - nbBikes;
		}
		finally
		{
			lock.unlock();
		}
	}

	public void returnBike() {

		lock.lock();
		try
		{
			availableBikes = availableBikes + 1;
		}
		finally
		{
			lock.unlock();
		}
	}

	public void returnBikes(int nbBikes) {
		try
		{
			availableBikes = availableBikes + nbBikes;
		}
		finally
		{
			lock.unlock();
		}
	}
}
