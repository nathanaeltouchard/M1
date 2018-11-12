package Train_Stations;

import java.util.ArrayList;

public class Guichet extends Thread{
	public int id;
	public Station station;
	public boolean running = true;
	ArrayList<Station> stations;

	public Guichet( int id, Station p, ArrayList<Station> stations){
		this.id=id;
		this.station=p;
		this.stations =stations;
	}
	
	public void arreter(){
		this.running  = false;
	}
	
	public void run(){
		int i =0;
		while(running){
			try{
				if(this.station.enoughPlacePassenger()){
					Passenger passenger = new Passenger(i,stations);
					passenger.setDestination(station);
					this.station.addPass(passenger);
				
				
				
				Thread.sleep(200);
				System.out.println("New Passenger at "+ this.station.name+" ("+this.station.getPlace()+")");}
			}catch (InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
}
