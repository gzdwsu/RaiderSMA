
package wrestlingtournamentcli;
import DataClasses.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Jared Murphy
 */
public class Main {

    public static void main(String[] args) {
    Model m = new Model();
    Scanner s = new Scanner(System.in);
    System.out.println("Welcome to the Murphy Wrestling Tournament Manager. For available commands, please type 'help'");
    
    while(true){
    	
    System.out.println("\nInput your next command!");
    String input = s.nextLine();
    try{
    ArrayList<String> arguments = new ArrayList();
    Scanner argScanner = new Scanner(input);
    while(argScanner.hasNext()){
        arguments.add(argScanner.next());
    }
    processInput(arguments);
    }catch(Exception e){
        System.out.println("Sorry! The command '" + input + "' either wasn't recognized or experienced an error.");
        System.out.println(e.getMessage());
    }
    }
    }
    
    public static void processInput(ArrayList<String> args) throws Exception{
    args.set(0, args.get(0).toUpperCase());
    switch(args.size()){
            case 0:
                throw new BadCommandException();
            case 1: //Single-Command expressions
                switch(args.get(0)){
                    case "SAVE":
                        Model.saveTournament();
                        return;
                    case "START":
                        Model.generateTournament();
                        return;
                    case "VIEW-TEAMS":
                        Model.printTeams();
                        return;
                    case "VIEW-WRESTLERS":
                        Model.printWrestlers();
                        return;
                    case "HELP":
                        printHelp();
                        return;
                    default:
                        throw new BadCommandException();
                }
            case 2: //Command-Param Expressions
                switch(args.get(0)){
                    case "LOAD":
                        Model.loadTournament(args.get(1));
                        return;
                    case "SAVE":
                        Model.saveTournament(args.get(1));
                        return;
                    case "IMPORT-TEAMS":
                        if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
                        Model.importTeamsFromText(args.get(1));
                        }else{
                        System.out.println("Error: Not a supported file extension.");
                        }
                        return;
                    case "IMPORT-WRESTLERS":
                        if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
                        Model.importWrestlersFromText(args.get(1));
                        }else{
                        System.out.println("Error: Not a supported file extension.");
                        }
                        return;
                    case "VIEW-WRESTLER":
                        Model.printWrestlerInformation(args.get(1));
                        return;
                    case "NAME":
                        Model.setTournamentName(args.get(1));
                        return;
                }
            case 6: //case 7, update match. NOTE: CHANGED FROM 7 TO 6
                
            	Model.updateMatch(args.get(1),Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4)), args.get(5));
            	//To remove some of the possibility of error I have taken away the matchID parameter
                System.out.println("Match Updated!");
                return;
            default:
                printHelp();
                return;
                
    }
    
}
    public static void printHelp(){
        System.out.println("List of Commands and their parameters:\n"
                + "Command Parameter1 Parameter2 Parameter 3...\n"
                + "SAVE //Saves the tournament under the current name\n"
                + "START //Makes brackets from the existing wrestlers\n"
                + "VIEW-TEAMS //Displays each team's information\n"
                + "VIEW-WRESTLERS //Displays wrestlers, organizerd by weight class\n"
                + "HELP //Display list of available commands\n"
                + "LOAD TournamentName //Loads all files associated with this tournament name\n"
                + "SAVE TournamentName //Saves all information associated with this tournament to files with tournamentName\n"
                + "NAME TournamentName //Changes the tournament's name\n"
                + "IMPORT-TEAMS FileName //Parses the provided file for Team objects\n"
                + "IMPORT-WRESTLERS FileName //Parses the provided file for Wrestler objects\n"
                + "VIEW-WRESTLER WrestlerName //Looks for the wrestler and prints his/her information\n"
                + "UPDATE-MATCH winningColor greenPoints redPoints fallType(int) fallTime\n");
                }
}
