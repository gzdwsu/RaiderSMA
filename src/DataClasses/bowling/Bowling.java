package DataClasses.bowling;

import java.util.ArrayList;

public class Bowling {
	
	private static ArrayList<Bowler> bowlers = new ArrayList<Bowler>(); // List of bowlers
	
	public static void printMenu() {
		System.out.println("List of Commands and their parameters:\n"
				+ "Command Parameter1 Parameter2 Parameter 3...\n"
				+ "AddBowler FirstName LastName Number //Add a bowler\n"
				+ "Help //Print this help menu\n"
				+ "Roll PlayerNumber PinsKnockedDown //Tell how many pins were knocked down on the roll\n"
				+ "Status //List bowlers with their scores\n"
                + "RESTART // Return to start menu\n"
				+ "Quit //Quit the program"
				);
	}
	
	public static void addBowler(String firstName, String lastName, int number) {
		Bowler bowler = new Bowler(firstName, lastName, number);
		bowlers.add(bowler);
		System.out.println(firstName + " " + lastName + " has been entered with number " + number);
	}
	
	public static void status() {
		for (int i = 0; i != bowlers.size(); i++) {
            System.out.println(bowlers.get(i));
        }
	}
	
	public static void roll(int playerNumber, int pins) {
		for (int i = 0; i != bowlers.size(); i++) {
			if (playerNumber == bowlers.get(i).getNumber()) { // Find the correct bowler based on the given number
				if(bowlers.get(i).getRollsRolled() == 21)
					System.out.println(bowlers.get(i).getFirstName() + " " + bowlers.get(i).getLastName() + " has already finished their game.");
				
				else if((bowlers.get(i).getRollsRolled() % 2 == 0) && bowlers.get(i).getRollsRolled() < 18) { // First roll of frame 1-9
						if (pins == 10) { // Strike
							bowlers.get(i).addScore(pins);
							bowlers.get(i).addScore(0);
						} else bowlers.get(i).addScore(pins);
				} else if((bowlers.get(i).getRollsRolled() % 2 != 0) && bowlers.get(i).getRollsRolled() < 19) { // Second roll of frame 1-9
					bowlers.get(i).addScore(pins);
				} else { // Last frame
					switch(bowlers.get(i).getRollsRolled()) {
						case 18:
							bowlers.get(i).addScore(pins);
							return;
						case 19:
							bowlers.get(i).addScore(pins);
							if (bowlers.get(i).getScore(19) + bowlers.get(i).getScore(20) < 10) {
								bowlers.get(i).addScore(0);
							}
							return;
						case 20:
							bowlers.get(i).addScore(pins);
							return;
					}
				}
			}
		}
	}
}
