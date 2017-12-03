/** Program: A.S.F Roster Manager
 * File: AlertBox.java
 * Summary: This class contains methods for creating an alert pop up window
 * Author: Chris Hyde
 * Date: December 2, 2017
 **/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public void display(String title, String message, String btnText){
        //Create a new stage to be displayed
        Stage window = new Stage();

        //formate the window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);


        Label lblMessage = new Label(message); // Set message to be displayed
        //Formate Label
        lblMessage.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        lblMessage.setTextFill(Color.ORANGE);

        Button btnClose = buttonMaker(btnText, event -> window.close()); // Create close button

        //create Pane to hold label and button
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setStyle("-fx-background-color: black;-fx-background-radius: 30; -fx-background-insets: 0; -fx-border-color: white; -fx-border-radius: 30; -fx-border-insets: 0;-fx-border-width: 4px;");
        vbox.getChildren().addAll(lblMessage, btnClose);

        // Create stack pane to hold vbox and color in background of the window
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: blue");
        pane.getChildren().add(vbox);

        //Set the scene and show the window
        Scene scene = new Scene(pane);
        window.setScene(scene);
        window.showAndWait();


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
}
