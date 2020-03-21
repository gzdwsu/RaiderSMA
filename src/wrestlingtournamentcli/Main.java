
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
 */
public class Main extends Application{
    static Error_Reporting log = new Error_Reporting();

    public static void main(String[] args) {
    Model m = new Model();
    Scanner s = new Scanner(System.in);
    log.createLogFiles();
    launch(args);
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
		layout.add(viewTeams, 0, 4);
		layout.add(viewWrestlers, 0, 2);
		layout.add(save, 0, 5);
		layout.add(saveTournament, 1, 5);
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