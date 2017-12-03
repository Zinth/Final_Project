/** Program: A.S.F Roster Manager
* File: OffensivePlayers.java
* Summary: Subclass of NFLPlayers - Contains stats that every Offensive NFL player shares.
* Author: Chris Hyde
* Date: November 30, 2017
**/

import java.util.ArrayList;
import java.util.Random;

public class OffensivePlayer extends NFLPlayers{
	// No-Args Condtuctor
	public OffensivePlayer() {
		
	}
	
	//Constructor with Args
	public OffensivePlayer(String firstName, String lastName, String team,  int age, int weight, int heightFeet, int heightInches, int seasonsPlayed, int teamNumber, int proBowls, int gamesMissed, boolean quarterback, int positionIndex, int modifier) {
		super(firstName, lastName, team,  age, weight, heightFeet, heightInches, seasonsPlayed, teamNumber, proBowls, gamesMissed, quarterback);
		this.position = OFFENSIVE_POSITIONS[positionIndex]; // Set the position based on the index of Offensive Positions.
		this.modifier = modifier; // Modifier that changes the average player score per season.

        //Set offensive stats using the generator
        if(getPosition() == OFFENSIVE_POSITIONS[0]) {
            setPassingStats(statGenerator(true, false, false)); //set passing array
            this.setOffensiveStats(getPassingStats());
        }else {
            setReceivingStats(statGenerator(false, false, true)); // set recieving arra
            this.setOffensiveStats(getReceivingStats());
        }

        setRushingStats(statGenerator(false,true,false)); // set rushing array
        this.setOffensiveStats2(getRushingStats());

	}

	
	// Constants
	private static final String[] OFFENSIVE_POSITIONS= {"Quarterback", "Running Back", "Fullback", "Offensive Line", "Tight End", "Wide Receivers"};
	private static final String[] PASSING_STATS = {"RANK", "ATT", "COMP", "PCT", "YARDS", "AVG", "SACK", "INT", "TD"}; 
	private static final String[] RUSHING_STATS = {"RANK", "ATT", "LONG", "20+", "YARDS", "AVG", "FUM", "1DN","TD"}; 
	private static final String[] RECEIVING_STATS = {"RANK", "REC", "LONG", "YAC", "YARDS", "AVG", "FUM", "1DN", "TD"};
	
	// Variables
	private String position;
	private int modifier;
    private int totalAttempted;
    private int totalCompleted;
    private int totalYards;
    private int totalSacks;
    private int totalInterceptions;
    private int totalTouchdowns;
    private int totalPercentCompleted;
    private int totalAverageYards;
    private int totalLongest;
    private int totalFumbles;
    private int totalFirstDowns;
    private int totalCarriesOver20;
    private int totalReceptions;
    private int totalYardsAfterCatch;
    private int totalGamesPlayed;

    private int[][] passingStats;
    private int[][] rushingStats;
    private int[][] receivingStats;

	
	@Override
	public String toString() {
		

		return getPosition() + ": " + formatPlayerInfo() + "\n" + formatOffensivePlayerStats() + "\n\n";
	}
	
	
	// Method to format offensivePlayerStats[][][] into a string
	public String formatOffensivePlayerStats() {
		String formatedStats = "";

        if (position == "Quarterback") {
            formatedStats += "PASSING:\n";
            formatedStats += String.format("%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s", "SEASON", "GAMES", "RANK", "ATT", "COMP", "PCT", "YARDS", "AVG", "SACK", "INT", "TD").replaceAll("[,]", "|") +"|\n";

            //Loop through all stat in passingStats[][] and add to formated string.
            for (int i = 0; i < getPassingStats().length; i++) {
                for (int j = 0; j < getPassingStats()[i].length; j++) {
                            formatedStats += String.format("%-6d", getPassingStats()[i][j]) + "|"; // Get each stat for that season
                }
            }
        } else {
            formatedStats += "RECEIVING:\n";
            formatedStats += String.format("%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s", "SEASON", "GAMES", "RANK", "REC", "LONG", "YAC", "YARDS", "AVG", "FUM", "1DN", "TD").replaceAll("[,]", "|") +"|\n";

            //Loop through all stats in receivingStats[][] and add to formated string
          for(int i = 0; i < getReceivingStats().length; i++){
              for(int j = 0; j < getReceivingStats()[i].length; j++){
                  formatedStats += String.format("%-6d", getReceivingStats()[i][j]) + "|"; // Get each stat for that season
              }
          }

        }

        formatedStats += "RUSHING:\n";
        formatedStats += String.format("%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s,%-6s", "SEASON", "GAMES", "RANK", "ATT", "LONG", "20+", "YARDS", "AVG", "FUM", "1DN","TD").replaceAll("[,]", "|") +"|\n";
        //Loop through all stat in rushingStats[][] and add to formated  string.
        for (int k = 0; k < getRushingStats().length; k++) {
            for (int j = 0; j < getRushingStats()[k].length; j++) {
                formatedStats += String.format("%-6d", getRushingStats()[k][j]) + "|"; // Get each stat for that season
            }
        }
		
		return formatedStats;
	}


	
	
	// Method for generating passing stats
	public int[][] statGenerator(boolean passing, boolean receiving, boolean rushing){
		int[][] statArray = new int[getSeasonsPlayed()][11];
		boolean isPassing = passing;
		boolean isReceiving = receiving;
		boolean isRushing = rushing;

		//Variables
		int attempted = 0;
		int completed = 0;
		int yards = 0;
		int sacks = 0;
		int interceptions = 0;
		int touchdowns = 0;
		int percentCompleted = 0;
		int averageYards = 0;
		int longest = 0;
		int fumbles = 0;
		int firstDowns = 0;
		int carriesOver20 = 0;
		int receptions = 0;
		int yardsAfterCatch = 0;
		int seasonRanking = 0;

		//Modifier base
        int statModifier = getModifier();
		
		
		for (int i = 0; i < statArray.length; i++) {
            // Number of games played that season.
            int gamesPlayed = getGamesPlayed()[i];

			// Generate the stats randomly based on the average per game stats
			// Passing Stats if passing is true
			if (isPassing) {
				attempted = (40 - statModifier) * gamesPlayed; // average 40 pass attempts per game minus modifier
				completed = attempted - (statModifier * gamesPlayed); // completed passes based on attempts minus modifier
                yards = (400 - (statModifier * 5)) * gamesPlayed ; // average 400 passing yards per game minus modifier times 5
                sacks  = (2 - (statModifier % 2)) * gamesPlayed; // average 2 sacks per game
                interceptions = (statModifier % 4) * gamesPlayed; // average 1 interception pass per game
                touchdowns = (statModifier % 5) * gamesPlayed;// average 3 passing touchdowns per game
				
				percentCompleted = ((completed * 100) / attempted); // completed divided by attempted passes to get percentage completed
				averageYards = yards / attempted; // Average Yards gain per pass attempt
				
				// The over ranking of the Quaterback
				seasonRanking = ((completed + touchdowns) /2 ) / 10;
			}
			
			// Rushing Stats if rushing is true
			if(isRushing) {
				attempted = (40 - statModifier) * gamesPlayed; // average 40 rushing attempts per game minus modifier
				longest = (55 + (statModifier % 10)) * gamesPlayed; // average 60 Logest rushing yards per game minus modifier times two
				yards = (100 + (statModifier * 10)) * gamesPlayed; // average 200 rushing yards per game + modifier times five
				fumbles = (statModifier % 4) * gamesPlayed; // average 2 fumbles per game
				firstDowns = (10 + (statModifier % 6)) * gamesPlayed; // average 13 rushing firstdowns per game
				touchdowns = (statModifier % 4) * gamesPlayed; // average 2 rushing touchdowns per game
				carriesOver20 = (statModifier % 7) * gamesPlayed; // average 5 rushes over 20 yards per game
				averageYards = yards / attempted; // Average Yards gain per rush attempt
				
				// The over ranking for rushing 
				seasonRanking = ((touchdowns + firstDowns) / 2) / 10;
			}
			
			// Receiving stats if receiving is true
			if(isReceiving){
				receptions = (25 + (statModifier % 10)) * gamesPlayed; // average 30 receptions per game
				longest = (50 + (statModifier * 2)) * gamesPlayed; // average 60 longest receiving yards per game
				yards = (300 + (statModifier * 10)) * gamesPlayed; // average 400 receiving yards per game
				fumbles = (statModifier % 3) * gamesPlayed; // average 2 fumbles per game
				firstDowns = (statModifier % 16) * gamesPlayed; // average 1 receiving fistdown per game
				touchdowns = (statModifier % 4) * gamesPlayed; // average 2 passing touchdowns per game
				yardsAfterCatch = (10 + (statModifier % 4)) * gamesPlayed; // average 12 yards after catch
				averageYards = yards / receptions; // Average Yards gain per reception
				
				// The over ranking of the Quaterback
				seasonRanking = ((receptions  + touchdowns +firstDowns ) / 3) / 10;
			}
			
			//Increment modifier per season
			statModifier++;
			
			// set the value of the generated stat to the position in seasonStats[i][j]
			for (int j = 0; j < statArray[i].length; j++) {
                //Add a stat to to the subarray based on index.

                switch (j) {
                    case 0:
                        statArray[i][j] = i + 1; //Season Stat
                        break;
                    case 1:
                        // Set the number of games player that season to the first elelent
                        statArray[i][j] = gamesPlayed;
                        setTotalGamesPlayed(getTotalGames() + gamesPlayed);
                        break;
                    case 2:
                        // all offensive stats share this variable
                        statArray[i][j] = seasonRanking;
                        break;
                    case 3:
                        // change variable based on if receiving is true
                        if (isReceiving) {
                            statArray[i][j] = receptions;
                            setTotalReceptions(getTotalReceptions() + receptions);
                        } else {
                            statArray[i][j] = attempted;
                            setTotalAttempted(getTotalAttempted() + attempted);
                        }
                        break;
                    case 4:
                        // change variable based on if passing is true
                        if (isPassing) {
                            statArray[i][j] = completed;
                            setTotalCompleted(getTotalCompleted() + completed);
                        } else {
                            statArray[i][j] = longest;
                            setTotalLongest(getTotalLongest() + longest);
                        }
                        break;
                    case 5:
                        // change variable based on which stat position is true
                        if (isPassing) {
                            statArray[i][j] = percentCompleted;
                        } else if (isRushing) {
                            statArray[i][j] = carriesOver20;
                            setTotalCarriesOver20(getTotalCarriesOver20() + carriesOver20);
                        } else {
                            statArray[i][j] = yardsAfterCatch;
                            setTotalYardsAfterCatch(getTotalYardsAfterCatch() + yardsAfterCatch);
                        }
                        break;
                    case 6:
                        // all offensive stats share this variable
                        statArray[i][j] = yards;
                        setTotalYards(getTotalYards() + yards);
                        break;
                    case 7:
                        // all offensive stats share this variable
                        statArray[i][j] = averageYards;
                        break;
                    case 8:
                        // change variable based on if passing is true
                        if (isPassing) {
                            statArray[i][j] = sacks;
                            setTotalSacks(getTotalSacks() + sacks);
                        } else {
                            statArray[i][j] = fumbles;
                            setTotalFumbles(getTotalFumbles() + fumbles);
                        }
                        break;
                    case 9:
                        // change variable based on if passing is true
                        if (isPassing) {
                            statArray[i][j] = interceptions;
                            setTotalInterceptions(getTotalInterceptions() + interceptions);
                        } else {
                            statArray[i][j] = firstDowns;
                            setTotalFirstDowns(getTotalFirstDowns() + firstDowns);
                        }
                        break;
                    case 10:
                        // all offensive stats share this variable
                        statArray[i][j] = touchdowns;
                        setTotalTouchdowns(getTotalTouchdowns() + touchdowns);
                }
            }
        }


		
		return statArray;
	}
	

	
	// Getter/Setter position
	public void setPosition(int positionIndex) {
		this.position = OFFENSIVE_POSITIONS[positionIndex];
	}
	
	public String getPosition() {
		return this.position;
	}

	public static String[] getOffensivePositions() {
		return OFFENSIVE_POSITIONS;
	}

	public int getModifier() {
		 return this.modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}


	//totalAttempted
    public int getTotalAttempted() {
        return totalAttempted;
    }

    public void setTotalAttempted(int totalAttempted) {
        this.totalAttempted = totalAttempted;
    }

    //totalCompleted
    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    //totalYards
    public int getTotalYards() {
        return totalYards;
    }

    public void setTotalYards(int totalYards) {
        this.totalYards = totalYards;
    }

    //totalSacks
    public int getTotalSacks() {
        return totalSacks;
    }

    public void setTotalSacks(int totalSacks) {
        this.totalSacks = totalSacks;
    }

    //totalInterceptions
    public int getTotalInterceptions() {
        return totalInterceptions;
    }

    public void setTotalInterceptions(int totalInterceptions) {
        this.totalInterceptions = totalInterceptions;
    }

    //totalTouchdowns
    public int getTotalTouchdowns() {
        return totalTouchdowns;
    }

    public void setTotalTouchdowns(int totalTouchdowns) {
        this.totalTouchdowns = totalTouchdowns;
    }

    //totalPercentCompleted
    public int getTotalPercentCompleted() {
        return (getTotalCompleted() * 100) / getTotalAttempted();
    }

    //totalAverageYards
    public int getTotalAverageYards() {
        return getTotalYards() / getTotalAttempted();
    }

    //totalLongest
    public int getTotalLongest() {
        return totalLongest;
    }

    public void setTotalLongest(int totalLongest) {
        this.totalLongest = totalLongest;
    }

    //totalFumbles
    public int getTotalFumbles() {
        return totalFumbles;
    }

    public void setTotalFumbles(int totalFumbles) {
        this.totalFumbles = totalFumbles;
    }

    //totalFirstDowns
    public int getTotalFirstDowns() {
        return totalFirstDowns;
    }

    public void setTotalFirstDowns(int totalFirstDowns) {
        this.totalFirstDowns = totalFirstDowns;
    }

    //totalCarriesOver20
    public int getTotalCarriesOver20() {
        return totalCarriesOver20;
    }

    public void setTotalCarriesOver20(int totalCarriesOver20) {
        this.totalCarriesOver20 = totalCarriesOver20;
    }

    //totalReceptions
    public int getTotalReceptions() {
        return totalReceptions;
    }

    public void setTotalReceptions(int totalReceptions) {
        this.totalReceptions = totalReceptions;
    }

    //totalYardsAfterCatch
    public int getTotalYardsAfterCatch() {
        return totalYardsAfterCatch;
    }

    public void setTotalYardsAfterCatch(int totalYardsAfterCatch) {
        this.totalYardsAfterCatch = totalYardsAfterCatch;
    }

    //totalGamesPlayed
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    //PassingStats
    public int[][] getPassingStats(){
	    return this.passingStats;
    }

    public void setPassingStats(int[][] passingStats) {
        this.passingStats = passingStats;
    }

    //RushingStats
    public int[][] getRushingStats(){
        return this.rushingStats;
    }

    public void setRushingStats(int[][] rushingStats) {
        this.rushingStats = rushingStats;
    }

    public int[][] getReceivingStats() {
        return this.receivingStats;
    }

    public void setReceivingStats(int[][] receivingStats) {
        this.receivingStats= receivingStats;
    }
}
