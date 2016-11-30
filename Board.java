package com.androidduell.garen.androidduell;
import com.androidduell.garen.androidduell.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import java.util.*;
import android.content.Context;
import android.view.View;
/**
 * Created by Garen on 11/3/2016.
 */
public class Board {
    public Board() {
        m_board = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < 9; i++) {
            ArrayList<String> row = new ArrayList<String>();
            for (int j = 0; j < 10; j++) {
                row.add("0");
            }
            m_board.add(row);
        }
        m_topPiece = 1;
        m_rightPiece = 1;
        m_xcoord = 1;
        m_ycoord = 8;
        m_die = new Die();
    }
    /*Function Name : FillBoard
Purpose : To populate the board when a game loads from a saved state.
Parameters :
a_lines[], an array of strings passed by value. It contains the dice in the row to be restored. Note that the board is being restored one row at a time.
a_index, an integer. It refers to the nth row that we are restoring from the saved state.
********************************************************************* */
    void FillBoard(String a_lines[], int a_index) {
        for (int i = 0; i < a_lines.length; i++) {
            m_board.get(a_index).set(i + 1, a_lines[i]);
        }
    }
    /* Function Name : DrawBoard
Purpose : To draw the initial model of the board when a round begins.
Parameters :
None
Algorithm :
1) For each empty space in the multidimensional array, check the subscript.
2) If both row and column are 0 place an empty string in the extreme left and the y coordinate numbers for the rest of the numbers
3) If row is 1, then this is row 8, so populate the computer's home row
4) If row is 8, this is row 1, so populate the human's home row.
5) In either case call GetStartingPiece to determine the die to place at the location.
6) If the row is not 0, 1, or 8, then place zeros in the board and at the extreme left column put the x coordinate numbers
Assistance Received : none
********************************************************************* */
    void DrawBoard() {
        for (int i = 0; i < 9; i++) {
            if (i == 0) { // If this is the first row, place the y coordinates on the top
                for (int j = 0; j < 10; j++) {
                    if (j == 0) {
                        m_board.get(i).set(j, "");
                    }
                    else {
                        m_board.get(i).set(j, Integer.toString(j));
                    }
                }
            }
            else if (i == 1) { // If this is the second row, then this is the computer's home row.
                for (int j = 0; j < 10; j++) {
                    if (j == 0) {
                        m_board.get(i).set(j, Integer.toString(m_ycoord));
                        m_ycoord--;
                    }
                    else {
                        // Get the computer's starting die
                        m_board.get(i).set(j, GetStartingPiece(m_board.get(i), j, "C"));
                    }
                }
            }
            else if (i == 8) { // If this is the last row, this is the human's home row.
                for (int j = 0; j < 10; j++) {
                    if (j == 0) {
                        m_board.get(i).set(j, Integer.toString(m_ycoord));
                        m_ycoord--;
                    }
                    else {
                        m_board.get(i).set(j, GetStartingPiece(m_board.get(i), j, "H"));
                    }
                }
            }
            else {
                // Otherwise, populate empty squares with 0's.
                m_board.get(i).set(0, Integer.toString(m_ycoord));
                for (int j = 1; j < 10; j++) {
                    if (!m_board.get(i).get(j).equals("0")) {
                        m_board.get(i).set(j, "0");
                    }
                }
                m_ycoord--;
            }
        }
    }
    /* Function Name : GetStartingPiece
Purpose : To determine what the top and right faces of the dice should be at the beginning of the game.
Returns: The string representing the die.
Parameters :
a_col, a string array list passed by value which holds the columns in the multidimensional array
a_index, an integer referring to the current index in the inner for loop
a_player, the string representing "C" or "H" to place at the beginning of the string to represent a computer or human die
Algorithm :
1) Check the subscript of the inner for loop.
2) If it is 5, then this is a key die, set top and right to 1.
3) If it is 1, set top to 5 and right to 6
4) If it is 2, set top to 1 and right to 5
5) If it is 6, set top to 6 and right to 2
6) If it is 7, set top to 2 and right to 1
7) Otherwise the die is in the middle, so convert ASCII code to integer and subtract the opposite die's top and right respectively from 7 to get the top and right faces of the middle dice.
8) Set the top and right faces to the results of the function.
9) Return the value as a concatenated string.
Assistance Received : none
********************************************************************* */
    String GetStartingPiece(ArrayList<String> a_col, int a_index, String a_player) {
        // Computer or human
        String piece = a_player;
        // If this is in the middle it is the key piece
        if (a_index == 5) {
            m_topPiece = 1;
            m_rightPiece = 1;
        }
        else if (a_index == 1) {
            // if it is 1, then it is a 5/6 die.
            m_topPiece = 5;
            m_rightPiece = 6;
        }
        else if (a_index == 2) {
            // if it is 2, it is a 1/5 die.
            m_topPiece = 1;
            m_rightPiece = 5;
        }
        // If it is close to the middle, subtract the opposite element from 7 to get the top and right faces. Convert ASCII to integer.
        else if (a_index == 3 || a_index == 4 || a_index == 8 || a_index == 9) {
            char firstPiece = a_col.get(a_index - 2).charAt(1);
            char secondPiece = a_col.get(a_index - 2).charAt(2);
            int first = (firstPiece - '0') % 48;
            int second = (secondPiece - '0') % 48;
            m_topPiece = 7 - first;
            m_rightPiece = 7 - second;
        }
        else if (a_index == 6) {
            // if it is 6, it is a 6/2 die.
            m_topPiece = 6;
            m_rightPiece = 2;
        }
        else if (a_index == 7) {
            // if it is 7, it is a 2/1 die.
            m_topPiece = 2;
            m_rightPiece = 1;
        }
        // Update the top and right faces.
        m_die.SetRight(m_rightPiece);
        m_die.SetTop(m_topPiece);
        // Return the die as a string
        return piece + m_die.ToString();
    }
    ArrayList<ArrayList<String>> GetBoard() {
        return m_board;
    }
    /* Function Name : UpdateComputerMove
Purpose : To update the board from a move made by the computer.
Parameters :
a_board, a 2D arraylist of strings passed by reference. It contains the board and its dice.
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
1) Call the Rotate function of the Die class to get the new top and right faces of the die after the move.
2) If there was a human die at the destination tell the human they captured that die.
3) Update the board with the new die.
Assistance Received : none
********************************************************************* */
    void UpdateComputerMove(ArrayList<ArrayList<String>> a_board, int a_front, int a_lat, String a_firstDirection, String a_secondDirection, String a_die, int a_NextRow, int a_NextCol, int a_DestRow, int a_DestCol) {
        String newDie = m_die.RotateComputerDie(a_board, a_die, a_firstDirection, a_front, a_secondDirection, a_lat, a_NextRow, a_NextCol, a_DestRow, a_DestCol);
        // Change the start to empty after the move
        m_board.get(9 - a_NextRow).set(a_NextCol, "0");
        System.out.println("The die is now " + newDie + " at (" + a_DestRow + ", " + a_DestCol + ")! ");
        // If there was an enemy die at the destination, say that it was captured.
        if (!m_board.get(9 - a_DestRow).get(a_DestCol).equals("0")) {
            System.out.println("The computer captured " + m_board.get(9 - a_DestRow).get(a_DestCol) + " which was previously located at (" + a_DestRow + ", " + a_DestCol + ")!");
        }
        System.out.println("\n");
        // Update the destination.
        m_board.get(9 - a_DestRow).set(a_DestCol, newDie);
    }
    /* Function Name : UpdateBoard
Purpose : To update the board from a human's move.
Parameters :
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
1) Call the Rotate function of the Die class to get the new top and right faces of the die after the move.
2) If there was a human die at the destination tell the human they captured that die.
3) Update the board with the new die.
Assistance Received : none
********************************************************************* */
    void UpdateBoard(ArrayList<ArrayList<String>> a_board, int a_front, int a_lat, String a_firstDirection, String a_secondDirection, String a_die, int a_NextRow, int a_NextCol, int a_DestRow, int a_DestCol) {
        String newDie = m_die.Rotate(a_board, a_die, a_firstDirection, a_front, a_secondDirection, a_lat, a_NextRow, a_NextCol, a_DestRow, a_DestCol);
        // Change the start to empty after the move
        m_board.get(9 - a_NextRow).set(a_NextCol, "0");
        System.out.println(m_board.get(9 - a_NextRow).get(a_NextCol));
        System.out.println("The die is now " + newDie + " at (" + a_DestRow + ", " + a_DestCol + ")! ");
        // If there was an enemy die at the destination, print to the screen that it was captured.
        if (!m_board.get(9 - a_DestRow).get(a_DestCol).equals("0")) {
            System.out.println("Garen captured " + m_board.get(9 - a_DestRow).get(a_DestCol) + " which was previously located at (" + a_DestRow + ", " + a_DestCol + ")!");
        }
        System.out.println("\n");
        // Update the destination.
        m_board.get(9 - a_DestRow).set(a_DestCol, newDie);
    }
    /*Function Name : FindKeyPiece
    Purpose : To determine if the game was over because the human captured the key die
    Returns: True if found, false if not found.
    Parameters :
    a_key, the string passed by value which contains the die to search for.
    ********************************************************************* */
    boolean FindKeyPiece(String a_key) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (m_board.get(i).get(j).equals(a_key)) {
                    // If we get here, the piece was found
                    return true;
                }
            }
        }
        return false; // Otherwise, it does not exist.
    }
    /* Function Name : FindColKeyPiece
    Purpose : Determines the column of the human key piece
    Returns: Column if found, -1 if not found
    Parameters :
    None
    ********************************************************************* */
    int FindColKeyPiece() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (m_board.get(i).get(j).equals("H11")) {
                    // If we get here the human's key piece was found
                    return j;
                }
            }
        }
        // It was not found
        return -1;
    }
    /* Function Name : FindRowKeyPiece
    Purpose : Determines the row of the human key piece
    Returns: Row if found, -1 if not found
    Parameters :
    None
    ********************************************************************* */
    int FindRowKeyPiece() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (m_board.get(i).get(j).equals("H11")) { // If we get here then the human's key piece was found
                    return 9 - i;
                }
            }
        }
        // Otherwise it was not found
        return -1;
    }
    /* Function Name : FindColCompKeyPiece
    Purpose : Determines the column of the computer key piece
    Returns: Column if found, -1 if not found
    Parameters :
    None
    ********************************************************************* */
    int FindColCompKeyPiece(ArrayList<ArrayList<String>> a_board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (a_board.get(i).get(j).equals("C11")) { // If we get here the computer's key piece was found
                    return j;
                }
            }
        }
        return -1; // Otherwise it was not found
    }
    /* Function Name : FindRowCompKeyPiece
    Purpose : Determines the row of the computer key piece
    Returns: Row if found, -1 if not found
    Parameters :
    None
    ********************************************************************* */
    int FindRowCompKeyPiece(ArrayList<ArrayList<String>> a_board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (a_board.get(i).get(j).equals("C11")) { // If we get here the key piece was found.
                    return 9 - i;
                }
            }
        }
        return -1; // It was not found at this point
    }// Getters and setters
    int GetTopPiece() {
        return m_topPiece;
    }
    int GetRightPiece() {
        return m_rightPiece;
    }
    int GetX() {
        return m_xcoord;
    }
    int GetY() {
        return m_ycoord;
    }
    void SetX(int a_xcoord) {
        m_xcoord = a_xcoord;
    }
    void SetY(int a_ycoord) {
        m_ycoord = a_ycoord;
    }
    void SetTop(int a_top) {
        m_topPiece = a_top;
    }
    void SetRight(int a_right) {
        m_rightPiece = a_right;
    }
    private ArrayList<ArrayList<String>> m_board;
    private Die m_die;
    private int m_xcoord;
    private int m_ycoord;
    private int m_topPiece;
    private int m_rightPiece;
}