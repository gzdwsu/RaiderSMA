package DataClasses.golf;

import java.util.ArrayList;

import DataClasses.Athlete;

public class Golfer extends Athlete{
	
	private int number;
	private ArrayList<Integer> strokes = new ArrayList<Integer>();
	
	public Golfer(String firstName, String lastName, int number) {
		super(firstName, lastName);
		this.number = number;
	}
	
	public int calculateScore(GolfCourse course) {
		int score = 0;
		int holesPlayed = strokes.size();
		
		if (holesPlayed != 0)
			for(int i = 0; i != holesPlayed; i++) {
				score += strokes.get(i) - course.getHolePar(i+1);
			}
		return score;
	}
	
	public int getHolesPlayed() {
		return strokes.size();
	}
	
	public int getNumber() {
		return number;
	}
	
	public void addScore(int strokeCount) {
			strokes.add(strokeCount);
	}
	
	public int getSwings(int holeNumber) {
		return strokes.get(holeNumber - 1);
	}
	
	public String toString(GolfCourse course) {
		return getFirstName() + " " + getLastName() + " #" + getNumber() + " Current Score: " + calculateScore(course) + " \n\tSwings: " + strokes + "\n";
	}
}
