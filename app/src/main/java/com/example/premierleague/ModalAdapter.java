package com.example.premierleague;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ModalAdapter extends BaseAdapter {
    private Context context;
    private List<Modal> tableData;

    public ModalAdapter(Context context, List<Modal> tableData) {
        this.context = context;
        this.tableData = tableData;
    }

    @Override
    public int getCount() {
        return tableData.size();
    }

    @Override
    public Object getItem(int position) {
        return tableData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.epl_table, parent, false);
        }

        TextView positionTextView = convertView.findViewById(R.id.position);
        TextView teamNameTextView = convertView.findViewById(R.id.team_name);
        TextView playedTextView = convertView.findViewById(R.id.played);
        TextView wonTextView = convertView.findViewById(R.id.won);
        TextView drawnTextView = convertView.findViewById(R.id.drawn);
        TextView lostTextView = convertView.findViewById(R.id.lost);
        TextView pointsTextView = convertView.findViewById(R.id.points);
        TextView goalsForView = convertView.findViewById(R.id.goals_for);
        TextView goalsAgainst = convertView.findViewById(R.id.goals_against);
        TextView goalsDifference = convertView.findViewById(R.id.goal_difference);


        Modal currentTeam = tableData.get(position);

        positionTextView.setText(String.valueOf(currentTeam.getTeamPosition()));
        teamNameTextView.setText(currentTeam.getTeamName());
        playedTextView.setText(String.valueOf(currentTeam.getMatchesPlayed()));
        wonTextView.setText(String.valueOf(currentTeam.getMatchesWon()));
        drawnTextView.setText(String.valueOf(currentTeam.getMatchesDrawn()));
        lostTextView.setText(String.valueOf(currentTeam.getMatchesLost()));
        pointsTextView.setText(String.valueOf(currentTeam.getPoints()));
        goalsForView.setText((String.valueOf(currentTeam.getGoalsScored())));
        goalsAgainst.setText((String.valueOf(currentTeam.getGoalsConceded())));
        goalsDifference.setText((String.valueOf(currentTeam.getGoalDifference())));

        return convertView;
    }
}
