package DataClasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import wrestlingtournamentcli.MatchRecord;
import wrestlingtournamentcli.Model;

/**
 * @author Jared Murphy https://github.com/murphman29
 *
 * // * ==Bracket==//Pick up here // //	=Member Variables= //
 * -roundThreeTwo:Match[16] //	-roundOneSix:Match[8] //	-roundZeroEight:Match[4]
 * //	-roundZeroFour:Match[2] //	-roundZeroTwo:Match[1] //	-winner:Wrestler //
 * -roundsNeeded:int //	-weightClass:WeightClass //	-primary:boolean //Primary
 * or Consolation bracket // //	=Methods= //	+void:Bracket(ArrayList<Wrestler>,
 * WeightClass, boolean) //	+ArrayList<Match>:makeMatches(int round) //
 * +void:advanceWrestler(int round, int matchID, String winningColor) //
 * +void:printBracket() //
 */
public class Bracket implements Serializable {

    ArrayList<ArrayList<Match>> bracket = new ArrayList();
    private ArrayList<Wrestler> wrestlerList;
    String winner;
    int roundsNeeded;
    int currentRound;
    int weightClass;
    boolean primary;

    public Bracket(ArrayList<Wrestler> wrestlerList) {
        this.wrestlerList = wrestlerList;
        this.weightClass = wrestlerList.get(0).getWeightClass();
        this.roundsNeeded = determineRounds();
        initializeBracket();
        this.currentRound = 0;
        printBracket(false);
    }

    public int determineRounds() {
        while (!bracketable(wrestlerList.size())) {
            Wrestler BYE = new Wrestler(weightClass);
            wrestlerList.add(BYE);
        }
        return twoPow(wrestlerList.size());
    }

    public int twoPow(int number) {
        int twos = 0;
        while (number != 1) {
            if (number % 2 != 0) {
                return -2;
            }
            number = number / 2;
            twos++;
        }
        return twos;
    }

    public boolean bracketable(int number) {
        if (twoPow(number) == -2) {
            return false;
        } else {
            return true;
        }
    }

    public void initializeBracket() {
        // for (int i = 0; i != roundsNeeded; i++) {
        //    bracket.add(new ArrayList());
        //}
        seedWrestlers();
        ArrayList<Match> temp = new ArrayList();
        for (int i = 0, j = wrestlerList.size() - 1; i < j; i++, j--) {
            temp.add(new Match(0, wrestlerList.get(i).getUserName(), wrestlerList.get(i).getTeamID(), wrestlerList.get(j).getUserName(),wrestlerList.get(j).getTeamID(), currentRound, weightClass));
        }
        ArrayList<Match> orgTemp = arrangeInitialMatches(temp);
        for(int i = 0; i != orgTemp.size(); i++){
          MatchRecord mr = orgTemp.get(i).generateMatchRecord(0, i);
          orgTemp.get(i).setMatchID(Model.getMatchID(mr));
        }
        bracket.add(orgTemp);
    }
    
    public void nextRound(){
        ArrayList<Match> nextRound = new ArrayList();
        for(int i = 0; i != bracket.get(bracket.size()-1).size(); i+=2){
            Match m = new Match(0,bracket.get(bracket.size()-1).get(i).getWinner(), bracket.get(bracket.size()-1).get(i).getWinnerTeam(),bracket.get(bracket.size()-1).get(i+1).getWinner(), bracket.get(bracket.size()-1).get(i+1).getWinnerTeam(),bracket.size()+1,weightClass);
            m.setMatchID(Model.getMatchID(m.generateMatchRecord(bracket.size(),i/2)));
            nextRound.add(m);
        }
        bracket.add(nextRound);
    }
    
    public void updateMatch(int roundID, int pos, String winningColor, int greenPoints, int redPoints, int fallType, String fallTime){
        bracket.get(roundID).get(pos).updateMatch(fallType, winningColor, greenPoints, redPoints, fallTime);
    }

    public void seedWrestlers() {
        Collections.sort(wrestlerList);
        for (int i = 0; i != wrestlerList.size(); i++) {
            if (wrestlerList.get(i).getSeed() == 100) {
                wrestlerList.get(i).setSeed(i);//Update Locally
                if (!wrestlerList.get(i).getUserName().contains("BYE")) {
                    Model.updateWrestlerSeed(wrestlerList.get(i).getUserName(), i);//Update Model
                }
            }
        }
    }

    public ArrayList<Match> arrangeInitialMatches(ArrayList<Match> matchList) {
        int[] guide = expand(roundsNeeded - 1, new int[]{1});
        for (int i = 0; i != guide.length; i++) {//Decrement each section in the guide
            guide[i] = guide[i] - 1;
        }
        ArrayList<Match> returnList = new ArrayList();
        for (int i = 0; i != matchList.size(); i++) {
            returnList.add(matchList.get(guide[i]));
        }
        return returnList;
    }

    public void printBracket(boolean flag) {
        int additionalBlanks = roundsNeeded - bracket.size();
        if (flag == true) {//If the flag to print to the console is set...
            if (additionalBlanks != 0) {
                printBlanks(additionalBlanks, bracket.get(0).size());
            }
            for (int i = bracket.size() - 1; i != -1; i--) {
                printRound(bracket.get(i), i);
            }
        } else {//Else, print to a document.
            File desktop = new File(System.getProperty("user.home"), "Desktop");
            File storageArea = new File(desktop.getPath() + "\\Brackets");
            if(!storageArea.exists()){
            storageArea.mkdir();
            }
            String filename = storageArea.getPath();
            filename += "\\Class_" + weightClass + ".txt";
            File f;
            BufferedWriter bw = null;
            try {
                f = new File(filename);
                if (f.exists()) {
                    f.delete();
                }
                f.createNewFile();
                bw = new BufferedWriter(new FileWriter(f));
            } catch (Exception e) {
                System.out.println("Could not write to file: = " + filename);
            }
            if (additionalBlanks != 0) {
                writeBlanks(additionalBlanks, bracket.get(0).size(), bw);
            }
            for (int i = bracket.size() - 1; i != -1; i--) {
                writeRound(bracket.get(i), i, bw);
            }
            System.out.println(weightClass + ": " + bracket.get(0).size() + "\tWrestlers: " + wrestlerList.size());
            //System.out.println("Bracket printed successfully!");
            try {
                bw.close();
            } catch (Exception e) {
                System.out.println("Couldn't close the buffer");
            }
        }
    }

    public void printBlanks(int additionalBlanks, int totalMatches) {
        int endLevel = roundsNeeded - additionalBlanks;
        for (int i = roundsNeeded; i != endLevel; i--) {
            ArrayList<Match> ghostMatches = new ArrayList();
            int matchesNeeded = (int) Math.pow(2, (roundsNeeded - i));
            for (int j = 0; j != matchesNeeded; j++) {
                ghostMatches.add(new Match());
            }
            printRound(ghostMatches, i - 1);
        }
    }

    public void writeBlanks(int additionalBlanks, int totalMatches, BufferedWriter bw) {
        int endLevel = roundsNeeded - additionalBlanks;
        for (int i = roundsNeeded; i != endLevel; i--) {
            ArrayList<Match> ghostMatches = new ArrayList();
            int matchesNeeded = (int) Math.pow(2, (roundsNeeded - i));
            for (int j = 0; j != matchesNeeded; j++) {
                ghostMatches.add(new Match());
            }
            writeRound(ghostMatches, i - 1, bw);
        }
    }

    public void printRound(ArrayList<Match> arr, int level) {
        int[] nameSpaceMatrix = new int[]{0, 6, 18, 42, 94}; //Lvl 0, Lvl 1, Lvl 2...(It's magic)
        int nameSpace = nameSpaceMatrix[level]; //Number of spaces between each name
        int hbarNum = arr.size();//Equivalent to the number of matches being wrestled during the level
        int hbarLength = 6 * (int) Math.pow(2, level + 1) + 1; //How long the string of underscores needs to be.
        int vbarSpace = nameSpace + 4; //How much space is between the edge and the first vertical bar
        int vbarNum = 5; //Number of bars to print between the name and the next level (Number of vertical bars between levels of the bracket)
        int stdSpace = 3; //On the bottom-most level, how many spaces are between each name

        //Prints the underscores
        for (int i = 0; i != hbarNum; i++) {
            printSpaces(vbarSpace);
            printUnderscore(hbarLength);
            printSpaces(vbarSpace + stdSpace);
        }

        //End the line
        System.out.println("");

        //Print the vertical bars
        for (int i = 0; i != vbarNum; i++) {
            for (int j = 0; j != arr.size() * 2; j++) {
                printSpaces(vbarSpace);
                System.out.print("|");
                if ((vbarNum / 2 == i) && (j % 2 == 0)) {
                    printSpaces(vbarSpace);
                    String s = (arr.get(j / 2).getMatchID()) + "";
                    int spaces = (stdSpace - s.length()) / 2;
                    printSpaces(spaces);
                    System.out.print(s);
                    printSpaces(spaces);
                } else {
                    printSpaces(vbarSpace + stdSpace);
                }
            }
            System.out.println("");
        }

        //Print the wrestler names
        for (int i = 0; i != arr.size(); i++) {
            printSpaces(nameSpace);
            System.out.print(arr.get(i).getGreenWrestler());
            printSpaces(nameSpace + stdSpace + nameSpace);
            System.out.print(arr.get(i).getRedWrestler());
            printSpaces(nameSpace + stdSpace);
        }

        //End the line
        System.out.println("");
    }

    public void writeRound(ArrayList<Match> arr, int level, BufferedWriter bw) {
        int[] nameSpaceMatrix = new int[]{0, 6, 18, 42, 94}; //Lvl 0, Lvl 1, Lvl 2...(It's magic)
        int nameSpace = nameSpaceMatrix[level]; //Number of spaces between each name
        int hbarNum = arr.size();//Equivalent to the number of matches being wrestled during the level
        int hbarLength = 6 * (int) Math.pow(2, level + 1) + 1; //How long the string of underscores needs to be.
        int vbarSpace = nameSpace + 4; //How much space is between the edge and the first vertical bar
        int vbarNum = 5; //Number of bars to print between the name and the next level (Number of vertical bars between levels of the bracket)
        int stdSpace = 3; //On the bottom-most level, how many spaces are between each name

        try {
            //Prints the underscores
            for (int i = 0; i != hbarNum; i++) {
                bw.append(writeSpaces(vbarSpace));
                bw.append(writeUnderscore(hbarLength));
                bw.append(writeSpaces(vbarSpace + stdSpace));
            }

            //End the line
            bw.append("\n");

            //Print the vertical bars
            for (int i = 0; i != vbarNum; i++) {
                for (int j = 0; j != arr.size() * 2; j++) {
                    bw.append(writeSpaces(vbarSpace));
                    bw.append("|");
                    if ((vbarNum / 2 == i) && (j % 2 == 0)) {//Print the match ID, too!
                        bw.append(writeSpaces(vbarSpace));
                        String s = (arr.get(j / 2).getMatchID()) + "";
                        int spaces = (stdSpace - s.length()) / 2;
                        bw.append(writeSpaces(spaces));
                        bw.append(s);
                        if ((spaces * 2) == (stdSpace - s.length())) {
                            bw.append(writeSpaces(spaces));
                        } else {
                            bw.append(writeSpaces(spaces + 1));
                        }
                    } else {
                        bw.append(writeSpaces(vbarSpace + stdSpace));
                    }
                }
                bw.append("\n");
            }

            //Print the wrestler names
            for (int i = 0; i != arr.size(); i++) {
                bw.append(writeSpaces(nameSpace));
                bw.append(arr.get(i).getGreenWrestler());
                bw.append(writeSpaces(nameSpace + stdSpace + nameSpace));
                bw.append(arr.get(i).getRedWrestler());
                bw.append(writeSpaces(nameSpace + stdSpace));
            }

            //End the line
            bw.append("\n");
            bw.flush();
        } catch (Exception e) {
            System.out.println("Couldn't write the bracket :(");
        }
    }

    public void printSpaces(int n) {//These are just helper methods. I separated them out so I didn't have a bunch of nested for() loops in the displayArray method.
        for (int i = 0; i != n; i++) {
            System.out.print(" ");
        }
        return;
    }

    public String writeSpaces(int n) throws Exception {//These are just helper methods. I separated them out so I didn't have a bunch of nested for() loops in the displayArray method.
        String rs = "";
        for (int i = 0; i != n; i++) {
            rs += " ";
        }
        return rs;
    }

    public void printUnderscore(int n) {
        for (int i = 0; i != n; i++) {
            System.out.print("_");
        }
        return;
    }

    public String writeUnderscore(int n) throws Exception {
        String rs = "";
        for (int i = 0; i != n; i++) {
            rs += "_";
        }
        return rs;
    }

    public int[] expand(int roundsLeft, int[] prevArr) {
        if (roundsLeft == 0) {
            return prevArr;
        } else {
            int[] returnArr = new int[2 * prevArr.length];
            for (int i = 0; i != prevArr.length; i++) {
                returnArr[2 * i] = prevArr[i];
                returnArr[(2 * i) + 1] = returnArr.length + 1 - returnArr[2 * i];
            }
            return expand(roundsLeft - 1, returnArr);
        }
    }
    
    public boolean hasMatch(int matchNumber){ //Returns whether or not the bracket has the given number
    for(ArrayList<Match> round: bracket){//For every round,
        for(Match m: round){//Check every match for the matchNumber
            if(m.getMatchID() == matchNumber){
                return true;
            }
        }
    }
    return false;
    }
    
    public int[] findMatch(int matchNumber){//Returns the position of the given number [round, position]
    for(int i = 0; i != bracket.size(); i++){//For every round,
        for(int j = 0; j != bracket.get(i).size(); j++){//Check every match for the matchNumber
            if(bracket.get(i).get(j).getMatchID() == matchNumber){
                return new int[]{i,j};
            }
        }
    }
    return new int[] {-1,-1};//Return [-1,-1] if the match doesn't exist here.
    }
    
    
}
