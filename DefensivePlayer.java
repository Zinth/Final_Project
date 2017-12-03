/** Program: A.S.F Roster Manager
* File: DefensivePlayers.java
* Summary: Subclass of NFL Players - Contains stats that every Defensive NFL player shares.
* Author: Chris Hyde
* Date: December 2, 2017
**/

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class DefensivePlayer extends NFLPlayers {
	
	//no-args constructor
	public DefensivePlayer() {
		
	}
	
	// Constructor with args including super from parent class. 
	public DefensivePlayer(String firstName, String lastName, String team,  int age, int weight, int heightFeet, int heightInches, int seasonsPlayed, int teamNumber, int proBowls, int gamesMissed, boolean quarterback, int positionIndex, int modifier) {
		super(firstName, lastName, team,  age, weight, heightFeet, heightInches, seasonsPlayed, teamNumber, proBowls, gamesMissed, quarterback);
		this.position = DEFENSE_POSITIONS[positionIndex];
		this.modifier = modifier; // Modifier that changes the player score per player
		setPlayerStats(statGenerator()); //Generate all the stats on construction of the Defensive Player
		this.setDefensiveStats(getPlayerStats());
	}

	//Instance variables
	private String position;
	private int[][] playerStats;
	private int modifier;
	private int totalInterceptions;
	private int totalSacks;
	private int totalGroupTackles;
	private int totalSoloTackles;
	private int totalForcedFumbles;
	private int totalTouchdowns;
	private int careerTackles;
	private int totalGamesPlayed;
	private ArrayList<Integer> statList;


	// Constants
	private static final String[] DEFENSE_POSITIONS= {"Defensive Line", "Linebacker", "Cornerback", "Safety "}; 
	
	//Override the toString() method from superclass
	@Override
	public String toString() {
		String statHeader = String.format("%s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s","SEASON","GAMES","RANK", "INT", "SACK", "AST", "SOLO", "TOT", "FF", "TD"); // Header for each row of stats
		return getPosition() + ": " + formatPlayerInfo() + "\n" + statHeader.replaceAll("[,]", "|") + "|\n" + formatAllStats() + "\n\n"; // The actual returned string for this overriden toString()
	}
	
	
	
	//Method to print the players all stats
	public String formatAllStats() {
		String allStats = ""; // String for holding all stats
		
		//Loop through all elements of playerStats and add them to allStats String. 
		for(int i = 0; i < getPlayerStats().length; i++) {
			allStats += String.format("%-6d", i + 1) + "|"; // Get Season #
			for(int j = 0; j < getPlayerStats()[i].length; j++) {
				allStats += String.format("%-6d", getPlayerStats()[i][j]) + "|"; // Get each stat for that season
			}
			allStats += "\n"; // Next Line
		}
		
		//Return allStats when method is ran. 
		return allStats;
	}
	
	
	//Method to generate an array random set of stats based on seasons played
	public int[][] statGenerator() {
		int[][] seasonStats = new int[getSeasonsPlayed()][10];

		// Variable set to season averages
		int interceptions = 0;
		int sacks = 0;
		int groupTackles = 0;
		int soloTackles = 0;
		int forcedFumbles = 0;
		int touchdowns = 0;
		int totalTackles = 0;
		int performanceRanking = 0;

		//Modifier base
		int statModifier = getModifier();

		for (int i = 0; i < seasonStats.length; i++) {
			// Number of games played that season.
			int gamesPlayed = getGamesPlayed()[i];

			// Set the modified stats based on the modifier
			interceptions = (statModifier % 4) * gamesPlayed;
			sacks = (statModifier % 4) * gamesPlayed;
			groupTackles = (75 + (statModifier % 10)) * gamesPlayed;
			soloTackles = (45 + (statModifier % 10)) * gamesPlayed;
			forcedFumbles = (statModifier % 4) * gamesPlayed;
			touchdowns = (statModifier % 4) * gamesPlayed;
			totalTackles = groupTackles + soloTackles;

			// Peformance ranking is the average of all stats.
			performanceRanking = ((interceptions + sacks + totalTackles + forcedFumbles + touchdowns) / 6) /10;

			//Increment statModifier
			statModifier++;

			// set the value of the generated stat to the position in seasonStats[i][j]
			for (int j = 0; j < seasonStats[i].length; j++) {
				//Add a stat to to the subarray based on index.
				switch (j) {
					case 0:
						seasonStats[i][j] = i + 1; //Season Stat
						break;
					case 1:
						seasonStats[i][j] = gamesPlayed;
						setTotalGamesPlayed(getTotalGamesPlayed() + gamesPlayed);
						break;
					case 2:
						seasonStats[i][j] = performanceRanking;
						break;
					case 3:
						seasonStats[i][j] = interceptions;
						setTotalInterceptions(getTotalInterceptions() + interceptions);
						break;
					case 4:
						seasonStats[i][j] = sacks;
						setTotalSacks(getTotalSacks() + sacks);
						break;
					case 5:
						seasonStats[i][j] = groupTackles;
						setTotalGroupTackles(getTotalGroupTackles() + groupTackles);
						break;
					case 6:
						seasonStats[i][j] = soloTackles;
						setTotalSoloTackles(getTotalSoloTackles() + soloTackles);
						break;
					case 7:
						seasonStats[i][j] = totalTackles;
						break;
					case 8:
						seasonStats[i][j] = forcedFumbles;
						setTotalForcedFumbles(getTotalForcedFumbles() + forcedFumbles);
						break;
					case 9:
						seasonStats[i][j] = touchdowns;
						setTotalTouchdowns(getTotalTouchdowns() + touchdowns);
						break;
				}
			}

		}
		
		
		return seasonStats;
	}
	
	
	//  --- Instance Variables Getters and Setters ---
	// position
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	// DEFENCE_POSITONS
	public String[] getDEFENCE_POSITIONS() {
		return DEFENSE_POSITIONS; // returns the String array of all Defensive Positions
	}

	// modifier
	 public int getModifier(){
		return this.modifier;
	 }

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	// playerStats
	public int[][] getPlayerStats(){
		return playerStats;
	}

	public void setPlayerStats(int[][] playerStats){
		this.playerStats = playerStats;
	}

	//totalInterceptions
	public int getTotalInterceptions() {
		return totalInterceptions;
	}

	public void setTotalInterceptions(int totalInterceptions) {
		this.totalInterceptions = totalInterceptions;
	}

	//totalSacks
	public int getTotalSacks() {
		return totalSacks;
	}

	public void setTotalSacks(int totalSacks) {
		this.totalSacks = totalSacks;
	}

	//totalGroupTackles
	public int getTotalGroupTackles() {
		return totalGroupTackles;
	}

	public void setTotalGroupTackles(int totalGroupTackles) {
		this.totalGroupTackles = totalGroupTackles;
	}

	//totalSoloTackles
	public int getTotalSoloTackles() {
		return totalSoloTackles;
	}

	public void setTotalSoloTackles(int totalSoloTackles) {
		this.totalSoloTackles = totalSoloTackles;
	}

	//totalForcedFumbles
	public int getTotalForcedFumbles() {
		return totalForcedFumbles;
	}

	public void setTotalForcedFumbles(int totalForcedFumbles) {
		this.totalForcedFumbles = totalForcedFumbles;
	}

	//totalTotalTouchdowns
	public int getTotalTouchdowns() {
		return totalTouchdowns;
	}

	public void setTotalTouchdowns(int totalTouchdowns) {
		this.totalTouchdowns = totalTouchdowns;
	}

	//careerTackles
	public int getCareerTackles() {
		return getTotalGroupTackles() + getTotalSoloTackles();
	}

	//totalGamesPlayed
	public int getTotalGamesPlayed(){
		return this.totalGamesPlayed;
	}

	public void setTotalGamesPlayed(int totalGamesPlayed){
		this.totalGamesPlayed = totalGamesPlayed;
	}

	//seasons


}
