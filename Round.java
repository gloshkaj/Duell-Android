package com.androidduell.garen.androidduell;
import java.util.*;
import java.io.File;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.*;
/**
 * Created by Garen on 11/7/2016.
 */

public class Round {
    public Round(Context a_context, MainActivity a_activity)
    {
        m_NextPlayer = "";
        m_CompWins = 0;
        m_HumanWins = 0;
        m_random = new Random();
        m_context = a_context;
        m_activity = a_activity;
    }
    /* *********************************************************************
    Function Name: GameOver
    Purpose: To check if the opponent's key die was captured or if the key piece was placed on the opponent's key square.
    Parameters:
    None
    Returns: True if the opponent's key die cannot be found or if the key piece was placed on the opponent's key square. False otherwise.
    Local Variables: intent, an intent that describes which activity to go to when the round ends.
    ********************************************************************* */
    boolean GameOver(Board a_board) { // In every case that returns true, reset the board.
        Intent intent = new Intent(m_context, RoundFinishActivity.class);
        if (a_board.GetBoard().get(1).get(5).equals("H11")) { // Human placed key piece on computer's key square. Human wins. Increment human wins.
            m_HumanWins++;
            intent.putExtra(m_activity.m_prompt, "Garen has placed his key piece on the computer's key square! Garen has won this round! The computer won " + m_CompWins + " rounds and Garen won " + m_HumanWins + " rounds. ");
            intent.putExtra("humanScore", Integer.toString(m_HumanWins));
            intent.putExtra("computerScore", Integer.toString(m_CompWins));
            m_context.startActivity(intent);
            return true;
        }
        else if (!a_board.FindKeyPiece("H11")) { // Computer captured human's key piece. Computer wins. Increment computer score.
            m_CompWins++;
            intent.putExtra(m_activity.m_prompt, "The computer has captured Garen's key piece! The computer has won this round! The computer won "  + m_CompWins  + " rounds and Garen won "  + m_HumanWins  + " rounds. ");
            intent.putExtra("humanScore", Integer.toString(m_HumanWins));
            intent.putExtra("computerScore", Integer.toString(m_CompWins));
            m_context.startActivity(intent);
            return true;
        }
        else if (a_board.GetBoard().get(8).get(5).equals("C11")) { // Computer placed key piece on human's key square. Computer wins. Increment computer wins.
            m_CompWins++;
            intent.putExtra(m_activity.m_prompt, "The computer has placed its key piece on Garen's key square! The computer has won this round! The computer won "  + m_CompWins  + " rounds and Garen won "  + m_HumanWins  + " rounds. " );
            intent.putExtra("humanScore", Integer.toString(m_HumanWins));
            intent.putExtra("computerScore", Integer.toString(m_CompWins));
            m_context.startActivity(intent);
            return true;
        }
        else if (!a_board.FindKeyPiece("C11")) { // Human captured computer's key piece. Human wins. Increment human score.
            m_HumanWins++;
            intent.putExtra(m_activity.m_prompt, "Garen has captured the computer's key piece! Garen has won this round! The computer won " + m_CompWins + " rounds and Garen won " + m_HumanWins + " rounds. ");
            intent.putExtra("humanScore", Integer.toString(m_HumanWins));
            intent.putExtra("computerScore", Integer.toString(m_CompWins));
            m_context.startActivity(intent);
            return true;
        }
        return false;
    }
    // Getters and setters
    int GetHumanWins() {
        return m_HumanWins;
    }
    void SetHumanWins(int a_humanWins) {
        m_HumanWins = a_humanWins;
    }
    int GetComputerWins() {
        return m_CompWins;
    }
    void SetComputerWins(int a_CompWins) {
        m_CompWins = a_CompWins;
    }
    String GetNextPlayer()  {
        return m_NextPlayer;
    }
    void SetNextPlayer(String a_NextPlayer) {
        m_NextPlayer = a_NextPlayer;
    }
    /* *********************************************************************
    Function Name: DetermineFirstPlayer
    Purpose: To determine who goes first for a given round
    Returns: Computer if it rolled the higher number, and Garen if he rolled the higher number.
    Parameters:
    None
    Algorithm:
    1) Get a random number between 1 and 6 for the computer and human dice rolls.
    2) While they are the same, continue rolling dice until the numbers don't equal.
    3) Return the person who got the higher number as the first player for the round.
    ********************************************************************* */
    String DetermineFirstPlayer() {
        int compRoll, humanRoll;
        TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
        // While the rolls are the same number, have the computer and human roll a die. The result will be random numbers.
        txtView.setText("For dice roll #1: ");
        int numRolls = 1;
        do {
            compRoll = m_random.nextInt(6) + 1;
            txtView.append("The computer rolled " + compRoll);
            humanRoll = m_random.nextInt(6) + 1;
            txtView.append(" and Garen rolled " + humanRoll);
            if (compRoll == humanRoll) {
                numRolls++;
                txtView.append(", which are the same. For dice roll #:" + numRolls + ": ");
            }
        } while (compRoll == humanRoll);
        // If the computer had the greater roll, the computer should go first. Otherwise the human would go first.
        if (compRoll < humanRoll) {
            return "Garen";
        }
        else {
            return "Computer";
        }
    }
    private String m_NextPlayer;
    private int m_CompWins;
    private int m_HumanWins;
    private Random m_random;
    private Context m_context;
    private MainActivity m_activity;
}