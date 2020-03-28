package DataClasses.bowling;

import java.util.ArrayList;

import DataClasses.Athlete;

public class Bowler extends Athlete{
	
	private int number;
	private ArrayList<Integer> scores = new ArrayList<Integer>(); // Up to 21 entries

	public Bowler(String firstName, String lastName, int number) {
		super(firstName, lastName);
		this.number = number;
	}
	
	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() + " #" + getNumber() + " Current Score: " + calculateScore() + " \n\tRolls: " + scores + "\n";
	}
	
	public int calculateScore() {
		
		int score = 0;
		
		if (getRollsRolled() != 0) {
			for (int i = 1; i != getRollsRolled(); i++) {
				if (getRollsRolled() < 5) {
					
				} else {
					score += getScore(i); // Score current 
					if (getScore(i-2) == 10) {
						
					}
				}
			}
		}
		
		
		
		return score;
	}
	
//	public int calculateScore() {
//		int score = 0;
//		if (getRollsRolled() != 0) {
//			for (int i = 1; i != getRollsRolled(); i++) {
//				if (getRollsRolled() < 18) { // Frames 1-9
//					if (getRollsRolled() % 2 == 0) { // First roll of frame
//						if (getScore(i) == 10) { // Strike
//							score += 10 + getScore(i+2);
//							if (getScore(i+2) == 10) { // Next roll strike
//								if(getRollsRolled() == 17) { // If currently in the 9th frame
//									score += getScore(i+3);
//								} else {
//									score += getScore(i+4);
//								}
//							} else score += getScore(i+3); // Next roll not strike
//						} else score += getScore(i);
//					} else { // Second roll of frame
//						if (getScore(i) + (getScore(i-1)) == 10) { // Spare
//							score += getScore(i) + getScore(i+1);
//						}
//					}
//	
//				} else { // Frame 10
//					score += getScore(i);
//				}
//			}
//		}
//		return score;
//	}
	
	public void addScore(int score) {
		scores.add(score);
	}
	
	public int getScore(int rollNumber) {
		return scores.get(rollNumber - 1);
	}
	
	public int getRollsRolled() {
		return scores.size();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ArrayList<Integer> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Integer> scores) {
		this.scores = scores;
	}

}
