public class Truck extends Thread{
	private static final int TIME_BETWEEN_SITES = 100 ; // in milliseconds
	
	private final int capacity ;
	private int bikes ;
	
	public Truck(int bikes, int capacity) {
		this.bikes = bikes;
		this.capacity = capacity;
	}
	
	public Truck(int capacity) {
		this.bikes = 0;
		this.capacity = capacity;
	}
	
	public void run() {
		// circle around sites
		while (true) {
			for (Stand s : World.getStands()) {
				// TODO here, equilibrate the number of bikes on the stand.
			}
		}
	}
}
