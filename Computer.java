package com.androidduell.garen.androidduell;
import java.util.*;
import android.content.DialogInterface;
import android.util.*;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.*;
/**
 * Created by Garen on 11/7/2016.
 */

public class Computer extends Player {

    public Computer(Round a_round, Context a_context, MainActivity a_activity)
    {
        // Initialize all variables
        m_direction = "";
        m_prompt = "";
        m_NextRow = 1;
        m_NextCol = 1;
        m_frontMove = 0;
        m_lateralMove = 0;
        m_secondDirection = "";
        m_random = new Random();
        m_tookSuggestion = false;
        m_context = a_context;
        m_activity = a_activity;
        m_view = new BoardView(m_board, a_context, a_activity);
        m_board = new Board();
        m_round = a_round;
        m_die = "";
        m_game = new ArrayList<ArrayList<String>>();
    }
    /* *********************************************************************
    Function Name: GetAllCoordsInBlockingPath
    Purpose: To get all the blocking paths and all the coordinates in the blocking paths
    Parameters:
    a_coords, the list of coordinate pairs passed by reference. It holds all the empty squares in the blocking paths.
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    Algorithm:
    1) Get the frontal and lateral distance.
    2) If they both are not zero, then check if the paths are valid. Populate the coordinate array with all empty squares in all valid paths.
    3) If frontal is zero, check the lateral path, and vice versa. Populate the path with all empty squares.
    4) Set the reference parameter, m_numFound, equal to the number of empty squares found so far for the next call of the function.
    Assistance Received: none
    ********************************************************************* */
    void GetAllCoordsInBlockingPath(ArrayList<Pair<Integer, Integer>> a_coords, ArrayList<ArrayList<String>> a_board) {
        m_frontMove = Math.abs(m_DestRow - m_NextRow);
        m_lateralMove = Math.abs(m_DestCol - m_NextCol);
        // Get frontal and lateral movement. If they are both not zero, then this must have a 90 degree turn.
        int count = m_numFound;
        if (m_frontMove != 0 && m_lateralMove != 0) {
            if (IsValidFrontLatPath(a_board) && IsValidLatFrontPath(a_board)) { // If both routes are valid, get all the empty coordinates in the path between start and end
                count = m_numFound;
                boolean occupied = false;
                if (m_NextRow < m_DestRow) {
                    for (int i = m_NextRow + 1; i <= m_DestRow; i++) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_NextCol));
                        count++;
                    }
                }
                else {
                    for (int i = m_NextRow - 1; i >= m_DestRow; i--) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_NextCol));
                        count++;
                    }
                }
                if (m_NextCol < m_DestCol) {
                    for (int i = m_NextCol + 1; i < m_DestCol; i++) {
                        a_coords.add(new Pair<Integer, Integer>(m_DestRow, i));
                        count++;
                    }
                }
                // Check the frontal path before the lateral path if there is a 90 degree turn and frontally is selected as the first direction.
                // Otherwise if laterally is selected as the first direction check the lateral path first.
                else {
                    for (int i = m_NextCol - 1; i > m_DestCol; i--) {
                        a_coords.add(new Pair<Integer, Integer>(m_DestRow, i));
                        count++;
                    }
                }
                if (m_NextCol < m_DestCol) {
                    for (int i = m_NextCol + 1; i <= m_DestCol; i++) {
                        a_coords.add(new Pair<Integer, Integer>(m_NextRow, i));
                        count++;
                    }
                }
                else {
                    for (int i = m_NextCol - 1; i >= m_DestCol; i--) {
                        a_coords.add(new Pair<Integer, Integer>(m_NextRow, i));
                        count++;
                    }
                }
                if (m_NextRow < m_DestRow) {
                    for (int i = m_NextRow + 1; i < m_DestRow; i++) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_DestCol));
                        count++;
                    }
                }
                else {
                    for (int i = m_NextRow - 1; i > m_DestRow; i--) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_DestCol));
                        count++;
                    }
                }
            }
            else if (IsValidFrontLatPath(a_board)) { // Then only the frontal/lateral path is valid. Algorithm is the same as above.
                count = m_numFound;
                boolean occupied = false;
                if (m_NextRow < m_DestRow) {
                    for (int i = m_NextRow + 1; i <= m_DestRow; i++) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_NextCol));
                        count++;
                    }
                }
                else {
                    for (int i = m_NextRow - 1; i >= m_DestRow; i--) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_NextCol));
                        count++;
                    }
                }
                if (m_NextCol < m_DestCol) {
                    for (int i = m_NextCol + 1; i < m_DestCol; i++) {
                        a_coords.add(new Pair<Integer, Integer>(m_DestRow, i));
                        count++;
                    }
                }
                // Check the frontal path before the lateral path if there is a 90 degree turn and frontally is selected as the first direction.
                // Otherwise if laterally is selected as the first direction check the lateral path first.
                else {
                    for (int i = m_NextCol - 1; i > m_DestCol; i--) {
                        a_coords.add(new Pair<Integer, Integer>(m_DestRow, i));
                        count++;
                    }
                }
            }
            else if (IsValidLatFrontPath(a_board)) { // Then only lateral/frontal path is valid. Use the same algorithm as above.
                count = m_numFound;
                boolean occupied = false;
                if (m_NextCol < m_DestCol) {
                    for (int i = m_NextCol + 1; i <= m_DestCol; i++) {
                        a_coords.add(new Pair<Integer, Integer>(m_NextRow, i));
                        count++;
                    }
                }
                else {
                    for (int i = m_NextCol - 1; i >= m_DestCol; i--) {
                        a_coords.add(new Pair<Integer, Integer>(m_NextRow, i));
                        count++;
                    }
                }
                if (m_NextRow < m_DestRow) {
                    for (int i = m_NextRow + 1; i < m_DestRow; i++) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_DestCol));
                        count++;
                    }
                }
                else {
                    for (int i = m_NextRow - 1; i > m_DestRow; i--) {
                        a_coords.add(new Pair<Integer, Integer>(i, m_DestCol));
                        count++;
                    }
                }
            }
        }
        else if (m_lateralMove != 0) {
            // This must be a lateral move. Get all coordinates in the row between start and end.
            count = m_numFound;
            boolean occupied = false;
            if (m_NextCol < m_DestCol) {
                for (int i = m_NextCol + 1; i < m_DestCol; i++) {
                    a_coords.add(new Pair<Integer, Integer>(m_NextRow, i));
                    count++;
                }
            }
            else {
                for (int i = m_NextCol - 1; i > m_DestCol; i--) {
                    a_coords.add(new Pair<Integer, Integer>(m_NextRow, i));
                    count++;
                }
            }
        }
        else if (m_frontMove != 0) {
            // This must be a frontal move. Get coordinates of all empty squares between start and end in same column.
            count = m_numFound;
            boolean occupied = false;
            // If there is no 90 degree turn then check the frontal path or the lateral path depending on the coordinates given.
            // In all cases, report error if there is at least one square on the path to the destination with a die on it, except at the destination coordinates.
            if (m_NextRow < m_DestRow) {
                for (int i = m_NextRow + 1; i < m_DestRow; i++) {
                    a_coords.add(new Pair<Integer, Integer>(i, m_NextCol));
                    count++;
                }
            }
            else {
                for (int i = m_NextRow - 1; i > m_DestRow; i--) {
                    a_coords.add(new Pair<Integer, Integer>(i, m_NextCol));
                    count++;
                }
            }
        }
        // Must set the reference parameter equal to use it to make the blocking move.
        m_numFound = count;
    }
    /* *********************************************************************
    Function Name: MakeBlockingMove
    Purpose: To perform the move to block the human from winning.
    Parameters:
    a_game, the board object passed by reference.
    a_coords, the array of coordinate pairs passed by reference. It holds all the empty squares in the blocking paths.
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    a_player, the character referring to whether the computer or the human is playing.
    a_view, the view of the board passed by reference. It contains the buttons that represent the board
    Returns: False if no blocking path was found or no die can arrive in the blocking path, True otherwise.
    Algorithm:
    1) For each die on the board, get the distance to the blocking path.
    2) If it can move into the blocking path and it is not the key die, make the move if the computer is playing, and suggest the move if the human is playing.
    Assistance Received: none
    ********************************************************************* */
    boolean MakeBlockingMove(Board a_game, ArrayList<Pair<Integer, Integer>> a_coords, ArrayList<ArrayList<String>> a_board, int a_numFound, char a_player, BoardView a_view) {
        int allowedDist;
        int dist;
        String DieToRoll = "";
        String player = "";
        if (m_numFound == 0) { // If the only winning move was a die with a 1 on top adjacent to the enemy key die, then there are no paths, so just move the key piece at random later on.
            return false;
        }
        m_numFound = a_numFound;
        // For each coordinate in the blocking paths, and for each die on the board, check that it can get to the path to block.
        for (int i = 0; i < m_numFound; i++) {
            System.out.println(a_coords.get(i).first + " " + a_coords.get(i).second);
            for (int j = 8; j >= 1; j--) {
                for (int k = 9; k >= 1; k--) {
                    if (a_board.get(9 - j).get(k).charAt(0) == a_player) {
                        int col = k;
                        int row = j;
                        allowedDist = Character.getNumericValue(a_board.get(9 - j).get(k).charAt(1));
                        dist = Math.abs(row - a_coords.get(i).first) + Math.abs(col - a_coords.get(i).second);
                        // If this is not the key die and the die can get to the path, check if the path is valid
                        if (dist == allowedDist) {
                            m_NextCol = col;
                            m_NextRow = row;
                            m_DestRow = a_coords.get(i).first;
                            m_DestCol = a_coords.get(i).second;
                            DieToRoll = a_board.get(9 - j).get(k);
                            if (!IsValidPath(a_board)) {
                                // Invalid path. Move on.
                                continue;
                            }
                            if (a_player == 'C') {
                                player = "Computer";
                            }
                            else {
                                player = "Human";
                            }
                            // If the computer is playing, make the move, if the human is playing, then the human is asking for help.
                            if (player.equals("Computer")) {
                                String msg = "The computer chose to move " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ") ";
                                if (m_direction.equals("frontally")) {
                                    msg += (" frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") ");
                                }
                                else {
                                    msg += (" laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") ");
                                }
                                msg += (" to block Garen's path to win the game!");
                                TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
                                txtView.setText(msg);
                                // Update and display the board
                                a_game.UpdateComputerMove(a_board, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                                a_view.Display();
                                txtView.append(" The die is now " + a_board.get(9 - m_DestRow).get(m_DestCol) + " at (" + m_DestRow + ", " + m_DestCol + ")!");
                            }
                            else {
                                String msg = "Are you sure you want to move " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                                String move = "";
                                if (m_direction.equals("frontally")) {
                                    move = msg + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to block the computer's path to win the game?";
                                }
                                else {
                                    move = msg + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to block the computer's path to win the game?";
                                }
                                DisplaySuggestion(move, a_game, a_view, a_board, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false; // If we get here then there are no dice the computer can move besides its key die to block.
    }
    /* Function Name : DisplaySuggestion
Purpose : To display the recommended move.
Parameters :
a_msg, a string which represents the message to be displayed in the dialog box.
a_game, the board object passed by reference.
a_view, the view object passed by reference.
a_board, a 2D arraylist passed by reference. It contains the board and its dice.
a_front, an integer giving the frontal distance
a_lat, an integer giving the lateral distance
a_firstDirection, a string passed by reference. It gives us the first movement of the die
a_secondDirection, a string passed by reference. It gives us the second movement of the die
a_die, a string passed by reference giving the die to be moved
a_NextRow, an integer indicating the starting x coordinate
a_NextCol, an integer indicating the starting y coordinate
a_DestRow, an integer indicating the ending x coordinate
a_DestCol, an integer indicating the ending y coordinate
Algorithm :
1) Assign all member variables
2) Display an alert dialog to display the recommendation on the screen.
3) If the yes button was clicked, automatically use the recommended move. Otherwise, enter coordinates as usual.
4) Update the board with the new die if the human said yes in 3.
Assistance Received : none
********************************************************************* */
    void DisplaySuggestion(String a_msg, Board a_game, BoardView a_view, ArrayList<ArrayList<String>> a_board, int a_front, int a_lat, String a_firstDirection, String a_secondDirection, String a_die, int a_NextRow, int a_NextCol, int a_DestRow, int a_DestCol) {
        m_frontMove = a_front;
        m_lateralMove = a_lat;
        m_direction = a_firstDirection;
        m_secondDirection = a_secondDirection;
        m_view = a_view;
        m_board = a_game;
        m_die = a_die;
        m_NextRow = a_NextRow;
        m_NextCol = a_NextCol;
        m_DestRow = a_DestRow;
        m_DestCol = a_DestCol;
        m_game = a_board;
        m_prompt = a_msg.replace("Are you sure you want to move ", "You moved ").replace('?', '!');
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(m_context);
        alertDialog.setMessage(a_msg);
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface a_dialog, int a_id) {
                        a_dialog.dismiss();
                        m_tookSuggestion = true;
                        TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
                        TextView next = (TextView)m_activity.findViewById(R.id.nextPlayer);
                        txtView.setText(m_prompt);
                        // Display the text and make the move.
                        m_board.UpdateBoard(m_game, m_frontMove, m_lateralMove, m_direction, m_secondDirection, m_die, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                        txtView.append("The die is now " + m_game.get(9 - m_DestRow).get(m_DestCol) + " at (" + m_DestRow + ", " + m_DestCol + ")!");
                        m_view.Display();
                        m_activity.GetCompButton().setEnabled(true);
                        m_activity.GetHelpButton().setEnabled(false);
                        m_activity.GetSaveButton().setEnabled(true);
                        m_view.UpdateStatus(false);
                        m_round.GameOver(m_board);
                        m_round.SetNextPlayer("Computer");
                        next.setText("Computer is playing.");
                    }
                });
        alertDialog.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface a_dialog, int a_id) {
                        a_dialog.dismiss();
                        m_tookSuggestion = false;
                    }
                }
        );
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    /* *********************************************************************
    Function Name: IsValidPath
    Purpose: To check if a path is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    Algorithm:
    1) Get the frontal and lateral distance.
    2) If they both are not zero, then check if the paths are valid. If there are dice in the way, return false.
    3) If frontal is zero, check the lateral path, and vice versa. If there are dice in the way, return false.
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidPath(ArrayList<ArrayList<String>> a_board) {
        m_frontMove = Math.abs(m_DestRow - m_NextRow);
        m_lateralMove = Math.abs(m_DestCol - m_NextCol);
        // Get frontal and lateral movement
        // If they are both not zero and both routes are blocked, then the path is not valid
        if (m_frontMove != 0 && m_lateralMove != 0) {
            if (!IsValidFrontLatPath(a_board) && !IsValidLatFrontPath(a_board)) {
                return false;
            }
            // Otherwise there exists a valid path of the two. Set the directions accordingly.
            else if (!IsValidFrontLatPath(a_board)) {
                m_direction = "laterally";
                m_secondDirection = "frontally";
                return true;
            }
            else {
                m_direction = "frontally";
                m_secondDirection = "laterally";
                return true;
            }
        }
        else if (m_lateralMove != 0) {
            // If there is only lateral movement check if the path is valid. Same for only frontal movement.
            if (!IsValidLatPath(a_board)) {
                return false;
            }
            m_direction = "laterally";
            m_secondDirection = "frontally";
            return true;
        }
        else if (m_frontMove != 0) {
            if (!IsValidFrontPath(a_board)) {
                return false;
            }
            m_direction = "frontally";
            m_secondDirection = "laterally";
            return true;
        }
        return false;
    }
    /* *********************************************************************
    Function Name: IsValidFrontPath
    Purpose: To check if a frontal path is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidFrontPath(ArrayList<ArrayList<String>> a_board) {
        int count = 0;
        boolean occupied = false;
        // In all cases, the path is invalid if there is at least one square on the path to the destination with a die on it, except at the destination coordinates.
        if (m_NextRow < m_DestRow) {
            for (int i = m_NextRow + 1; i < m_DestRow; i++) {
                if (!a_board.get(9 - i).get(m_NextCol).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        else {
            for (int i = m_NextRow - 1; i > m_DestRow; i--) {
                if (!a_board.get(9 - i).get(m_NextCol).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        if (occupied) {
            return false;
        }
        return true;
    }
    /* *********************************************************************
    Function Name: IsValidFrontLatPath
    Purpose: To check if a frontal/lateral path is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidFrontLatPath(ArrayList<ArrayList<String>> a_board) {
        int count = 0;
        boolean occupied = false;
        if (m_NextRow < m_DestRow) {
            for (int i = m_NextRow + 1; i <= m_DestRow; i++) {
                if (!a_board.get(9 - i).get(m_NextCol).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        else {
            for (int i = m_NextRow - 1; i >= m_DestRow; i--) {
                if (!a_board.get(9 - i).get(m_NextCol).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        if (m_NextCol < m_DestCol) {
            for (int i = m_NextCol + 1; i < m_DestCol; i++) {
                if (!a_board.get(9 - m_DestRow).get(i).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        // Check the frontal path before the lateral path
        else {
            for (int i = m_NextCol - 1; i > m_DestCol; i--) {
                if (!a_board.get(9 - m_DestRow).get(i).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        if (occupied) {
            return false;
        }
        return true;
    }
    /* *********************************************************************
    Function Name: IsValidLatFrontPath
    Purpose: To check if a lateral/frontal path is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidLatFrontPath(ArrayList<ArrayList<String>> a_board) {
        // Otherwise if laterally is selected as the first direction check the lateral path first.
        int count = 0;
        boolean occupied = false;
        if (m_NextCol < m_DestCol) {
            for (int i = m_NextCol + 1; i <= m_DestCol; i++) {
                if (!a_board.get(9 - m_NextRow).get(i).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        else {
            for (int i = m_NextCol - 1; i >= m_DestCol; i--) {
                if (!a_board.get(9 - m_NextRow).get(i).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        if (m_NextRow < m_DestRow) {
            for (int i = m_NextRow + 1; i < m_DestRow; i++) {
                if (!a_board.get(9 - i).get(m_DestCol).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        else {
            for (int i = m_NextRow - 1; i > m_DestRow; i--) {
                if (!a_board.get(9 - i).get(m_DestCol).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        if (occupied) {
            return false;
        }
        return true;
    }
    /* *********************************************************************
    Function Name: IsValidLatPath
    Purpose: To check if a lateral path is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidLatPath(ArrayList<ArrayList<String>> a_board) {
        int count = 0;
        boolean occupied = false;
        // Check the lateral path. If any dice invade it the path is invalid.
        if (m_NextCol < m_DestCol) {
            for (int i = m_NextCol + 1; i < m_DestCol; i++) {
                if (!a_board.get(9 - m_NextRow).get(i).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        else {
            for (int i = m_NextCol - 1; i > m_DestCol; i--) {
                if (!a_board.get(9 - m_NextRow).get(i).equals("0")) {
                    occupied = true;
                }
                count++;
            }
        }
        if (occupied) { // In all cases if we find an occupied die, the path must be invalid.
            return false;
        }
        return true;
    }
    /* *********************************************************************
    Function Name: IsValidDestinationSquare
    Purpose: To check if the ending square of a computer's move is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    a_top, an integer. It refers to the number at the top of the die before the move.
    Assistance Received: none
    **********************************************************************/
    boolean IsValidDestinationSquare(ArrayList<ArrayList<String>> a_board, int a_top) {
        // Report error if the user attempts to capture his own dice or tries to move out of bounds of the board.
        if (m_DestRow < 1 || m_DestRow > 8 || m_DestCol < 1 || m_DestCol > 9) {
            return false;
        }
        if (a_board.get(9 - m_DestRow).get(m_DestCol).startsWith("C")) {
            return false;
        }
        // Report error if the number of spaces covered is not equal to the number on the top of the die
        // Must use Math.absolute value because the user can move their dice in the following directions.
        // Up, down, left, and right
        if (Math.abs(Math.abs(m_DestRow - m_NextRow) + Math.abs(m_DestCol - m_NextCol)) != Math.abs(a_top)) {
            return false;
        }
        return true;
    }
    /* *********************************************************************
    Function Name: IsValidHumanDestinationSquare
    Purpose: To check if the ending square of a user's move is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    a_top, an integer. It refers to the number at the top of the die before the move.
    Assistance Received: none
    **********************************************************************/
    boolean IsValidHumanDestinationSquare(ArrayList<ArrayList<String>> a_board, int a_top) {
        // Report error if the user attempts to capture his own dice or tries to move out of bounds of the board.
        if (m_DestRow < 1 || m_DestRow > 8 || m_DestCol < 1 || m_DestCol > 9) {
            return false;
        }
        if ((a_board.get(9 - m_DestRow).get(m_DestCol).startsWith("H"))) {
            return false;
        }
        // Report error if the number of spaces covered is not equal to the number on the top of the die
        // Must use Math.absolute value because the user can move their dice in the following directions.
        // Up, down, left, and right
        if (Math.abs(Math.abs(m_DestRow - m_NextRow) + Math.abs(m_DestCol - m_NextCol)) != Math.abs(a_top)) {
            return false;
        }
        return true;
    }
    /* *********************************************************************
    Function Name: Play
    Purpose: To allow the computer player to make a move
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    a_view, the view object passed by reference.
    Local Variables:
    currentBoard, a pointer to a pointer to an array of Strings (2D array containing the board)
    coords, a array of (x, y) pairs containing all dice in a blocking path on the board
    Algorithm:
    1) Access the board using the getter function of a_board.
    2) Get the row and column of the human and computer key dice.
    3) For each space on the board, check if there is a die that will lead to a win. If there is, use that move (call UpdateComputerDie then RotateComputerDie)
    4) For each human die on the board, see if it can get to the computer key die right away. If it can, determine all possible paths that be invaded.
    5) If none of them result in a blocking move or if the key die is adjacent to a die with 1 on the top, move the key piece. Otherwise, use the piece that blocks the human from winning the game. This prevents the human from winning even if they had many moves to win though does not fit with certain cases.
    6) If 3, 4, and 5 don't result in a successful move made by the computer, Move the die which is the minimum of the distance to the key piece minus the number at the top of the die before the move.
    7) If 6 is what results in a reasonable move, choose the directions at random until the computer chooses a valid path. If the computer made 10 incorrect paths it forfeits its turn.
    8) Update the board and the faces of the dice by calling a_board::UpdateComputerDie, then Die::RotateComputerDie.
    Assistance Received: none
    Notes: The computer's strategy is by far the hardest part of the project.
    However, we decided to use the following strategy:
    If the computer does not have a direct move to the human's key die or it cannot block it should move the die that:
    Can be moved the most squares (has the highest number on top before the move) towards the human's key piece
    and that would be the closest to the key piece after its turn has ended. The movement is chosen at random.
    ********************************************************************* */
    void Play(Board a_board, BoardView a_view) {
        m_numFound = 0;
        ArrayList<ArrayList<String>> currentBoard = a_board.GetBoard();
        String DieToRoll = "";
        // Get the row and column of the key dice.
        int rowOfHumanKeyPiece = a_board.FindRowKeyPiece();
        int colOfHumanKeyPiece = a_board.FindColKeyPiece();
        int rowOfCompKeyPiece = a_board.FindRowCompKeyPiece(currentBoard);
        int colOfCompKeyPiece = a_board.FindColCompKeyPiece(currentBoard);
        System.out.println(rowOfCompKeyPiece + " " + colOfCompKeyPiece);
        boolean isOnKeySquare = false;
        int min = 25;
        int dist, allowedDist, distToOtherKeySquare;
        int currentRow;
        int currentCol;
        int comp = 0;
        int human = 0;
        int humanRow, humanCol;
        int distToCompKeyDie;
        int humanAllowedDist;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                currentRow = i;
                currentCol = j;
                if (currentBoard.get(9 - i).get(j).startsWith("C")) {
                    // Get the distance between each computer dice and the human's key die
                    dist = Math.abs(currentRow - rowOfHumanKeyPiece) + Math.abs(currentCol - colOfHumanKeyPiece);
                    distToOtherKeySquare = Math.abs(currentRow - 1) + Math.abs(currentCol - 5);
                    allowedDist = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                    System.out.println(dist + " " + allowedDist);
                    isOnKeySquare = (distToOtherKeySquare == allowedDist && currentBoard.get(9 - i).get(j).endsWith("11"));
                    if (dist == allowedDist || isOnKeySquare) { // If the distance to the key piece is equal to the allowed distance and the path is not blocked, then this is a move that captures the key piece
                        m_NextRow = currentRow;
                        m_NextCol = currentCol;
                        DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                        if (dist == allowedDist) {
                            m_DestCol = colOfHumanKeyPiece;
                            m_DestRow = rowOfHumanKeyPiece;
                        }
                        else if (isOnKeySquare) {
                            m_DestCol = 5;
                            m_DestRow = 1;
                        }
                        if (!IsValidPath(currentBoard)) {
                            // Invalid path. Computer does not win. Move on.
                            continue;
                        }
                        if (!IsValidDestinationSquare(currentBoard, allowedDist)) {
                            continue;
                        }
                        String move = "The computer moved " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                        String msg = "";
                        if (m_direction.equals("frontally")) {
                            msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to win this round!";
                        }
                        else {
                            msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to win this round!";
                        }
                        TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
                        txtView.setText(msg);
                        // Update and display the board
                        a_board.UpdateComputerMove(currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                        a_view.Display();
                        txtView.append(" The die is now " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " at (" + m_DestRow + ", " + m_DestCol + ")!");
                        return;
                    }
                    // If this gets the computer close to the key piece, but not exactly at the key piece, update the minimum distance
                    if (dist - allowedDist < min) {
                        min = dist - allowedDist;
                    }
                }
            }
        }
        // Have an array of 200 (x, y) pairs in case there are multiple blocking paths
        ArrayList<Pair<Integer, Integer>> coords = new ArrayList<Pair<Integer, Integer>>();
        int blocking = 0;
        for (int i = 8; i >= 1; i--) {
            for (int j = 9; j >= 1; j--) {
                humanCol = j;
                humanRow = i;
                if (currentBoard.get(9 - i).get(j).startsWith("H")) {
                    distToCompKeyDie = Math.abs(humanRow - rowOfCompKeyPiece) + Math.abs(humanCol - colOfCompKeyPiece);
                    humanAllowedDist = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                    // Check if the computer can block.
                    if (humanAllowedDist == distToCompKeyDie) {
                        // If it can the computer should move a die that will prevent the human from using the winning move
                        m_NextRow = humanRow;
                        m_NextCol = humanCol;
                        m_DestRow = rowOfCompKeyPiece;
                        m_DestCol = colOfCompKeyPiece;
                        if (!IsValidPath(currentBoard)) {
                            // Invalid path. Move on.
                            continue;
                        }
                        blocking++;
                        // Increment number of blocking moves available.
                        if (distToCompKeyDie < 2) {
                            // If the winning move is only one square away, move on
                            continue;
                        }
                        // Get all the paths that the human can take to win and the coordinates in those paths
                        GetAllCoordsInBlockingPath(coords, currentBoard);
                    }
                }
            }
        }
        if (MakeBlockingMove(a_board, coords, currentBoard, m_numFound, 'C', a_view)) {
            // If a successful blocking move was made, there is nothing left to do.
            return;
        }
        else if (blocking >= 1) {
            // Otherwise if there were at least one blocking move, move the key piece in a random direction by one while the destination is not the player's own dice.
            m_NextRow = rowOfCompKeyPiece;
            m_NextCol = colOfCompKeyPiece;
            DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
            do {
                int flag = m_random.nextInt(4);
                if (flag == 0) {
                    m_DestCol = m_NextCol;
                    m_DestRow = m_NextRow - 1;
                }
                else if (flag == 1) {
                    m_DestCol = m_NextCol + 1;
                    m_DestRow = m_NextRow;
                }
                else if (flag == 2) {
                    m_DestCol = m_NextCol;
                    m_DestRow = m_NextRow + 1;
                }
                else {
                    m_DestCol = m_NextCol - 1;
                    m_DestRow = m_NextRow;
                }
            } while (!IsValidDestinationSquare(currentBoard, 1));
            m_frontMove = Math.abs(m_DestRow - m_NextRow);
            m_lateralMove = Math.abs(m_DestCol - m_NextCol);
            m_direction = "frontally";
            m_secondDirection = "laterally";
            String move = "The computer moved " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
            String msg = "";
            if (m_direction.equals("frontally")) {
                msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to block Garen from winning the game!";
            }
            else {
                msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to block Garen from winning the game!";
            }
            TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
            txtView.setText(msg);
            a_board.UpdateComputerMove(currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
            a_view.Display();
            txtView.append(" The die is now " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " at (" + m_DestRow + ", " + m_DestCol + ")!");
            // Again, update and display the board and then get out of the function.
            return;
        }
        // If we get here then the computer cannot win nor block. Check if there are moves that capture non key dice.
        Map<Pair<Integer, Integer>, String> myPieces = new HashMap<Pair<Integer, Integer>, String>();
        // Keep all the coordinate pairs in a unordered map. Location is key and die is value.
        // Put all human dice in the map when the computer is playing and computer dice in the map if the human wants help.
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                if (currentBoard.get(i).get(j).startsWith("H")) {
                    myPieces.put(new Pair<Integer, Integer>(9 - i, j), currentBoard.get(i).get(j));
                }
            }
        }
        // For each human die in the map check if a computer die can legally capture it.
        for(Map.Entry<Pair<Integer, Integer>, String> entry : myPieces.entrySet()) {
            System.out.println(entry.getKey().first + " " + entry.getKey().second + " " + entry.getValue());
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    currentRow = i;
                    currentCol = j;
                    if (currentBoard.get(9 - i).get(j).startsWith("C")) {
                        // Get the distance between each computer dice and the human's key die
                        dist = Math.abs(currentRow - entry.getKey().first) + Math.abs(currentCol - entry.getKey().second);
                        allowedDist = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                        System.out.println(dist + " " + allowedDist);
                        if (dist == allowedDist) { // If the distance is equal to the allowed distance and the path is not blocked, then this is a move that captures the non key die
                            m_NextRow = currentRow;
                            m_NextCol = currentCol;
                            DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                            m_DestCol = entry.getKey().second;
                            m_DestRow = entry.getKey().first;
                            if (!IsValidPath(currentBoard)) {
                                // Invalid path. Computer does not capture non key die. Move on.
                                continue;
                            }
                            if (!IsValidDestinationSquare(currentBoard, allowedDist)) {
                                continue;
                            }
                            String move = "The computer moved " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                            String msg = "";
                            if (m_direction.equals("frontally")) {
                                msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to capture " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " located at square (" + m_DestRow + ", " + m_DestCol + ")!";
                            }
                            else {
                                msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to capture " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " located at square (" + m_DestRow + ", " + m_DestCol + ")!";
                            }
                            TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
                            txtView.setText(msg);
                            // Update and display the board
                            a_board.UpdateComputerMove(currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                            a_view.Display();
                            txtView.append(" The die is now " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " at (" + m_DestRow + ", " + m_DestCol + ")!");
                            return;
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                currentRow = i;
                currentCol = j;
                dist = Math.abs(currentRow - rowOfHumanKeyPiece) + Math.abs(currentCol - colOfHumanKeyPiece);
                int allowedDist2 = 0;
                if (currentBoard.get(9 - i).get(j).startsWith("C")) {
                    allowedDist2 = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                }
                if (currentBoard.get(9 - i).get(j).startsWith("C") && dist - allowedDist2 == min) {
                    // If the move gets the computer closer to the key piece, check if the path is valid and choose the directions and movement at random.
                    // But the die to move is not random though.
                    m_NextRow = currentRow;
                    m_NextCol = j;
                    int invalid = 0;
                    DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                    do {
                        int firstMove = (m_random.nextInt(allowedDist2) + 1);
                        int flag = (m_random.nextInt(2));
                        int secondMove = allowedDist2 - firstMove;
                        int secondflag = (m_random.nextInt(2));
                        if (flag == 0) {
                            firstMove *= -1;
                        }
                        if (secondflag == 0) {
                            secondMove *= -1;
                        }
                        m_DestCol = m_NextCol + firstMove;
                        m_DestRow = m_NextRow + secondMove;
                        m_frontMove = Math.abs(m_DestRow - i);
                        m_lateralMove = Math.abs(m_DestCol - m_NextCol);
                        if (!IsValidDestinationSquare(currentBoard, allowedDist2) || !IsValidPath(currentBoard)) {
                            invalid++;
                            continue;
                        }
                    } while (!IsValidDestinationSquare(currentBoard, allowedDist2) || !IsValidPath(currentBoard));
                    String move = "The computer moved " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                    String msg = "";
                    if (m_direction.equals("frontally")) {
                        msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") because there were no winning or blocking moves available!";
                    }
                    else {
                        msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") because there were no winning or blocking moves available!";
                    }
                    TextView txtView = (TextView)m_activity.findViewById(R.id.prompt);
                    txtView.setText(msg);
                    a_board.UpdateComputerMove(currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, i, m_NextCol, m_DestRow, m_DestCol);
                    a_view.Display();
                    txtView.append(" The die is now " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " at (" + m_DestRow + ", " + m_DestCol + ")!");
                    // Update the board and get out of the function.
                    return;
                }
            }
        }
    }
    /* *********************************************************************
    Function Name: HelpHuman
    Purpose: To allow the computer player to suggest a move to the human if it asks for help.
    Returns: True if the human took the suggestion, false otherwise.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    a_view, the view object passed by reference.
    Local Variables:
    currentBoard, a pointer to a pointer to an array of Strings (2D array containing the board)
    coords, a array of (x, y) pairs containing all dice in a blocking path on the board
    Algorithm:
    1) Access the board using the getter function of a_board.
    2) Get the row and column of the human and computer key dice.
    3) For each space on the board, check if there is a die that will lead to a win. If there is, suggest that move.
    4) For each computer die on the board, see if it can get to the human key die right away. If it can, determine all possible paths that be invaded.
    5) If none of them result in a blocking move or if the key die is adjacent to a die with 1 on the top, suggest moving the key piece. Otherwise, suggest the piece that blocks the human from winning the game. This prevents the computer from winning even if they had many moves to win though does not fit with certain cases.
    6) If 3, 4, and 5 don't result in a successful move made by the human, suggest the die which is the minimum of the distance to the key piece minus the number at the top of the die before the move.
    7) If 6 is what results in a reasonable move, choose the directions at random until the computer chooses a valid path. If the computer made 10 incorrect paths it has nothing to say to the human.
    8) If the human took the suggestion, update the board and the faces of the dice by calling a_board::UpdateBoard, then Die::Rotate.
    ********************************************************************* */
    boolean HelpHuman(Board a_board, BoardView a_view) {
        m_numFound = 0;
        ArrayList<ArrayList<String>> currentBoard = a_board.GetBoard();
        String DieToRoll = "";
        // Get row and column of all the key dice.
        int rowOfHumanKeyPiece = a_board.FindRowKeyPiece();
        int colOfHumanKeyPiece = a_board.FindColKeyPiece();
        int rowOfCompKeyPiece = a_board.FindRowCompKeyPiece(currentBoard);
        int colOfCompKeyPiece = a_board.FindColCompKeyPiece(currentBoard);
        boolean isOnKeySquare = false;
        int min = 25;
        int dist, allowedDist, distToOtherKeySquare;
        int currentRow;
        int currentCol;
        int comp = 0;
        int human = 0;
        String move = "";
        int humanRow, humanCol;
        int distToCompKeyDie;
        int humanAllowedDist;
        // Check if the human can win the game.
        for (int i = 8; i >= 1; i--) {
            for (int j = 9; j >= 1; j--) {
                currentRow = i;
                currentCol = j;
                if (currentBoard.get(9 - i).get(j).startsWith("H")) {
                    dist = Math.abs(currentRow - rowOfCompKeyPiece) + Math.abs(currentCol - colOfCompKeyPiece);
                    distToOtherKeySquare = Math.abs(currentRow - 8) + Math.abs(currentCol - 5);
                    allowedDist = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                    isOnKeySquare = (distToOtherKeySquare == allowedDist && currentBoard.get(9 - i).get(j).endsWith("11"));
                    if (dist == allowedDist || isOnKeySquare) { // If the distance to the key piece is equal to the allowed distance and the path is not blocked, then this is a move that captures the key piece
                        m_NextRow = currentRow;
                        m_NextCol = currentCol;
                        DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                        if (dist == allowedDist) {
                            m_DestCol = colOfCompKeyPiece;
                            m_DestRow = rowOfCompKeyPiece;
                        }
                        else if (isOnKeySquare) {
                            m_DestCol = 5;
                            m_DestRow = 8;
                        }
                        m_frontMove = Math.abs(m_DestRow - m_NextRow);
                        m_lateralMove = Math.abs(m_DestCol - m_NextCol);
                        if (!IsValidPath(currentBoard)) {
                            // Invalid path. Move on.
                            continue;
                        }
                        if (!IsValidHumanDestinationSquare(currentBoard, allowedDist)) {
                            continue;
                        }
                        move += "Are you sure you want to move " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                        String msg = "";
                        if (m_direction.equals("frontally")) {
                            msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to win this round?";
                        }
                        else {
                            msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to win this round?";
                        }
                        DisplaySuggestion(msg, a_board, a_view, currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                        if (m_tookSuggestion){
                            return true;
                        }
                        return false;
                    }
                    if (dist - allowedDist < min) { // Otherwise, set the minimum equal to the difference between the distance to the key die and the allowed distance
                        min = dist - allowedDist;
                    }
                }
            }
        }
        int blocking = 0;
        ArrayList<Pair<Integer, Integer>> coords = new ArrayList<Pair<Integer, Integer>>();
        // Check for blocking moves.
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 9; j++) {
                humanRow = i;
                humanCol = j;
                if (currentBoard.get(9 - i).get(j).startsWith("C")) {
                    distToCompKeyDie = Math.abs(humanRow - rowOfHumanKeyPiece) + Math.abs(humanCol - colOfHumanKeyPiece);
                    humanAllowedDist = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                    if (humanAllowedDist == distToCompKeyDie) {
                        m_NextRow = humanRow;
                        m_NextCol = humanCol;
                        DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                        m_DestCol = colOfHumanKeyPiece;
                        m_DestRow = rowOfHumanKeyPiece;
                        if (!IsValidPath(currentBoard)) {
                            // Invalid path. Move on.
                            continue;
                        }
                        blocking++;
                        // If this is adjacent to the key die, move on.
                        if (distToCompKeyDie < 2) {
                            continue;
                        }
                        // Get all the coordinates in the blocking path.
                        GetAllCoordsInBlockingPath(coords, currentBoard);
                    }
                }
            }
        }
        // If the blocking move was successful, suggest it.
        // If the human took the suggestion, automatically use the move.
        if (MakeBlockingMove(a_board, coords, currentBoard, m_numFound, 'H', a_view)) {
            if (m_tookSuggestion) {
                return true;
            }
            return false;
        }
        else if (blocking >= 1) {
            // Otherwise if there exists one blocking move, move the key die.
            m_NextRow = rowOfHumanKeyPiece;
            m_NextCol = colOfHumanKeyPiece;
            DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
            do {
                int flag = m_random.nextInt(4);
                if (flag == 0) {
                    m_DestCol = m_NextCol;
                    m_DestRow = m_NextRow - 1;
                }
                else if (flag == 1) {
                    m_DestCol = m_NextCol + 1;
                    m_DestRow = m_NextRow;
                }
                else if (flag == 2) {
                    m_DestCol = m_NextCol;
                    m_DestRow = m_NextRow + 1;
                }
                else {
                    m_DestCol = m_NextCol - 1;
                    m_DestRow = m_NextRow;
                }
            } while (!IsValidHumanDestinationSquare(currentBoard, 1));
            m_frontMove = Math.abs(m_DestRow - m_NextRow);
            m_lateralMove = Math.abs(m_DestCol - m_NextCol);
            if (m_frontMove != 0) {
                m_direction = "frontally";
                m_secondDirection = "laterally";
            }
            else if (m_lateralMove != 0) {
                m_direction = "laterally";
                m_secondDirection = "frontally";
            }
            move += "Are you sure you want to move " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
            String msg = "";
            if (m_direction.equals("frontally")) {
                msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to block the computer from winning the game?";
            }
            else {
                msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") to block the computer from winning the game?";
            }
            DisplaySuggestion(msg, a_board, a_view, currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
            if (m_tookSuggestion) {
                return true;
            }
            return false;
        }
        // If we get here there were no winning or blocking moves that the computer could recommend.
        Map<Pair<Integer, Integer>, String> myPieces = new HashMap<Pair<Integer, Integer>, String>();
        for (int i = 8; i >= 1; i--) {
            for (int j = 9; j >= 1; j--) {
                if (currentBoard.get(i).get(j).startsWith("C")) {
                    myPieces.put(new Pair<Integer, Integer>(9 - i, j), currentBoard.get(i).get(j));
                }
            }
        }
        for(Map.Entry<Pair<Integer, Integer>, String> entry : myPieces.entrySet()) {
            System.out.println(entry.getKey().first + " " + entry.getKey().second + " " + entry.getValue());
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    currentRow = i;
                    currentCol = j;
                    if (currentBoard.get(9 - i).get(j).startsWith("H")) {
                        // Get the distance between each computer dice and the human's key die
                        dist = Math.abs(currentRow - entry.getKey().first) + Math.abs(currentCol - entry.getKey().second);
                        allowedDist = Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1));
                        System.out.println(dist + " " + allowedDist);
                        if (dist == allowedDist) { // If the distance to the key piece is equal to the allowed distance and the path is not blocked, then this is a move that captures the key piece
                            m_NextRow = currentRow;
                            m_NextCol = currentCol;
                            DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                            m_DestCol = entry.getKey().second;
                            m_DestRow = entry.getKey().first;
                            if (!IsValidPath(currentBoard)) {
                                // Invalid path. Capturing move cannot be made. Move on.
                                continue;
                            }
                            if (!IsValidHumanDestinationSquare(currentBoard, allowedDist)) {
                                continue;
                            }
                            move += "Are you sure you want to move " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                            String msg = "";
                            if (m_direction.equals("frontally")) {
                                msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to capture " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " located at square (" + m_DestRow + ", " + m_DestCol + ")?";
                            }
                            else {
                                msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to capture " + currentBoard.get(9 - m_DestRow).get(m_DestCol) + " located at square (" + m_DestRow + ", " + m_DestCol + ")?";
                            }
                            DisplaySuggestion(msg, a_board, a_view, currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                            if (m_tookSuggestion) {
                                return true;
                            }
                            return false;
                        }
                    }
                }
            }
        }
        for (int i = 8; i >= 1; i--) {
            for (int j = 9; j >= 1; j--) {
                currentRow = i;
                currentCol = j;
                dist = Math.abs(currentRow - rowOfCompKeyPiece) + Math.abs(currentCol - colOfCompKeyPiece);
                int allowedDist2 = 0;
                if (currentBoard.get(9 - i).get(j).startsWith("H")) {
                    allowedDist2 = (Character.getNumericValue(currentBoard.get(9 - i).get(j).charAt(1)));
                }
                if (currentBoard.get(9 - i).get(j).startsWith("H") && dist - allowedDist2 == min) {
                    // Suggest the move that will get closest to the human's key piece.
                    m_NextRow = currentRow;
                    m_NextCol = j;
                    int invalid = 0;
                    DieToRoll = currentBoard.get(9 - m_NextRow).get(m_NextCol);
                    do {
                        int firstMove = (m_random.nextInt(allowedDist2) + 1);
                        int flag = (m_random.nextInt(2));
                        int secondMove = allowedDist2 - firstMove;
                        int secondflag = (m_random.nextInt(2));
                        if (flag == 0) {
                            firstMove *= -1;
                        }
                        if (secondflag == 0) {
                            secondMove *= -1;
                        }
                        m_DestCol = m_NextCol + firstMove;
                        m_DestRow = m_NextRow + secondMove;
                        m_frontMove = Math.abs(m_DestRow - i);
                        m_lateralMove = Math.abs(m_DestCol - m_NextCol);
                        if (!IsValidHumanDestinationSquare(currentBoard, allowedDist2) || !IsValidPath(currentBoard)) {
                            invalid++;
                            continue;
                        }
                    } while (!IsValidHumanDestinationSquare(currentBoard, allowedDist2) || !IsValidPath(currentBoard));
                    move += "Are you sure you want to move " + DieToRoll + " located at (" + m_NextRow + ", " + m_NextCol + ")";
                    String msg = "";
                    if (m_direction.equals("frontally")) {
                        msg = move + " frontally by " + m_frontMove + " and laterally by " + m_lateralMove + " to square (" + m_DestRow + ", " + m_DestCol + ") (there are no moves to win the game or block the computer from winning)?";
                    }
                    else {
                        msg = move + " laterally by " + m_lateralMove + " and frontally by " + m_frontMove + " to square (" + m_DestRow + ", " + m_DestCol + ") (there are no moves to win the game or block the computer from winning)?";
                    }
                    DisplaySuggestion(msg, a_board, a_view, currentBoard, m_frontMove, m_lateralMove, m_direction, m_secondDirection, DieToRoll, m_NextRow, m_NextCol, m_DestRow, m_DestCol);
                    if (m_tookSuggestion) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
    private String m_direction;
    private String m_secondDirection;
    private int m_NextRow;
    private int m_NextCol;
    private int m_DestRow;
    private int m_DestCol;
    private int m_frontMove;
    private int m_lateralMove;
    private boolean m_tookSuggestion;
    private Random m_random;
    private int m_numFound;
    private Board m_board;
    private BoardView m_view;
    private Context m_context;
    private Round m_round;
    private MainActivity m_activity;
    public String m_die;
    public String m_prompt;
    public ArrayList<ArrayList<String>> m_game;
}