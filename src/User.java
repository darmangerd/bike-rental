

public class User extends Thread {
	
	private static final int MIN_RUNS = 3 ;
	private static final int MAX_RUNS = 8 ;
	private static final int TIME_FOR_ONE_HOP = 20 ; // in milliseconds
		
	private static final int[] startStands = {0,0,0,0,0,1,1,1,2,2,2,3,3,4,4,5}; 
	private static final int[] arrivalStands = {0,1,1,2,2,2,3,3,3,4,4,4,5,5,5,5};  
	
	/**
	 * Get a random starting or arrival stand. The parameter is an array (either startStands or arrivalStands).
	 * The int returned is the index of the stand in the static Vector of stands defined in the World class.
	 * This method allows to bias the selection of the start and arrival stands.
	 * @param source An array with indexes of the stand. More instances of an index increase the probability for it to be picked.
	 * @return the index of the stand, randomly selected in the source array.
	 */
	private int getRandStand (int[] source) {
		return source[(int)(source.length * Math.random())];
	}
	
	/**
	 * Selects a number of runs between MIN_RUNS and MAX_RUNS
	 * @return the number of runs.
	 */
	private int getNbRuns () {
		return MIN_RUNS + (int)Math.floor(Math.random() * (MAX_RUNS - MIN_RUNS));
	}
	
	/**
	 * Returns the minimal number of hops (distance between two stands on a circle) for a travel between source and destination.
	 * @param source The source stand.
	 * @param destination The destination stand.
	 * @return The minimal number of hops.
	 */
	private int getMinNbHops (int source, int destination) {
		int nbHops = 0 ;
		if (source > destination) {
			int clockWise = destination - source ;
			int counterClockWise = World.getStands().size() - destination + source ;
			nbHops = Math.min(clockWise,counterClockWise);
		} else {
			int clockWise = World.getStands().size() - source + destination ;
			int counterClockWise = source - destination ;
			nbHops = Math.min(clockWise,counterClockWise);
		}
		return nbHops;
	}
	
	private final int id ;
	
	public User(int id) {
		this.id = id ;
	}
	
	public void run() {
		// decide on number of runs
		int nbRuns = getNbRuns();
		System.out.println("User "+id+" will make "+nbRuns+" travels.");
		
		for (int r = 0 ; r < nbRuns ; r++) {
			// wait for 100 to 200 ms before starting a new trip
			try {
				Thread.sleep(100 + (int) Math.floor(Math.random() * 100));

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// choose start/stop
			int start = getRandStand(startStands);
			Stand startStand = World.getStands().get(start);
			int stop = getRandStand(arrivalStands);
			Stand stopStand = World.getStands().get(stop);
			// calculate the number of hops
			int nbHops = getMinNbHops(start, stop);


			// get a bike (check if there is a bike available and wait if not)
			synchronized (startStand) {
				while (startStand.getAvailableBikes() <= 0) {
					try {
						//System.out.println("User "+id+" waits for a bike at stand "+start);
						startStand.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// get a bike
				startStand.getBike();
				startStand.notifyAll();
			}

			// waits according to the number of hops for simulating the travel time
			try {
				System.out.println("User "+id+" is travelling from stand "+start+" to stand "+stop+" in "+Math.abs(nbHops)+" hops.");
				Thread.sleep(Math.abs(nbHops * TIME_FOR_ONE_HOP));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// return the bike (check if there is a free slot and wait if not)
			synchronized (stopStand)
			{
				while(stopStand.getFreeSlots() <= 0)
				{
					try {
						stopStand.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				stopStand.returnBike();
				stopStand.notifyAll();
			}
		}
	}
}
