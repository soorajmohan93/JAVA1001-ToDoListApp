package com.soorajmohan.test.java1001_todolistapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //  String to hold input text from EditText
    private  String toDoTextEntry;
    //  Integer variables to hold visibility attribute used by different views
    private int linearLayoutVisibility = View.GONE;
    private int addEntriesVisibility = View.VISIBLE;
    private int deleteAllVisibility = View.GONE;
    //  Counter for each entry (index) in the array list
    private int counter = 0;
    //  array list of strings used in the list view
    private final ArrayList<String> listOfToDos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Button declaration
        Button addToDoList = findViewById(R.id.addToDoList);
        Button addToDoEntry = findViewById(R.id.addText);
        Button entriesDone = findViewById(R.id.entriesDone);
        Button deleteToDo = findViewById(R.id.deleteToDo);
//      Listeners on the buttons
        addToDoList.setOnClickListener(this);
        addToDoEntry.setOnClickListener(this);
        entriesDone.setOnClickListener(this);
        deleteToDo.setOnClickListener(this);


//      setDisplay method to update the views in the initial screen
        setDisplay();
    }

    @Override
    public void onClick(View view)
    {
//      EditText Entry
        EditText toDoEntry = findViewById(R.id.toDoText);
//      toDoTextEntry value assigned with the EditText value
        toDoTextEntry = String.valueOf(toDoEntry.getText());

//      Switch to add functionality to each buttons and setting visibility attributes to different views
        switch (view.getId())
        {

            case R.id.addToDoList:
//          Add to Do List button enables user to display the EditText and button to add into the ListView
//          This button disappears once its clicked.
                linearLayoutVisibility = View.VISIBLE;
                addEntriesVisibility = View.GONE;
                break;

            case R.id.addText:
//          This is the + button view next to the EditText. It calls addToDoListView() to add into ArrayList used by the ListView
//          This will be done only if there is a text entered in the EditText
                if (!toDoTextEntry.isEmpty())
                    addToDoListView();
                break;

            case R.id.entriesDone:
//              Once user enters all the entries in to-do, the EditText and buttons disappear and delete all button appears
//              The checking property of the ListView will be activated when user presses this button
//              The background colour changes to a stick note colour to resemble a to-do note
                linearLayoutVisibility = View.GONE;
                deleteAllVisibility = View.VISIBLE;
                checkToDo();
                findViewById(R.id.constraintLayout).setBackgroundResource(R.color.stick_note);
                break;
            case R.id.deleteToDo:
//              The delete all button removes all entries and resets to the initial view
                addEntriesVisibility = View.VISIBLE;
                deleteAllVisibility = View.GONE;
                deleteAllEntries();
                findViewById(R.id.constraintLayout).setBackgroundResource(R.color.white);
                break;
        }

//      setDisplay to update screen
        setDisplay();
    }

    //  setDisplay will update all the views
    public void setDisplay()
    {
//      EditText is cleared after each entry
        EditText toDoEntry = findViewById(R.id.toDoText);
        toDoEntry.setText(null);
//      The visibility attribute property is set here
        findViewById(R.id.addToDoList).setVisibility(addEntriesVisibility);
        findViewById(R.id.deleteToDo).setVisibility(deleteAllVisibility);
        LinearLayout textLinearLayout = findViewById(R.id.textLinearLayout);
        textLinearLayout.setVisibility(linearLayoutVisibility);

    }

    public void addToDoListView()
    {
//      Add String toDoTextEntry into the ArrayList
        listOfToDos.add(counter, counter+1 + ". " + toDoTextEntry);

//      ArrayAdapter which uses a simple_list_item_checked and the listOfToDos ArrayList and add into the ListView
        ArrayAdapter<String> toDoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, listOfToDos);
        ListView toDoList = findViewById(R.id.toDoList);
        toDoList.setAdapter(toDoArrayAdapter);

//      The checking out of the To-Do List will be activated only after ENTRIES DONE button is clicked. So till then item.setChecked() is always false
        toDoList.setOnItemClickListener((parent, view, position, id) -> {
            CheckedTextView item = (CheckedTextView) view;
            item.setChecked(false);
        });
//      Counter increments by one for each ListView Entry
        counter++;
    }

    public void deleteAllEntries()
    {
        //Clears the array and the List view, sets the counter 0
        listOfToDos.clear();
        ListView toDoList = findViewById(R.id.toDoList);
        toDoList.setAdapter(null);
        counter = 0;
    }

    public void checkToDo()
    {
//      The Checking toggle attribute of the ListView is activated once the user clicks on Entries Done button
        ListView toDoList = findViewById(R.id.toDoList);

        toDoList.setOnItemClickListener((parent, view, position, id) -> {
            CheckedTextView item = (CheckedTextView) view;
            item.toggle();
        });
    }
}