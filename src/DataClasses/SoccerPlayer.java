package DataClasses;

import java.io.Serializable;
import wrestlingtournamentcli.*;

/**
 * @author Cody Francis
 */
public class SoccerPlayer implements Serializable{

    String firstName;
    String lastName;
    String userName; 
    Team team;
    String position;
    double gpg;//average goals per game
    double apg;//average assists per game
    double savePercentage;//goalie save percentages
    
    public SoccerPlayer(String fn, String ln, String teamAlias, String position, double gpg, double apg, double saves) throws Exception {
        this.firstName = fn.toUpperCase();
        this.lastName = ln.toUpperCase();
        this.team = Model.teamLookup(teamAlias.toUpperCase());
        this.userName = Model.getUserName(lastName, firstName);
        this.position = position.toUpperCase();
        this.gpg = gpg;
        this.apg = apg;
        this.savePercentage = saves;
    }
    
    public SoccerPlayer(){
    this.firstName = "";
    this.lastName = "";
    try{
    this.team = Model.teamLookup("BYE");
    this.userName = "   BYE   ";
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
    
    
    public String getTeamID(){
        return team.getTeamName();
    }
    public String getPosition() {
        return position;
    }

    public double getGPG() {
        return gpg;
    }

    public double getAPG() {
        return apg;
    }
    
    
    public double getSave(){
        return savePercentage;
    }
    

    public String getLongString(){
    StringBuffer rs = new StringBuffer();
    rs.append("Name: " + lastName + ", " + firstName + "\n");
    rs.append("Username: " + userName + "\n");
    rs.append("Team: " + team.getTeamName() + "\n");
    rs.append("Position: " + position + "\n");
    rs.append("Goals Per Game: " + gpg + "\n");
    rs.append("Assists Per Game: " + apg+ "\n");
    rs.append("Save Percentage: " + savePercentage+ "\n");
    return rs.toString();
    }
    
    @Override
    public String toString() {
        return lastName + ", " + firstName + "\t(" + userName + ")\tTeam: " + team.getTeamName() +"\tPosition: " + position +"\tGPG: " + gpg +"\tAPG: " + apg +"\tSave %: " + savePercentage;
    }
    
    
}


