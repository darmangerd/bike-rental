public class Truck extends Thread {
    private static final int TIME_BETWEEN_SITES = 25; // in milliseconds

    private final int capacity;
    private int bikes;

    public Truck(int bikes, int capacity) {
        this.bikes = bikes;
        this.capacity = capacity;
    }

    public Truck(int capacity) {
        this.bikes = 0;
        this.capacity = capacity;
    }


    // balance the number of bikes on the stand
    private void balanceBikes(Stand s) {
        // get the desired and current number of bikes on the stand
        int desiredNb = s.getCapacity() / 2;
        int currentNb = s.getAvailableBikes();

        try {
            if (currentNb > desiredNb) {
                // take the minimum between the difference between the current number of bikes and the desired number
                // of bikes on stand; and the number of bikes the truck can take
                // to avoid taking more bikes than the truck can carry
                int bikeToTake = Math.min(currentNb - desiredNb, this.capacity - this.bikes);
                // take the bikes from the stand
                this.bikes += bikeToTake;
                s.getBikes(bikeToTake);
            } else if (currentNb < desiredNb) {
                // take the minimum between the difference between the desired number of bikes
                // and the current number of bikes on the stand; and the number of bikes the truck has
                // to avoid giving more bikes than the truck has
                int bikeToGive = Math.min(desiredNb - currentNb, this.bikes);
                this.bikes -= bikeToGive;
                s.returnBikes(bikeToGive);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void run() {
        // circle around sites
        try {
            // Use interrupt to stop the thread when the users finish their trips
            while (!Thread.interrupted()) {

                for (Stand s : World.getStands()) {
                    synchronized (s) {
                        System.out.println("Truck is balancing stand " + s.getId());
                        balanceBikes(s);
                    }
                    // wait before going to the next site (simulate travel time)
                    Thread.sleep(TIME_BETWEEN_SITES);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Truck finished its work (interrupted)");
        }

    }
}