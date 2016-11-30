package com.androidduell.garen.androidduell;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.graphics.Color;
public class RoundFinishActivity extends AppCompatActivity {
    public String m_computerWins;
    public String m_humanWins;
    public String m_winner;
    @Override
    /* *********************************************************************
Function Name: onCreate
Purpose: To display the winner of the previous round
Parameters:
a_savedInstanceState, the instance of the activity.
********************************************************************* */
    protected void onCreate(Bundle a_savedInstanceState) {
        super.onCreate(a_savedInstanceState);
        setContentView(R.layout.activity_round_finish);
        Intent intent = getIntent();
        // Get the intent and display the text that was sent from the main activity.
        m_winner = intent.getStringExtra(MainActivity.m_prompt);
        m_computerWins = intent.getStringExtra("computerScore");
        m_humanWins = intent.getStringExtra("humanScore");
        TextView textView = (TextView)findViewById(R.id.msg);
        textView.setTextSize(32);
        // Display the winner on the screen of the previous round.
        textView.setText(m_winner);
        if (m_winner.contains("computer's")) {
            textView.setTextColor(Color.parseColor("blue"));
        }
        else if (m_winner.contains("Garen's")) {
            textView.setTextColor(Color.parseColor("red"));
        }
    }
    /* *********************************************************************
Function Name: BeginNewRound
Purpose: To start a new round.
Parameters:
a_view, the button object passed by value. Not used.
Local Variables: intent, an intent to go to the main activity when the user wants to begin a new round.
********************************************************************* */
    public void BeginNewRound(View a_view) {
        // Send the human and computer wins to the new activity.
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("cScore", m_computerWins);
        intent.putExtra("hScore", m_humanWins);
        startActivity(intent);
    }
    /* *********************************************************************
Function Name: DetermineWinner
Purpose: To determine winner of the tournament
Parameters:
a_view, the button object passed by value. Not used.
********************************************************************* */
    public void DetermineWinner(View a_view) {
        Tournament tournament = new Tournament(RoundFinishActivity.this, this);
        // Convert the string extras to ints and get the winner of the tournment by calling GetWinner in the tournament class.
        int humanWins = Integer.parseInt(m_humanWins);
        int computerWins = Integer.parseInt(m_computerWins);
        tournament.GetWinner(humanWins, computerWins);
    }
}