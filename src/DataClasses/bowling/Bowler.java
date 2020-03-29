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
		return getFirstName() + " " + getLastName() + " #" + getNumber() + " \n\tRolls: " + scores + "\n";
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
