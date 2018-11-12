package Train_Stations;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Train extends Thread{
	public final int NB_PASSENGERS_MAX = 10;
	public int nb_passengers = 0;
	public int id;
	public boolean inUse =false;
	public Station departure;
	public Station arrival;
	public final int TRAVEL_TIME = 2000;
	public boolean running =true;
	ArrayList<Station> stations;
	
	public Train (int id, Station depart,ArrayList<Station> stats ) {
		this.id = id;
		this.departure = depart;
		this.stations = stats;
		setDestination(this.departure);
		//this.inUse = inUse;
		//this.start = start;
		//this.end = end;
	}

	public void setStation(Station p) {
		this.departure = p;		
	}

	public void arreter(){
		this.running  = false;
	}
	
	@Override
	public void run() {
		while (running && !stationIsFull()){
			try {
				this.departure.removeTrain(this);
				this.inUse = true;
				System.out.println(this.departure.name+" : "+this.id+" OUT");
				TimeUnit.MILLISECONDS.sleep(TRAVEL_TIME);
				System.out.println(this.arrival.name+" : "+this.id+" IN");
				this.departure = this.arrival;
				this.departure.addTrain(this);
				setDestination(this.departure);
				this.inUse = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		String s = ""+ this.id;
		return s;
	}

	public void setDestination(Station departure) {
		Station st0 = departure;
		while(departure == st0) {
			Random rand= new Random();
			st0 = stations.get(rand.nextInt(stations.size()));
		}

		this.arrival=st0;
	}


	public boolean stationIsFull() { 
		return this.arrival.enoughPlaceTrain();
	}
	
	public boolean trainIsFull() {
		return this.nb_passengers < this.NB_PASSENGERS_MAX;
	}

}
