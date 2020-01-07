package wrestlingtournamentcli;

import java.io.Serializable;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 *  **Not included in the original UML schematic**
 */
public class MatchRecord implements Serializable{
//Where to find the full match information
int bracketPosition;
int roundNumber;
int matchInRound;
//Wrestler Information
int weightClass;
String greenWrestler;
String greenTeam;
String redWrestler;
String redTeam;
public MatchRecord(int bracketPosition, int roundNumber, int matchInRound, int weightClass, String greenWrestler, String greenTeam, String redWrestler, String redTeam){
    this.bracketPosition = bracketPosition;
    this.roundNumber = roundNumber;
    this.matchInRound = matchInRound;
    this.weightClass = weightClass;
    this.greenWrestler = greenWrestler;
    this.greenTeam = greenTeam;
    this.redWrestler = redWrestler;
    this.redTeam = redTeam;
}

public int[] getLocation(){
    return new int [] {bracketPosition, roundNumber, matchInRound};
}

public boolean hasWrestler(String username){//Takes in the Wrestler's USERNAME
    if(greenWrestler.equals(username) || redWrestler.equals(username)){
      return true;
    }else{
     return false;
    }
}

public boolean hasTeam (String teamID){//Takes in the team ID, not full name.
    if(greenTeam.equals(teamID) || redTeam.equals(teamID)){
      return true;
    }else{
     return false;
    }
}

}
