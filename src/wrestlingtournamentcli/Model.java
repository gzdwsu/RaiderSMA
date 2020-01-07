package wrestlingtournamentcli;

import DataClasses.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import wrestlingtournamentcli.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Jared Murphy https://github.com/murphman29
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
 * =Methods= +ArrayList<Match>:getMatchesByRound(int round)
 * +ArrayList<Match>:getMatchesByWrestler(String wrestlerName)
 * +ArrayList<Match>:getMatchesByWrestler(Wrestler wrestler)
 * +void:importWrestlersFromText(String filePath)
 * +void:importWrestlersFromExcel(String filePath)
 * -Wrestler:wrestlerFactory(String wrestlerInfo)
 * +void:importTeamsFromText(String filePath) +void:importTeamsFromExcel(String
 * filePath) -String:separateArgumentsFromString(String largeString)
 * +void:initializeWeightClasses() +int[matNumber]:getMatchesByMat()
 * +int:getLeastUsedMat()
 */
public class Model {

    private static String tournamentName;
    private static ArrayList<Team> teamList;
    private static ArrayList<Wrestler> wrestlerList;
    private static ArrayList<Integer> weightClasses;
    private static ArrayList<Bracket> bracketList;
    private static ArrayList<MatchRecord> matchBank;
    private static int matches;

    public Model() {
        this.teamList = new ArrayList();
        this.wrestlerList = new ArrayList();
        this.bracketList = new ArrayList();
        this.matchBank = new ArrayList();
        this.teamList.add(new Team("BYE", "", ""));
        initializeWeightClasses();
        matches = 0;
    }

    public static void generateTournament() {
        if (wrestlerList.size() == 0 || teamList.size() == 1) {
            System.out.println("Error: No Wrestlers or Teams Found");
            System.out.println("Please add wrestlers/teams before generating a tournament.");
            return;
        }else if(bracketList.size() != 0){
            System.out.println("Are you sure you want to restart this tournament?\ny=yes n=abort");
            Scanner s = new Scanner(System.in);
            if(s.next().equals("y")){
                System.out.println("Clearing old tournament and making another...");
            }else{
                System.out.println("Operation aborted.");
                return;
            }
               
            bracketList.clear();
        }
        Collections.sort(wrestlerList);
        ArrayList<Wrestler> temp = new ArrayList();
        int currentClass = 0;
        for (Wrestler w : wrestlerList) {
            if (w.getWeightClass() == currentClass) {
                temp.add(w);
            } else {
                if (temp.size() != 0) {
                    bracketList.add(new Bracket(temp));
                    temp.clear();
                    currentClass = w.getWeightClass();
                    temp.add(w);
                } else {
                    currentClass = w.getWeightClass();
                    temp.add(w);
                }
            }
        }
        if (temp.size() != 0) {
            bracketList.add(new Bracket(temp));
        }
    }
    
    public static void advanceTournament(){
    for(Bracket b: bracketList){
        b.nextRound();
    }
    }
    
    public static void setTournamentName(String name){
         tournamentName = name;
    }
    
   public static void saveTournament(){
       if(tournamentName != null && tournamentName.length() > 1){
         saveTournament(tournamentName);
       }else{
        System.out.println("Error: Tournament not saved because no name was found.\n"
                + "Please use the command: SAVE %tournamentName% to set the name and save,\n"
                + "or NAME %tournamentName% to set the name of the tournament.");
       }
   }

    public static void saveTournament(String baseName) {
        try {
            File desktop = new File(System.getProperty("user.home"), "Desktop");
            File storageArea = new File(desktop.getPath() + "\\Saved Tournaments");
            if(!storageArea.exists()){
            storageArea.mkdir();
            }
            File wrestlers = new File(storageArea.getPath() + "\\" + baseName + "_wrestlers.bin");
            File teams = new File(storageArea.getPath() + "\\" + baseName + "_teams.bin");
            File brackets = new File(storageArea.getPath() + "\\" + baseName + "_brackets.bin");
            File matches = new File(storageArea.getPath() + "\\" + baseName + "_matches.bin");
            File settings = new File(storageArea.getPath() + "\\" + baseName + "_settings.bin");
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
            wrestlers.createNewFile();
            teams.createNewFile();
            brackets.createNewFile();
            matches.createNewFile();
            settings.createNewFile();
            ObjectOutputStream os_wrestlers = new ObjectOutputStream(new FileOutputStream(wrestlers));
            ObjectOutputStream os_teams = new ObjectOutputStream(new FileOutputStream(teams));
            ObjectOutputStream os_brackets = new ObjectOutputStream(new FileOutputStream(brackets));
            ObjectOutputStream os_matchList = new ObjectOutputStream(new FileOutputStream(matches));
            os_wrestlers.writeObject(wrestlerList);
            os_teams.writeObject(teamList);
            os_brackets.writeObject(bracketList);
            os_matchList.writeObject(matchBank);
            os_wrestlers.flush();
            os_teams.flush();
            os_brackets.flush();
            os_matchList.flush();
            os_wrestlers.close();
            os_teams.close();
            os_brackets.close();
            os_matchList.close();
            writeSettings(settings);
            System.out.println("Saved this many objects:\n\tWrestlers: " + wrestlerList.size() + "\n\tTeams: " + teamList.size() + "\n\tBrackets: "+ bracketList.size());
        } catch (Exception e) {
            System.out.println("Error saving tournament!");
        }
    }
    
        public static void loadTournament(String baseName) {
        try {
            File desktop = new File(System.getProperty("user.home"), "Desktop");
            File storageArea = new File(desktop.getPath() + "\\Saved Tournaments");
            File wrestlers = new File(storageArea.getPath() + "\\" + baseName + "_wrestlers.bin");
            File teams = new File(storageArea.getPath() + "\\" + baseName + "_teams.bin");
            File brackets = new File(storageArea.getPath() + "\\" + baseName + "_brackets.bin");
            File matches = new File(storageArea.getPath() + "\\" + baseName + "_matches.bin");
            File settings = new File(storageArea.getPath() + "\\" + baseName + "_settings.bin");
            ObjectInputStream is_wrestlers = new ObjectInputStream(new FileInputStream(wrestlers));
            ObjectInputStream is_teams = new ObjectInputStream(new FileInputStream(teams));
            ObjectInputStream is_brackets = new ObjectInputStream(new FileInputStream(brackets));
            ObjectInputStream is_matches = new ObjectInputStream(new FileInputStream(matches));
            wrestlerList = (ArrayList<Wrestler>) is_wrestlers.readObject();
            teamList = (ArrayList<Team>) is_teams.readObject(); 
            bracketList =  (ArrayList<Bracket>) is_brackets.readObject();
            matchBank = (ArrayList<MatchRecord>) is_matches.readObject();
            is_wrestlers.close();
            is_teams.close();
            is_brackets.close();
            is_matches.close();
            //loadSettings(settings); No settings to be included at this time
            System.out.println("Loaded this many objects:\n\tWrestlers: " + wrestlerList.size() + "\n\tTeams: " + teamList.size() + "\n\tBrackets: "+ bracketList.size());
        } catch (Exception e) {
            System.out.println("Error loading tournament!");
        }
    }
    
    

    public static void writeSettings(File settings) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(settings));
        bw.append("Match_Count: " + matches);
        bw.flush();
        bw.close();
    }

    public static int getMatchID(MatchRecord mr) {//Trades a MatchRecord for a MatchNumber
        matchBank.add(mr);
        matches++;
        return matches;
    }
    
    public static void updateMatch(int matchID, String winningColor, int greenPoints, int redPoints, int fallType, String fallTime){
        int[] location = matchBank.get(matchID-1).getLocation();
        bracketList.get(location[0]).updateMatch(location[1],location[2],winningColor, greenPoints, redPoints, fallType, fallTime);
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

    public static void addTeamPoints(String teamAlias, int points) {
        try {
            Team t = teamLookup(teamAlias);
            int pos = teamList.indexOf(t);
            teamList.get(pos).addTeamScore(points);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateWrestlerMatchRecord(String alias, boolean win) {
        try {
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            wrestlerList.get(pos).addMatch(win);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateWrestlerSeed(String alias, int seed) {
        try {
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            wrestlerList.get(pos).setSeed(seed);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Team teamLookup(String alias) throws NotFoundException {
        alias = alias.toUpperCase();
        for (Team team : teamList) {
            if (team.getTeamName().contains(alias)) {
                return team;
            } else if (team.getTeamMascot().contains(alias)) {
                return team;
            } else if (team.getInitials().contains(alias)) {
                return team;
            }
        }
        throw new NotFoundException(alias);
    }

    public static Wrestler wrestlerLookup(String alias) throws NotFoundException {
        alias = alias.toUpperCase();
        for (Wrestler w : wrestlerList) {
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

    public static void importWrestlersFromText(String filePath) {
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
                Wrestler w = wrestlerFactory(line);
                wrestlerList.add(w);
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " wrestlers.");
    }

    public static void importTeamsFromText(String filePath) {
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
                Team t = null;
                String line = s.nextLine();
                Scanner ls = new Scanner(line);
                ArrayList<String> tokens = new ArrayList();
                String token = "";
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
                if (tokens.size() == 2) {
                    t = new Team(tokens.get(0), tokens.get(1));
                } else {
                    t = new Team(tokens.get(0), tokens.get(1), tokens.get(2));
                }
                teamList.add(t);
                importCount++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successfully imported " + importCount + " teams.");
    }

    private static Wrestler wrestlerFactory(String wrestlerInfo) throws Exception {
        try {
            Scanner s = new Scanner(wrestlerInfo);
            String fn = s.next();
            String ln = s.next();
            String teamAlias = s.next();
            int grade = s.nextInt();
            int weightClass = s.nextInt();
            int wins = s.nextInt();
            int matches = s.nextInt();
            Wrestler w = new Wrestler(fn, ln, teamAlias, grade, weightClass, wins, matches);
            return w;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new IncorrectFormatException(wrestlerInfo);
        }
    }

    public static void printWrestlers() {
        Collections.sort(wrestlerList);
        System.out.println("List of Wrestlers: ");
        for (int i = 0; i != wrestlerList.size(); i++) {
            System.out.println(wrestlerList.get(i));
        }
    }

    public static void printTeams() {
        Collections.sort(teamList);
        System.out.println("List of Teams: ");
        for (int i = 0; i != teamList.size(); i++) {
            System.out.println(teamList.get(i));
        }
    }

    public static void printWrestlerInformation(String alias) {
        try {
            Wrestler w = wrestlerLookup(alias);
            int pos = wrestlerList.indexOf(w);
            String rs = wrestlerList.get(pos).getLongString();
            System.out.println(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
    
    public static int getWeightClassPosition(int weightClass){
        for(int i = 0; i != weightClasses.size();i++){
          if(weightClasses.get(i) == weightClass){
              return i;
          }
        }
        return -1;//Didn't find it
    }

    public static boolean verifyWeightClass(int weightClass) {
        return weightClasses.contains(weightClass);
    }

}
