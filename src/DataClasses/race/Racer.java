package DataClasses.race;

public class Racer {
	private String firstName;
	private String lastName;
	private int carNumber;
	private int lapsCompleted;
	private int position;
	private boolean raceComplete; // Passed the finish line after there is a declared winner
	
	public Racer(String firstName, String lastName, int carNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.carNumber = carNumber;
		lapsCompleted = 0;
		raceComplete = false;
	}
	
	public void lapCompleted() {
		lapsCompleted++;
	}
	
	public String toString() {
		return firstName + " " + lastName + " is #" + carNumber;
	}
	
	public int getCarNumber() {
		return carNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getLapsCompleted() {
		return lapsCompleted;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public boolean getRaceComplete() {
		return raceComplete;
	}
	
	public void setRaceComplete() {
		raceComplete = true;
	}
}
