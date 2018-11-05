package Train_Stations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Train {
	public final int NB_PASSENGERS_MAX = 10;
	public int nb_passengers = 0;
	public int id;
	public boolean inUse =false;
	public Platform departure;
	public Platform arrival;
	public final int TRAVEL_TIME = 2000;
	
	public Train (int id/*, boolean inUse, Date start, Date end*/) {
		this.id = id;
		//this.inUse = inUse;
		//this.start = start;
		//this.end = end;
	}

	public void setStation(Platform p) {
		this.departure = p;		
	}
	
	public void travel( Platform p) throws InterruptedException {
		this.departure.removeTrain(this);
		this.inUse = true;
		TimeUnit.MILLISECONDS.sleep(TRAVEL_TIME);
		this.arrival = p;
		p.addTrain(this);
		this.inUse = false;
	}
	
	public String toString() {
		String s = ""+ this.id;
		return s;
	}
}
