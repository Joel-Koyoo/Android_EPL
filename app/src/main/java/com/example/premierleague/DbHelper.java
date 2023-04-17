package com.example.premierleague;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final String DATABASE_NAME = "teams.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "teams";
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "team_name";
    public static final String COLUMN_PLAYED = "played";
    public static final String COLUMN_WON = "won";
    public static final String COLUMN_DRAWN = "drawn";
    public static final String COLUMN_LOST = "lost";
    public static final String COLUMN_GOALS_FOR = "goals_for";
    public static final String COLUMN_GOALS_AGAINST = "goals_against";
    public static final String COLUMN_GOAL_DIFFERENCE = "goal_difference";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_POSITION = "position";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table to store teams
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PLAYED + " INTEGER,"
                + COLUMN_WON + " INTEGER,"
                + COLUMN_DRAWN + " INTEGER,"
                + COLUMN_LOST + " INTEGER,"
                + COLUMN_GOALS_FOR + " INTEGER,"
                + COLUMN_GOALS_AGAINST + " INTEGER,"
                + COLUMN_GOAL_DIFFERENCE + " INTEGER,"
                + COLUMN_POINTS + " INTEGER,"
                + COLUMN_POSITION + " INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<String> getTeamNamesFromDatabase() {
        // Get a readable database
        SQLiteDatabase db = getReadableDatabase();

        // Query the database to fetch team names
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME}, null, null, null, null, null);

        // Create a list to store team names
        ArrayList<String> teamNames = new ArrayList<>();

        // Iterate through the cursor and extract team names
        if (cursor.moveToFirst()) {
            do {
                int columnIndex = cursor.getColumnIndex(COLUMN_NAME);
                if (columnIndex >= 0) {
                    String teamName = cursor.getString(columnIndex);
                    teamNames.add(teamName);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return teamNames;
    }


    public void updateTeamData(String teamName, int goalsScored, int goalsConceded, ArrayList<Modal> teams, ModalAdapter adapter) {
        SQLiteDatabase db = getWritableDatabase();

        // Create a ContentValues object to store the new data
        ContentValues values = new ContentValues();
        values.put(COLUMN_GOALS_FOR, goalsScored);
        values.put(COLUMN_GOALS_AGAINST, goalsConceded);

        // Update the row in the database with the new data
        db.update(TABLE_NAME, values, COLUMN_NAME + "=?", new String[]{teamName});

        // Update the corresponding team data in the teams list
        for (Modal team : teams) {
            if (team.getTeamName().equals(teamName)) {
                team.setGoalsScored(goalsScored);
                team.setGoalsConceded(goalsConceded);
                // Update any other fields you need to modify
                break;
            }
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();

        // Close the database
        db.close();
    }

    public ArrayList<Modal> getAllTeamsFromDatabase() {
        // Get a readable database
        SQLiteDatabase db = getReadableDatabase();

        // Query the database to fetch all teams
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        // Create a list to store teams
        ArrayList<Modal> teams = new ArrayList<>();

        // Check if cursor is not null and move to first row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve column indices
                int teamNameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int playedIndex = cursor.getColumnIndex(COLUMN_PLAYED);
                int wonIndex = cursor.getColumnIndex(COLUMN_WON);
                int drawnIndex = cursor.getColumnIndex(COLUMN_DRAWN);
                int lostIndex = cursor.getColumnIndex(COLUMN_LOST);
                int goalsForIndex = cursor.getColumnIndex(COLUMN_GOALS_FOR);
                int goalsAgainstIndex = cursor.getColumnIndex(COLUMN_GOALS_AGAINST);
                int goalDifferenceIndex = cursor.getColumnIndex(COLUMN_GOAL_DIFFERENCE);
                int pointsIndex = cursor.getColumnIndex(COLUMN_POINTS);
                int positionIndex = cursor.getColumnIndex(COLUMN_POSITION);

                // Retrieve team data from cursor, handling cases where column not found
                String teamName = teamNameIndex != -1 ? cursor.getString(teamNameIndex) : "";
                int played = playedIndex != -1 ? cursor.getInt(playedIndex) : 0;
                int won = wonIndex != -1 ? cursor.getInt(wonIndex) : 0;
                int drawn = drawnIndex != -1 ? cursor.getInt(drawnIndex) : 0;
                int lost = lostIndex != -1 ? cursor.getInt(lostIndex) : 0;
                int goalsFor = goalsForIndex != -1 ? cursor.getInt(goalsForIndex) : 0;
                int goalsAgainst = goalsAgainstIndex != -1 ? cursor.getInt(goalsAgainstIndex) : 0;
                int goalDifference = goalDifferenceIndex != -1 ? cursor.getInt(goalDifferenceIndex) : 0;
                int points = pointsIndex != -1 ? cursor.getInt(pointsIndex) : 0;
                int position = positionIndex != -1 ? cursor.getInt(positionIndex) : 0;

                // Create a Modal object and add it to the list
                Modal team = new Modal(teamName, played, won, lost, drawn, goalsFor, goalsAgainst, points);
                teams.add(team);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return teams;
    }



}
