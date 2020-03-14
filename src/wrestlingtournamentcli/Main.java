
package wrestlingtournamentcli;
import DataClasses.*;
import java.util.ArrayList;
import java.util.Scanner;
import loggingFunctions.*;

/**
 * @author Jared Murphy
 * @author Cody Francis
 */
public class Main {
    static Error_Reporting log = new Error_Reporting();
    
    public static void main(String[] args) {
    
    Scanner s = new Scanner(System.in);
    log.createLogFiles(); 
    System.out.println("Please enter the sport you would like to manage(wrestling/soccer):");
	while(true) {
	String sportSelection = s.nextLine();
	switch(sportSelection.toLowerCase()) {
	case "wrestling":
		Model wrestlerModel = new Model("wrestling");
		wrestlingMenu();
		break;
    case "soccer":
    	Model soccerModel = new Model("soccer");
    	soccerMenu();
    	break;
    default:	
    	System.out.println("Incorrect input. Please enter wrestling/soccer");   
				}

    }
        
    }
    
     public static void wrestlingMenu() {
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
            log.writeErrorLog(e.getMessage());
            log.writeErrorStack(e);
        }
        }
   }
    
    
     public static void soccerMenu() { 
	   Scanner s = new Scanner(System.in);
   	   System.out.println("Welcome to the Murphy Soccer Player Manager. For available commands, please type 'help'");
       while(true){
       System.out.println("\nInput your next command!");
       String input = s.nextLine();
       try{
       ArrayList<String> arguments = new ArrayList();
       Scanner argScanner = new Scanner(input);
       while(argScanner.hasNext()){
           arguments.add(argScanner.next());
       }
       processInputSoccer(arguments);
       }catch(Exception e){
           System.out.println("Sorry! The command '" + input + "' either wasn't recognized or experienced an error.");
           System.out.println(e.getMessage());
       }
       }
	   
   }
    
    public static void processInputSoccer(ArrayList<String> args) throws Exception{
   args.set(0, args.get(0).toUpperCase());
   switch(args.size()){
           case 0:
               throw new BadCommandException();
           case 1: //Single-Command expressions
               switch(args.get(0)){
                   case "VIEW-TEAMS":
                       Model.printTeams();
                       return;
                   case "VIEW-SOCCER-PLAYERS":
                       Model.printSoccerPlayers();
                       return;
                   case "HELP":
                       printHelpSoccer();
                       return;
                   default:
                       throw new BadCommandException();
               }
           case 2: //Command-Param Expressions
               switch(args.get(0)){
                   case "IMPORT-TEAMS":
                       if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
                       Model.importTeamsFromText(args.get(1));
                       }else{
                       System.out.println("Error: Not a supported file extension.");
                       }
                       return;
                   case "IMPORT-SOCCER-PLAYERS":
                       if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
                       Model.importSoccerPlayersFromText(args.get(1));
                       }else{
                       System.out.println("Error: Not a supported file extension.");
                       }
                       return;
                   case "VIEW-SOCCER-PLAYER":
                       Model.printSoccerPlayerInformation(args.get(1));
                       return;
				default:
					break;
               }
           default:
               printHelpSoccer();
               return;
               
   }
   
} 
    
      public static void printHelpSoccer(){
       System.out.println("List of Commands and their parameters:\n"
               + "Command Parameter1 Parameter2 Parameter 3...\n"
               + "VIEW-TEAMS //Displays each team's information\n"
               + "VIEW-SOCCER-PLAYERS //Displays soccer players\n"
               + "HELP //Display list of available commands\n"
               + "IMPORT-TEAMS FileName //Parses the provided file for Team objects\n"
               + "IMPORT-SOCCER-PLAYERS FileName //Parses the provided file for Soccer objects\n"
               + "VIEW-SOCCER-PLAYER SoccerPlayerName //Looks for the soccer player and prints his/her information\n"
               );
               }
    
    
    public static void processInput(ArrayList<String> args) throws Exception{
    args.set(0, args.get(0).toUpperCase());
    switch(args.size()){
            case 0:
                throw new BadCommandException();
            case 1: //Single-Command expressions
                switch(args.get(0)){
                    case "SAVE":
                    	log.writeActionlog("Command Entered: " +args.get(0));
                        Model.saveTournament();
                        return;
                    case "ADVANCE":
                    	log.writeActionlog("Command Entered: " +args.get(0));
                        Model.advanceTournament();
                        return;
                    case "START":
                    	log.writeActionlog("Command Entered: " +args.get(0));
                        Model.generateTournament();
                        return;
                    case "VIEW-TEAMS":
                    	log.writeActionlog("Command Entered: " +args.get(0));
                        Model.printTeams();
                        return;
                    case "VIEW-WRESTLERS":
                    	log.writeActionlog("Command Entered: " +args.get(0));
                        Model.printWrestlers();
                        return;
                    case "HELP":
                    	log.writeActionlog("Command Entered: " +args.get(0));
                        printHelp();
                        return;
                    default:
                        throw new BadCommandException();
                }
            case 2: //Command-Param Expressions
                switch(args.get(0)){
                    case "LOAD":
                    	log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
                        Model.loadTournament(args.get(1));
                        return;
                    case "SAVE":
                    	log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
                        Model.saveTournament(args.get(1));
                        return;
                    case "IMPORT-TEAMS":
                        if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
                        log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
                        Model.importTeamsFromText(args.get(1));
                        }else{
                        System.out.println("Error: Not a supported file extension.");
                        log.writeErrorLog("Error: Not a supported file extension.");
                        }
                        return;
                    case "IMPORT-WRESTLERS":
                        if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
                        log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
                        Model.importWrestlersFromText(args.get(1));
                        }else{
                        System.out.println("Error: Not a supported file extension.");
                        log.writeErrorLog("Error: Not a supported file extension.");
                        }
                        return;
                    case "VIEW-WRESTLER":
                    	log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
                        Model.printWrestlerInformation(args.get(1));
                        return;
                    case "COMPARE-WRESTLERS":
                    	Model.compareWrestlersInformation(args.get(1));
                    	return;
                    case "NAME":
                    	log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
                        Model.setTournamentName(args.get(1));
                        return;
                }
            case 7:
            	log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1)+ " "+ args.get(2)+ " "+ args.get(3)+ " "+ args.get(4)+ " "+ args.get(5)+ " "+ args.get(6));
                Model.updateMatch(Integer.parseInt(args.get(1)), args.get(2),Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4)), Integer.parseInt(args.get(5)), args.get(6));
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
                + "ADVANCE //Makes the next round's matches\n"
                + "START //Makes brackets from the existing wrestlers\n"
                + "VIEW-TEAMS //Displays each team's information\n"
                + "VIEW-WRESTLERS //Displays wrestlers, organizerd by weight class\n"
                + "HELP //Display list of available commands\n"
                + "LOAD TournamentName //Loads all files associated with this tournament name\n"
                + "SAVE TournamentName //Saves all information associated with this tournament to files with tournamentName\n"
                + "NAME TournamentName //Changes the tournament's name"
                + "IMPORT-TEAMS FileName //Parses the provided file for Team objects\n"
                + "IMPORT-WRESTLERS FileName //Parses the provided file for Wrestler objects\n"
                + "VIEW-WRESTLER WrestlerName //Looks for the wrestler and prints his/her information\n"
                + "COMPARE-WRESTLERS WrestlerName,WrestlerName //Prints two wrestler's information side-by-side\n"
                + "UPDATE-MATCH matchNumber winningColor greenPoints redPoints fallType(int) fallTime\n");
                }
}
