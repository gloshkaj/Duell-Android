package com.androidduell.garen.androidduell;
import android.content.Context;
import android.graphics.Color;
import android.widget.*;
/**
 * Created by Garen on 11/7/2016.
 */

public class Tournament {
    public Tournament(Context a_context, RoundFinishActivity a_activity) {
        m_context = a_context;
        m_activity = a_activity;
    }
    /* *********************************************************************
Function Name: GetWinner
Purpose: To determine winner of the tournament
Parameters:
a_humanWins, an integer. It refers to the number of games the human won.
a_compWins, an integer, It refers to the number of games the computer won.
********************************************************************* */
    public void GetWinner(int a_humanWins, int a_compWins) {
        Button yes = (Button)m_activity.findViewById(R.id.newgame);
        Button no = (Button)m_activity.findViewById(R.id.finish);
        yes.setEnabled(false);
        no.setEnabled(false);
        TextView txtView = (TextView)m_activity.findViewById(R.id.endMsg);
        // Human won more rounds
        if (a_humanWins > a_compWins) {
            txtView.setText("Garen has won the tournament!");
            txtView.setTextColor(Color.parseColor("blue"));
        }
        // Computer won more rounds
        else if (a_humanWins < a_compWins) {
            txtView.setText("The computer has won the tournament");
            txtView.setTextColor(Color.parseColor("red"));
        }
        // If we get here then we have a draw
        else {
            txtView.setText("The scores were tied, so the tournament was a draw!");
            txtView.setTextColor(Color.parseColor("green"));
        }
    }
    public Context m_context;
    public RoundFinishActivity m_activity;
}