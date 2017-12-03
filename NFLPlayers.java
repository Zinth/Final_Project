/** Program: A.S.F Roster Manager
* File: NFLPlayers.java
* Summary: Base Class of NFL Players - Contains stats that every NFL player shares without going into positional stats.
* Author: Chris Hyde
* Date: December 2, 2017
**/

/* -- EDITS --
 * (November 25, 2017) Removed games won and lost because those are team driven stats not player driven. 
 */


public abstract class NFLPlayers {

	// Constructors:
	// No argument constructor.
	public NFLPlayers(){
		
	}

	// All properties Constructor.
	public NFLPlayers(String firstName, String lastName, String team, int age, int weight, int heightFeet, int heightInches, int seasonsPlayed, int teamNumber, int proBowls, int gamesMissed, boolean quarterback) {
		// set all data fields to the value of the corresponding argument. 
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		this.weight = weight;
		this.age = age;
		this.heightFeet = heightFeet;
		this.heightInches = heightInches;
		this.seasonsPlayed = seasonsPlayed;
		this.teamNumber = teamNumber;
		this.proBowls = proBowls;
		this.gamesMissed = gamesMissed;
		this.quarterback = quarterback;
	}
	
	
	//Data Fields
	private boolean quarterback;
	private String firstName;
	private String lastName; 
	private String team;
	private int weight; 
	private int age;
	private int teamNumber; 
	private int seasonsPlayed;
	private int proBowls; 
	private int heightFeet; 
	private int heightInches;
	private int gamesMissed;
	private int totalGames;
	private int[] gamesPlayed;
	private int[][] defensiveStats;
	private int[][] offensiveStats;
	private int[][] offensiveStats2;
	
	
	public String toString() {
		// Return all data fields in a structured way whenever an object of this class is printed. 
		return "PLAYER INFO: Name: " + formatPlayerInfo();
	}
	
	// Method to use in subclasses so I don't have to write this part of the toString a billion times. 
	public String formatPlayerInfo() {
		return getFirstName() + " " + getLastName() + ", Age: " + getAge() + ", Height: " + heightFeet + "'"
				+ heightInches + "\"" + ", Weight: " + this.getWeight() + ", Team: " + getTeam() + " #"
				+ getTeamNumber() + ", Seasons: " + getSeasonsPlayed() + ", Number of Pro-Bowls: " + getProBowls();
	}
	
	// Method to get an array of games played per season that will be set the getGames int[];
	public int[] gamesPerSeason() {
		int[] gamesPlayed = new int[getSeasonsPlayed()]; // set the array size to number of seasons
		int missedGames = getGamesMissed(); // number of games missed
		int missed = 0;// missed games per

		// Loop through gamesPlayed and set the number of games missed for the season
		for (int i = 0; i < gamesPlayed.length; i++) {
			missed = missedGames / gamesPlayed.length; //set number of missed games to subtract by
			if (missedGames < missed) {
				gamesPlayed[i] = 16 - missedGames; // if missedGames is less than missed subtract it from 16
			} else {
				gamesPlayed[i] = 16 - missed; // Subtract missed games from the number of games in a season
				missedGames -= missed; // de-increment missed games by number of games missed so far. 
			}

		}

		return gamesPlayed;
	}

	//To string[][] method
	public String[][] toStringArray(int[][] intArray){
		String[][] stringArray = new String[intArray.length][intArray[0].length];

		for(int i = 0; i < stringArray.length; i++){
			for(int j = 0; j < stringArray[i].length; j++){
				stringArray[i][j]= String.valueOf(intArray[i][j]);
			}
		}

		return stringArray;
	}

	
	//  --- Instance Variables Getters and Setters ---
	// firstName
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	// lastName
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
    
	// team
	public void setTeam(String team) {
		this.team = team;
	}
	
	public String getTeam() {
		return this.team;
	}
   
	// weight
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return this.weight;
	}

	// age
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return this.age;
	}

	// seasonsPlayed
	public void setSeasonsPlayed(int seasonsPlayed) {
		this.seasonsPlayed = seasonsPlayed;
	}
	
	public int getSeasonsPlayed() {
		return this.seasonsPlayed;
	}

	// teamNumber
	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}
	
	public int getTeamNumber() {
		return this.teamNumber;
	}
	
	// heightFeet
	public void setHeightFeet(int heightFeet) {
		this.heightFeet = heightFeet;
	}
	
	public int getHeightFeet() {
		return this.heightFeet;
	}
	
	// heightInches
	public void setHeightInches(int heightInches) {
		this.heightInches = heightInches;
	}
	
	public int getHeightInches() {
		return this.heightInches;
	}
	
	// proBowls
	public void setProBowls(int proBowls) {
		this.proBowls = proBowls;
	}
	
	public int getProBowls() {
		return this.proBowls;
	}

	// gamesMissed
	public int getGamesMissed() {
		int maxPossibleGames = getSeasonsPlayed()*16; // the max number of games possible in the players career.
		
		//Make sure number of games missed doesn't exceed number of max games possible
		if (this.gamesMissed > maxPossibleGames) {
			this.gamesMissed = (int) (maxPossibleGames - (Math.random()*maxPossibleGames - 16)); // if true change to a random number of missed games based on the max possible - 16
		}
		
		return gamesMissed;
	}

	public void setGamesMissed(int gamesMissed) {
		this.gamesMissed = gamesMissed;
	}

	// totalGames: No setter as it is set by other variables.
	public int getTotalGames() {
		totalGames = (getSeasonsPlayed() * 16) - getGamesMissed(); // total games is the number of games in a season times the number of seasons played minus the number of games missed
		return totalGames;
	}
	
	// gamesPlayed
	public int[] getGamesPlayed() {
		return this.gamesPlayed = gamesPerSeason();
	}

	//Defensive Stats
	public int[][] getDefensiveStats() {
		return this.defensiveStats;
	}

	public void setDefensiveStats(int[][] defensiveStats) {
		this.defensiveStats = defensiveStats;
	}

	//Offensive Stats
	public int[][] getOffensiveStats() {
		return this.offensiveStats;
	}

	public void setOffensiveStats(int[][] offensiveStats) {
		this.offensiveStats = offensiveStats;
	}

	//Offensive Stats
	public int[][] getOffensiveStats2() {
		return this.offensiveStats2;
	}

	public void setOffensiveStats2(int[][] offensiveStats2) {
		this.offensiveStats2 = offensiveStats2;
	}

	//Quarterback
	public boolean isQuarterback() {
		return quarterback;
	}

	public void setQuarterback(boolean quarterback) {
		this.quarterback = quarterback;
	}
}
