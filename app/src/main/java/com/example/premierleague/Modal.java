package com.example.premierleague;

public class Modal {
    private String teamName;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesLost;
    private int matchesDrawn; // New field for matches drawn
    private int goalsScored;
    private int goalsConceded;
    private int points;
    private int teamPosition; // New field for team position


        // Constructor
        public Modal(String teamName, int matchesPlayed, int matchesWon, int matchesLost, int matchesDrawn,
                     int goalsScored, int goalsConceded, int points) {
            this.teamName = teamName;
            this.matchesPlayed = matchesPlayed;
            this.matchesWon = matchesWon;
            this.matchesDrawn = matchesDrawn;
            this.matchesLost = matchesLost;
            this.goalsScored = goalsScored;
            this.goalsConceded = goalsConceded;
            this.points = this.points = (matchesWon * 3) + matchesDrawn; // Calculate points based on matchesWon and matchesDrawn;
            this.teamPosition = -1; // Initialize teamPosition to -1
        }


        // Getters and Setters


        public int getTeamPosition() {
            return teamPosition;
        }

        public void setTeamPosition(int teamPosition) {
            this.teamPosition = teamPosition;
        }
        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public int getMatchesPlayed() {
            return matchesPlayed;
        }

        public void setMatchesPlayed(int matchesPlayed) {
            this.matchesPlayed = matchesPlayed;
        }

        public int getMatchesWon() {
            return matchesWon;
        }

        public void setMatchesWon(int matchesWon) {
            this.matchesWon = matchesWon;
        }
        public int getMatchesDrawn() {
            return matchesDrawn;
        }

    public void setMatchesDrawn(int matchesDrawn) {
        this.matchesDrawn = matchesDrawn;
    }

        public int getMatchesLost() {
            return matchesLost;
        }

        public void setMatchesLost(int matchesLost) {
            this.matchesLost = matchesLost;
        }

        public int getGoalsScored() {
            return goalsScored;
        }

        public void setGoalsScored(int goalsScored) {
            this.goalsScored = goalsScored;
        }

        public int getGoalsConceded() {
            return goalsConceded;
        }

        public void setGoalsConceded(int goalsConceded) {
            this.goalsConceded = goalsConceded;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getGoalDifference() {
            return goalsScored - goalsConceded;
        }

        public void updateTeamPosition(Modal[] teamArray) {
            int highestPoints = 0;
            int teamCount = teamArray.length;

            // Find highest points
            for (Modal team : teamArray) {
                if (team.getPoints() > highestPoints) {
                    highestPoints = team.getPoints();
                }
            }

            // Update team position based on points
            for (Modal team : teamArray) {
                if (team.getPoints() == highestPoints) {
                    team.setTeamPosition(1);
                } else {
                    int teamPosition = 1;
                    for (Modal otherTeam : teamArray) {
                        if (otherTeam.getPoints() > team.getPoints()) {
                            teamPosition++;
                        }
                    }
                    team.setTeamPosition(teamPosition);
                }
            }
        }
    }