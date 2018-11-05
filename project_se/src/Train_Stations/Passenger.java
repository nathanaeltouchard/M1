package Train_Stations;

public class Passenger {
	
	public String name;
	public String surname;
	public int age;
	public String fidelity;
	public boolean ticket;
	public boolean onBoard = false;
	
	public Passenger(String name/*, String surname, int age, String fidelity, boolean ticket, boolean onBoard*/) {
		this.name = name;
		/*this.surname = surname;
		this.age = age;
		this.fidelity = fidelity;
		this.ticket = ticket;
		this.onBoard = onBoard;*/
	}
	
	public String toString() {
		return this.name;
	}
	
}
