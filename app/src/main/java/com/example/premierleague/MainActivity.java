package com.example.premierleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DbHelper db;
    ListView listView;
    List<Modal> teams;
    ModalAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        teamArray();
        updateTeamPositions();
        adapter = new ModalAdapter(getApplicationContext(), teams);
        listView.setAdapter(adapter);
    }




    // Update the onUpdateButtonClick() method
    public void onUpdateButtonClick(View view) {

        // Open new activity "update"
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    // Method to populate the team data
    private void teamArray() {
        teams = new ArrayList<>();

        teams.add(new Modal("Arsenal", 10, 9, 1, 0, 12, 7, 0));
        teams.add(new Modal("Aston Villa", 10, 7, 0, 3, 15, 10, 0));
        teams.add(new Modal("Brentford", 10, 2, 4, 4, 9, 11, 0));
        teams.add(new Modal("Brighton", 10, 4, 4, 2, 12, 9, 0));
        teams.add(new Modal("Burnley", 10, 1, 3, 6, 7, 17, 0));
        teams.add(new Modal("Chelsea", 10, 6, 3, 1, 18, 6, 0));
        teams.add(new Modal("Crystal Palace", 10, 1, 5, 4, 6, 14, 0));
        teams.add(new Modal("Everton", 10, 5, 1, 4, 14, 12, 0));
        teams.add(new Modal("Leeds United", 10, 3, 2, 5, 13, 14, 0));
        teams.add(new Modal("Leicester City", 10, 5, 3, 2, 16, 10, 0));
        teams.add(new Modal("Liverpool", 10, 7, 2, 1, 25, 8, 0));
        teams.add(new Modal("Manchester City", 10, 8, 1, 1, 28, 6, 0));
        teams.add(new Modal("Manchester United", 10, 6, 2, 2, 22, 12, 0));
        teams.add(new Modal("Newcastle United", 10, 1, 3, 6, 8, 20, 0));
        teams.add(new Modal("Norwich City", 10, 0, 3, 7, 3, 22, 0));
        teams.add(new Modal("Southampton", 10, 2, 2, 6, 12, 19, 0));
        teams.add(new Modal("Tottenham Hotspur", 10, 6, 1, 3, 21, 12, 0));
        teams.add(new Modal("Watford", 10, 0, 4, 6, 6, 18, 0));
        teams.add(new Modal("West Ham United", 10, 5, 3, 2, 17, 11, 0));
        teams.add(new Modal("Wolverhampton Wanderers", 10, 0, 5, 5, 5, 14, 0));


        // Update team positions
        updateTeamPositions();

        // Create and set adapter
        adapter = new ModalAdapter(getApplicationContext(), teams);
        listView.setAdapter(adapter);

        // Create SQLite database and insert data
        db = new DbHelper(this);
        insertDataToDatabase();
    }


    private void updateTeamPositions() {
        // Sort teams list based on points in descending order
        Collections.sort(teams, new Comparator<Modal>() {
            @Override
            public int compare(Modal team1, Modal team2) {
                // Sort by points in descending order
                return Integer.compare(team2.getPoints(), team1.getPoints());
            }
        });

        // Assign position to each team based on index in sorted list
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).setTeamPosition(i + 1); // Adding 1 to i to start positions from 1 instead of 0
        }
    }

    public void updateTeam(String teamName, int goalsScored, int goalsConceded) {
        DbHelper dbHelper = new DbHelper(this);
        ArrayList<Modal> teams = dbHelper.getAllTeamsFromDatabase();
        ModalAdapter adapter = new ModalAdapter(this, teams);
        dbHelper.updateTeamData(teamName, goalsScored, goalsConceded, teams, adapter);
        adapter.notifyDataSetChanged(); // Call notifyDataSetChanged() after updating data
    }




    // Method to insert team data into SQLite database
    public void insertDataToDatabase() {
        DbHelper dbHelper = new DbHelper(this); // Create an instance of DbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Get a reference to the writable database

        // Loop through the teams list and insert each team into the database
        for (Modal team : teams) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_NAME, team.getTeamName());
            values.put(DbHelper.COLUMN_PLAYED, team.getMatchesPlayed());
            values.put(DbHelper.COLUMN_WON, team.getMatchesWon());
            values.put(DbHelper.COLUMN_DRAWN, team.getMatchesDrawn());
            values.put(DbHelper.COLUMN_LOST, team.getMatchesLost());
            values.put(DbHelper.COLUMN_GOALS_AGAINST, team.getGoalsScored());
            values.put(DbHelper.COLUMN_GOAL_DIFFERENCE, team.getGoalsConceded());
            values.put(DbHelper.COLUMN_POINTS, team.getPoints());

            // Uncomment the following lines to insert data into the database
             long newRowId = db.insert(DbHelper.TABLE_NAME, null, values);
             if (newRowId == -1) {
                 Toast.makeText(this, "Error inserting data into database", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(this, "Data inserted into database", Toast.LENGTH_SHORT).show();
                 break;
             }
        }

        db.close(); // Close the database connection
    }




}

