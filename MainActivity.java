package com.androidduell.garen.androidduell;
import com.androidduell.garen.androidduell.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import java.util.*;
import java.io.*;
import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextWatcher;
import android.text.Editable;
import java.util.regex.*;
public class MainActivity extends AppCompatActivity {
    public final static String m_prompt = "com.androidduell.garen.androidduell";
    public Board m_model;
    public BoardView m_view;
    public boolean m_wasClicked;
    public Button m_button;
    public Button m_second;
    public int m_startRow;
    public int m_startCol;
    public int m_destRow;
    public int m_destCol;
    public int m_frontMove;
    public int m_lateralMove;
    public int m_allowedToMove;
    public ArrayList<String> m_lines;
    public ArrayList<ArrayList<String>> m_board;
    public String m_direction;
    public String m_secondDirection;
    public String m_buttonId;
    public TextView m_txtView;
    public TextView m_scores;
    public TextView m_nextPlayer;
    public Human m_human;
    public Computer m_computer;
    public Round m_round;
    public Button m_save;
    public String m_fileName;
    public Intent m_intent;
    public EditText m_editText;
    @Override
        /* *********************************************************************
Function Name: onCreate
Purpose: To populate the board and determine first player of the round.
Parameters:
a_savedInstanceState, the instance of the activity.
Algorithm:
1) Check if there is an extra for "lines"-> if there is, then we are reloading from a file.
2) Check if there is an extra for "hScore" and "cScore"-> If there are then we are not in the first round of a new game. Restore human and computer scores.
3) If the lines extra does not exist determine the first player. Otherwise set the next player to the last entry in the last line of the file to restore from.
4) If the lines extra exists split each line by space and populate the board.
5) Disable and enable buttons accordingly.
********************************************************************* */
    protected void onCreate(Bundle a_savedInstanceState) {
        super.onCreate(a_savedInstanceState);
        setContentView(R.layout.activity_main);
        m_intent = getIntent();
        m_model = new Board();
        m_model.DrawBoard();
        m_human = new Human();
        m_view = new BoardView(m_model, MainActivity.this, this);
        m_scores = (TextView)findViewById(R.id.scoreprompt);
        m_nextPlayer = (TextView)findViewById(R.id.nextPlayer);
        if (!m_intent.hasExtra("lines")) {
            m_view.Display();
        }
        m_direction = "";
        m_secondDirection = "";
        m_fileName = "";
        m_startRow = 1;
        m_startCol = 1;
        m_frontMove = 1;
        m_lateralMove = 1;
        m_destRow = 1;
        m_destCol = 1;
        m_buttonId = "";
        m_allowedToMove = 1;
        m_wasClicked = false;
        m_board = m_model.GetBoard();
        m_round = new Round(MainActivity.this, this);
        m_computer = new Computer(m_round, MainActivity.this, this);
        if (m_intent.hasExtra("hScore") && m_intent.hasExtra("cScore")) {
            // Then we are not in the first round of a new tournament. Save human and computer scores.
            m_round.SetComputerWins(Integer.parseInt(m_intent.getStringExtra("cScore")));
            m_round.SetHumanWins(Integer.parseInt(m_intent.getStringExtra("hScore")));
        }
        if (m_intent.hasExtra("lines")) {
            m_lines = m_intent.getStringArrayListExtra("lines");
            String[] lines;
            // Split each line by space and fill the board with the board
            for (int i = 0; i < m_lines.size(); i++) {
                System.out.println(m_lines.get(i));
                if (i > 0 && i < 9) {
                    lines = m_lines.get(i).trim().split("\\s+");
                    m_model.FillBoard(lines, i);
                }
                // If we are at the last three lines of the file, restore scores and next player respectively.
                else if (i >= 9){
                    lines = m_lines.get(i).split("\\s+");
                    if (i == 9) {
                        m_round.SetComputerWins(Integer.parseInt(lines[6]));
                    }
                    else if (i == 10) {
                        m_round.SetHumanWins(Integer.parseInt(lines[7]));
                    }
                    else {
                        m_round.SetNextPlayer(lines[2]);
                    }
                }
            }
            m_view.Display();
        }
        m_button = (Button)findViewById(R.id.comp);
        m_second = (Button)findViewById(R.id.help);
        m_txtView = (TextView)findViewById(R.id.prompt);
        m_save = (Button)findViewById(R.id.save);
        m_save.setEnabled(false);
        if (!m_intent.hasExtra("lines")) {
            if (m_round.DetermineFirstPlayer().equals("Computer")) {
                // Then we did not restore from a file. Determine the first player.
                m_txtView.append(" So the computer player gets to move first!");
                m_second.setEnabled(false);
                m_view.UpdateStatus(false);
                m_nextPlayer.setText("Computer is playing.");
            }
            else {
                m_txtView.append(" So Garen gets to move first!");
                m_button.setEnabled(false);
                m_nextPlayer.setText("Garen is playing.");
            }
        }
        else {
            // Then we restored a game from a saved state.
            if (m_round.GetNextPlayer().equals("Computer")) {
                m_second.setEnabled(false);
                m_view.UpdateStatus(false);
                m_nextPlayer.setText("Computer is playing.");
            }
            else {
                m_button.setEnabled(false);
                m_nextPlayer.setText("Garen is playing.");
            }
        }
        m_scores.setText("Computer: " + m_round.GetComputerWins() + "\n" + "Garen: " + m_round.GetHumanWins());
        m_scores.setTextColor(Color.parseColor("red"));
        m_nextPlayer.setTextColor(Color.parseColor("blue"));
        m_txtView.setTextColor(Color.parseColor("blue"));
    }
    // Getters
    public Button GetCompButton() {
        return m_button;
    }
    public Button GetHelpButton() {
        return m_second;
    }
    public Button GetSaveButton() {
        return m_save;
    }
    /* *********************************************************************
Function Name: SaveGame
Purpose: To prompt user for the file to save the current game state.
Parameters:
a_view, the button object passed by value. Not used.
Local Variables:
saveText: The text box to input the file to save the currentstate.
buttonID. The button to click once the user finished entering text.
********************************************************************* */
    public void SaveGame(View a_view) {
        m_view.UpdateStatus(false);
        m_button.setEnabled(false);
        m_second.setEnabled(false);
        m_save.setEnabled(false);
        TextView saveText = (TextView)findViewById(R.id.savePrompt);
        Button buttonID = (Button)findViewById(R.id.serialize);
        buttonID.setVisibility(View.VISIBLE);
        saveText.setText("Enter the name of the file to save the current state of this game.");
        m_editText = (EditText)findViewById(R.id.saveText);
        m_editText.setVisibility(View.VISIBLE);
    }
    /* *********************************************************************
Function Name: Serialize
Purpose: To save the progress of the game to the file.
Parameters:
a_view, the button object passed by value. Not used.
********************************************************************* */
    public void Serialize(View a_view) {
        m_fileName = m_editText.getText().toString();
        if (m_fileName.isEmpty()) {
            return;
        }
        System.out.println(m_fileName);
        WriteToFile(m_fileName);
    }
    /* *********************************************************************
Function Name: WriteToFile
Purpose: To write the board and game statistics to the file.
Parameters:
a_fileName, the string referencing the file path to save current game state.
Local variables:
intent, the intent to go back to the start activity after saving game state.
dir, the directory of files to place the saved game..
file, the file object in "dir" to put the saved game.
fileStream, the fileWriter referring to the file to write the game data and the board.
Algorithm:
1) Read each line from the file and insert into arraylist.
2) Send the arraylist to the new activity.
3) Check that the file is valid.
********************************************************************* */
    public void WriteToFile(String a_fileName) {
        File dir = getExternalFilesDir(null);
        System.out.println(dir);
        File file = new File(dir, a_fileName);
        System.out.println(file);
        try {
            FileWriter fileStream = new FileWriter(file);
            fileStream.write("Board:\n");
            m_view.Serialize(fileStream);
            fileStream.write("Number of rounds won by computer: " + m_round.GetComputerWins() + "\n");
            fileStream.write("Number of rounds won by human player: " + m_round.GetHumanWins() + "\n");
            fileStream.write("Next Player: " + m_round.GetNextPlayer() + "\n");
            fileStream.close();
        } catch (IOException a_execp) {
            System.out.println("Cannot find file!");
            a_execp.printStackTrace();
        }

        Intent intent = new Intent(this, StartState.class);
        startActivity(intent);
    }
    /* *********************************************************************
Function Name: CompPlay
Purpose: To allow the computer to make a move when the computer button is clicked.
Parameters:
a_view, the button passed by value. Not used.
********************************************************************* */
    public void CompPlay(View a_view) {
        m_computer.Play(m_model, m_view);
        m_button.setEnabled(false);
        m_second.setEnabled(true);
        m_view.UpdateStatus(true);
        if (!m_save.isEnabled()) {
            m_save.setEnabled(true);
        }
        m_round.GameOver(m_model);
        m_round.SetNextPlayer("Human");
        m_nextPlayer.setText("Garen is playing.");
    }
    /* *********************************************************************
Function Name: HelpHuman
Purpose: To allow the human to ask for help when the help button is clicked
Parameters:
a_view, the button passed by value. Not used.
********************************************************************* */
    public void HelpHuman(View a_view) {
        if (m_save.isEnabled()) {
            m_save.setEnabled(false);
        }
        m_computer.HelpHuman(m_model, m_view);
    }
    /* *********************************************************************
Function Name: GetCoordinate
Purpose: To allow the human to make a move.
Parameters:
a_view, the button passed by value. Not used.
Algorithm:
1) Enable and disable any necessary buttons.
2) If the first input is one's own die, highlight it yellow. Otherwise, don't highlight anything.
3) Validate destination. Start over if it doesn't correspond to an empty square or an opponent's dice.
4) If both x coordinates and both y coordinates are not equal then there is a 90 degree turn. Have an alert dialog come up to have the user specify frontally or laterally as the first direction.
5) Check the path of the die that no other dice are in the way. Start over if the path is occupied.
6) Update the board and the faces of the dice by calling UpdateBoard, then Rotate.
Assistance Received: none
********************************************************************* */
    public void GetCoordinate(View a_view) {
        if (m_save.isEnabled()) {
            // Disable serialization option on first button click
            m_save.setEnabled(false);
        }
        int controlID = a_view.getId();
        if (!m_direction.isEmpty()) {
            m_direction = "";
        }
        if (!m_secondDirection.isEmpty()) {
            m_secondDirection = "";
        }
        // Use regex to strip off everything but numbers from the id of the control.
        Button selected = (Button) a_view;
        String buttonText = selected.getText().toString();
        m_buttonId = a_view.getResources().getResourceEntryName(controlID);
        System.out.println(m_buttonId);
        String numStr = m_buttonId.replaceAll("[^0-9.]", "");
        System.out.println(numStr);
        int num = Integer.parseInt(numStr);
        System.out.println(num);
        if (!m_wasClicked) {
            if (!buttonText.startsWith("H")) {
                // Start over if this is not a human die.
                m_txtView.setText("You must pick your own die to move! Try again.");
                return;
            }
            else {
                // Otherwise highlight it yellow.
                selected.setBackgroundColor(Color.parseColor("yellow"));
            }
            m_allowedToMove = Math.abs(Character.getNumericValue(buttonText.charAt(1)));
            m_startCol = num % 9;
            // Get starting row and column.
            if (m_startCol == 0) {
                m_startCol = 9;
            }
            if (num < 10) {
                m_startRow = 8;
            } else if (num < 19) {
                m_startRow = 7;
            } else if (num < 28) {
                m_startRow = 6;
            } else if (num < 37) {
                m_startRow = 5;
            } else if (num < 46) {
                m_startRow = 4;
            } else if (num < 55) {
                m_startRow = 3;
            } else if (num < 64) {
                m_startRow = 2;
            } else {
                m_startRow = 1;
            }
            m_wasClicked = true;
            m_txtView.setText("And to which row and column do you want to move " + m_board.get(9 - m_startRow).get(m_startCol) + " located at (" + m_startRow + ", " + m_startCol + ")?");
            return;
        } else {
            if (buttonText.startsWith("H")) {
                // Then the user is trying to move to their own dice.
                m_txtView.setText("Can't land on your own dice. Try again!");
                m_wasClicked = false;
                m_view.Display();
                return;
            }
            m_destCol = num % 9;
            // Get ending row and column.
            if (m_destCol == 0) {
                m_destCol = 9;
            }
            if (num < 10) {
                m_destRow = 8;
            } else if (num < 19) {
                m_destRow = 7;
            } else if (num < 28) {
                m_destRow = 6;
            } else if (num < 37) {
                m_destRow = 5;
            } else if (num < 46) {
                m_destRow = 4;
            } else if (num < 55) {
                m_destRow = 3;
            } else if (num < 64) {
                m_destRow = 2;
            } else {
                m_destRow = 1;
            }
            m_txtView.setText("Please select the die you want to move next!");
            m_wasClicked = false;
        }
        m_frontMove = Math.abs(m_destRow - m_startRow);
        m_lateralMove = Math.abs(m_destCol - m_startCol);
        System.out.println(m_destRow + " " + m_startRow + " " + m_destCol + " " + m_startCol);
        if (Math.abs(m_frontMove + m_lateralMove) != Math.abs(m_allowedToMove)) {
            // Then the user is not moving the correct number of squares.
            m_txtView.setText("Must move exactly " + m_allowedToMove + " squares!");
            m_view.Display();
            return;
        }
        AlertDialog.Builder alertDialog;
        if (m_destRow != m_startRow && m_destCol != m_startCol) {
            // Then there is a 90 degree turn. Display a pop up to prompt for frontal or lateral distance.
            alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Do you first want to move frontally or laterally?");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton(
                    "Frontally",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface a_dialog, int a_id) {
                            a_dialog.cancel();
                            if (m_human.IsValidFrontLatPath(m_board, m_startRow, m_startCol, m_destRow, m_destCol)) {
                                // Update the board and enable appropriate buttons.
                                m_direction = "frontally";
                                m_secondDirection = "laterally";
                                String currentDie = m_board.get(9 - m_startRow).get(m_startCol);
                                m_model.UpdateBoard(m_board, m_frontMove, m_lateralMove, m_direction, m_secondDirection, currentDie, m_startRow, m_startCol, m_destRow, m_destCol);
                                m_view.Display();
                                m_txtView.setText("You moved " + currentDie + " located at (" + m_startRow + ", " + m_startCol + ") frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_destRow + ", " + m_destCol + ")! ");
                                m_txtView.append("The die is now " + m_board.get(9 - m_destRow).get(m_destCol) + " at (" + m_destRow + ", " + m_destCol + ")! ");
                                m_second.setEnabled(false);
                                m_save.setEnabled(true);
                                m_button.setEnabled(true);
                                m_view.UpdateStatus(false);
                                m_round.GameOver(m_model);
                                m_round.SetNextPlayer("Computer");
                                m_nextPlayer.setText("Computer is playing.");
                            }
                            else {
                                // Invalid path
                                m_txtView.setText("Can't move this way, there is a die in the way!");
                                m_view.Display();
                            }
                        }
                    });
            alertDialog.setNegativeButton(
                    "Laterally",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface a_dialog, int a_id) {
                            a_dialog.cancel();
                            if (m_human.IsValidLatFrontPath(m_board, m_startRow, m_startCol, m_destRow, m_destCol)) {
                                // Update board and enable appropriate buttons
                                m_direction = "laterally";
                                m_secondDirection = "frontally";
                                String currentDie = m_board.get(9 - m_startRow).get(m_startCol);
                                m_model.UpdateBoard(m_board, m_frontMove, m_lateralMove, m_direction, m_secondDirection, currentDie, m_startRow, m_startCol, m_destRow, m_destCol);
                                m_view.Display();
                                m_txtView.setText("You moved " + currentDie + " located at (" + m_startRow + ", " + m_startCol + ") laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_destRow + ", " + m_destCol + ")! ");
                                m_txtView.append("The die is now " + m_board.get(9 - m_destRow).get(m_destCol) + " at (" + m_destRow + ", " + m_destCol + ")! ");
                                m_second.setEnabled(false);
                                m_button.setEnabled(true);
                                m_save.setEnabled(true);
                                m_view.UpdateStatus(false);
                                m_round.GameOver(m_model);
                                m_round.SetNextPlayer("Computer");
                                m_nextPlayer.setText("Computer is playing.");
                            }
                            else {
                                // Invalid path
                                m_txtView.setText("Can't move this way, there is a die in the way!");
                                m_view.Display();
                            }
                        }
                    }
            );
            AlertDialog alert = alertDialog.create();
            alert.show();
            return;
        }
        else if (m_destRow != m_startRow) {
            if (m_human.IsValidFrontPath(m_board, m_startRow, m_startCol, m_destRow, m_destCol)) {
                // Update board and enable buttons accordingly.
                m_direction = "frontally";
                String currentDie = m_board.get(9 - m_startRow).get(m_startCol);
                System.out.println(currentDie);
                m_model.UpdateBoard(m_board, m_frontMove, m_lateralMove, m_direction, m_secondDirection, currentDie, m_startRow, m_startCol, m_destRow, m_destCol);
                m_view.Display();
                m_txtView.setText("You moved " + currentDie + " located at (" + m_startRow + ", " + m_startCol + ") frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_destRow + ", " + m_destCol + ")! ");
                m_txtView.append("The die is now " + m_board.get(9 - m_destRow).get(m_destCol) + " at (" + m_destRow + ", " + m_destCol + ")! ");
                m_second.setEnabled(false);
                m_button.setEnabled(true);
                m_view.UpdateStatus(false);
                m_save.setEnabled(true);
                m_round.GameOver(m_model);
                m_round.SetNextPlayer("Computer");
                m_nextPlayer.setText("Computer is playing.");
            }
            else {
                // Invalid path
                m_txtView.setText("Can't move this way, there is a die in the way!");
                m_view.Display();
            }
        }
        else {
            if (m_human.IsValidLatPath(m_board, m_startRow, m_startCol, m_destRow, m_destCol)) {
                // Update board and enable appropriate buttons.
                m_direction = "laterally";
                String currentDie = m_board.get(9 - m_startRow).get(m_startCol);
                m_model.UpdateBoard(m_board, m_frontMove, m_lateralMove, m_direction, m_secondDirection, currentDie, m_startRow, m_startCol, m_destRow, m_destCol);
                m_view.Display();
                m_txtView.setText("You moved " + currentDie + " located at (" + m_startRow + ", " + m_startCol + ") laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_destRow + ", " + m_destCol + ")! ");
                m_txtView.append("The die is now " + m_board.get(9 - m_destRow).get(m_destCol) + " at (" + m_destRow + ", " + m_destCol + ")! ");
                m_second.setEnabled(false);
                m_button.setEnabled(true);
                m_save.setEnabled(true);
                m_view.UpdateStatus(false);
                m_round.GameOver(m_model);
                m_round.SetNextPlayer("Computer");
                m_nextPlayer.setText("Computer is playing.");
            }
            else {
                // Invalid path
                m_txtView.setText("Can't move this way, there is a die in the way!");
                m_view.Display();
            }
        }
    }
}