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
        int x = getRollsRolled();

        if (x != 0) {
            for (int i = 1; i != x+1; i++) {
                score += getScore(i);
                if (i > 4 && i < 20) { // Not Frames 1,2
                    if (i % 2 != 0 ) { // 1st roll of frame 3-10
                        if (getScore(i-2) == 10 || getScore(i-2) + getScore(i-1) == 10) score += getScore(i); // Previous frame was a mark
                        if (getScore(i-4) == 10 && getScore(i-2) == 10) score += getScore(i); // Past 2 frames were a strike
                    } else {
                        if (getScore(i-3) == 10) score += getScore(i);  // 2nd roll of frame 3-10
                    }
                }
                if (i == 20) {
                    if (getScore(i-1) == 10) score += getScore(i);
                }
                if (i == 3) {
                    if (getScore(i-2) + getScore(i-1) == 10) score += getScore(i);
                }
            }
        }
        return score;
    }
	
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
