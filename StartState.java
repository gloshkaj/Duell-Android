package com.androidduell.garen.androidduell;
import com.androidduell.garen.androidduell.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.content.Intent;
/************************************************************
 * Project Created by:  Garen Loshkajian
 * Project Description: Java/Android Duell Project (#2)
 * Class: CMPS 366, Organization of Programming Languages
 * Project Due on: 12/02/16 (Extended Submission)
 * Project Submitted on: 11/29/2016
 ************************************************************/
public class StartState extends AppCompatActivity {
    public EditText m_editText;
    public String m_fileName;
    @Override
        /* *********************************************************************
Function Name: onCreate
Purpose: To start the game.
Parameters:
a_savedInstanceState, the instance of the activity.
********************************************************************* */
    protected void onCreate(Bundle a_savedInstanceState) {
        super.onCreate(a_savedInstanceState);
        setContentView(R.layout.activity_start_state);
    }
    /* *********************************************************************
Function Name: StartNewGame
Purpose: To begin a new series.
Parameters:
a_view, the button object passed by value. Not used.
********************************************************************* */
    public void StartNewGame(View a_view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /* *********************************************************************
Function Name: LoadSavedGame
Purpose: To prompt the user for the file to restore progress from.
Parameters:
a_view, the button object passed by value. Not used.
********************************************************************* */
    public void LoadSavedGame(View a_view) {
        Button first = (Button)findViewById(R.id.load);
        Button second = (Button)findViewById(R.id.start);
        Button third = (Button)findViewById(R.id.deserialize);
        first.setEnabled(false);
        second.setEnabled(false);
        TextView txtView = (TextView)findViewById(R.id.savePrompt1);
        // Prompt the user to enter text into a text box to determine the file to load from.
        txtView.setText("Enter the name of the file to restore your progress from.");
        m_editText = (EditText)findViewById(R.id.reload);
        m_editText.setVisibility(View.VISIBLE);
        third.setVisibility(View.VISIBLE);
    }
    /* *********************************************************************
Function Name: DeSerialize
Purpose: To get the filename from the text box.
Parameters:
a_view, the button object passed by value. Not used.
********************************************************************* */
    public void DeSerialize(View a_view) {
        m_fileName = m_editText.getText().toString();
        if (m_fileName.isEmpty()) {
            return;
        }
        LoadFromFile(m_fileName);
    }
    /* *********************************************************************
Function Name: LoadFromFile
Purpose: To load the saved game.
Parameters:
a_fileName, the string referencing the file path to restore progress from.
Local variables:
intent, the intent to send the saved game to the main activity.
dir, the directory of files to find the saved game.
file, the file object in "dir" to restore the saved state.
lines, an arraylist of lines containing the lines from the file to restore.
fileReader, the reader to read the lines
fileStream, the buffered reader to get each line from the file.
Algorithm:
1) Read each line from the file and insert into arraylist.
2) Send the arraylist to the new activity.
3) Check that the file is valid.
********************************************************************* */
    public void LoadFromFile(String a_fileName) {
        Intent intent = new Intent(this, MainActivity.class);
        // Access directory.
        File dir = getExternalFilesDir(null);
        System.out.println(dir);
        File file = new File(dir, a_fileName);
        System.out.println(file);
        ArrayList<String> lines = new ArrayList<String>();
        // Read each line from the file.
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader fileStream = new BufferedReader(fileReader);
            for (String line; (line = fileStream.readLine()) != null;) {
                lines.add(line);
            }
            // Send lines to new activity.
            intent.putStringArrayListExtra("lines", lines);
            fileStream.close();
            fileReader.close();
            startActivity(intent);
        } catch (IOException a_execp) {
            System.out.println("Cannot find file!");
            a_execp.printStackTrace();
        }
    }
}