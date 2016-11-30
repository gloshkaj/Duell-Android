package com.androidduell.garen.androidduell;
import java.util.*;
/**
 * Created by Garen on 11/7/2016.
 */

public class Human extends Player {
    public Human() {
        m_NextCol = 1;
        m_NextRow = 1;
        m_DestRow = 1;
        m_DestCol = 1;
    }
    /* *********************************************************************
    Function Name: IsValidFrontPath
    Purpose: To check if a frontal path is valid
    Returns: True if valid, false if not.
    Parameters:
    a_board, the current board passed by reference. It holds the all the computer and human dice.
    a_startRow, an integer. It contains the row of the starting square
    a_startCol, an integer. It contains the column of the starting square
    a_destRow, an integer. It contains the row of the ending square
    a_destCol, an integer. It contains the column of the ending square
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidFrontPath(ArrayList<ArrayList<String>> a_board, int a_startRow, int a_startCol, int a_destRow, int a_destCol) {
        m_NextRow = a_startRow;
        m_NextCol = a_startCol;
        m_DestCol = a_destCol;
        m_DestRow = a_destRow;
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
    a_board, 2D array list representing the current board passed by reference. It holds the all the computer and human dice.
    a_startRow, an integer. It contains the row of the starting square
    a_startCol, an integer. It contains the column of the starting square
    a_destRow, an integer. It contains the row of the ending square
    a_destCol, an integer. It contains the column of the ending square
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidFrontLatPath(ArrayList<ArrayList<String>> a_board, int a_startRow, int a_startCol, int a_destRow, int a_destCol) {
        m_NextRow = a_startRow;
        m_NextCol = a_startCol;
        m_DestCol = a_destCol;
        m_DestRow = a_destRow;
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
    a_startRow, an integer. It contains the row of the starting square
    a_startCol, an integer. It contains the column of the starting square
    a_destRow, an integer. It contains the row of the ending square
    a_destCol, an integer. It contains the column of the ending square
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidLatFrontPath(ArrayList<ArrayList<String>> a_board, int a_startRow, int a_startCol, int a_destRow, int a_destCol) {
        // Otherwise if laterally is selected as the first direction check the lateral path first.
        m_NextRow = a_startRow;
        m_NextCol = a_startCol;
        m_DestCol = a_destCol;
        m_DestRow = a_destRow;
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
    a_startRow, an integer. It contains the row of the starting square
    a_startCol, an integer. It contains the column of the starting square
    a_destRow, an integer. It contains the row of the ending square
    a_destCol, an integer. It contains the column of the ending square
    Assistance Received: none
    ********************************************************************* */
    boolean IsValidLatPath(ArrayList<ArrayList<String>> a_board, int a_startRow, int a_startCol, int a_destRow, int a_destCol) {
        m_NextRow = a_startRow;
        m_NextCol = a_startCol;
        m_DestCol = a_destCol;
        m_DestRow = a_destRow;
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
    private int m_NextRow;
    private int m_NextCol;
    private int m_DestCol;
    private int m_DestRow;
}