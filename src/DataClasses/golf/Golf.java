package DataClasses.golf;

import java.util.ArrayList;

public class Golf {
	
	private static ArrayList<Golfer> golfers = new ArrayList<Golfer>(); // List of golfers
	private static int matchTypeCode = 0; // 0 = Not set, 1 = back, 2 = Front, 3 = Full
	private static GolfCourse course = new GolfCourse();
	
	public static void printMenu() {
		System.out.println("List of Commands and their parameters:\n"
				+ "Command Parameter1 Parameter2 Parameter 3...\n"
				+ "AddGolfer FirstName LastName Number//Add a golfer\n"
				+ "AddHole HoleNumber Par // Add a hole to the course"
				+ "Help //Print this help menu\n"
				+ "MatchType type[back, front, full] //Determine which holes will be used and starts the match\n"
				+ "Score PlayerNumber Strokes //Tell how many strokes were given on the current hole\n"
				+ "Status //List the golfers with thier score\n"
				+ "Quit //Quit the program\n"
				);
	}
	
	public static void addGolfer(String firstName, String lastName, int number) {
		Golfer golfer = new Golfer(firstName, lastName, number);
		golfers.add(golfer);
		System.out.println(firstName + " " + lastName + " has been entered with number " + number);
	}
	
	public static void addHole(int holeNumber, int par) {
		
		if (par > 0) {
			Hole hole = new Hole(holeNumber,par);
			course.addHole(hole);
		} else
			System.out.println("Cannot set par value below 1\n");
			
	}
	
	public static void setMatchType(String matchType) {
		if (matchTypeCode == 0) {
			int holeCount = course.getHoleCount();
			switch(matchType) {
				case "BACK":
					if (holeCount == 9)
						matchTypeCode = 1;
					else
						System.out.println("The course does not have 9 holes, it has " + holeCount + "\n");
					return;
				case "FRONT":
					if (holeCount == 9)
						matchTypeCode = 2;
					else
						System.out.println("The course does not have 9 holes, it has " + holeCount + "\n");
					return;
				case "FULL":
					if (holeCount == 18)
						matchTypeCode = 3;
					else
						System.out.println("The course does not have 18 holes, it has " + holeCount + "\n");
					return;
				default:
					System.out.println("Invalid match type.\n");
					return;
			}
		}
		else
			System.out.println("The match has already started.\n");
	}
	
	public static void score(int playerNumber, int strokes) {
		for (int i = 0; i != golfers.size(); i++) {
			if (playerNumber == golfers.get(i).getNumber()) { // Find the correct golfer based on the given number
				if(matchTypeCode == 0)
					System.out.println("The match has not been started yet.\n");
				else if(golfers.get(i).getHolesPlayed() == 18)
					System.out.println(golfers.get(i).getFirstName() + " " + golfers.get(i).getLastName() + " has already finished their match.\n");
				else if(golfers.get(i).getHolesPlayed() == 9 && (matchTypeCode == 1 || matchTypeCode == 2))
					System.out.println(golfers.get(i).getFirstName() + " " + golfers.get(i).getLastName() + " has already finished their match.\n");
				else { // The golfer has been found and is still in a match
					if (strokes > 0)
						golfers.get(i).addScore(strokes);
					else
						System.out.println("Cannot set swing value below 1\n");
				}
			}
		}
	}
	
	public static void status() {
		status(course);
	}
	
	public static void status(GolfCourse course) {
		for (int i = 0; i != golfers.size(); i++) {
            System.out.println(golfers.get(i).toString(course));
        }
	}
}
