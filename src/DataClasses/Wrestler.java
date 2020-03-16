package DataClasses;

import java.io.Serializable;
import wrestlingtournamentcli.*;

/**
 * @author Jared Murphy https://github.com/murphman29
 * 
 * Edited 02/12/2020
 * Editor: James Wright https://github.com/JamesKillmonger
 * Edited the Following: getLongString() and added comment explanation
 * 
 * 
 */
public class Wrestler implements Comparable, Serializable{

    String firstName;
    String lastName;
    String userName; //Length 9
    Team team;
    Integer weightClass;
    int grade;
    int tournamentWinCount;
    int tournamentMatchCount;
    int totalWinCount;
    int totalMatchCount;
    int seed;
    double rating;
    
    public Wrestler(String fn, String ln, String teamAlias, int grade, int weightClass, int totalWins, int totalMatches) throws Exception {
        this.firstName = fn.toUpperCase();
        this.lastName = ln.toUpperCase();
        this.team = Model.teamLookup(teamAlias.toUpperCase());
        this.userName = Model.getUserName(lastName, firstName);
        this.grade = grade;
        if(Model.verifyWeightClass(weightClass)){
        this.weightClass = weightClass;
        }else{
        throw new NotFoundException(weightClass);
        }
        this.totalWinCount = totalWins;
        this.totalMatchCount = totalMatches;
        this.rating = generateRating();
        this.seed = 100;
    }
    
    public Wrestler(int weightClass){
    this.firstName = "";
    this.lastName = "";
    this.weightClass = weightClass;
    try{
    this.team = Model.teamLookup("BYE");
    this.userName = "   BYE   ";
    this.totalMatchCount = 100;
    this.totalWinCount = 0;
    this.rating = 0;
    this.seed = 100;
    }catch(Exception e){
        
    }
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }
    
    public int getWeightClass(){
        return weightClass;
    }
    
    public void setSeed(int seed){
    this.seed = seed;
    }
    
    public int getSeed(){
        return seed;
    }
    
    private double generateRating(){
    return (double)((double)totalWinCount/(double)totalMatchCount)*((double)totalMatchCount/10.00);
    }
    
    public void setRating(double rating){
        this.rating = rating;
    }
    
    public double getRating(){
        return rating;
    }
    
    public String getTeamID(){
        return team.getTeamName();
    }
    
    public void addMatch(boolean win){
	    totalMatchCount++;
	    tournamentMatchCount++;
	    if(win){
	    totalWinCount++;
	    tournamentWinCount++;
	    }
    }
    
    
    //String by itself whenever operated on adds a copy of its self to the string pool thus wasting tons of memory
    //if performing frequent operations on strings thus I used string buffer which modifies the exact string in 
    //its according memory location which is the solution to the object churning problem
    //This required rs to be changed to stringBuffer in printWrestlerInformation method in Model.java as well
    public StringBuffer getLongString(){
    StringBuffer rs = new StringBuffer();
    rs.append( "Name: " + lastName + ", " + firstName + "\n");
    rs.append("Username: " + userName + "\n");
    rs.append("Weight Class: " + weightClass + "\n");
    rs.append("Team: " + team.getTeamName() + "\n");
    rs.append("Grade: " + grade + "\n");
    rs.append("Seed: " + seed + "\n");
    rs.append("Rating: " + rating + "\n");
    if(totalMatchCount != 0){
    	rs.append("YTD Record:\n\t" + "Wins: " + totalWinCount + "\n\tMatches: " + totalMatchCount + "\n\tW/L Ratio: " + ((double)totalWinCount/(double)totalMatchCount) + "\n");
    }
    if(tournamentMatchCount != 0){
    	rs.append("Tournament Record:\n\t" + "Wins: " + tournamentWinCount + "\n\tMatches: " + tournamentMatchCount + "\n\tW/L Ratio: " + (tournamentWinCount/tournamentMatchCount) + "\n");
    }
    return rs;
    }
    
    
    
    
    @Override
    public String toString() {
    	//This must return a string so can't use string buffer here, recommended fix for later developers
        return lastName + ", " + firstName + "\t(" + userName + ")\tClass: " + weightClass + "\tTeam: " + team.getTeamName();
    }
    
    @Override
    public int compareTo(Object o){
    Wrestler other = (Wrestler)o;
    if(this.weightClass < other.getWeightClass()){
      return -1;
    }else if(this.weightClass > other.getWeightClass()){
      return 1;
    }else{
    if(this.seed == 100 && other.getSeed() != 100){//If the other person is seeded, they're higher
        return 1;
    }else if(this.seed != 100 && other.getSeed() == 100){//If I'm seeded but the other guy isn't, I'm higher
        return -1;
    }else if(this.seed != 100 && other.getSeed() != 100){//If they're both seeded...
      if(this.seed < other.getSeed()){//If my seed is lower than their seed, I'm better (Seed #1 is best)
         return -1;
      }else if(this.seed == other.getSeed()){//If their seed is equal to my seed, something is wrong but we're equal
          return 0;
      }else{//If their seed is lower than mine, then they're better.
          return 1;
      }
    }else{//This is if neither wrestler has a seed
        if(this.rating > other.getRating()){
            return -1;
        }else if(this.rating == other.getRating()){
            return 0;
        }else{
            return 1;
        }
    }
    }
    }
    
}



