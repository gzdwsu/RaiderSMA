package DataClasses.golf;

import java.util.ArrayList;

public class GolfCourse {
	private static ArrayList<Hole> holes = new ArrayList<Hole>();
	
	public void addHole(Hole hole) {
		int holeCount = holes.size();
		boolean uniqueHole = true;
		
		if(holeCount > 0)
			for (int i=0; i != holeCount; i++)
				if (hole.getHoleNumber() == holes.get(i).getHoleNumber()) {
					uniqueHole = false;
					System.out.println("That hole already exists\n");
				}
		
		if (uniqueHole)
			holes.add(hole);
	}
	
	public int getHolePar(int holeNumber) {
		return holes.get(holeNumber-1).getPar();
	}
	
	public int getHoleCount() {
		return holes.size();
	}
}
