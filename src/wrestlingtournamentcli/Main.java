
package wrestlingtournamentcli;
import DataClasses.*;
import java.util.ArrayList;
import java.util.Scanner;
import loggingFunctions.*;
import DataClasses.*;
import javafx.scene.control.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
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
/**
 * @author Jared Murphy
 * @author Cody Francis
 */
public class Main extends Application{
    static Error_Reporting log = new Error_Reporting();
    
    public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    Race race = new Race();
    log.createLogFiles();
    launch(args);

    System.out.println("Please enter the sport you would like to manage(wrestling/soccer):\nNote: Enter 'QUIT' at anytime to end the program.");
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
    case "quit":
    	exitProgram();
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
                    case "RACE":
                    	Race.printMenu();
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
	            		}
	            }
            case 5:
            	switch(args.get(0)) {
	            	case "RACE":
		            	switch(args.get(1).toUpperCase()) {
		            		case "ADDRACER":
			            		Race.addRacer(args.get(2), args.get(3), Integer.parseInt(args.get(4)));
			            		return;
		            	}
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
                + "RACE //View Race commands\n"
                + "quit // Exit the program.\n");
    }
    
    public static void exitProgram() {
    	System.out.println("Exiting program...");
    	System.exit(0);
    }

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("RaiderSMA");
		BorderPane root = new BorderPane();
		VBox mainMenu = new VBox();
		VBox viewList = new VBox();
		Button viewTeams = new Button();
		Button viewWrestlers = new Button();
		Button importTeams = new Button();
		Button importWrestlers = new Button();
		Button save = new Button();
		Button start = new Button();
		Button wrestlerBack = new Button();
		TextField saveTournament = new TextField();
		ListView<Team> listView = new ListView<Team>();
		ListView<Wrestler> wrestlerView = new ListView<Wrestler>();
		TextField importWrestlerField = new TextField ();
		Label menu = new Label("Main Menu");
		menu.setStyle("-fx-font-weight: bold; -fx-font: 24 arial");
		// create buttons/textfields for view of team and wrestlers
		
		BorderPane introRoot= new BorderPane();
		GridPane introLayout = new GridPane();
		Button wrestling = new Button();
		Button soccer = new Button();
		Label introMenu = new Label("Please select the sport you would like to manage:");
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
		wrestling.setText("Wrestling");
		wrestling.setPadding(new Insets(10,10,10,10));
		soccer.setText("Soccer");
		soccer.setPadding(new Insets(10,10,10,10));
		introLayout.setPadding(new Insets(300,100,100,100));
		introLayout.add(introMenu, 0, 0);
		introLayout.add(wrestling, 2,3 );
		introLayout.add(soccer, 3, 3);
		introRoot.setCenter(introLayout);		
		//Initializing introduction UI elements
		
		importTeams.setMinWidth(110);
		importWrestlers.setMinWidth(110);
		viewTeams.setMinWidth(110);
		viewWrestlers.setMinWidth(110);
		saveTournament.setMinWidth(110);
		save.setMinWidth(110);
		start.setMinWidth(110);
		save.setText("Save");
		start.setText("Start");
		
		importTeams.setText("Import Teams");
		importWrestlers.setText("Import Wrestlers");
		viewTeams.setText("View Teams");
		viewWrestlers.setText("View Wrestlers");
		saveTournament.setText("Name of Tournament");
		wrestlerBack.setText("Back");
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
		layout.add(start, 0, 6);
		layout.add(wrestlerBack, 0, 7);
		
		
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
			/*for(int i = 0; i < wrestlerListshow.size(); i++) {
				listView.getItems().add(wrestlerListshow.get(i));
			}*/
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
			int check = Model.generateTournament();
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
				Alert genTour2 = new Alert(AlertType.CONFIRMATION);
				genTour2.setTitle("Generate Tournament Success!");
				String genTourn2Info = "Generating the tourament was a success!";
				genTour2.setContentText(genTourn2Info);
				genTour2.show();
			}
			else {
				return;
			}
		});
		
		mainMenu.getChildren().addAll(layout);
		viewList.prefWidth(100);
		viewList.getChildren().addAll(listView,wrestlerView);
		root.setLeft(mainMenu);
		root.setCenter(viewList);
		
		//stage.setScene(new Scene(root, 700, 700));
		stage.setScene(introScene);
		
		stage.show();
		
	}
}