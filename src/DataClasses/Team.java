package DataClasses;

import java.io.Serializable;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 * 
 * ==Team implements Comparable==

	=Member Variables=
	-teamName:String
	-teamInitials:String
	-teamMascot:String
	-teamScore:int
	
	=Methods=
	+void:Team(String teamName, String teamInitials)
	+void:Team(String teamName, String teamInitials, String teamMascot)
	+String:getTeamName()
	+String:getInitials()
	+String:getTeamMascot()
	+int:getTeamScore()
	+void:addTeamScore(int score)
	+void:removeTeamScore(int score)
	+int:compareTo(Object o)
 */
public class Team implements Comparable, Serializable{
String teamName;
String teamInitials;
String mascot;
int teamScore;

public Team(String teamName, String teamInitials){
this.teamName = teamName.toUpperCase();
this.teamInitials = teamInitials.toUpperCase();
this.mascot = "";
this.teamScore = 0;
}

public Team(String teamName, String teamInitials, String mascot){
this.teamName = teamName.toUpperCase();
this.teamInitials = teamInitials.toUpperCase();
this.mascot = mascot.toUpperCase();
this.teamScore = 0;
}

public String getTeamName(){
    return teamName;
}
public String getInitials(){
    return teamInitials;
}
public String getTeamMascot(){
    return mascot;
}
public int getTeamScore(){
    return teamScore;
}

public void addTeamScore(int pointsToAdd){
    teamScore += pointsToAdd;
}

@Override
public String toString(){
return teamName + "\t(" + teamInitials + ")\tPoints: " + teamScore;
}

@Override
public int compareTo(Object o){
Team other = (Team)o;
if(this.teamScore > other.getTeamScore()){
    return -1;
}else if(this.teamScore == other.getTeamScore()){
    return 0;
}else{
    return 1;
}
}


}
