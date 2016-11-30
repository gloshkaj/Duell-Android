package com.androidduell.garen.androidduell;
import android.graphics.Color;
import android.widget.Button;
import android.content.Context;
import android.graphics.Color;
import java.util.*;
import java.io.*;
/**
 * Created by Garen on 11/4/2016.
 */

public class BoardView {
    BoardView(Board a_board, Context a_context, MainActivity a_activity) {
        this.m_context = a_context;
        this.m_activity = a_activity;
        m_board = a_board;
        m_game = new ArrayList<ArrayList<String>>();
    }
    /** Function Name : Display
Purpose : To display the board on the screen
Parameters :
None
********************************************************************* */
    public void Display() {
        m_game = m_board.GetBoard();
        int count = 1;
        // For each die on the board display the text on the corresponding the button
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                String bid = "foo" + Integer.toString(count);
                int resource = m_context.getResources().getIdentifier(bid, "id", m_context.getPackageName());
                Button buttonID = (Button) m_activity.findViewById(resource);
                if (count % 2 == 0) {
                    // change the color every other button.
                    buttonID.setBackgroundColor(Color.parseColor("green"));
                }
                else {
                    buttonID.setBackgroundColor(Color.parseColor("white"));
                }
                // Human dice are blue and computer dice are red
                if (!m_game.get(i).get(j).equals("0")) {
                    buttonID.setText(m_game.get(i).get(j));
                    if(m_game.get(i).get(j).startsWith("C")) {
                        buttonID.setTextColor(Color.RED);
                    }
                    else if (m_game.get(i).get(j).startsWith("H")) {
                        buttonID.setTextColor(Color.BLUE);
                    }
                }
                else {
                    buttonID.setText("");
                }
                count++;
            }
        }
    }
    /*Function Name : Serialize
Purpose : To save the current game state to the file
Parameters :
a_writer: an filewriter object passed by reference. It gives the file to save the game to.
********************************************************************* */
    public void Serialize(FileWriter a_writer) {
        m_game = m_board.GetBoard();
        int count = 1;
        // For each square on the board, write each square's text to the file and format it in the file given by the parameter "a_writer"
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                String bid = "foo" + Integer.toString(count);
                int resource = m_context.getResources().getIdentifier(bid, "id", m_context.getPackageName());
                Button buttonID = (Button) m_activity.findViewById(resource);
                String txt = "";
                if (!buttonID.getText().toString().isEmpty()) {
                    txt = buttonID.getText().toString();
                }
                else {
                    txt = "0";
                }
                try {
                    if (txt.length() == 1) {
                        a_writer.write("  " + txt + " ");
                    }
                    else {
                        a_writer.write(txt + " ");
                    }
                } catch (IOException a_execp) {
                    System.out.println("Cannot find file!");
                    a_execp.printStackTrace();
                }
                count++;
            }
            try {
                a_writer.write("\n");
            } catch (IOException a_execp) {
                a_execp.printStackTrace();
            }
        }
    }
    /*Function Name : UpdateStatus
Purpose : To enable/disable buttons on the board depending on whose turn it is.
Returns: True if found, false if not found.
Parameters :
a_status, a boolean value, true or false, determining whether the board should be enabled or not.
********************************************************************* */
    public void UpdateStatus(boolean a_status) {
        m_game = m_board.GetBoard();
        int count = 1;
        // for each button on the board, change the enability of it
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                String bid = "foo" + Integer.toString(count);
                int resource = m_context.getResources().getIdentifier(bid, "id", m_context.getPackageName());
                Button buttonID = (Button) m_activity.findViewById(resource);
                buttonID.setEnabled(a_status);
                count++;
            }
        }
    }
    private Board m_board;
    private ArrayList<ArrayList<String>> m_game;
    private Context m_context;
    private MainActivity m_activity;
}