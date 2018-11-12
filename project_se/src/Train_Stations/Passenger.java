package Train_Stations;

import java.util.ArrayList;
import java.util.Random;

public class Passenger {
	
	public int id;
	public Station destination;
	ArrayList<Station> stations;
	
	public Passenger(int id , ArrayList<Station> stats) {
		this.id = id;
	
	}
	
	public void setDestination(Station departure) {
		Station st0 = departure;
		while(departure == st0) {
			Random rand= new Random();
			st0 = stations.get(rand.nextInt(stations.size()));
		}

		this.destination=st0;
	}
	
}
