package Train_Stations;

import java.util.ArrayList;

public class Station extends Thread{

	public final int NB_TRAINS = 2;
	public final int NB_PASS = 10;
	public String name;
	public ArrayList<Train> lTrain;
	public ArrayList<Passenger> lPass;
	public int nbPlatform;


	public Station(String name, int nbPlat) {
		this.name = name;
		this.lTrain = new ArrayList<Train>();
		this.lPass = new ArrayList<Passenger>();
		this.nbPlatform=nbPlat;
	}

	public void addTrain(Train t) {
		if( this.lTrain.size()< this.NB_TRAINS) {
			this.lTrain.add(t);
			t.setStation(this);
		}else {
			System.err.println("Exception: FULL");
		}
	}
	
	public void addPass(Passenger p) {
		if( this.lPass.size()< this.NB_PASS) {
			this.lPass.add(p);
		}else {
			System.err.println("Exception: FULL");
		}
	}
	
	public String getPlace(){
		return ""+this.lPass.size()+"/"+this.NB_PASS;
	}
	public boolean enoughPlacePassenger(){
		return (this.lPass.size()< this.NB_PASS);
	}
	
	public void removeTrain(Train t) {
		this.lTrain.remove(t);
	}
	
	public String toString() {
		return this.lTrain.toString();
	}
	
	public boolean enoughPlaceTrain(){
		return (this.lTrain.size()< this.NB_TRAINS * this.nbPlatform);
	}
	
	public boolean enoughPlaceinTrain(Train t) {
		return t.trainIsFull();
	}
}
