package Train_Stations;

import java.util.ArrayList;

public class Simulation {

	public static void main(String[] args) throws InterruptedException {

		ArrayList<Station> stations = new ArrayList<Station>();
		Station p1 = new Station("A",3);
		Station p2 = new Station("B",2);
		Station p3 = new Station("C",2);
		
		stations.add(p1);
		stations.add(p2);
		stations.add(p3);
		Train t1 = new Train(1,p1,stations);
		Train t2 = new Train(2,p2,stations);
		Train t3 = new Train(3,p3,stations);
		Guichet g1 = new Guichet(1,p1,stations);
		Guichet g2 = new Guichet(2,p2,stations);
		Guichet g3 = new Guichet(3,p3,stations);
		t1.start();
		t2.start();
		t3.start();
		//g1.start();
		//g2.start();
		//g3.start();

	}

}
