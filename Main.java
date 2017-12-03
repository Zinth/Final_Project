/** Program: A.S.F Roster Manager
 * File: Main.java
 * Summary: This class contains the main method and all Gui elements
 * Author: Chris Hyde
 * Date: December 2, 2017
 **/

import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import javafx.application.Application;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main extends Application{
    //Class calls
    private NFLPlayerManager manger = new NFLPlayerManager(11);
    private AlertBox alert = new AlertBox();

    // Variables
    private int panelNumber;
    private StackPane pane;
    private ArrayList<NFLPlayers> chosenPlayers = new ArrayList<>();
    private Stage window;
    private Scene login;
    private Scene roster;
    private TableView<NFLPlayers> rosterTable;
    private TableView<NFLPlayers> lineupTable;
    private TableView<NFLPlayers> statTable;

    // Main Method
    public static void main(String[] args){
        Application.launch();

    }

    // Start Application Method Override
    @Override
    public void start(Stage primaryStage){

        //Set the value of our 4 scenes
        setLogin(new Scene(logInPane(), 1280, 720));

        //set window to be primaryStage
        setWindow(primaryStage);

        //Set window Title
        getWindow().setTitle("A.S.F Football Roster Manager");


        //Set the first scene the user sees in the primary stage
        getWindow().setScene(getLogin());
        //Keep window from being resizable
        getWindow().setResizable(false);
        // Display window
        getWindow().show();
    }

    // --- Methods for GUI Panes --

    // fake log in pane
    public StackPane logInPane(){
        // Create a StackPlane that will hold an image and BorderPlane
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: blue");


        //ImageView with image
        ImageView backGroundImage = new ImageView(new Image("images//FakeLogin.png"));

        //Create Loggin Button
        Button btLoggin = buttonMaker("LOG IN", e -> {
            setRoster(new Scene(rosterPane(), 1280,720));
            getWindow().setScene(getRoster());
        });

        //Create an HBox to hold the button
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(btLoggin);

        //Create BorderPane for holding the HBox
        BorderPane borderPane = new BorderPane();
        // Set the padding of the borderPane
        borderPane.setPadding(new Insets(10,10,30,10));
        borderPane.setBottom(hBox);

        //Add the background Image and the borderPane to the pane
        pane.getChildren().addAll(backGroundImage, borderPane);


        return pane;
    }


    // Roster Pane
    public BorderPane rosterPane(){
        // Create all panels for this stage scene
        BorderPane borderPane = new BorderPane();
        VBox tableBox = new VBox();
        HBox buttonBox = new HBox();

        // Format borderPane
        borderPane.setStyle("-fx-background-color: blue");
        borderPane.setPadding(new Insets(5,0,5,0));


        //Format tableBox
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setStyle("-fx-background-color: black;-fx-background-radius: 30; -fx-background-insets: 0; -fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0;-fx-border-width: 4px;");
        tableBox.setPadding(new Insets(10,10,10,10));
        tableBox.setMaxWidth(800);


        //Format buttonBox
        buttonBox.setPadding(new Insets(10,10,10,10));
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);


        //ImageView with image
        ImageView headerImage = new ImageView(new Image("images//RosterHeader.png"));
        ImageView logoLeftImage = new ImageView(new Image("images//LogoLeft.png"));
        ImageView logoRightImage = new ImageView(new Image("images//LogoRight.png"));

        //Create Table
        setRosterTable(getPlayerTable(getManger().getPlayerList()));

        //Create insturction Text
        Text instructions = new Text("Select a player and choose an action:");
        instructions.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        instructions.setFill(Color.WHITE);

        //Create Buttons
        //Add button that adds player to the chosenPlayer ArrayList and remove from the manager playerList
        Button btnAdd = buttonMaker("ADD", event -> {
            getChosenPlayers().add(getRosterTable().getSelectionModel().getSelectedItem());
            getRosterTable().getItems().remove(getRosterTable().getSelectionModel().getSelectedItem());
        });

        //View Stats button that changes the pane to statPane
        Button btnStats = buttonMaker("VIEW STATS", event -> {
            //Make sure a player is selected before viewing thier stats
            if(rosterTable.getSelectionModel().getSelectedItem() != null) {
                getWindow().setScene(new Scene(statPane(getRosterTable().getSelectionModel().getSelectedIndex(), getManger().getPlayerList()), 1280, 720));
            }else{
                alert.display("Error: No Player Selected", "Please select a player to view thier stats!", "OK");
            }

            ;});

        // View Lineup button
        Button btnLineup = buttonMaker( "MY LINEUP", event -> {
            getWindow().setScene(new Scene(lineupPane(), 1280,720));
            getLineupTable().requestFocus();
            getLineupTable().getSelectionModel().select(0);
            getLineupTable().getFocusModel().focus(0);
        });

        //Add Table to tableBox
        tableBox.getChildren().addAll(instructions,getRosterTable());

        //Add buttons to buttonBox
        buttonBox.getChildren().addAll(btnAdd, btnStats, btnLineup);


        borderPane.setTop(headerImage);
        borderPane.setCenter(tableBox);
        borderPane.setBottom(buttonBox);
        borderPane.setLeft(logoLeftImage);
        borderPane.setRight(logoRightImage);


        return borderPane;
    }

    // Stat Pane
    public BorderPane statPane(int index, ArrayList<NFLPlayers> playerList){
        //Get the NFLPlayer to show stats for
        NFLPlayers player = playerList.get(index);

        //Font
        Font textFont = Font.font("Calibri", FontWeight.BOLD,  20);


        //Array of stat names
        String[] defensiveStatNames = {"SEASON","GAMES","RANK", "INT", "SACK", "AST", "SOLO", "TOT", "FF", "TD"}; // Deffensive Stat Names
        String[] passingStatNames = {"SEASON", "GAMES", "RANK", "ATT", "COMP", "PCT", "YARDS", "AVG", "SACK", "INT", "TD"}; // Offensive Passing Stat Names
        String[] rushingStatNames = {"SEASON", "GAMES", "RANK", "ATT", "LONG", "20+", "YARDS", "AVG", "FUM", "1DN","TD"}; // Offensive Rushing Stat Names
        String[] recievingStatNames = {"SEASON", "GAMES", "RANK", "REC", "LONG", "YAC", "YARDS", "AVG", "FUM", "1DN", "TD"}; // Offensive Recieving Stat Names

        //Create inset for easy padding
        Insets padding = new Insets(10,10,10,10);


        // Create Panes
        BorderPane borderPane = new BorderPane();
        HBox butonHbox = new HBox(); // Pane for holding the buttons
        HBox nameHBox = new HBox(); // Pane to hold The player full Name and position
        HBox infoHBox = new HBox(); // Pane to hold player info like height and weight
        HBox gameInfoHBox = new HBox(); // Pane to hold game info like seasons played & probowls played.
        VBox centerVBox = new VBox(); // Vbox to hold all the Panes in the center
        VBox tableVBox = new VBox(); // VBox to hold all the tables in the center

        //Format Panes
        borderPane.setStyle("-fx-background-color: blue;"); //Set the background color of the BorderPane

        tableVBox.setAlignment(Pos.CENTER); //center the HBox on the screen
        tableVBox.setPadding(padding); // padd the sides of the hbox
        tableVBox.setSpacing(5); // set the spacing of nodes within the hbox
        tableVBox.setMaxWidth(600);

        nameHBox.setAlignment(Pos.CENTER); //center the HBox on the screen
        nameHBox.setPadding(padding); // padd the sides of the hbox
        nameHBox.setSpacing(5); // set the spacing of nodes within the hbox

        infoHBox.setAlignment(Pos.CENTER); //center the HBox on the screen
        infoHBox.setPadding(padding); // padd the sides of the hbox
        infoHBox.setSpacing(10); // set the spacing of nodes within the hbox
        infoHBox.setStyle("-fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0;-fx-border-width: 4px;");

        gameInfoHBox.setAlignment(Pos.CENTER); //center the HBox on the screen
        gameInfoHBox.setPadding(padding); // padd the sides of the hbox
        gameInfoHBox.setSpacing(10); // set the spacing of nodes within the hbox
        gameInfoHBox.setStyle("-fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0;-fx-border-width: 4px;");

        centerVBox.setPadding(padding);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setSpacing(5);
        centerVBox.setStyle("-fx-background-color: black;-fx-background-radius: 30; -fx-background-insets: 0; -fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0;-fx-border-width: 4px;");

        butonHbox.setAlignment(Pos.CENTER); //center the HBox on the screen
        butonHbox.setPadding(padding); // padd the sides of the hbox
        butonHbox.setSpacing(5); // set the spacing of nodes within the hbox

        //Create nodes to go into panes
        // create ImageView for StatHeader image
        ImageView statHeader = new ImageView(new Image("images\\StatHeader.png"));
        ImageView logoLeftImage = new ImageView(new Image("images//LogoLeft.png"));
        ImageView logoRightImage = new ImageView(new Image("images//LogoRight.png"));

        //Create and format text to hold player name
        Text nameTxt = new Text(player.getFirstName() + " " + player.getLastName());
        nameTxt.setFont(textFont);
        nameTxt.setFill(Color.WHITE);

        //Create and format text to hold player info
        Text ageTxt = new Text("Age: " + player.getAge());
        ageTxt.setFont(textFont);
        ageTxt.setFill(Color.WHITE);

        Text heightTxt = new Text(" Height: " + player.getHeightFeet() + "'" + player.getHeightInches() + "\"");
        heightTxt.setFont(textFont);
        heightTxt.setFill(Color.WHITE);

        Text weightTxt = new Text(" Weight: " +player.getWeight());
        weightTxt.setFont(textFont);
        weightTxt.setFill(Color.WHITE);

        //Create and format text to hold player game info
        Text teamTxt = new Text("Team: " + player.getTeam() + " #" + player.getTeamNumber());
        teamTxt.setFont(textFont);
        teamTxt.setFill(Color.WHITE);

        Text seasonTxt = new Text(" Seasons Played: " + player.getSeasonsPlayed());
        seasonTxt.setFont(textFont);
        seasonTxt.setFill(Color.WHITE);

        Text probowlTxt = new Text(" Probowls: " + player.getProBowls());
        probowlTxt.setFont(textFont);
        probowlTxt.setFill(Color.WHITE);

        //Create and format text to hold stat label
        Text passingTxt = new Text("---PASSING---");
        passingTxt.setFont(textFont);
        passingTxt.setFill(Color.WHITE);

        //Create and format text to hold stat label
        Text rushingTxt = new Text("---RUSHING---");
        rushingTxt.setFont(textFont);
        rushingTxt.setFill(Color.WHITE);

        //Create and format text to hold  stat lable
        Text receivingTxt = new Text("---RECEIVING---");
        receivingTxt.setFont(textFont);
        receivingTxt.setFill(Color.WHITE);

        //Create and format text to hold  stat lable
        Text defenceTxt = new Text("---DEFENCE---");
        defenceTxt.setFont(textFont);
        defenceTxt.setFill(Color.WHITE);

        //Create Text to hold stat Glossary
        Text offensiveGlossary = new Text();
        offensiveGlossary.setFill(Color.WHITE);

        Text rushingGlossary = new Text("ATT: Rushing attempts YDS: Rushing yards AVG: Yards per rush attempt LONG: Longest rush 20+: Carries over 20 yards TD: Rushing touchdowns FUM: Rushing fumbles 1DN: Rushing first downs");
        rushingGlossary.setFill(Color.WHITE);

        Text defenseGlossary = new Text("SOLO: Unassisted tackles AST: Assisted tackles TOT: Tackles SACK: Sacks INT: Interceptions FF: Forced fumbles TD: Defensive touchdowns");
        defenseGlossary.setFill(Color.WHITE);



        //To Roster button
        Button btnBack = buttonMaker("BACK", event -> {
            // return user to the roster scene
            if(playerList == chosenPlayers){
                getWindow().setScene(new Scene(lineupPane(), 1280,720));
                getLineupTable().requestFocus();
                getLineupTable().getSelectionModel().select(0);
                getLineupTable().getFocusModel().focus(0);
            }else {
                getWindow().setScene(getRoster());
            }
        });

        //Add button
        Button btnAdd = buttonMaker("ADD PLAYER TO LINEUP", event -> {
            // Add player to chosenPlayer ArrayList
            chosenPlayers.add(getManger().getPlayerList().get(index));
            //Remove player from Roster Table
            getRosterTable().getItems().remove(getManger().getPlayerList().get(index));
            //return player to roster
            getWindow().setScene(getRoster());
        });

        //Remove button
        Button btnRemove = buttonMaker("REMOVE PLAYER FROM LINEUP", event -> {
            // Remove the player form the lineup
            chosenPlayers.remove(player);

            //Add player back to roster.
            rosterTable.getItems().add(player);

            //return player to lineup
            getWindow().setScene(new Scene(lineupPane(), 1280,720));
            getLineupTable().requestFocus();
            getLineupTable().getSelectionModel().select(0);
            getLineupTable().getFocusModel().focus(0);
        });



        // Create table for dispaling the players game stats
        if(player instanceof OffensivePlayer){
            //Table that will store Passing or Receiving stats
            TableView<ObservableList<String>> offensiveTable;

            //Create Text to hold offensive stat label
            Text offensiveTxt = new Text();

            //Check if player is a quarterback and set offensiveTable to passing else set Receiving
            if(player.isQuarterback()){
                offensiveTable = createTableView(player.toStringArray(player.getOffensiveStats()), passingStatNames);
                offensiveTxt = passingTxt;
                offensiveGlossary.setText("ATT: Passing attempts COMP: Completions PCT: Completion percentage YDS: Gross passing yards AVG: Yards per pass attempt SACK: Sacks INT: Interceptions TD: Passing touchdowns");

            }
            else{
                offensiveTable = createTableView(player.toStringArray(player.getOffensiveStats()), recievingStatNames);
                offensiveTxt = receivingTxt;
                offensiveGlossary.setText("REC: Receptions YDS: Receiving yards AVG: Average yards per reception TD: Receiving touchdowns LONG: Longest reception FUM: Receiving fumbles YAC: Yards after catch 1DN: Receiving first downs");
            }
            //Table that will hold rushing stats for every offensive player
            TableView<ObservableList<String>> rushingTable = createTableView(player.toStringArray(player.getOffensiveStats2()), rushingStatNames);

            //Add tables to tableVbox
            tableVBox.getChildren().addAll(offensiveTxt, offensiveGlossary, offensiveTable, rushingTxt, rushingGlossary, rushingTable);


        }else {
            //Table that will hold ever Defensive Players Stat
            TableView<ObservableList<String>> defenceTable = createTableView(player.toStringArray(player.getDefensiveStats()), defensiveStatNames);

            //Add table to table Vbox
            tableVBox.getChildren().addAll(defenceTxt, defenseGlossary, defenceTable);
        }

        // Add text to the appropriate Panel
        nameHBox.getChildren().add(nameTxt);
        infoHBox.getChildren().addAll(ageTxt, heightTxt, weightTxt);
        gameInfoHBox.getChildren().addAll(teamTxt, seasonTxt, probowlTxt);

        //Check wich scene this scene loaded from by checking the ArrayList and set buttons.
        if(playerList == chosenPlayers) {
            butonHbox.getChildren().addAll(btnRemove, btnBack);
        }else{
            butonHbox.getChildren().addAll(btnAdd,btnBack);
        }

        //Add all Center panels to the CenterVbox
        centerVBox.getChildren().addAll(nameHBox, infoHBox, gameInfoHBox, tableVBox);

        //Add all nodes to the boderPane
        borderPane.setTop(statHeader);
        borderPane.setBottom(butonHbox);
        borderPane.setCenter(centerVBox);
        borderPane.setRight(logoRightImage);
        borderPane.setLeft( logoLeftImage);


        return borderPane;
    }

    // Lineup Pane
    public BorderPane lineupPane(){
        // Create all panels for this stage scene
        BorderPane borderPane = new BorderPane();
        VBox tableBox = new VBox();
        HBox buttonBox = new HBox();

        // Format borderPane
        borderPane.setStyle("-fx-background-color: blue");
        borderPane.setPadding(new Insets(5,0,5,0));

        //Format tableBox
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setStyle("-fx-background-color: black;-fx-background-radius: 30; -fx-background-insets: 0; -fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0;-fx-border-width: 4px;");
        tableBox.setPadding(new Insets(10,10,10,10));
        tableBox.setMaxWidth(800);



        //Format buttonBox
        buttonBox.setPadding(new Insets(10,10,10,10));
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);


        //ImageView with image
        ImageView headerImage = new ImageView(new Image("images//LineupHeader.png"));
        ImageView logoLeftImage = new ImageView(new Image("images//LogoLeft.png"));
        ImageView logoRightImage = new ImageView(new Image("images//LogoRight.png"));

        //Create Table
        setLineupTable(getPlayerTable(getChosenPlayers()));

        //Create insturction Text
        Text instructions = new Text("Select a player and choose an action or Confirm Lineup:");
        instructions.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        instructions.setFill(Color.WHITE);

        //Create Buttons
        //Remove button that removes the player from the chosenList and add player back to the manager playerList.
        Button btnRemove = buttonMaker("REMOVE", event -> {
            getRosterTable().getItems().add(getLineupTable().getSelectionModel().getSelectedItem());
            getChosenPlayers().remove(getLineupTable().getSelectionModel().getSelectedItem());
            lineupTable.refresh();

        });

        //View Stats button that changes the pane to statPane
        Button btnStats = buttonMaker("VIEW STATS", event -> {
            if(lineupTable.getSelectionModel().getSelectedItem() != null) {
                getWindow().setScene(new Scene(statPane(getLineupTable().getSelectionModel().getSelectedIndex(), chosenPlayers)));
            }else if(chosenPlayers.size() == 0){
                alert.display("Error: No Players in Lineup", "Please add a player to your lineup!", "OK");
            }else{
                alert.display("Error: No Player Selected", "Please select a player to view thier stats!", "OK");
            }

        });

        //Back Button
        Button btnBack = buttonMaker("BACK", event -> {
                // return user to the roster scene
                getWindow().setScene(getRoster());
        });

        //Button Confirm Lineup
        Button btnConfirm = buttonMaker("CONFIRM SELECTION", event -> {
            if(chosenPlayers.size() < 11) {
                alert.display("Error: No Players in Lineup", "Please add at least 11 players to your lineup!", "OK");
            }else {
                //Pop up confirmation window
                alert.display("Lineup Confirmed", "Your Lineup has been confirmed!", "OK");

                //Return user to logging page
                getWindow().setScene(getLogin());

                //Clear chosenPlayer ArrayList
                getChosenPlayers().clear();

                //rebuild Roster Tables
                setRosterTable(getPlayerTable(getManger().getPlayerList()));
            }
        });

        //Add Table to tableBox
        tableBox.getChildren().addAll(instructions, getLineupTable());

        //Add buttons to buttonBox
        buttonBox.getChildren().addAll(btnConfirm, btnStats, btnRemove, btnBack);


        borderPane.setTop(headerImage);
        borderPane.setCenter(tableBox);
        borderPane.setBottom(buttonBox);
        borderPane.setLeft(logoLeftImage);
        borderPane.setRight(logoRightImage);




        return borderPane;
    }

    public TableView<NFLPlayers> getPlayerTable(ArrayList<NFLPlayers> playerList){
        // -- Create Table Columns --
        //Positions
        TableColumn<NFLPlayers, String> postionColumn = new TableColumn<>("Position:");
        postionColumn.setMinWidth(100);
        postionColumn.setCellValueFactory(new PropertyValueFactory("position"));
        postionColumn.setResizable(false);

        //First Name
        TableColumn<NFLPlayers, String> firstNameColumn = new TableColumn<>("First Name:");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        firstNameColumn.setResizable(false);

        //Last Name
        TableColumn<NFLPlayers, String> lastNameColumn = new TableColumn<>("Last Name:");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        lastNameColumn.setResizable(false);

        //Team
        TableColumn<NFLPlayers, String> teamColumn = new TableColumn<>("Team:");
        teamColumn.setMinWidth(200);
        teamColumn.setCellValueFactory(new PropertyValueFactory("team"));
        teamColumn.setResizable(false);

        //Seasons
        TableColumn<NFLPlayers, Integer> seasonsColumn = new TableColumn<>("Seasons:");
        seasonsColumn.setMinWidth(15);
        seasonsColumn.setCellValueFactory(new PropertyValueFactory("seasonsPlayed"));
        seasonsColumn.setResizable(false);

        //Probowls
        TableColumn<NFLPlayers, Integer> probowlColumn = new TableColumn<>("Probowls");
        probowlColumn.setMinWidth(15);
        probowlColumn.setCellValueFactory(new PropertyValueFactory("proBowls"));
        probowlColumn.setResizable(false);

        TableView table = new TableView();
        table.setItems(getPlayerObservable(playerList));
        table.getColumns().addAll(postionColumn, firstNameColumn, lastNameColumn, teamColumn, seasonsColumn, probowlColumn);


        table.setMaxWidth(700);
        table.setMaxHeight(700);

        table.applyCss();

        return table;
    }

    //Method to get all base Player stats from an ArrayList as an ObservableList
    public ObservableList<NFLPlayers> getPlayerObservable(ArrayList<NFLPlayers> playerList){
        ObservableList<NFLPlayers> playersObservableList = FXCollections.observableList(playerList);
        playersObservableList.addAll();
        return playersObservableList;
    }



    // A method that defines the look for every button in this application
    public Button buttonMaker(String btName, EventHandler<ActionEvent> handler){
        Button button = new Button(btName);
        // Set the font and text size of the text in the button
        button.setFont(Font.font("Calibri", FontWeight.BOLD, 26));

        // Set initial style of the button
        button.setStyle("-fx-background-color: black;-fx-background-radius: 30; -fx-background-insets: 0; " +
                "-fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0; -fx-text-fill: white; -fx-border-width: 4px;");

        // change style on mouse enter
        button.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                button.setStyle("-fx-background-color: white;-fx-background-radius: 30; -fx-background-insets: 0; " +
                        "-fx-border-color: black; -fx-border-radius: 30; -fx-border-insets: 0; -fx-text-fill: black; -fx-border-width: 4px;");
            }});

        // change style back on mouse exit
        button.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                button.setStyle("-fx-background-color: black;-fx-background-radius: 30; -fx-background-insets: 0; " +
                        "-fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0; -fx-text-fill: white; -fx-border-width: 4px;");
            }});

        // Set the action handler of the button
        button.setOnAction(handler);
        // Return the button
        return button;
    }

    // Method for turning a 2d string array in a list of object containing 1d arrays
    private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        for (String[] row : dataArray) {
            data.add(FXCollections.observableArrayList(row));
        }

        return data;
    }

    //Method for creating tables using the buildData Method
    private TableView<ObservableList<String>> createTableView(String[][] dataArray, String[] columnNames) {
        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.setItems(buildData(dataArray));

        for (int i = 0; i < dataArray[0].length; i++) {
            final int curCol = i;
            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames[i]
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }

        tableView.setMaxHeight(200);

        return tableView;
    }

    //Get pane

    public StackPane getPane() {
        return pane;
    }

    public void setPane(StackPane pane) {
        this.pane = pane;
    }

    public NFLPlayerManager getManger() {
        return manger;
    }

    public ArrayList<NFLPlayers> getChosenPlayers() {
        return chosenPlayers;
    }

    public void setChosenPlayers(ArrayList<NFLPlayers> chosenPlayers) {
        this.chosenPlayers = chosenPlayers;
    }


    //Log in
    public Scene getLogin() {
        return login;
    }

    public void setLogin(Scene login) {
        this.login = login;
    }

    //Roster
    public Scene getRoster() {
        return roster;
    }

    public void setRoster(Scene roster) {
        this.roster = roster;
    }

    //Window
    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    //RosterTable
    public TableView<NFLPlayers> getRosterTable() {
        return rosterTable;
    }

    public void setRosterTable(TableView<NFLPlayers> rosterTable) {
        this.rosterTable = rosterTable;
    }

    //LineupTable
    public TableView<NFLPlayers> getLineupTable() {
        return lineupTable;
    }

    public void setLineupTable(TableView<NFLPlayers> lineupTable) {
        this.lineupTable = lineupTable;
    }

    //StatTable
    public TableView<NFLPlayers> getStatTable() {
        return statTable;
    }

    public void setStatTable(TableView<NFLPlayers> statTable) {
        this.statTable = statTable;
    }
}
