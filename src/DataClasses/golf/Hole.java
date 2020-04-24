package DataClasses.golf;

public class Hole {
	private int holeNumber;
	private int par;
	
	public Hole(int holeNumber, int par) {
		this.holeNumber = holeNumber;
		this.par = par;
	}
	
	public int getHoleNumber() {
		return holeNumber;
	}

	public void setHoleNumber(int holeNumber) {
		this.holeNumber = holeNumber;
	}
	
	public int getPar() {
		return par;
	}
	
	public void setPar(int par) {
		this.par = par;
	}
}
