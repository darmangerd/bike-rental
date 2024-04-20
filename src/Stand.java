public class Stand {
	private int id ;
	private int availableBikes ;
	private int capacity ;

	public Stand (int id, int capacity, int availableBikes) {
		this.id = id;
		this.capacity = capacity;
		this.availableBikes = availableBikes;
	}

	public int getId() {
		return id;
	}

	public int getAvailableBikes() {
		return availableBikes;
	}

	public int getFreeSlots () {
		return capacity - availableBikes ;
	}

	public int getCapacity() {
		return capacity;
	}

	public void getBike () {
		availableBikes = availableBikes - 1;
	}

	public void getBikes(int nbBikes) {
		availableBikes = availableBikes - nbBikes;
	}

	public void returnBike() {
		availableBikes = availableBikes + 1;
	}

	public void returnBikes(int nbBikes) {
		availableBikes = availableBikes + nbBikes ;
	}
}
