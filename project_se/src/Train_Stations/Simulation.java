package Train_Stations;

import java.util.ArrayList;

public class Simulation {

	public static void main(String[] args) throws InterruptedException {
		Train t1 = new Train(101);
		Train t2 = new Train(201);
		Train t3 = new Train(301);
		Platform p1 = new Platform("A");
		Platform p2 = new Platform("B");
		
		ArrayList<Passenger> p = new ArrayList<Passenger>();
		
		for(int i = 0;i < 10; i++) {
			
			p.add(new Passenger("Passenger"+i));
			
		}
		System.out.println( p.toString());
		
		p1.addTrain(t1);
		t1.travel(p2);
		
		System.out.println(p1.toString());
		System.out.println(p2.toString());

	}

}
