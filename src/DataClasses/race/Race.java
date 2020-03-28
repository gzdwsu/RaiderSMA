package DataClasses.race;

import java.util.ArrayList;

public class Race {
	
	private static int lapsTotal; // Number of laps in the race
	private static int lapCurrent; // Current lap
	private static ArrayList<Racer> racers = new ArrayList<Racer>(); // List of racers
	private static ArrayList<Racer> unofficialResults = new ArrayList<Racer>();
	private static boolean checkerdFlag; // Race is finishing
	
	public Race() {
	}
	
	public Race(int lapsTotal) {
		Race.lapsTotal = lapsTotal;
	}
	
	public static void printMenu() {
		System.out.println("List of Commands and their parameters:\n"
				+ "Command Parameter1 Parameter2 Parameter 3...\n"
				+ "RACE AddRacer FirstName LastName CarNumber //Add a new racer\n"
				+ "RACE Laps //Set the amount of laps in the race\n"
				+ "RACE LapCompleted CarNumber //Choose which racer completed a lap\n"
				+ "RACE ListRacers //View the list of racers with their numbers\n"
				+ "RACE Start //Start the race\n"
				+ "RACE Status //View the race status\n");
	}
	
	public static void addRacer(String firstName, String lastName, int carNumber) {
		Racer racer = new Racer (firstName, lastName, carNumber);
		racers.add(racer);
		System.out.println(firstName + " " + lastName + " has been entered with number " + carNumber);
	}
	
	public static void listRacers() {
		for (int i = 0; i != racers.size(); i++) {
            System.out.println(racers.get(i));
        }
	}
	
	public static void lapCompleted(int carNumber) {
		
		if (lapCurrent == 0)
			System.out.println("False start, please start the race.");
		
		else { // A race has already started or is finishing
		
				for (int i = 0; i != racers.size(); i++) {
		            if (carNumber == racers.get(i).getCarNumber()) { // Find the correct racer based on the given car number
		            	if (!(racers.get(i).getRaceComplete())) { // Check to see if the racer did not finish the race already, else just skip doing anything
			            	if (checkerdFlag == false) { // If the checkered flag has not been thrown yet
			            		racers.get(i).lapCompleted(); // Add a lap to the racer
					            if (racers.get(i).getLapsCompleted() == lapCurrent) { // Check to see if the racer is in the lead
					            	if (lapCurrent == lapsTotal) {
					            		checkerdFlag = true;
					            		racers.get(i).setRaceComplete();
					            		unofficialResults.add(racers.get(i));
					            		System.out.println(racers.get(i).getFirstName() + " " + racers.get(i).getLastName() + " has won the race!");
					            	} else
					            		lapCurrent++;
					            }
					            System.out.println(racers.get(i).getFirstName() + " " + racers.get(i).getLastName() + " completed lap " + racers.get(i).getLapsCompleted());
			            	} else { // Checkered flag has already been flown and the racer did not win
			            		racers.get(i).lapCompleted();
			            		unofficialResults.add(racers.get(i));
			            		racers.get(i).setRaceComplete();
			            		System.out.println(racers.get(i).getFirstName() + " " + racers.get(i).getLastName() + " has finshed their race with " + racers.get(i).getLapsCompleted() + " laps completed.");
			            		
			            		// Check to see if all racers have finished the race
			            		boolean finished = true;
			            		for (int j = 0; j != racers.size(); j++) {
			    		            if (racers.get(j).getRaceComplete() == false) {
			    		            	finished = false;
			    		            }
			    		        }
			            		if (finished) {
			            			System.out.println("The race has been complete.");
			            			calculateResults();
			            		}
			            		
			            	}
		            	}
		            }
				}
		}
	}
	
	public static void getStatus() {
		System.out.println("The race is on lap " + lapCurrent + "/" + lapsTotal);
	}
	
	public static void setLapsTotal(int lapsTotal) {
		Race.lapsTotal = lapsTotal;
	}
	
	public static void start() {
		if (lapsTotal < 1)
			System.out.println("Please set the amount of laps before starting the race.");
		else if (lapCurrent == 0) {
			lapCurrent++;
			System.out.println("The race has begun!");
		} else
			System.out.println("The race is already in progress.");
	}
	
	public static void calculateResults() {
		for (int i = 0; i != unofficialResults.size(); i++) {
	           System.out.println((i+1) + " - " + racers.get(i).getFirstName() + " " + racers.get(i).getLastName() + " #" + racers.get(i).getCarNumber());
	           System.out.println("Race lap counts has been reset with the same racers");
	    }
		lapCurrent = 0;
		lapsTotal = 0;
	}
}