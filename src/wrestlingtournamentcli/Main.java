
package wrestlingtournamentcli;
import DataClasses.*;
import java.util.ArrayList;
import java.util.Scanner;
import loggingFunctions.*;
import DataClasses.*;
import javafx.scene.control.ListView;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    Model m = new Model("wrestling");
    Scanner s = new Scanner(System.in);
    Race race = new Race();
    log.createLogFiles();
    launch(args);

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
                    case "RACE":
                    	Race.printMenu();
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
                + "RACE //View Race commands\n");
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
		Button addNewTeams = new Button();
		
		TextField saveTournament = new TextField();
		
		ListView<Team> listView = new ListView<Team>();
		ListView<Wrestler> wrestlerView = new ListView<Wrestler>();
		TextField importWrestlerField = new TextField ();
		Label menu = new Label("Main Menu");
		menu.setStyle("-fx-font-weight: bold; -fx-font: 24 arial");
		// create buttons/textfields for view of team and wrestlers
		
		
		importTeams.setMinWidth(110);
		importWrestlers.setMinWidth(110);
		viewTeams.setMinWidth(110);
		viewWrestlers.setMinWidth(110);
		saveTournament.setMinWidth(110);
		save.setMinWidth(110);
		start.setMinWidth(110);
		addNewTeams.setMinWidth(110);
		addNewTeams.setText("Add your own Team");
		save.setText("Save");
		start.setText("Start");
		
		importTeams.setText("Import Teams");
		importWrestlers.setText("Import Wrestlers");
		viewTeams.setText("View Teams");
		viewWrestlers.setText("View Wrestlers");
		saveTournament.setText("Name of Tournament");
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
		layout.add(addNewTeams, 1, 1);
		layout.add(viewTeams, 0, 4);
		layout.add(viewWrestlers, 0, 2);
		layout.add(save, 0, 5);
		layout.add(saveTournament, 1, 8);
		layout.add(start, 0, 6);
		viewTeams.setOnAction(e -> {
			
			ArrayList<Team> show = Model.printTeams();
			for(int i = 0; i < show.size(); i++) {
				listView.getItems().add(show.get(i));
			}
			
		    return;
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
		
		//Set the button on action, it will display labels and text field 
		// to allow the user to input his/her own team information. 
		addNewTeams.setOnAction(e -> {
			
			
			//Label and text field for the team name. 
			Label teamName = new Label("Team 1 Name:");
			TextField userOwnTeam1Name = new TextField();
			
			//Label and textField for the team Initials
			Label teamInitials = new Label("Team 1 Initials:");
			TextField userOwnTeamInitials = new TextField();
			
			//Label and text field for team Mascot
			Label teamMascot = new Label("Team 1 Mascot:");
			TextField userOwnTeamMascot = new TextField();
			
			//Second Team Info: 

			//Label and text field for the team name. 
			Label team2Name = new Label("Team 2 Name:");
			TextField userOwnTeam2Name = new TextField();
			
			//Label and textField for the team Initials
			Label team2Initials = new Label("Team 2 Initials:");
			TextField userOwnTeam2Initials = new TextField();
			
			//Label and text field for team Mascot
			Label team2Mascot = new Label("Team 2 Mascot:");
			TextField userOwnTeam2Mascot = new TextField();
			
			Button addTeams = new Button("Add");

			addTeams.setMinWidth(110);
			
			//Placing the labels and textField in the stage window for the first team. 
			layout.add(teamName, 1, 2);  
			layout.add(userOwnTeam1Name, 1, 3); 
			layout.add(teamInitials, 1, 4); 
			layout.add(userOwnTeamInitials, 1, 5);
			layout.add(teamMascot, 1, 6);
			layout.add(userOwnTeamMascot, 1, 7);

			//Placing the labels and textField in the stage window for the first team for Second Team 
			layout.add(team2Name, 2, 2);  
			layout.add(userOwnTeam2Name, 2, 3); 
			layout.add(team2Initials, 2, 4); 
			layout.add(userOwnTeam2Initials, 2, 5);
			layout.add(team2Mascot, 2, 6);
			layout.add(userOwnTeam2Mascot, 2, 7);

			layout.add(addTeams, 2, 8);
			

			addTeams.setOnAction(e2 -> {
				//Get user input and assign it to a String variable. 
				String teamNameText = userOwnTeam1Name.getText();
				String teamInitiText = userOwnTeamInitials.getText();
				String teamMascotText = userOwnTeamMascot.getText();
				
				String teamNameText2 = userOwnTeam2Name.getText();
				String teamInitiText2 = userOwnTeam2Initials.getText();
				String teamMascotText2 = userOwnTeam2Mascot.getText();
				
				//Check if any of the textFields is empty, if empty alert the user if not proceed to next code block
					if (teamNameText.isEmpty() || teamInitiText.isEmpty() || teamMascotText.isEmpty() ||
					teamNameText2.isEmpty() || teamInitiText2.isEmpty() || teamMascotText2.isEmpty()){
						
						Alert emptyTextField = new Alert(AlertType.ERROR);
						 emptyTextField.setTitle("ERROR");
						 String errorMessage = "Please insert information in all text Fields";
						 emptyTextField.setContentText(errorMessage);
						 emptyTextField.show();
						 return;
						
					}
					else {
						
						try {
							//Create a file that will store the user input. 
							File newFile = new File("myTeams.txt");
							
								FileWriter writeTo = new FileWriter("myTeams.txt");
								PrintWriter printToFile = new PrintWriter(writeTo);
								
								printToFile.print(teamNameText + ", ");
								printToFile.print(teamInitiText + ", ");
								printToFile.print(teamMascotText + ", ");
								printToFile.print("\n");
								
								printToFile.print(teamNameText2 + ", ");
								printToFile.print(teamInitiText2 + ", ");
								printToFile.print(teamMascotText2 + ", ");
								printToFile.close();
								
								
						Alert addAlert = new Alert(AlertType.CONFIRMATION);
						addAlert.setTitle("Alert");
						String addMsg = "Your teams added successfully, please Import the file 'myTeams.txt' by using 'Import Teams' Button ";
						addAlert.setContentText(addMsg);
						addAlert.show();
						
							
						}
						
						catch (Exception ex) {
							
							Alert createFileError = new Alert(AlertType.ERROR);
							createFileError.setTitle("DONE!");
							 String errorMessage = "The file is not exists";
							 createFileError.setContentText(errorMessage);
							 createFileError.show();
							 return;
						}
						
					}
					
			});
			
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
		
		stage.setScene(new Scene(root, 700, 700));
		
		stage.show();
		
	}
}