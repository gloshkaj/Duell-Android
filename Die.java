package com.androidduell.garen.androidduell;
import com.androidduell.garen.androidduell.*;
import java.util.*;
import android.widget.*;
/**
 * Created by Garen on 10/31/2016.
 */
public class Die {
    public Die() {
        m_top = 1;
        m_right = 1;
    }
    public Die(int a_top, int a_right) {
        m_top = a_top;
        m_right = a_right;
    }
    // Constructor, getters, and setters
    public int GetTop() { return m_top; }
    public int GetRight() { return m_right; }
    public void SetTop(int a_top) { m_top = a_top; }
    public void SetRight(int a_right) { m_right = a_right; }
    /* *********************************************************************
Function Name: ToString
Purpose: To represent the die object as a string
Parameters:
None
Returns: a concatenated string with the top, then the right faces.
********************************************************************* */
    public String ToString() {return Integer.toString(m_top) + Integer.toString(m_right);}
    /* *********************************************************************
    Function Name: DetermineLateralMovement
    Purpose: To determine values from forward lateral movement of the die.
    Parameters:
    a_lateral: The array passed by reference holding values from lateral movement
    a_frontal: The array passed by reference holding values from frontal movement
    Algorithm:
    1) If top and right are both 1 don't do anything with the arrays.
    2) Otherwise make sure that opposite elements add up to 7.
    ********************************************************************* */
    public void DetermineLateralMovement(int a_lateral[], int a_frontal[]) {
        if (m_top == 5 && m_right == 6) {
            a_lateral[0] = 5;
            a_lateral[1] = 1;
            a_lateral[2] = 2;
            a_lateral[3] = 6;
        }
        else if (m_top == 5 && m_right == 3) {
            a_lateral[0] = 5;
            a_lateral[1] = 4;
            a_lateral[2] = 2;
            a_lateral[3] = 3;
        }
        else if (m_top == 5 && m_right == 1) {
            a_lateral[0] = 5;
            a_lateral[1] = 6;
            a_lateral[2] = 2;
            a_lateral[3] = 1;
        }
        else if (m_top == 5 && m_right == 4) {
            a_lateral[0] = 5;
            a_lateral[1] = 3;
            a_lateral[2] = 2;
            a_lateral[3] = 4;
        }
        else if (m_top == 1 && m_right == 5) {
            a_lateral[0] = 1;
            a_lateral[1] = 2;
            a_lateral[2] = 6;
            a_lateral[3] = 5;
        }
        else if (m_top == 1 && m_right == 3) {
            a_lateral[0] = 1;
            a_lateral[1] = 4;
            a_lateral[2] = 6;
            a_lateral[3] = 3;
        }
        else if (m_top == 1 && m_right == 2) {
            a_lateral[0] = 1;
            a_lateral[1] = 5;
            a_lateral[2] = 6;
            a_lateral[3] = 2;
        }
        else if (m_top == 1 && m_right == 4) {
            a_lateral[0] = 1;
            a_lateral[1] = 3;
            a_lateral[2] = 6;
            a_lateral[3] = 4;
        }
        else if (m_top == 2 && m_right == 1) {
            a_lateral[0] = 2;
            a_lateral[1] = 6;
            a_lateral[2] = 5;
            a_lateral[3] = 1;
        }
        else if (m_top == 2 && m_right == 3) {
            a_lateral[0] = 2;
            a_lateral[1] = 4;
            a_lateral[2] = 5;
            a_lateral[3] = 3;
        }
        else if (m_top == 2 && m_right == 6) {
            a_lateral[0] = 2;
            a_lateral[1] = 1;
            a_lateral[2] = 5;
            a_lateral[3] = 6;
        }
        else if (m_top == 2 && m_right == 4) {
            a_lateral[0] = 2;
            a_lateral[1] = 3;
            a_lateral[2] = 5;
            a_lateral[3] = 4;
        }
        else if (m_top == 6 && m_right == 2) {
            a_lateral[0] = 6;
            a_lateral[1] = 5;
            a_lateral[2] = 1;
            a_lateral[3] = 2;
        }
        else if (m_top == 6 && m_right == 3) {
            a_lateral[0] = 6;
            a_lateral[1] = 4;
            a_lateral[2] = 1;
            a_lateral[3] = 3;
        }
        else if (m_top == 6 && m_right == 5) {
            a_lateral[0] = 6;
            a_lateral[1] = 2;
            a_lateral[2] = 1;
            a_lateral[3] = 5;
        }
        else if (m_top == 6 && m_right == 4) {
            a_lateral[0] = 6;
            a_lateral[1] = 3;
            a_lateral[2] = 1;
            a_lateral[3] = 4;
        }
        else if (m_top == 3 && m_right == 2) {
            a_lateral[0] = 3;
            a_lateral[1] = 5;
            a_lateral[2] = 4;
            a_lateral[3] = 2;
        }
        else if (m_top == 3 && m_right == 1) {
            a_lateral[0] = 3;
            a_lateral[1] = 6;
            a_lateral[2] = 4;
            a_lateral[3] = 1;
        }
        else if (m_top == 3 && m_right == 5) {
            a_lateral[0] = 3;
            a_lateral[1] = 2;
            a_lateral[2] = 4;
            a_lateral[3] = 5;
        }
        else if (m_top == 3 && m_right == 6) {
            a_lateral[0] = 3;
            a_lateral[1] = 1;
            a_lateral[2] = 4;
            a_lateral[3] = 6;
        }
        else if (m_top == 4 && m_right == 5) {
            a_lateral[0] = 4;
            a_lateral[1] = 2;
            a_lateral[2] = 3;
            a_lateral[3] = 5;
        }
        else if (m_top == 4 && m_right == 1) {
            a_lateral[0] = 4;
            a_lateral[1] = 6;
            a_lateral[2] = 3;
            a_lateral[3] = 1;
        }
        else if (m_top == 4 && m_right == 2) {
            a_lateral[0] = 4;
            a_lateral[1] = 5;
            a_lateral[2] = 3;
            a_lateral[3] = 2;
        }
        else if (m_top == 4 && m_right == 6) {
            a_lateral[0] = 4;
            a_lateral[1] = 1;
            a_lateral[2] = 3;
            a_lateral[3] = 6;
        }
        else if (m_top == 1 && m_right == 1) {
            for (int i = 0; i < 4; i++) {
                a_frontal[i] = 1;
                a_lateral[i] = 1;
            }
        }
    }
    /* *********************************************************************
    Function Name: DetermineReverseLateralMovement
    Purpose: To determine values from backwards lateral movement.
    Parameters:
    a_lateral: The array passed by reference holding values from lateral movement
    a_frontal: The array passed by reference holding values from frontal movement
    Algorithm:
    1) If top and right are both 1 don't do anything with the arrays.
    2) Otherwise make sure that opposite elements add up to 7.
    ********************************************************************* */
    public void DetermineReverseLateralMovement(int a_lateral[], int a_frontal[]) {
        if (m_top == 5 && m_right == 6) {
            a_lateral[0] = 5;
            a_lateral[1] = 6;
            a_lateral[2] = 2;
            a_lateral[3] = 1;
        }
        else if (m_top == 5 && m_right == 3) {
            a_lateral[0] = 5;
            a_lateral[1] = 3;
            a_lateral[2] = 2;
            a_lateral[3] = 4;
        }
        else if (m_top == 5 && m_right == 1) {
            a_lateral[0] = 5;
            a_lateral[1] = 1;
            a_lateral[2] = 2;
            a_lateral[3] = 6;
        }
        else if (m_top == 5 && m_right == 4) {
            a_lateral[0] = 5;
            a_lateral[1] = 4;
            a_lateral[2] = 2;
            a_lateral[3] = 3;
        }
        else if (m_top == 1 && m_right == 5) {
            a_lateral[0] = 1;
            a_lateral[1] = 5;
            a_lateral[2] = 6;
            a_lateral[3] = 2;
        }
        else if (m_top == 1 && m_right == 3) {
            a_lateral[0] = 1;
            a_lateral[1] = 3;
            a_lateral[2] = 6;
            a_lateral[3] = 4;
        }
        else if (m_top == 1 && m_right == 2) {
            a_lateral[0] = 1;
            a_lateral[1] = 2;
            a_lateral[2] = 6;
            a_lateral[3] = 5;
        }
        else if (m_top == 1 && m_right == 4) {
            a_lateral[0] = 1;
            a_lateral[1] = 3;
            a_lateral[2] = 6;
            a_lateral[3] = 4;
        }
        else if (m_top == 2 && m_right == 1) {
            a_lateral[0] = 2;
            a_lateral[1] = 1;
            a_lateral[2] = 5;
            a_lateral[3] = 6;
        }
        else if (m_top == 2 && m_right == 3) {
            a_lateral[0] = 2;
            a_lateral[1] = 3;
            a_lateral[2] = 5;
            a_lateral[3] = 4;
        }
        else if (m_top == 2 && m_right == 6) {
            a_lateral[0] = 2;
            a_lateral[1] = 6;
            a_lateral[2] = 5;
            a_lateral[3] = 1;
        }
        else if (m_top == 2 && m_right == 4) {
            a_lateral[0] = 2;
            a_lateral[1] = 4;
            a_lateral[2] = 5;
            a_lateral[3] = 3;
        }
        else if (m_top == 6 && m_right == 2) {
            a_lateral[0] = 6;
            a_lateral[1] = 2;
            a_lateral[2] = 1;
            a_lateral[3] = 5;
        }
        else if (m_top == 6 && m_right == 3) {
            a_lateral[0] = 6;
            a_lateral[1] = 3;
            a_lateral[2] = 1;
            a_lateral[3] = 4;
        }
        else if (m_top == 6 && m_right == 5) {
            a_lateral[0] = 6;
            a_lateral[1] = 5;
            a_lateral[2] = 1;
            a_lateral[3] = 2;
        }
        else if (m_top == 6 && m_right == 4) {
            a_lateral[0] = 6;
            a_lateral[1] = 4;
            a_lateral[2] = 1;
            a_lateral[3] = 3;
        }
        else if (m_top == 3 && m_right == 2) {
            a_lateral[0] = 3;
            a_lateral[1] = 2;
            a_lateral[2] = 4;
            a_lateral[3] = 5;
        }
        else if (m_top == 3 && m_right == 1) {
            a_lateral[0] = 3;
            a_lateral[1] = 1;
            a_lateral[2] = 4;
            a_lateral[3] = 6;
        }
        else if (m_top == 3 && m_right == 5) {
            a_lateral[0] = 3;
            a_lateral[1] = 5;
            a_lateral[2] = 4;
            a_lateral[3] = 2;
        }
        else if (m_top == 3 && m_right == 6) {
            a_lateral[0] = 3;
            a_lateral[1] = 6;
            a_lateral[2] = 4;
            a_lateral[3] = 1;
        }
        else if (m_top == 4 && m_right == 5) {
            a_lateral[0] = 4;
            a_lateral[1] = 5;
            a_lateral[2] = 3;
            a_lateral[3] = 2;
        }
        else if (m_top == 4 && m_right == 1) {
            a_lateral[0] = 4;
            a_lateral[1] = 1;
            a_lateral[2] = 3;
            a_lateral[3] = 6;
        }
        else if (m_top == 4 && m_right == 2) {
            a_lateral[0] = 4;
            a_lateral[1] = 2;
            a_lateral[2] = 3;
            a_lateral[3] = 5;
        }
        else if (m_top == 4 && m_right == 6) {
            a_lateral[0] = 4;
            a_lateral[1] = 6;
            a_lateral[2] = 3;
            a_lateral[3] = 1;
        }
        else if (m_top == 1 && m_right == 1) {
            for (int i = 0; i < 4; i++) {
                a_frontal[i] = 1;
                a_lateral[i] = 1;
            }
        }
    }
    /* *********************************************************************
Function Name: DetermineFrontalMovement
Purpose: To determine values from frontal movement for moving toward's the opponent's home row
Parameters:
a_lateral: The array passed by reference holding values from lateral movement
a_frontal: The array passed by reference holding values from frontal movement
Algorithm:
1) If top and right are both 1 don't do anything with the arrays.
2) Otherwise make sure that opposite elements add up to 7.
********************************************************************* */
    public void DetermineFrontalMovement(int a_frontal[], int a_lateral[]) {
        if (m_top == 5 && m_right == 6) {
            a_frontal[0] = 5;
            a_frontal[1] = 3;
            a_frontal[2] = 2;
            a_frontal[3] = 4;
        }
        else if (m_top == 5 && m_right == 3) {
            a_frontal[0] = 5;
            a_frontal[1] = 1;
            a_frontal[2] = 2;
            a_frontal[3] = 6;
        }
        else if (m_top == 5 && m_right == 1) {
            a_frontal[0] = 5;
            a_frontal[1] = 4;
            a_frontal[2] = 2;
            a_frontal[3] = 3;
        }
        else if (m_top == 5 && m_right == 4) {
            a_frontal[0] = 5;
            a_frontal[1] = 6;
            a_frontal[2] = 2;
            a_frontal[3] = 1;
        }
        else if (m_top == 1 && m_right == 5) {
            a_frontal[0] = 1;
            a_frontal[1] = 3;
            a_frontal[2] = 6;
            a_frontal[3] = 4;
        }
        else if (m_top == 1 && m_right == 3) {
            a_frontal[0] = 1;
            a_frontal[1] = 2;
            a_frontal[2] = 6;
            a_frontal[3] = 5;
        }
        else if (m_top == 1 && m_right == 2) {
            a_frontal[0] = 1;
            a_frontal[1] = 4;
            a_frontal[2] = 6;
            a_frontal[3] = 3;
        }
        else if (m_top == 1 && m_right == 4) {
            a_frontal[0] = 1;
            a_frontal[1] = 5;
            a_frontal[2] = 6;
            a_frontal[3] = 2;
        }
        else if (m_top == 2 && m_right == 1) {
            a_frontal[0] = 2;
            a_frontal[1] = 3;
            a_frontal[2] = 5;
            a_frontal[3] = 4;
        }
        else if (m_top == 2 && m_right == 3) {
            a_frontal[0] = 2;
            a_frontal[1] = 6;
            a_frontal[2] = 5;
            a_frontal[3] = 1;
        }
        else if (m_top == 2 && m_right == 6) {
            a_frontal[0] = 2;
            a_frontal[1] = 4;
            a_frontal[2] = 5;
            a_frontal[3] = 3;
        }
        else if (m_top == 2 && m_right == 4) {
            a_frontal[0] = 2;
            a_frontal[1] = 1;
            a_frontal[2] = 5;
            a_frontal[3] = 6;
        }
        else if (m_top == 6 && m_right == 2) {
            a_frontal[0] = 6;
            a_frontal[1] = 3;
            a_frontal[2] = 1;
            a_frontal[3] = 4;
        }
        else if (m_top == 6 && m_right == 3) {
            a_frontal[0] = 6;
            a_frontal[1] = 5;
            a_frontal[2] = 1;
            a_frontal[3] = 2;
        }
        else if (m_top == 6 && m_right == 5) {
            a_frontal[0] = 6;
            a_frontal[1] = 4;
            a_frontal[2] = 1;
            a_frontal[3] = 3;
        }
        else if (m_top == 6 && m_right == 4) {
            a_frontal[0] = 6;
            a_frontal[1] = 2;
            a_frontal[2] = 1;
            a_frontal[3] = 5;
        }
        else if (m_top == 3 && m_right == 2) {
            a_frontal[0] = 3;
            a_frontal[1] = 1;
            a_frontal[2] = 4;
            a_frontal[3] = 6;
        }
        else if (m_top == 3 && m_right == 1) {
            a_frontal[0] = 3;
            a_frontal[1] = 5;
            a_frontal[2] = 4;
            a_frontal[3] = 2;
        }
        else if (m_top == 3 && m_right == 5) {
            a_frontal[0] = 3;
            a_frontal[1] = 6;
            a_frontal[2] = 4;
            a_frontal[3] = 1;
        }
        else if (m_top == 3 && m_right == 6) {
            a_frontal[0] = 3;
            a_frontal[1] = 2;
            a_frontal[2] = 4;
            a_frontal[3] = 5;
        }
        else if (m_top == 4 && m_right == 5) {
            a_frontal[0] = 4;
            a_frontal[1] = 1;
            a_frontal[2] = 3;
            a_frontal[3] = 6;
        }
        else if (m_top == 4 && m_right == 1) {
            a_frontal[0] = 4;
            a_frontal[1] = 2;
            a_frontal[2] = 3;
            a_frontal[3] = 5;
        }
        else if (m_top == 4 && m_right == 2) {
            a_frontal[0] = 4;
            a_frontal[1] = 6;
            a_frontal[2] = 3;
            a_frontal[3] = 1;
        }
        else if (m_top == 4 && m_right == 6) {
            a_frontal[0] = 4;
            a_frontal[1] = 5;
            a_frontal[2] = 3;
            a_frontal[3] = 2;
        }
        else if (m_top == 1 && m_right == 1) {
            for (int i = 0; i < 4; i++) {
                a_frontal[i] = 1;
                a_lateral[i] = 1;
            }
        }
    }
    /* *********************************************************************
Function Name: DetermineReverseFrontalMovement
Purpose: To populate array with frontal movement from moving away from the opponent's home row.
Parameters:
a_lateral: The array passed by reference holding values from lateral movement
a_frontal: The array passed by reference holding values from frontal movement
Algorithm:
1) If top and right are both 1 don't do anything with the arrays.
2) Otherwise make sure that opposite elements add up to 7.
********************************************************************* */
    public void DetermineReverseFrontalMovement(int a_frontal[], int a_lateral[]) {
        if (m_top == 5 && m_right == 6) {
            a_frontal[0] = 5;
            a_frontal[1] = 4;
            a_frontal[2] = 2;
            a_frontal[3] = 3;
        }
        else if (m_top == 5 && m_right == 3) {
            a_frontal[0] = 5;
            a_frontal[1] = 6;
            a_frontal[2] = 2;
            a_frontal[3] = 1;
        }
        else if (m_top == 5 && m_right == 1) {
            a_frontal[0] = 5;
            a_frontal[1] = 3;
            a_frontal[2] = 2;
            a_frontal[3] = 4;
        }
        else if (m_top == 5 && m_right == 4) {
            a_frontal[0] = 5;
            a_frontal[1] = 1;
            a_frontal[2] = 2;
            a_frontal[3] = 6;
        }
        else if (m_top == 1 && m_right == 5) {
            a_frontal[0] = 1;
            a_frontal[1] = 4;
            a_frontal[2] = 6;
            a_frontal[3] = 3;
        }
        else if (m_top == 1 && m_right == 3) {
            a_frontal[0] = 1;
            a_frontal[1] = 5;
            a_frontal[2] = 6;
            a_frontal[3] = 2;
        }
        else if (m_top == 1 && m_right == 2) {
            a_frontal[0] = 1;
            a_frontal[1] = 3;
            a_frontal[2] = 6;
            a_frontal[3] = 4;
        }
        else if (m_top == 1 && m_right == 4) {
            a_frontal[0] = 1;
            a_frontal[1] = 2;
            a_frontal[2] = 6;
            a_frontal[3] = 5;
        }
        else if (m_top == 2 && m_right == 1) {
            a_frontal[0] = 2;
            a_frontal[1] = 4;
            a_frontal[2] = 5;
            a_frontal[3] = 3;
        }
        else if (m_top == 2 && m_right == 3) {
            a_frontal[0] = 2;
            a_frontal[1] = 1;
            a_frontal[2] = 5;
            a_frontal[3] = 6;
        }
        else if (m_top == 2 && m_right == 6) {
            a_frontal[0] = 2;
            a_frontal[1] = 3;
            a_frontal[2] = 5;
            a_frontal[3] = 4;
        }
        else if (m_top == 2 && m_right == 4) {
            a_frontal[0] = 2;
            a_frontal[1] = 6;
            a_frontal[2] = 5;
            a_frontal[3] = 1;
        }
        else if (m_top == 6 && m_right == 2) {
            a_frontal[0] = 6;
            a_frontal[1] = 4;
            a_frontal[2] = 1;
            a_frontal[3] = 3;
        }
        else if (m_top == 6 && m_right == 3) {
            a_frontal[0] = 6;
            a_frontal[1] = 2;
            a_frontal[2] = 1;
            a_frontal[3] = 5;
        }
        else if (m_top == 6 && m_right == 5) {
            a_frontal[0] = 6;
            a_frontal[1] = 3;
            a_frontal[2] = 1;
            a_frontal[3] = 4;
        }
        else if (m_top == 6 && m_right == 4) {
            a_frontal[0] = 6;
            a_frontal[1] = 5;
            a_frontal[2] = 1;
            a_frontal[3] = 2;
        }
        else if (m_top == 3 && m_right == 2) {
            a_frontal[0] = 3;
            a_frontal[1] = 6;
            a_frontal[2] = 4;
            a_frontal[3] = 1;
        }
        else if (m_top == 3 && m_right == 1) {
            a_frontal[0] = 3;
            a_frontal[1] = 2;
            a_frontal[2] = 4;
            a_frontal[3] = 5;
        }
        else if (m_top == 3 && m_right == 5) {
            a_frontal[0] = 3;
            a_frontal[1] = 1;
            a_frontal[2] = 4;
            a_frontal[3] = 6;
        }
        else if (m_top == 3 && m_right == 6) {
            a_frontal[0] = 3;
            a_frontal[1] = 5;
            a_frontal[2] = 4;
            a_frontal[3] = 2;
        }
        else if (m_top == 4 && m_right == 5) {
            a_frontal[0] = 4;
            a_frontal[1] = 6;
            a_frontal[2] = 3;
            a_frontal[3] = 1;
        }
        else if (m_top == 4 && m_right == 1) {
            a_frontal[0] = 4;
            a_frontal[1] = 5;
            a_frontal[2] = 3;
            a_frontal[3] = 2;
        }
        else if (m_top == 4 && m_right == 2) {
            a_frontal[0] = 4;
            a_frontal[1] = 1;
            a_frontal[2] = 3;
            a_frontal[3] = 6;
        }
        else if (m_top == 4 && m_right == 6) {
            a_frontal[0] = 4;
            a_frontal[1] = 2;
            a_frontal[2] = 3;
            a_frontal[3] = 5;
        }
        else if (m_top == 1 && m_right == 1) {
            for (int i = 0; i < 4; i++) {
                a_frontal[i] = 1;
                a_lateral[i] = 1;
            }
        }
    }
    /* *********************************************************************
Function Name: Rotate
Purpose: To determine values of new top and right faces after move
Returns: A new string with the new top and right faces after the move
Parameters:
a_board, the current board passed by reference. It contains the computer and human's dice
a_die, a string passed by reference holding the final die. Not used
a_front, a string passed by reference containing the first direction
a_lat, a string passed by reference containing the second direction
a_frontDist, an integer containing frontal distance. Not used
a_latDist, an integer containing lateral distance. Not used
a_NextRow, an integer containing the starting row
a_NextCol, an integer containing the starting column
a_DestRow, an integer referencing the ending row.
a_DestCol, an integer referencing the ending column.
Algorithm:
1) If frontal movement is first, do frontal calculation first, otherwise, do lateral calculation first.
2) If start is less than end, do forward frontal and lateral movement, otherwise do backward frontal and lateral movement.
3) For each empty square in the path, calculate the new top and right face of the die at each roll.
4) Return the die at the destination coordinate.
********************************************************************* */
    public String Rotate(ArrayList<ArrayList<String>> a_board, String a_die, String a_front, int a_frontDist, String a_lat, int a_latDist, int a_NextRow, int a_NextCol, int a_DestRow, int a_DestCol) {
        String result = "H";
        String die = a_board.get(9 - a_NextRow).get(a_NextCol);
        System.out.println(die);
        SetTop((die.charAt(1) - '0') % 48);
        SetRight((die.charAt(2) - '0') % 48);
        // Set initial top and right faces
        int [] frontal = new int[4]; // frontal movement
        int [] lateral = new int [4]; // lateral movement
        // Populate the frontal and lateral arrays
        if (a_front.equals("frontally") && a_lat.equals("laterally")) {
            if (a_NextRow <= a_DestRow) { // If frontal was the first direction, check start and end. If the start was less than end, determine the forward frontal movement, otherwise determine the backward movement.
                DetermineFrontalMovement(frontal, lateral);
            }
            else {
                DetermineReverseFrontalMovement(frontal, lateral);
            }
            // For the frontal movement, the right does not change.
            for (int i = 1; i <= Math.abs(a_DestRow - a_NextRow); i++) {
                SetTop(frontal[i % 4]);
            }
            if (a_NextCol <= a_DestCol) { // for lateral movement, check the start and end columns
                DetermineLateralMovement(lateral, frontal); // forward lateral movement
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int top = m_top;
                    SetRight(top);
                    SetTop(lateral[i % 4]);
                }
            }
            else {
                DetermineReverseLateralMovement(lateral, frontal); // reverse lateral movement
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int right = m_right;
                    SetTop(right);
                    SetRight(lateral[((i + 1) % 4)]);
                }
            }
        }
        else { // The rest of the algorithm is very similar, so we won't comment it.
            if (a_NextCol <= a_DestCol) {
                DetermineLateralMovement(lateral, frontal);
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int top = m_top;
                    SetRight(top);
                    SetTop(lateral[i % 4]);
                }
            }
            else {
                DetermineReverseLateralMovement(lateral, frontal);
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int right = m_right;
                    SetTop(right);
                    SetRight(lateral[((i + 1)% 4)]);
                }
            }
            if (a_NextRow <= a_DestRow) {
                DetermineFrontalMovement(frontal, lateral);
            }
            else {
                DetermineReverseFrontalMovement(frontal, lateral);
            }
            for (int i = 1; i <= Math.abs(a_DestRow - a_NextRow); i++) {
                SetTop(frontal[i % 4]);
            }
        }
        result += ToString();
        return result;
    }
    /* *********************************************************************
Function Name: RotateComputerDie
Purpose: To determine values of new top and right faces of a computer die after move
Returns: A new string with the new top and right faces after the move
Parameters:
a_board, the current board passed by reference. It contains the computer and human's dice
a_die, a string passed by reference holding the final die. Not used
a_front, a string passed by reference containing the first direction
a_lat, a string passed by reference containing the second direction
a_frontDist, an integer containing frontal distance. Not used
a_latDist, an integer containing lateral distance. Not used
a_NextRow, an integer containing the starting row
a_NextCol, an integer containing the starting column
a_DestRow, an integer referencing the ending row.
a_DestCol, an integer referencing the ending column.
Algorithm:
1) If frontal movement is first, do frontal calculation first, otherwise, do lateral calculation first.
2) If start is less than end, do backward frontal and lateral movement, otherwise do forward frontal and lateral movement.
3) For each empty square in the path, calculate the new top and right face of the die at each roll.
4) Return the die at the destination coordinate.
********************************************************************* */
    public String RotateComputerDie(ArrayList<ArrayList<String>> a_board, String a_die, String a_front, int a_frontDist, String a_lat, int a_latDist, int a_NextRow, int a_NextCol, int a_DestRow, int a_DestCol) {
        // This is very similar to the human's rotate function
        String result = "C";
        String die = a_board.get(9 - a_NextRow).get(a_NextCol);
        SetTop((die.charAt(1) - '0') % 48);
        SetRight((die.charAt(2) - '0') % 48);
        int [] frontal = new int[4];
        int [] lateral = new int[4];
        if (a_front.equals("frontally") && a_lat.equals("laterally")) {
            if (a_NextRow >= a_DestRow) {
                DetermineFrontalMovement(frontal, lateral);
            }
            else {
                DetermineReverseFrontalMovement(frontal, lateral);
            }
            for (int i = 1; i <= Math.abs(a_DestRow - a_NextRow); i++) {
                SetTop(frontal[i % 4]);
            }
            if (a_NextCol >= a_DestCol) {
                DetermineLateralMovement(lateral, frontal);
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int top = m_top;
                    SetRight(top);
                    SetTop(lateral[i % 4]);
                }
            }
            else {
                DetermineReverseLateralMovement(lateral, frontal);
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int right = m_right;
                    SetTop(right);
                    SetRight(lateral[((i + 1) % 4)]);
                }
            }
        }
        else {
            if (a_NextCol >= a_DestCol) {
                DetermineLateralMovement(lateral, frontal);
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int top = m_top;
                    SetRight(top);
                    SetTop(lateral[i % 4]);
                }
            }
            else {
                DetermineReverseLateralMovement(lateral, frontal);
                for (int i = 1; i <= Math.abs(a_DestCol - a_NextCol); i++) {
                    int right = m_right;
                    SetTop(right);
                    SetRight(lateral[((i + 1) % 4)]);
                }
            }
            if (a_NextRow >= a_DestRow) {
                DetermineFrontalMovement(frontal, lateral);
            }
            else {
                DetermineReverseFrontalMovement(frontal, lateral);
            }
            for (int i = 1; i <= Math.abs(a_DestRow - a_NextRow); i++) {
                SetTop(frontal[i % 4]);
            }
        }
        result += ToString();
        return result;
    }
    private int m_top;
    private int m_right;
}