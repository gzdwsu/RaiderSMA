package wrestlingtournamentcli;

import DataClasses.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Jared Murphy https://github.com/murphman29
 * 
 * @author Cody Francis
 * 
 * Edited 01/2020 
 * Editor: Nathan Berry https://github.com/nathan-berry
 *
 * ==Model==
 *
 * =Member Variables= -wrestlerList:ArrayList<Wrestler>
 * -CreatedMatches:ArrayList<Match>
 * -MatchesInProgress:ArrayList<Match>
 * -FinishedMatches:ArrayList<Match>
 * -weightClasses:ArrayList<WeightClass>
 * -tournamentRound:int -numberOfMats:int
 * -matchesByMat:ArrayList<ArrayList<Match>> -teamList:ArrayList<Team>
 *
 * =Methods= 
 * +ArrayList<Match>:getMatchesByRound(int round)
 * +ArrayList<Match>:getMatchesByWrestler(String wrestlerName)
 * +ArrayList<Match>:getMatchesByWrestler(Wrestler wrestler)
 * +void:importWrestlersFromText(String filePath)
 * +void:importWrestlersFromExcel(String filePheath)
 * -Wrestler:wrestlerFactory(String wrestlerInfo)
 * +void:importTeamsFromText(String filePath) 
 * +void:importTeamsFromExcel(String filePath) 
 * -String:separateArgumentsFromString(String largeString)
 * +void:initializeWeightClasses() 
 * +int[matNumber]:getMatchesByMat()
 * +int:getLeastUsedMat()
 */










public class Model {

	/*******************   Class Variables   *************************/
    private static String tournamentName;	//String to hold Tournament Name
    
    //ArrayLists for Teams, Wrestlers, Weight Classes, Brackets, and Matches for Tournament
    private static ArrayList<Team> teamList;
    private static ArrayList<Wrestler> wrestlerList;
    private static ArrayList<Integer> weightClasses;
    private static ArrayList<Bracket> bracketList;
    private static ArrayList<MatchRecord> matchBank;
    private static ArrayList<SoccerPlayer> soccerPlayerList;
    private static int matches;
    private static int currentMatchID = 0;

    
    /*******************   Constructor   *****************************/
    public Model(String sport) {

    	if(sport.contentEquals("wrestling")) {
	    	this.teamList = new ArrayList();
	        this.wrestlerList = new ArrayList();
	        this.bracketList = new ArrayList();
	        this.matchBank = new ArrayList();
	        this.teamList.add(new Team("BYE", "", ""));
	        initializeWeightClasses();
	        matches = 0;
    	}
    	else if(sport.contentEquals("soccer")) {
    		this.teamList = new ArrayList();
            this.soccerPlayerList= new ArrayList();
            this.teamList.add(new Team("BYE", "", ""));
    	}
    }

    
    /*******************   Class Methods   ****************************/
    
    /**   Getters and Setters  **/
    //TournamentName Setter
    public static void setTournamentName(String name){
        tournamentName = name;
   }
    
    
    //Getter for matches
    public static int getMatchID(MatchRecord mr) {//Trades a MatchRecord for a MatchNumber
        matchBank.add(mr);
        matches++;
        return matches;
    }
    
    
    
    
   /** Other Methods 
 * @return **/
    //Tournament Generator
    public static int generateTournament() {
    	
    	//Returns to caller if no Wrestlers exist, or there exists only 1 team (not enough for a tournament)
        if (wrestlerList.size() == 0 || teamList.size() == 1) {
            System.out.println("Error: No Wrestlers or Teams Found");
            System.out.println("Please add wrestlers/teams before generating a tournament.");
            return 0;  
        }
        
        //Bracket has elements in it, so a current Tournament is in program, asks if user wishes to clear or abort
        else if(bracketList.size() != 0){
            System.out.println("Are you sure you want to restart this tournament?\ny=yes n=abort");
            Scanner s = new Scanner(System.in);
            
            //Clear current bracket
            if(s.next().equals("y")){
                System.out.println("Clearing old tournament and making another...");
            }
            
            //Do not clear current bracket
            else{
                System.out.println("Operation aborted.");
                return 1;
            }
               
            bracketList.clear();
        }
        
        //Creates a Bracket based off of each wrestlers weightclass
        Collections.sort(wrestlerList);
        ArrayList<Wrestler> temp = new ArrayList();
        int currentClass = 0;
        for (Wrestler w : wrestlerList) {
            if (w.getWeightClass() == currentClass) {
                temp.add(w);
            } 
            else {
                if (temp.size() != 0) {
                    bracketList.add(new Bracket(temp));
                    temp.clear();
                    currentClass = w.getWeightClass();
                    temp.add(w);
                } 
                else {
                    currentClass = w.getWeightClass();
                    temp.add(w);
                }
            }
        }
        
        if (temp.size() != 0) {
            bracketList.add(new Bracket(temp));
        }
		return 2;
    }
    
    

    
    //Advance Tournament to next round
    public static void advanceTournament(){
	    for(Bracket b: bracketList){
	    	
	    	//calls nextRound() found in Bracket.java
	        b.nextRound();
	    }
    }
    
  
    
    
    
   //NO-argument Tournament Save Method
   public static void saveTournament(){
       if(tournamentName != null && tournamentName.length() > 1){
    	   
    	 //Using whatever the tournaments name is, call saveTournament(String) function found within class
         saveTournament(tournamentName);
       }
       else{
        System.out.println("Error: Tournament not saved because no name was found.\n"
                + "Please use the command: SAVE %tournamentName% to set the name and save,\n"
                + "or NAME %tournamentName% to set the name of the tournament.");
       }
   }
   
   
   
   
   //Tournament Save to File Method
    public static void saveTournament(String baseName) {
        try {
        	
        	//Creating file directories for the entire tournament, saved to Desktop in Saved Tournaments folder
        	
            File desktop = new File(System.getProperty("user.home"), "Desktop");
            File storageArea = new File(desktop.getPath() + "\\Saved Tournaments");
            
            //If the directory does not exist yet, create it
            if(!storageArea.exists()){
            storageArea.mkdir();
            }
            
            //Create path for Wrestlers, Teams, Brackets, Matches, and Settings bin files, all with the added
            // prefix of the current tournaments name
            File wrestlers = new File(storageArea.getPath() + "\\" + baseName + "_wrestlers.bin");
            File teams = new File(storageArea.getPath() + "\\" + baseName + "_teams.bin");
            File brackets = new File(storageArea.getPath() + "\\" + baseName + "_brackets.bin");
            File matches = new File(storageArea.getPath() + "\\" + baseName + "_matches.bin");
            File settings = new File(storageArea.getPath() + "\\" + baseName + "_settings.bin");
            
            //If paths to these bin files already exist with these names, delete them
            if (wrestlers.exists()) {
                wrestlers.delete();
            }
            if (teams.exists()) {
                teams.delete();
            }
            if (brackets.exists()) {
                brackets.delete();
            }
            if (matches.exists()) {
                brackets.delete();
            }
            if (settings.exists()) {
                settings.delete();
            }
            
            //If previous file existed, or never did, new files are cleared to be created
            wrestlers.createNewFile();
            teams.createNewFile();
            brackets.createNewFile();
            matches.createNewFile();
            settings.createNewFile();
            
            //Creates objects that will contain each separate classes information
            ObjectOutputStream os_wrestlers = new ObjectOutputStream(new FileOutputStream(wrestlers));
            ObjectOutputStream os_teams = new ObjectOutputStream(new FileOutputStream(teams));
            ObjectOutputStream os_brackets = new ObjectOutputStream(new FileOutputStream(brackets));
            ObjectOutputStream os_matchList = new ObjectOutputStream(new FileOutputStream(matches));
            
            //Writes objects to respective FileOutputStream locations
            os_wrestlers.writeObject(wrestlerList);
            os_teams.writeObject(teamList);
            os_brackets.writeObject(bracketList);
            os_matchList.writeObject(matchBank);
            
            //Flushing to force each ObjectOutputStream object to push the intended FileOutputStream objects
            // to go to their intended places with all of their data
            os_wrestlers.flush();
            os_teams.flush();
            os_brackets.flush();
            os_matchList.flush();
            
            //Closing ObjectOutputStreams to ensure saving
            os_wrestlers.close();
            os_teams.close();
            os_brackets.close();
            os_matchList.close();
            
            //Go to writeSettings(File) method within this class
            writeSettings(settings);
            
            System.out.println("Saved this many objects:\n\tWrestlers: " + wrestlerList.size() + "\n\tTeams: " + teamList.size() + "\n\tBrackets: "+ bracketList.size());
            
        } catch (Exception e) {
            System.out.println("Error saving tournament!");
        }
    }
    
    
    
    //Tournament Load Method
    public static void loadTournament(String baseName) {
        try {
        	
        	//Find file directories that should exist for each object
            File desktop = new File(System.getProperty("user.home"), "Desktop");
            File storageArea = new File(desktop.getPath() + "\\Saved Tournaments");
            File wrestlers = new File(storageArea.getPath() + "\\" + baseName + "_wrestlers.bin");
            File teams = new File(storageArea.getPath() + "\\" + baseName + "_teams.bin");
            File brackets = new File(storageArea.getPath() + "\\" + baseName + "_brackets.bin");
            File matches = new File(storageArea.getPath() + "\\" + baseName + "_matches.bin");
            File settings = new File(storageArea.getPath() + "\\" + baseName + "_settings.bin");
            
            //Load ObjectInputStream object to load each needed object from FileInputStreams
            ObjectInputStream is_wrestlers = new ObjectInputStream(new FileInputStream(wrestlers));
            ObjectInputStream is_teams = new ObjectInputStream(new FileInputStream(teams));
            ObjectInputStream is_brackets = new ObjectInputStream(new FileInputStream(brackets));
            ObjectInputStream is_matches = new ObjectInputStream(new FileInputStream(matches));
            
            //Read each object from files, loading member variables with data
            wrestlerList = (ArrayList<Wrestler>) is_wrestlers.readObject();
            teamList = (ArrayList<Team>) is_teams.readObject(); 
            bracketList =  (ArrayList<Bracket>) is_brackets.readObject();
            matchBank = (ArrayList<MatchRecord>) is_matches.readObject();
            
            //Close each ObjectInputStream to save data added to member variables, and ensure accessed files remain intact
            is_wrestlers.close();
            is_teams.close();
            is_brackets.close();
            is_matches.close();
            
            
            //loadSettings(settings); No settings to be included at this time
            System.out.println("Loaded this many objects:\n\tWrestlers: " + wrestlerList.size() + "\n\tTeams: " + teamList.size() + "\n\tBrackets: "+ bracketList.size());
        } 
        
        //If any exceptions are thrown, cancel loadTournament, and display message
        catch (Exception e) {
            System.out.println("Error loading tournament!");
        }
    }
    
    
    
    
    
    //Write Settings Method
    public static void writeSettings(File settings) throws Exception {
    	
    	//Using File from saveTournament for settings, create BufferedWriter
        BufferedWriter bw = new BufferedWriter(new FileWriter(settings));
        
        //Add matches value to file
        bw.append("Match_Count: " + matches);
        
        //Flushing to force values to the intended file
        bw.flush();
        
        //Closing BufferedWriter so data is saved.
        bw.close();
    }

    public static int getCurrentMatchID() {
    	return currentMatchID;
    }

    public static void setCurrentMatchID() {
    	currentMatchID += 1;
    }
    
    public static void updateMatch(String winningColor, int greenPoints, int redPoints, int fallType, String fallTime){ 
    	
        int[] location = matchBank.get(currentMatchID).getLocation();

        matchBank.get(currentMatchID).matchInRound++;//increment both match in round and round number
        //matchBank.get(currentMatchID).roundNumber++;
        bracketList.get(location[0]).updateMatch(location[1],location[2],winningColor, greenPoints, redPoints, fallType, fallTime);
        advanceTournament();//added a call to advance tournament. this should be done automatically for every match updated 

    }
    //Takes in a wrestler's name and returns a nine character username
    //Ideally, it will use 6 from the lastname and 3 from the firstname.
    //If lastName.length < 6, it will switch over to the firstName early
    //If the firstName cannot fill the remaining characters, it will pad the end with " "
    public static String getUserName(String lastName, String firstName) {
        String returnValue = "";
        if (lastName == null || firstName == null) {
            return returnValue;
        } else {
            if (lastName.length() >= 6) {
                returnValue += lastName.substring(0, 6);
            } else {
                returnValue += lastName.substring(0, lastName.length());
            }
            int charRemaining = 9 - returnValue.length();
            for (int i = 0; charRemaining != 0; i++, charRemaining--) {
                if (i < firstName.length()) {
                    returnValue += firstName.charAt(i);
                } else {
                    returnValue += " ";
                }
            }
        }
        return returnValue;
    }

    
    
    
    
    
    //Add Team Points Method
    public static void addTeamPoints(String teamAlias, int points) {
        try {
        	
        	//Using the string alias, find if the team exists within Teams ArrayList teamList
            Team t = teamLookup(teamAlias);
            
            //Grab index of said team within teamList
            int pos = teamList.indexOf(t);
            
            //Give team at alias' position points
            teamList.get(pos).addTeamScore(points);
        } 
        
        //If any exceptions are thrown, display message
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    
    
    
    
    //Update Wrestler Match Record
    public static void updateWrestlerMatchRecord(String alias, boolean win) {
        try {
        	
        	//Using the string alias, find if the wrestler exists within Wrester ArrayList wrestlerList
            Wrestler w = wrestlerLookup(alias);
            
            //Grab index of said wrestler within wrestlerList
            int pos = wrestlerList.indexOf(w);
            
            //Send boolean value win to alias wrestler for calculation
            wrestlerList.get(pos).addMatch(win);
        } 
        
        //If any exceptions are thrown, display message
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    
    
    
    //Update Wrestler Seed
    public static void updateWrestlerSeed(String alias, int seed) {
        try {
        	
        	//Using the string alias, find if the wrestler exists within Wrester ArrayList wrestlerList
            Wrestler w = wrestlerLookup(alias);
            
            //Grab index of said wrestler within wrestlerList
            int pos = wrestlerList.indexOf(w);
            
            //Set new seed value for alias wrestler
            wrestlerList.get(pos).setSeed(seed);
        } 
        
        //If any exceptions are thrown, display message
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    
    
    
    //Team Lookup Method
    public static Team teamLookup(String alias) throws NotFoundException {
    	
    	//Change alias string to all upper case, to eliminate case sensitive entry
        alias = alias.toUpperCase();
        
        //Return team if the alias entered was either the teams name, mascot, or initials
        for (Team team : teamList) {
            if (team.getTeamName().contains(alias)) {
                return team;
            } else if (team.getTeamMascot().contains(alias)) {
                return team;
            } else if (team.getInitials().contains(alias)) {
                return team;
            }
        }
        
        //Throw an exception if the requested alias team is not found
        throw new NotFoundException(alias);
    }
    
    
    
    
    
    
    //Wrestler Lookup String
    public static Wrestler wrestlerLookup(String alias) throws NotFoundException {
    	
    	//Change alias string to all upper case, to eliminate case sensitive entry
        alias = alias.toUpperCase();
        
        //Return wrestler if alias matches wrestlers first name, last name, or user name
        for (Wrestler w : wrestlerList) {
            if (w.getLastName().contains(alias)) {
                return w;
            } else if (w.getFirstName().contains(alias)) {
                return w;
            } else if (w.getUserName().contains(alias)) {
                return w;
            }
        }
        
        //Throw an exception if the requested alias wrestler is not found
        throw new NotFoundException(alias);
    }

    
    public static SoccerPlayer soccerPlayerLookup(String alias) throws NotFoundException {
        alias = alias.toUpperCase();
        for (SoccerPlayer w : soccerPlayerList) {
            if (w.getLastName().contains(alias)) {
                return w;
            } else if (w.getFirstName().contains(alias)) {
                return w;
            } else if (w.getUserName().contains(alias)) {
                return w;
            }
        }
        throw new NotFoundException(alias);
    }
    
    
    
    //Import Wrestlers From Text Method
    public static int importWrestlersFromText(String filePath) {
    	
        File file;				//File object which will use the filepath string
        Scanner s = null;		//Scanner for reading file
        int importCount = 0;	//counter
        
        //Try to find and open the file at the filepath
        try {
            file = new File(filePath);
            s = new Scanner(file);
        } catch (Exception e) {
            System.out.println("The file could not be found/opened.");
            return 0;
        }
        
        //While the Scanner is reading each line...
        while (s.hasNextLine()) {
            try {
            	
            	//Convert line to string
                String line = s.nextLine();
                
                //Use string to construct a wrestler
                Wrestler w = wrestlerFactory(line);
                
                //Add the wrestler to the wrestlerList
                wrestlerList.add(w);
                
                //increment counter
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " wrestlers.");
		return 1;
    }

    
    public static void importSoccerPlayersFromText(String filePath) {
        File file;
        Scanner s = null;
        int importCount = 0;
        try {
            file = new File(filePath);
            s = new Scanner(file);
        } catch (Exception e) {
            System.out.println("The file could not be found/opened.");
            return;
        }
        while (s.hasNextLine()) {
            try {
                String line = s.nextLine();
                SoccerPlayer w = soccerPlayerFactory(line);
                soccerPlayerList.add(w);
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " soccer players.");
    }
    
    
    
    //Import Teams From Text Method
    public static void importTeamsFromText(String filePath) {
    	
    	
    	File file;				//File object which will use the filepath string
        Scanner s = null;		//Scanner for reading file
        int importCount = 0;	//counter
        
        //Try to find and open the file at the filepath
        try {
            file = new File(filePath);
            s = new Scanner(file);
        } catch (Exception e) {
            System.out.println("The file could not be found/opened.");
            return;
        }
        
        //While Scanner is reading each line...
        while (s.hasNextLine()) {
            try {
            	
            	//Create null Team object
                Team t = null;
                
                //Scanner reads in dataline into String line
                String line = s.nextLine();
                
                //New Scanner for the String line
                Scanner ls = new Scanner(line);
                
                //Create a new ArrayList of Strings
                ArrayList<String> tokens = new ArrayList();
                String token = "";
                
                //Using a token, break apart line at specified placed to add information to Team's 3 possible fields
                for (int i = 0; ls.hasNext(); i++) { //For the three fields possible
                    while (ls.hasNext()) {
                        token += ls.next();
                        if (token.charAt(token.length() - 1) == ',') {
                            tokens.add(token.substring(0, token.length() - 1));
                            token = "";
                            break;
                        } else {
                            token += " ";
                        }
                    }
                }
                
                //Each team will have at least a name and initials
                if (tokens.size() == 2) {
                    t = new Team(tokens.get(0), tokens.get(1));
                } 
                
                //Some teams will have a name, initials, and a Mascot
                else {
                    t = new Team(tokens.get(0), tokens.get(1), tokens.get(2));
                }
                
                //add the Team to teamList
                teamList.add(t);
                
                //Increment importCount, which tracks the number of Teams added to teamList
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " teams.");
    }

    
    
    
    
    
    //Wrestler Factory Method
    private static Wrestler wrestlerFactory(String wrestlerInfo) throws Exception {
        try {
        	
        	//Uses the string found in importWrestlersFromText method
        	//Scanner reads from whitespace-to-whitespace
            Scanner s = new Scanner(wrestlerInfo);
            
            //Read in each value for Wrestler member variables
            String fn = s.next();				
            String ln = s.next();
            String teamAlias = s.next();
            int grade = s.nextInt();
            int weightClass = s.nextInt();
            int wins = s.nextInt();
            int matches = s.nextInt();
            
            //Create a wrestler from the above read in values
            Wrestler w = new Wrestler(fn, ln, teamAlias, grade, weightClass, wins, matches);
            
            //Return Wrestler to importWrestlerFromText method
            return w;
            
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new IncorrectFormatException(wrestlerInfo);
        }
    }

    
    private static SoccerPlayer soccerPlayerFactory(String soccerPlayerInfo) throws Exception {
        try {
            Scanner s = new Scanner(soccerPlayerInfo);
            String fn = s.next();
            String ln = s.next();
            String teamAlias = s.next();
            String position = s.next();
            double gpg = s.nextDouble();
            double apg = s.nextDouble();
            double save = s.nextDouble();
            SoccerPlayer w = new SoccerPlayer(fn, ln, teamAlias, position, gpg, apg, save);
            return w;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new IncorrectFormatException(soccerPlayerInfo);
        }
    }
    
    //Print Wrestlers Function
    public static ArrayList<Wrestler> printWrestlers() {
    	
    	//Sort the wrestlers
        Collections.sort(wrestlerList);
        
        //Print each wrestlers toString() method
        System.out.println("List of Wrestlers: ");
        for (int i = 0; i != wrestlerList.size(); i++) {
            System.out.println(wrestlerList.get(i));
        }
		return wrestlerList;
    }

    
    public static void printSoccerPlayers() {
        System.out.println("List of Soccer Players: ");
        for (int i = 0; i != soccerPlayerList.size(); i++) {
            System.out.println(soccerPlayerList.get(i));
        }
    }
    
    
    public static ArrayList<Team> printTeams() {
    	
    	//Sort the teams
        Collections.sort(teamList);
        System.out.println("List of Teams: ");
        
        //Print each teams toString() method
        for (int i = 0; i != teamList.size(); i++) {
            System.out.println(teamList.get(i));
        }
		return teamList;
    }

    
    
    
    
    public static void printWrestlerInformation(String alias) {
        try {
        	
        	//use alias string to find requested wrestler
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            StringBuffer rs = wrestlerList.get(pos).getLongString();
            
            //Print the LongString representation of the alias wrestler
            System.out.println(rs);
        }
        
        //If wrestler not found, throw exception
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void printSoccerPlayerInformation(String alias) {
        try {
            SoccerPlayer w = soccerPlayerLookup(alias);
            int pos = soccerPlayerList.indexOf(w);
            String rs = soccerPlayerList.get(pos).getLongString();
            System.out.println(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    public static void compareWrestlersInformation(String alias) {
    	try {
    		String[] tempArray;
    		tempArray = alias.split(",");
    		Wrestler w1 = wrestlerLookup(tempArray[0]);
    		Wrestler w2 = wrestlerLookup(tempArray[1]);
    		
    		System.out.println("\n\t" + "Guide: " + w1.getFirstName() + " || " + w2.getFirstName());
    		System.out.println("Name: " + w1.getLastName() + ", " + w1.getFirstName() + " || " +  w2.getLastName() + ", " + w2.getFirstName());
    		System.out.println("Username: " + w1.getUserName() + " || " +  w2.getUserName());
    		System.out.println("Weight Class: " + w1.getWeightClass() + " || " +  w2.getWeightClass());
    		System.out.println("Team Name: " + w1.getTeamID() + " || " +  w2.getTeamID());
    		System.out.println("Seed: " + w1.getSeed() + " || " +  w2.getSeed());
    		System.out.println("Rating: " + w1.getRating() + " || " +  w2.getRating() + "\n");
    		
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }

    
    
    
    
    //Initialize Weight Classes
    private void initializeWeightClasses() {
        weightClasses = new ArrayList();
        weightClasses.add(106);
        weightClasses.add(113);
        weightClasses.add(120);
        weightClasses.add(126);
        weightClasses.add(132);
        weightClasses.add(138);
        weightClasses.add(145);
        weightClasses.add(152);
        weightClasses.add(160);
        weightClasses.add(170);
        weightClasses.add(182);
        weightClasses.add(195);
        weightClasses.add(220);
        weightClasses.add(285);
        Collections.sort(weightClasses);
    }
    
    
    
    
    
    //Get Weight Class Position Method
    public static int getWeightClassPosition(int weightClass){
    	
    	//Go through weightClasses ArrayList, and if the value at that position is equal to requested value
    	// then return the position of weightClasses in which they are equal
        for(int i = 0; i != weightClasses.size();i++){
          if(weightClasses.get(i) == weightClass){
              return i;
          }
        }
        
        //If no position of weightClasses was returned, return -1 (indicating that it does not exist within weightClass)
        return -1;
    }

    
    
    
    
    
    //Verify Weight Class Method
    public static boolean verifyWeightClass(int weightClass) {
    	
    	//Check weightClasses ArrayList to see if it contains the requested Weight Class
        return weightClasses.contains(weightClass);
    }
    
    
    
    
}
