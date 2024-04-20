import java.util.Vector;

public class World {
	private static final int NB_STANDS = 6 ;
	private static final int NB_USERS = 100 ;
	private static final int STAND_CAPACITY = 20 ;
	private static final int STAND_INITIAL_BIKES = 10 ;
	private static final int TRUCK_CAPACITY = 20 ;
	private static final int TRUCK_INITIAL_BIKES = 10 ;
	
	private static Vector<Stand> stands;
	private static Vector<User> users;
		
	public World() {
		System.err.println("The World class is not supposed to be instantiated. Exiting.");
		System.exit(1);
	}
	
	public static Vector<Stand> getStands() {
		return stands;
	}
	
	public static void main(String[] args) {
		// create stands
		System.out.println("World creates "+NB_STANDS+" stands");
		stands = new Vector<Stand>();
		for (int i=0;i<NB_STANDS;i++){
			stands.add(new Stand(i, STAND_CAPACITY, STAND_INITIAL_BIKES));
		}
		
		// create the truck
		System.out.println("World creates truck");
		Truck t = new Truck(TRUCK_INITIAL_BIKES, TRUCK_CAPACITY);
		
		// create the users	
		System.out.println("World creates "+NB_USERS+" users");
		users = new Vector<User>();
		for (int i=0;i<NB_USERS;i++) {
			User u = new User(i);
			users.add(u);
		}
				
		// TODO start the truck and the users as independent threads.
		// TODO the program must stop when all users have finished their travels
		// TODO the truck needs to stop working when all users have finished their travels.

		// start the truck
		t.start();

		// start the users
		for (User u : users) {
			u.start();
		}

		// wait for all users to finish
		for (User u : users) {
			try {
				u.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// stop the truck
		t.interrupt();

//		// wait for the truck to finish
//		try {
//			t.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		System.out.println("All users have finished their travels");
	}
}
