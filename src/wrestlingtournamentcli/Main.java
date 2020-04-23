
package wrestlingtournamentcli;
import DataClasses.*;
import java.util.Optional;
import DataClasses.bowling.Bowling;
import DataClasses.race.Race;
import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import java.util.Scanner;
import loggingFunctions.*;
import javafx.scene.control.ListView;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;
/**
 * @author Jared Murphy
 * @author Cody Francis
 */
public class Main extends Application{
    static Error_Reporting log = new Error_Reporting();
    
    public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    log.createLogFiles();
    launch(args);

	while(true) {
		String sportSelection = s.nextLine();
		switch(sportSelection.toLowerCase()) {
			case "wrestling":
				Model wrestlerModel = new Model("wrestling");
				wrestlingMenu();
				break;
			case "race":
				Race race = new Race();
				raceMenu();
				break;
			case "bowling":
				Bowling bowling = new Bowling();
				bowlingMenu();
				break;
			case "soccer":
				Model soccerModel = new Model("soccer");
				soccerMenu();
				break;
			case "quit":
				exitProgram();
				break;
			default:	
				System.out.println("Incorrect input. Please enter wrestling/soccer/race/bowling");
				}
    	}
    }
    
    public static void raceMenu(){
    	Scanner s = new Scanner(System.in);
    	System.out.println("Welcome to the Murphy Racing Manager. For available commands, please type 'help'");
    	while(true){
    		System.out.println("\nInput your next command!");
    		String input = s.nextLine();
    		try {
    			ArrayList<String> arguments = new ArrayList();
    			Scanner argScanner = new Scanner(input);
    			while(argScanner.hasNext()){
    				arguments.add(argScanner.next());
    			}
    			processInputRace(arguments);
    		} catch(Exception e){
    			System.out.println("Sorry! The command '" + input + "' either wasn't recognized or experienced an error.");
    			System.out.println(e.getMessage());
    		}
    	}
    }

    public static void bowlingMenu(){
    	Scanner s = new Scanner(System.in);
    	System.out.println("Welcome to the Murphy Bowling Manager. For available commands, please type 'help'");
    	while(true){
    		System.out.println("\nInput your next command!");
    		String input = s.nextLine();
    		try {
    			ArrayList<String> arguments = new ArrayList();
    			Scanner argScanner = new Scanner(input);
    			while(argScanner.hasNext()){
    				arguments.add(argScanner.next());
    			}
    			processInputBowling(arguments);
    		} catch(Exception e){
    			System.out.println("Sorry! The command '" + input + "' either wasn't recognized or experienced an error.");
    			System.out.println(e.getMessage());
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
	      log.writeErrorLog(e.getMessage());
	      log.writeErrorStack(e);
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
    	                	   log.writeActionlog("Command Entered: " +args.get(0));
    	                       Model.printTeams();
    	                       return;
    	                   case "VIEW-SOCCER-PLAYERS":
    	                	   log.writeActionlog("Command Entered: " +args.get(0));
    	                       Model.printSoccerPlayers();
    	                       return;
    	                   case "HELP":
    	                	   log.writeActionlog("Command Entered: " +args.get(0));
    	                       printHelpSoccer();
    	                       return;
    	                   case "QUIT":
    	                   	exitProgram();
    	                   	break;
    	                   default:
    	                       throw new BadCommandException();
    	               }
    	           case 2: //Command-Param Expressions
    	               switch(args.get(0)){
    	                   case "IMPORT-TEAMS":
    	                       if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
    	                       Model.importTeamsFromText(args.get(1));
    	                       log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
    	                       }else{
    	                       System.out.println("Error: Not a supported file extension.");
    	                       }
    	                       return;
    	                   case "IMPORT-SOCCER-PLAYERS":
    	                       if(args.get(1).substring(args.get(1).length()-4).equals(".txt")){
    	                       Model.importSoccerPlayersFromText(args.get(1));
    	                       log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
    	                       }else{
    	                       System.out.println("Error: Not a supported file extension.");
    	                       }
    	                       return;
    	                   case "VIEW-SOCCER-PLAYER":
    	                       Model.printSoccerPlayerInformation(args.get(1));
    	                       log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1));
    	                       return;
    					default:
    						break;
    	               }
    	           default:
    	               printHelpSoccer();
    	               return;
    	   }
    	}
    
    public static void processInputRace(ArrayList<String> args) throws Exception{
    	args.set(0, args.get(0).toUpperCase());
    	switch(args.size()){
    		case 0:
    			throw new BadCommandException();
    		case 1: //1-Command expressions
    			switch(args.get(0)){
	    			case "HELP":
	                	Race.printMenu();
	                	return;
	    			case "QUIT":
	                   	exitProgram();
	                default:
	                    throw new BadCommandException();
    			}
    		case 2: //2-Command expressions
    			switch(args.get(0)){
	    			case "RACE":
	                	switch(args.get(1).toUpperCase()) {
	                    	case "LISTRACERS":
	                    		Race.listRacers();
	                    		return;
	                    	case "START":
	                    		Race.start();
	                    		return;
	                    	case "STATUS":
	                    		Race.getStatus();
	                    		return;
	                    	default:
	    	                    throw new BadCommandException();
	                	}
    			}
    		case 3:
            	switch(args.get(0)){
	            	case "RACE":
	            		switch(args.get(1).toUpperCase()) {
	            			case "LAPCOMPLETED":
	            				Race.lapCompleted(Integer.parseInt(args.get(2)));
	            				return;
	            			case "LAPS":
	            				Race.setLapsTotal(Integer.parseInt(args.get(2)));
	            				return;
	            			default:
	    	                    throw new BadCommandException();
	            		}
	            }
    		case 5:
            	switch(args.get(0)) {
	            	case "RACE":
		            	switch(args.get(1).toUpperCase()) {
		            		case "ADDRACER":
			            		Race.addRacer(args.get(2), args.get(3), Integer.parseInt(args.get(4)));
			            		return;
		            		default:
	    	                    throw new BadCommandException();
		            	}
            	}
    	}
    }

    public static void processInputBowling(ArrayList<String> args) throws Exception{
    	args.set(0, args.get(0).toUpperCase());
    	switch(args.size()){
    		case 0:
    			throw new BadCommandException();
    		case 1: // 1 command expressions
    			switch(args.get(0)){
	    			case "HELP":
	                	Bowling.printMenu();
	                	return;
	    			case "STATUS":
	    				Bowling.status();
	    				return;
	    			case "QUIT":
	                   	exitProgram();
	                default:
	                    throw new BadCommandException();
    			}
    		case 3: // 3 command expressions
    			switch(args.get(0)) {
				case "ROLL":
					Bowling.roll(Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)));
					return;
				default:
                    throw new BadCommandException();
			}
    		case 4: // 4 command expressions
    			switch(args.get(0)) {
    				case "ADDBOWLER":
    					Bowling.addBowler(args.get(1), args.get(2), Integer.parseInt(args.get(3)));
    					return;
    				default:
	                    throw new BadCommandException();
    			}
    		default:
                throw new BadCommandException();
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
                + "QUIT // Exits the program\n"
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
                    case "QUIT":
                    	exitProgram();
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
            case 6:
              log.writeActionlog("Command Entered: " +args.get(0)+ " "+ args.get(1)+ " "+ args.get(2)+ " "+ args.get(3)+ " "+ args.get(4)+ " "+ args.get(5));
            	Model.updateMatch(args.get(1),Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4)), args.get(5));
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
                + "NAME TournamentName //Changes the tournament's name\n"
                + "IMPORT-TEAMS FileName //Parses the provided file for Team objects\n"
                + "IMPORT-WRESTLERS FileName //Parses the provided file for Wrestler objects\n"
                + "VIEW-WRESTLER WrestlerName //Looks for the wrestler and prints his/her information\n"
                + "COMPARE-WRESTLERS WrestlerName,WrestlerName //Prints two wrestler's information side-by-side\n"
                + "UPDATE-MATCH matchNumber winningColor greenPoints redPoints fallType(int) fallTime\n"
                + "QUIT // Exit the program.\n");
    }
    
    public static void exitProgram() {
    	System.out.println("Exiting program...");
    	System.exit(0);
    }

	@Override
	public void start(Stage stage) throws Exception {
	
		BorderPane root = new BorderPane();
		VBox mainMenu = new VBox();
		VBox viewList = new VBox();
		Button add = new Button();
		Button viewTeams = new Button();
		Button viewWrestlers = new Button();
		Button importTeams = new Button();
		Button importWrestlers = new Button();
		Button save = new Button();
		Button start = new Button();


		Button compareWrestlers = new Button();
		TextField compareWrestlersTxt = new TextField();
		TextField saveTournament = new TextField();
		TextField FirstName = new TextField();
		TextField LastName = new TextField();
		TextField TeamAlias = new TextField();
		TextField Grade = new TextField();
		TextField Weight = new TextField();
		TextField TotalWins = new TextField();
		TextField TotalMatch =  new TextField();
		compareWrestlers.setMinWidth(110);
		compareWrestlersTxt.setMinWidth(110);
		ListView<String> compareWrestlerView = new ListView<String>();
		Button wrestlerBack = new Button();
		ListView<String> startView = new ListView<String>();
		//button declarations i've added
		Button advance = new Button();
		Button help = new Button();
		Button update = new Button();
    
		

		// adds tooltips to each menu button
		Tooltip viewTeamsTooltip = new Tooltip("Displays the imported teams in the display box to the right");
		Tooltip.install(viewTeams, viewTeamsTooltip);
		Tooltip viewWrestlersTooltip = new Tooltip("Displays the imported wrestlers in the display box to the right");
		Tooltip.install(viewWrestlers, viewWrestlersTooltip);
		Tooltip importTeamsTooltip = new Tooltip("Imports teams from a txt file");
		Tooltip.install(importTeams, importTeamsTooltip);
		Tooltip importWrestlersToolTip = new Tooltip("Imports wrestlers from a txt file");
		Tooltip.install(importWrestlers, importWrestlersToolTip);
		Tooltip startToolTip = new Tooltip("Initiates the simulation");
		Tooltip.install(start, startToolTip);
		Tooltip saveTooltip = new Tooltip("Saves the results of the matches to a file(Use text box to save the name of the tournament)");
		Tooltip.install(save, saveTooltip);
		ListView<Team> listView = new ListView<Team>();
		ListView<Wrestler> wrestlerView = new ListView<Wrestler>();
		
		ListView<String> helpView = new ListView<String>();
		
		TextField importWrestlerField = new TextField ();

		Label menu = new Label("Main Menu");
		menu.setStyle("-fx-font-weight: bold; -fx-font: 24 arial");

		// create buttons/textfields for view of team and wrestlers
		
		add.setMinWidth(110); 
		add.setText("Add Wrestler");
		BorderPane introRoot= new BorderPane();
		GridPane introLayout = new GridPane();
		Button wrestling = new Button();
		Button soccer = new Button();
	
		//UI elements for selection Screen
		
		BorderPane soccerRoot = new BorderPane();
		Label soccerLabel = new Label("Soccer Menu");
		VBox soccerMenu = new VBox();
		VBox soccerViewList = new VBox();
		GridPane soccerLayout = new GridPane();
		Button importSoccerTeams = new Button();
		Button importSoccerPlayers = new Button();
		Button viewSoccerTeams = new Button();
		Button viewSoccerPlayers = new Button();
		Button soccerBack = new Button();
		ListView<Team> soccerTeamListView = new ListView<Team>();
		ListView<SoccerPlayer> soccerPlayerView = new ListView<SoccerPlayer>();
		//UI elements for soccer management
		importSoccerTeams.setText("Import Soccer Teams");
		importSoccerPlayers.setText("Import Soccer Players");
		viewSoccerTeams.setText("View Soccer Teams");
		viewSoccerPlayers.setText("View Soccer Players");
		soccerBack.setText("Back");
		soccerLayout.setPadding(new Insets(10,10,10,10));
		soccerLayout.setMinSize(300, 300);
		soccerLayout.setVgap(5);
		soccerLayout.setHgap(5);
		soccerLayout.setAlignment(Pos.BASELINE_LEFT); 
		soccerLayout.add(soccerLabel, 1, 0);
		soccerLayout.add(importSoccerTeams, 0, 1);
		soccerLayout.add(importSoccerPlayers, 0, 3);
		soccerLayout.add(viewSoccerTeams, 0, 4);
		soccerLayout.add(viewSoccerPlayers, 0, 2);
		soccerLayout.add(soccerBack, 0, 5);
		//Initializing soccer UI elements
		soccerMenu.getChildren().addAll(soccerLayout);
		soccerViewList.prefWidth(100);
		soccerViewList.getChildren().addAll(soccerTeamListView,soccerPlayerView);
		soccerRoot.setLeft(soccerMenu);
		soccerRoot.setCenter(soccerViewList);
		//Creating soccer scene
		introLayout.setHgap(10);
		introLayout.setVgap(12);
		wrestling.setText("Wrestling");
		wrestling.setPadding(new Insets(10,10,10,10));
		soccer.setText("Soccer");
		soccer.setPadding(new Insets(10,10,10,10));
		//Intro Layout
		Text intro = new Text ("Please select the sport you would like to manage");
		intro.setFont(new Font(20));
		intro.setFill(Color.DARKGREEN);
		intro.setTextAlignment(TextAlignment.CENTER);
		stage.setTitle("RaiderSMA");
		introLayout.setPadding(new Insets(250,100,100,100));
		introLayout.add(intro, 0, 0);
		introLayout.add(wrestling, 0, 4);
		introLayout.add(soccer,1,4);
		introRoot.setCenter(introLayout);	
		//Initializing introduction UI elements

		importTeams.setMinWidth(110);
		importWrestlers.setMinWidth(110);
		viewTeams.setMinWidth(110);
		viewWrestlers.setMinWidth(110);
		saveTournament.setMinWidth(110);
		save.setMinWidth(110);
		start.setMinWidth(110);
		FirstName.setMinWidth(110);
		LastName.setMinWidth(110);
		update.setMinWidth(110);
		help.setMinWidth(110);
		advance.setMinWidth(110);
		advance.setText("Advance");
		help.setText("Help");
		update.setText("Update Match");
		
		save.setText("Save");
		start.setText("Start");
		FirstName.setPromptText("First Name");
		LastName.setPromptText("Last Name");
		TeamAlias.setPromptText("Team Alias");
		Grade.setPromptText("Grade");
		Weight.setPromptText("Player's Weight");
		TotalWins.setPromptText("Total Wins");
		TotalMatch.setPromptText("Total Match");
		
		importTeams.setText("Import Teams");
		importWrestlers.setText("Import Wrestlers");
		viewTeams.setText("View Teams");
		viewWrestlers.setText("View Wrestlers");
		saveTournament.setPromptText("Name of Tournament");
		wrestlerBack.setText("Back");
		compareWrestlers.setText("Compare Wrestlers");
		compareWrestlersTxt.setPromptText("Alias,Alias");
		//create the layout of the menu
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10,10,10,10));
		layout.setMinSize(300, 300);
		layout.setVgap(5);
		layout.setHgap(5);
		layout.setAlignment(Pos.BASELINE_LEFT); 
		layout.add(menu, 1, 0);
		layout.add(importWrestlers, 0, 1);
		layout.add(importTeams, 0, 3);
		layout.add(viewTeams, 0, 4);
		layout.add(viewWrestlers, 0, 2);
		layout.add(save, 0, 5);
		layout.add(saveTournament, 1, 5);
		layout.add(add, 0, 9);
		layout.add(FirstName, 1, 9);
		layout.add(LastName, 1, 10);
		layout.add(TeamAlias, 1, 11);
		layout.add(Grade, 1, 12);
		layout.add(Weight, 1, 13);
		layout.add(TotalWins, 1,14);
		layout.add(TotalMatch, 1, 15);
		layout.add(compareWrestlers, 0, 17);
		layout.add(compareWrestlersTxt, 1, 17);
		layout.add(advance, 0, 18);
		layout.add(update, 0, 19);
		layout.add(start, 0, 21);
		layout.add(help, 0, 28);


		Scene introScene = new Scene (introRoot, 700, 700);
		Scene wrestlerScene = new Scene(root,700,700);
		Scene soccerScene = new Scene(soccerRoot,700,700);
		
		wrestling.setOnAction(e ->{
			
			Model m = new Model("wrestling");
			stage.setScene(wrestlerScene);
				
		});
		soccer.setOnAction(e ->{
						
			Model soccerModel = new Model("soccer");
			stage.setScene(soccerScene);
			
		});
				viewTeams.setOnAction(e -> {
	
			ArrayList<Team> show = Model.printTeams();
			for(int i = 0; i < show.size(); i++) {
				listView.getItems().add(show.get(i));
			}
			
		    return;
		});
		
		soccerBack.setOnAction(e -> {
			
			stage.setScene(introScene);
			
		});
		wrestlerBack.setOnAction(e->{
			
			stage.setScene(introScene);
			
		});
		viewSoccerTeams.setOnAction(e->{
			
			ArrayList<Team> show = Model.printTeams();
			for(int i = 0; i < show.size(); i++) {
				soccerTeamListView.getItems().add(show.get(i));
			}
			
		    return;
			
		});
		
		viewSoccerPlayers.setOnAction(e->{
			
			ArrayList<SoccerPlayer> soccerPlayerList = Model.printSoccerPlayers();
			
			for(int i = 0; i < soccerPlayerList.size(); i++) {
				soccerPlayerView.getItems().add(soccerPlayerList.get(i));
			}
			
		    return;
			
		});
		
		importSoccerTeams.setOnAction(e ->{
			
			FileChooser fc = new FileChooser();
			File seletedFile = fc.showOpenDialog(null);
			if(seletedFile != null) {
				String a = seletedFile.getAbsolutePath();
				 if(a.substring(a.length()-4).equals(".txt")) {
					Model.importTeamsFromText(a);
					Alert fileAlert = new Alert(AlertType.CONFIRMATION);
					fileAlert.setTitle("Import Alert");
					String info = "Import was a success";
					fileAlert.setContentText(info);
					fileAlert.show();
					return;
				 }
				 else {
					 Alert fileAlert = new Alert(AlertType.ERROR);
					 fileAlert.setTitle("Import Alert");
					 String info = "IMPORT FAILED! Not a .txt file";
					 fileAlert.setContentText(info);
					 fileAlert.show();
					 return;
				 }
					 
				
			}
			else {
				System.out.println("Error");
			}
			
		});
		
		importSoccerPlayers.setOnAction(e ->{
			
			FileChooser fc = new FileChooser();
			File seletedFile = fc.showOpenDialog(null);
			if(seletedFile != null) {
				String a = seletedFile.getAbsolutePath();
				 if(a.substring(a.length()-4).equals(".txt")) {
					Model.importSoccerPlayersFromText(a);
					Alert fileAlert = new Alert(AlertType.CONFIRMATION);
					fileAlert.setTitle("Import Alert");
					String info = "Import was a success";
					fileAlert.setContentText(info);
					fileAlert.show();
					return;
				 }
				 else {
					 Alert fileAlert = new Alert(AlertType.ERROR);
					 fileAlert.setTitle("Import Alert");
					 String info = "IMPORT FAILED! Not a .txt file";
					 fileAlert.setContentText(info);
					 fileAlert.show();
					 return;
				 }
					 
				
			}
			else {
				System.out.println("Error");
			}
			
		});
		
		viewWrestlers.setOnAction(e -> {
			
			ArrayList<Wrestler> wrestlerList = Model.printWrestlers();
			
			for(int i = 0; i < wrestlerList.size(); i++) {
				wrestlerView.getItems().add(wrestlerList.get(i));
			}
			
		    return;
		});
		
		importTeams.setOnAction(e -> {
		
			FileChooser fc = new FileChooser();
			File seletedFile = fc.showOpenDialog(null);
			if(seletedFile != null) {
				String a = seletedFile.getAbsolutePath();
				 if(a.substring(a.length()-4).equals(".txt")) {
					Model.importTeamsFromText(a);
					Alert teamsAlert = new Alert(AlertType.CONFIRMATION);
					teamsAlert.setTitle("Import Alert");
					String info = "Import was a success";
					teamsAlert.setContentText(info);
					teamsAlert.show();
					return;
				 }
				 else {
					 Alert teamsAlert = new Alert(AlertType.ERROR);
					 teamsAlert.setTitle("Import Alert");
					 String info = "IMPORT FAILD! Not a .txt file";
					 teamsAlert.setContentText(info);
					 teamsAlert.show();
					 return;
				 }
					 
				
			}
			else {
				System.out.println("Err");
			}
		});
		
		importWrestlers.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			File seletedFile = fc.showOpenDialog(null);
			if(seletedFile != null) {
				String a = seletedFile.getAbsolutePath();
				 if(a.substring(a.length()-4).equals(".txt")) {
					int check = Model.importWrestlersFromText(a);
					Alert wrestlerAlert = new Alert(AlertType.CONFIRMATION);
					wrestlerAlert.setTitle("Import Alert");
					String info = "Import was a success";
					wrestlerAlert.setContentText(info);
					wrestlerAlert.show();
					return;
				 }
				 else {
					 Alert wrestlerAlert = new Alert(AlertType.ERROR);
					 wrestlerAlert.setTitle("Import Alert");
					 String info = "IMPORT FAILD! Not a .txt file";
					 wrestlerAlert.setContentText(info);
					 wrestlerAlert.show();
					 return;
				 }
					 
				
			}
			else {
				System.out.println("Err");
			}
		});
		
		
		save.setOnAction(e -> {
			String textbox = saveTournament.getText();
			if(textbox.equals("Name of Tournament")) {
				Model.saveTournament();
			}
			else {
				Model.saveTournament(textbox);
			}
		});
		
		start.setOnAction(e -> {
			/*int check = Model.generateTournament();
			if(check == 0) {
				Alert genTourn0 = new Alert(AlertType.ERROR);
				genTourn0.setTitle("Generate Tournament ALert");
				String genTourn0Info = "Error: No Wrestlers or Teams Found \n"
						+ "Please add wrestlers/teams before generating a tournament.";
				genTourn0.setContentText(genTourn0Info);
				genTourn0.show();	
			}
			else if (check == 1) {
				return;
			}
			else if (check == 2) {
				if(Model.getBracketList().size() != 0) {
					
				}
				
                 	//System.out.println(Model.getWeightClass().get(i) + ": " + Model.getBracketList().get(i).bracket.get(0).size()+ "\tWrestlers: " + bracketSize);
                 }
                
			}
			else {
				return;
			}*/
			
			
			if(Model.getWrestlerList().size() == 0 || Model.getTeamList().size() == 0) {//checks to see if there are teams and wrestlers
				Alert genTourn0 = new Alert(AlertType.ERROR);
				genTourn0.setTitle("Generate Tournament Alert");
				String genTourn0Info = "Error: No Wrestlers or Teams Found \n"
						+ "Please add wrestlers/teams before generating a tournament.";
				genTourn0.setContentText(genTourn0Info);
				genTourn0.show();	
			} else if(Model.getBracketList().size() != 0) {//checks to see if there is already a Tournament and then as user if they want to restart match
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Restart Tournement Conformation");
				alert.setContentText("Restart?");
				alert.setHeaderText("Want to Restart Tournament?");
				Optional<ButtonType> result = alert.showAndWait(); 
					if(result.get() == ButtonType.OK) {
						Model.getBracketList().clear();
						Model.generateTournament();
						Alert genTour2 = new Alert(AlertType.CONFIRMATION);
						genTour2.setTitle("Generate Tournament Success!");
						String genTourn2Info = "Generating the tourament was a success!";
						genTour2.setContentText(genTourn2Info);
						genTour2.show();
						 for(int i =0 ; i < Model.getBracketList().size(); i++) {
			             	int size =  Model.getBracketList().get(i).getWrestlerListSize();
			             	String numOfWrestlers = String.valueOf(size);
			             	String weightClass = String.valueOf(Model.getWeightClass().get(i));
			             	String sizeofBracket = String.valueOf( Model.getBracketList().get(i).bracket.get(0).size());
			             	
			             	startView.getItems().add(weightClass + ": " + sizeofBracket + "\tWrestlers: " + numOfWrestlers);
						 }
					}
				
			}
			else {//Generates tournament if there is non generated
				Model.generateTournament();
				Alert genTour2 = new Alert(AlertType.CONFIRMATION);
				genTour2.setTitle("Generate Tournament Success!");
				String genTourn2Info = "Generating the tourament was a success!";
				genTour2.setContentText(genTourn2Info);
				genTour2.show();
				 for(int i =0 ; i < Model.getBracketList().size(); i++) {
	             	int size =  Model.getBracketList().get(i).getWrestlerListSize();
	             	String numOfWrestlers = String.valueOf(size);
	             	String weightClass = String.valueOf(Model.getWeightClass().get(i));
	             	String sizeofBracket = String.valueOf( Model.getBracketList().get(i).bracket.get(0).size());
	             	
	             	startView.getItems().add(weightClass + ": " + sizeofBracket + "\tWrestlers: " + numOfWrestlers);
				 }
			}
			
		});
		
			compareWrestlers.setOnAction(e -> {
            String textbox = compareWrestlersTxt.getText();
            ArrayList<Wrestler> compareWrestlerPrint = Model.compareWrestlersInformation(textbox);
            
            if(compareWrestlerPrint.size() == 0) {
                Alert comWresPrintError = new Alert(AlertType.ERROR);
                comWresPrintError.setTitle("Error Name Not Found");
                String comWresPrintErrorInfo = "One of the names entered does not exist.";
                comWresPrintError.setTitle(comWresPrintErrorInfo);
                comWresPrintError.show();
            } else {
            Wrestler w1 = compareWrestlerPrint.get(0);
            Wrestler w2 = compareWrestlerPrint.get(1);
            compareWrestlerView.getItems().add("\n\t" + "Guide: " + w1.getFirstName() + "  " + w2.getFirstName() +
            "\nName: " + w1.getLastName() + ", " + w1.getFirstName() + "  " +  w2.getLastName() + ", " + w2.getFirstName() +
            "\nUsername: " + w1.getUserName() + "  " +  w2.getUserName() +
            "\nWeight Class: " + Integer.toString(w1.getWeightClass()) + "  " +  Integer.toString(w2.getWeightClass()) +
            "\nTeam Name: " + w1.getTeamID() + "  " +  w2.getTeamID() +
            "\nSeed: " + Integer.toString(w1.getSeed()) + "  " +  Integer.toString(w2.getSeed()) +
            "\nRating: " + Double.toString(w1.getRating()) + "  " +  Double.toString(w2.getRating()) + "\n");
            }
            return;

        });


		advance.setOnAction(e -> {
			Model.advanceTournament();
			Alert adv = new Alert(AlertType.CONFIRMATION);
			adv.setTitle("Advance success");
			String advInfo = "The tournament has been successfully advanced!";
			adv.setContentText(advInfo);
			adv.show();
		});
		
		help.setOnAction(e ->{
			helpView.getItems().add("Import Wrestlers - Choose a text file with wrestler data to import (You must have teams imported first)");
			helpView.getItems().add("View Wrestlers - Displays information on all imported wrestlers");
			helpView.getItems().add("Import teams - Choose a text file with team data to import");
			helpView.getItems().add("View Teams - Displays information on all imported teams");
			helpView.getItems().add("Save - Save the current tournament");
			helpView.getItems().add("Start - Starts the tournament once wrestlers and teams have been imported");
			helpView.getItems().add("Advance - Advances the tournament to the next match (you will need to have started the tournament and updated the current match)");
			helpView.getItems().add("Update Match - updates the current match");
		});
		
		update.setOnAction(e ->{
			BorderPane root2 = new BorderPane();
			VBox fields = new VBox();
			GridPane pane = new GridPane();
			
			TextField colorField = new TextField();
			TextField greenPts = new TextField();
			TextField redPts = new TextField();
			TextField fallType = new TextField();
			TextField fallTime = new TextField();
			Button confirm = new Button();
			
			Label cfLabel = new Label("Winning Color");
			Label gpLabel = new Label("Green Points");
			Label rpLabel = new Label("Red Points");
			Label ftyLabel = new Label("Fall Type");
			Label ftiLabel = new Label("Fall Time");
			colorField.setMinWidth(110);
			greenPts.setMinWidth(110);
			redPts.setMinWidth(110);
			fallType.setMinWidth(110);
			fallTime.setMinWidth(110);
			confirm.setMinWidth(110);
			confirm.setMinHeight(50);
			confirm.setText("Update");
			
			pane.setPadding(new Insets(10, 10, 10, 10));
			pane.setMinSize(300, 300);
			pane.setVgap(5);
			pane.setHgap(5);
			pane.setAlignment(Pos.BASELINE_LEFT);
			pane.add(colorField, 0, 1);
			pane.add(greenPts, 0, 2);
			pane.add(redPts, 0, 3);
			pane.add(fallType, 0, 4);
			pane.add(fallTime, 0, 5);
			pane.add(confirm, 0, 6);
			
			pane.add(cfLabel, 1, 1);
			pane.add(gpLabel, 1, 2);
			pane.add(rpLabel, 1, 3);
			pane.add(ftyLabel, 1, 4);
			pane.add(ftiLabel, 1, 5);
			
			confirm.setOnAction(e2 ->{
				String wc = colorField.getText();
				int gp = Integer.parseInt(greenPts.getText());
				int rp = Integer.parseInt(redPts.getText());
				int ftype = Integer.parseInt(fallType.getText());
				String ftime = fallTime.getText();
				Model.updateMatch(wc, gp, rp, ftype, ftime);
			});
			
			Stage stage2 = new Stage();
			fields.getChildren().addAll(pane);
			root2.setCenter(fields);//put content here(pane)
			
			stage2.setScene(new Scene(root2, 300, 300));
			stage2.setTitle("Update Match");
			stage2.show();
			
		});
		add.setOnAction(e-> {
			if(FirstName.getText().isEmpty() && LastName.getText().isEmpty() 
					&& TeamAlias.getText().isEmpty() && Grade.getText().isEmpty() && Weight.getText().isEmpty() &&
					TotalWins.getText().isEmpty() && TotalMatch.getText().isEmpty()) {
				Alert FirstNameEmpty = new Alert(AlertType.ERROR);
				FirstNameEmpty.setTitle("New Player Alert");
				String FirstNameEmptyInfo = "Please enter Information \n";
				FirstNameEmpty.setContentText(FirstNameEmptyInfo);
				FirstNameEmpty.show();
			}
			else {
				try {
				String FN = FirstName.getText();
				String LN = LastName.getText();
				String TA = TeamAlias.getText();
				int grade = Integer.parseInt(Grade.getText());
				int weight = Integer.parseInt(Weight.getText());
				int totalWins = Integer.parseInt(TotalWins.getText());
				int totalMatch = Integer.parseInt(TotalMatch.getText());
				new Wrestler(FN, LN,TA, grade, weight, totalWins, totalMatch);
				}
				catch(Exception a) {
					System.out.println("Something went wrong");
				}
			}
		});
		mainMenu.getChildren().addAll(layout);
		viewList.prefWidth(100);
		viewList.getChildren().addAll(listView, wrestlerView, compareWrestlerView,startView,helpView);

		root.setLeft(mainMenu);
		root.setCenter(viewList);
		
		//stage.setScene(new Scene(root, 700, 700));
		stage.setScene(introScene);
		
		stage.show();
				
	}
};
