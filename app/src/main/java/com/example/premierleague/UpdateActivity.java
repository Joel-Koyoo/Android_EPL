package com.example.premierleague;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    DbHelper db;
    Spinner spinnerTeams;
    EditText etGoalsScored, etGoalsConceded;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        db = new DbHelper(this);
        spinnerTeams = findViewById(R.id.team_name);
        etGoalsScored = findViewById(R.id.goals_scored);
        etGoalsConceded = findViewById(R.id.goals_conceeded);
        btnUpdate = findViewById(R.id.btnUpdate);


        // Get team names from the database and populate the spinner
        ArrayList<String> teamNames = db.getTeamNamesFromDatabase();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeams.setAdapter(spinnerAdapter);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName = spinnerTeams.getSelectedItem().toString();
                int goalsScored = Integer.parseInt(etGoalsScored.getText().toString());
                int goalsConceded = Integer.parseInt(etGoalsConceded.getText().toString());
                ArrayList<Modal> teams = new ArrayList<>();

                // Update team data in the database
                db.updateTeamData(teamName, goalsScored, goalsConceded, teams, new ModalAdapter(UpdateActivity.this, teams));


                // Update team data in the modal (replace teams with the appropriate data structure)
                // Example: teams.updateTeamGoals(teamName, goalsScored, goalsConceded);
                // Note: Replace "teams" with the appropriate reference to your data structure that holds the team data

                Toast.makeText(UpdateActivity.this, "Team data updated successfully!", Toast.LENGTH_SHORT).show();

                // Finish the activity and return to MainActivity
                finish();
            }
        });

    }
}
