package DataClasses;

import java.io.Serializable;
import wrestlingtournamentcli.*;

/**
 * @author Jared Murphy https://github.com/murphman29
 *
 * ==Match implements Comparable==
 *
 * =Member Variables= -matchID:int -greenWrestler:String //Will be regarded as
 * Wrestler One -redWrestler:String //Will be regarded as Wrestler Two
 * -matNumber:int -fallType:int //Before being placed into the match details, it
 * will be converted from a String to an integer. pin = 6, tech = 5, major
 * decision = 4, decision = 3, BYE = 1 -fallTime:String -winningColor:String
 * //GREEN or RED -redPoints:int -greenPoints:int -complete:boolean -round:int
 *
 * =Methods= +void:Wrestler(int matchID, String a, String b, int matID)
 * +void:Wrestler(int matchID, String a, String b) +void:setMatID(int matID)
 * +Wrestler:getWinner() +String:getGreenWrestler() +String:getRedWrestler()
 * +void:updateMatch(int fallType, String winningColor) +void:updateMatch(int
 * fallType, String winningColor, int greenPoints, int redPoints)
 * +void:updateMatch(int fallType, String winningColor, int greenPoints, int
 * redPoints, String fallTime) +boolean:isComplete() +int:getRound()
 * +int:getFallType() +String:getFallTime() +String:getWinningColor()
 * +int:getGreenPoints() +int:getRedPoints() +String:toString()
 * +int:CompareTo(Object o)
 */
public class Match implements Comparable, Serializable{

    private int matchID;
    private String greenWrestler;
    private String redWrestler;
    private String greenTeam;
    private String redTeam;
    private int matNumber;
    private int fallType; //Before being placed into the match details, it will be converted from a String to an integer. pin = 6, tech = 5, major decision = 4, decision = 3, BYE = 1
    private String fallTime;
    private String winningColor;
    private int redPoints;
    private int greenPoints;
    private boolean complete;
    private int round;
    private int weightClass;

    public Match(int matchID, String green, String greenTeam, String red, String redTeam, int matID, int round, int weightClass) {//For matches that are being assigned a mat immediately
        this.matchID = matchID;
        this.greenWrestler = green;
        this.greenTeam = greenTeam;
        this.redWrestler = red;
        this.redTeam = redTeam;
        this.matNumber = matID;
        this.round = round;
        this.weightClass = weightClass;
        complete = false;
        greenPoints = 0;
        redPoints = 0;
        fallTime = "";
    }

    public Match(int matchID, String green, String greenTeam, String red, String redTeam, int round, int weightClass) {//For matches that are being assigned a mat immediately
        this.matchID = matchID;
        this.greenWrestler = green;
        this.greenTeam = greenTeam;
        this.redWrestler = red;
        this.redTeam = redTeam;
        this.matNumber = -1;
        this.round = round;
        this.weightClass = weightClass;
        complete = false;
        greenPoints = 0;
        redPoints = 0;
        fallTime = "";
    }
    
    public Match(){ //
        this.matchID = 0;
        this.greenWrestler = "   TBD   ";
        this.redWrestler = "   TBD   ";
    }
    
    public void setMatchID(int matchID){
        this.matchID = matchID;
    }

    public void setMatID(int matID) {
        this.matNumber = matID;
    }

    public int getMatID() {
        return matNumber;
    }

    public String getGreenWrestler() {
        return greenWrestler;
    }

    public String getRedWrestler() {
        return redWrestler;
    }

    public String getWinner() { //Returns only a username so that the wrestler's record can be updated in the model.
        if (complete) {
            if (winningColor.equals("GREEN")) {
                return greenWrestler;
            } else {
                return redWrestler;
            }
        } else {
            Exception e = new NotFoundException(this);
            System.out.println(e.getMessage());
            return "ERROR    ";
        }
    }
    
        public String getWinnerTeam() { //Returns only a username so that the wrestler's record can be updated in the model.
        if (complete) {
            if (winningColor.equals("GREEN")) {
                return greenTeam;
            } else {
                return redTeam;
            }
        } else {
            Exception e = new NotFoundException(this);
            System.out.println(e.getMessage());
            return "ERROR    ";
        }
    }

    public void updateMatch(int fallType, String winningColor) {//For BYEs
        this.fallType = fallType;
        this.winningColor = winningColor.toUpperCase();
        complete = true;
    }

    public void updateMatch(int fallType, String winningColor, int greenPoints, int redPoints) {
        this.fallType = fallType;
        this.winningColor = winningColor.toUpperCase();
        this.greenPoints = greenPoints;
        this.redPoints = redPoints;
        complete = true;
    }

    public void updateMatch(int fallType, String winningColor, int greenPoints, int redPoints, String fallTime) {
        this.fallType = fallType;
        this.winningColor = winningColor.toUpperCase();
        this.greenPoints = greenPoints;
        this.redPoints = redPoints;
        this.fallTime = fallTime;
        complete = true;
    }

    public void updateMatch(String fallType, String winningColor) {//For BYEs
        this.fallType = StringToFallType(fallType);
        this.winningColor = winningColor.toUpperCase();
        complete = true;
    }

    public void updateMatch(String fallType, String winningColor, int greenPoints, int redPoints) {
        this.fallType = StringToFallType(fallType);
        this.winningColor = winningColor.toUpperCase();
        this.greenPoints = greenPoints;
        this.redPoints = redPoints;
        complete = true;
    }

    public void updateMatch(String fallType, String winningColor, int greenPoints, int redPoints, String fallTime) {
        this.fallType = StringToFallType(fallType);
        this.winningColor = winningColor.toUpperCase();
        this.greenPoints = greenPoints;
        this.redPoints = redPoints;
        this.fallTime = fallTime;
        complete = true;
    }
    //Can hold up to 500 total references. The y column is used to store [bracketPosition, roundNumber, matchInRound, greenWrestler, redWrestler]
    public MatchRecord generateMatchRecord(int roundNumber, int matchInRound){
       int pos = Model.getWeightClassPosition(weightClass);
       return new MatchRecord(pos,roundNumber,matchInRound,weightClass,greenWrestler,greenTeam,redWrestler,redTeam);
    }

    private int StringToFallType(String s) {
        s = s.toUpperCase();
        if (s.contains("PIN")) {
            return 1;
        } else if (s.contains("TECH")) {
            return 2;
        } else if (s.contains("MAJ")) {
            return 3;
        } else if (s.contains("DEC")) {
            return 4;
        } else if (s.contains("DIS") || s.contains("DQ")) {
            return 5;
        } else if (s.contains("DEF") || s.contains("INJ")) {
            return 6;
        } else if (s.contains("FOR") || s.contains("BYE")) {
            return 7;
        } else {
            Exception e = new IncorrectFormatException(s);
            System.out.println(e.getMessage());
            return 8;
        }
    }

    private String getFallType () {
        switch (fallType) {
            case 1:
                return "PIN";
            case 2:
                return "TECH";
            case 3:
                return "MAJOR DECISION";
            case 4:
                return "DECISION";
            case 5:
                return "DISQUALIFICATION";
            case 6:
                return "INJURY DEFAULT";
            case 7:
                return "FORFEIT";
            default:
                return "ERROR";
        }
    }

    public int getTeamPoints() {
        if (!complete) {
            return 0;
        } else {
            switch (fallType) {
                case 1:
                    return 6;
                case 2:
                    return 5;
                case 3:
                    return 4;
                case 4:
                    return 3;
                case 5:
                    return 6;
                case 6:
                    return 6;
                case 7:
                    return 6;
                default:
                    return 0;
            }
        }
    }
 public boolean isComplete(){
     return complete;
 }
 
 public int getRound(){
     return round;
 }
 
 public String getFallTime(){
     return fallTime;
 }
 
 public int getGreenPoints(){
     return greenPoints;
 }
 
 public int getRedPoints(){
     return redPoints;
 }
 
 public int getMatchID(){
     return matchID;
 }
 
 @Override
 public String toString(){
 String rs = "";
 rs += matchID + ": ";
 rs += greenWrestler + " vs. " + redWrestler;
 rs += " (" + winningColor + ", " + getFallType() + ", " + fallTime;
 return rs;
 }
 
 @Override
 public int compareTo(Object o){
 Match other = (Match)o;
 if(matchID < other.getMatchID()){
   return 1;
 }else if(matchID == other.getMatchID()){
     return 0;
 }else{
     return -1;
 }
 }
 
}
