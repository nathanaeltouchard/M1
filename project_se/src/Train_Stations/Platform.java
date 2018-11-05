package Train_Stations;

import java.util.ArrayList;

public class Platform {

	public final int NB_TRAINS = 2;
	public String name;
	public ArrayList<Train> lTrain;


	public Platform(String name) {
		this.name = name;
		this.lTrain = new ArrayList<Train>();
	}

	public void addTrain(Train t) {
		if( this.lTrain.size()< this.NB_TRAINS) {
			this.lTrain.add(t);
			t.setStation(this);
			System.out.println("Train is at "+this.name+" Station");
		}else {
			System.err.println("Exception: FULL");
		}
	}
	
	public void removeTrain(Train t) {
		this.lTrain.remove(t);
	}
	
	public String toString() {
		return this.lTrain.toString();
	}

}
