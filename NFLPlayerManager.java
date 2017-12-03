/** Program: A.S.F Roster Manager
* File: NFLPlayerManager.java
* Summary: Class that stores and creates NFL players from the NFLPlayers class
* Author: Chris Hyde
* Date: December 2, 2017
**/

import java.util.ArrayList;
import java.util.Random;

public class NFLPlayerManager {
	
	//Basic constructor with no args
	public NFLPlayerManager() {
		
	}
	
	public NFLPlayerManager(int numOfPlayers) {
		createPlayers(numOfPlayers);
	}
	
	private Random random = new Random();
	
	// An array to store 6 NFLPlayers object
	private ArrayList<NFLPlayers> playerList = new ArrayList<>();
	
	
	// Method that randomly fills the playerList with NFLPlayers.
	public void createPlayers(int numOfPlayers) {
		// Array of 22 first names for player generation
		String[] firstNames = {"Stan", "Jim", "Bob", "George", "Henry", "Steve", "Troy", "Correy", "Conner" , "Harvey", "Chris", "Tim", "Jake", "Alan", "Adam", "Keeth", "John", "Tom", "Victor", "Clark", "Bruce" , "Ron", "Peter"};
		//Array of 22 last names for random player generation
		String[] lastNames = {"Harvey", "Connors", "Smith", "Henley", "Lorance", "Manning", "Dent", "White", "Black", "Fard", "Musk", "Bloom", "Newman", "Johnson", "Long", "Adams", "Kent", "Wayn", "Sawyer", "Carter", "Banks", "Bell", "Parker"};
		// Array of every NFL team for random player Generation
		String[] teams = { "Arizona Cardinals", "Atlanta Falcons", "Baltimore Ravens", "Buffalo Bills",
				"Carolina Panthers", "Chicago Bears", "Cincinnati Bengals", "Cleveland Browns", "Dallas Cowboys",
				"Denver Broncos", "Detroit Lions", "Green Bay Packers", "Houston Texans", "Indianapolis Colts",
				"Jacksonville Jaguars", "Kansas City Chiefs", "Los Angeles Rams", "Los Angeles Chargers",
				"Miami Dolphins", "Minnesota Vikings", "New England Patriots", "New Orleans Saints", "New York Giants",
				"New York Jets", "Oakland Raiders", "Philadelphia Eagles", "Pittsburgh Steelers", "San Francisco 49ers",
				"Seattle Seahawks", "Tampa Bay Buccaneers", "Tennessee Titans", "Washington Redskins", };

		int modifier = 0;
		
		// Loop for creating Random Defensive Players
		for (int i = 0; i < numOfPlayers; i++) {
			modifier++; // Increment the modifier

			int wieght = ((modifier % 10) + 20) * 10; // Generate a random weight from 150lbs to 300lbs
			int heightFeet = 5 + (modifier % 2); // Generate a random height in feet from 4 to 6
			int heightInches = modifier % 12; // Generate a random height in inches from 0 to 12
			int teamNumber = (modifier % 10) * 10; // Generate a random team number from 1 to 100
			int seasonsPlayed = modifier % 19 + 1; // Generate a random seasons played between 1 and 20
			int gamesMissed = (modifier * 10) % seasonsPlayed * 8; // Generate a random number of games missed based on  half the number of games that could have been played.
			int age = (seasonsPlayed % 5) + 18 + seasonsPlayed; // generate a random age based on min of age 18 to 24 then add seasons played.
			int positionIndex = modifier % 4; // set the firs positionIndex for defensive player
			boolean quaterback = false; // set the boolean for quarterback default to false

			// Add a new defensive player to the playerList ArrayList using the above generated information
			setPlayerList(new DefensivePlayer(firstNames[i], lastNames[i], teams[i], age, wieght, heightFeet, heightInches, seasonsPlayed, teamNumber, modifier % 3, gamesMissed, quaterback, positionIndex, modifier  +1));

			positionIndex = modifier % 6; // Change for offensive player positions

			if(positionIndex == 0){
				quaterback = true;
			}

			// Add a new Offensive Player to the playerList ArrayList using the above generated information
			setPlayerList(new OffensivePlayer(firstNames[i+11],
					lastNames[i+11], teams[i+11], age, wieght, heightFeet, heightInches, seasonsPlayed, teamNumber, modifier % 3, gamesMissed, quaterback,
					positionIndex, modifier +1));
		}
	}
	
	// toString Method.
	public String toString() {
		// Returns all elements in the ArrayList as thier toString() representation.
		return playerList.toString();
	}
	
	//Getter for the playerList Array
	public ArrayList<NFLPlayers> getPlayerList() {
		return this.playerList;
	}
	
	//Setter for changing an element of the playerList array
	public void setPlayerList(NFLPlayers player) {
		playerList.add(player);
	}
	

}
