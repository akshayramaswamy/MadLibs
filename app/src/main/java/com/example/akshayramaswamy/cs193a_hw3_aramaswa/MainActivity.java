/*
 * Akshay Ramaswamy <aramaswa@stanford.edu>
 * CS 193A, Winter 2017
 * Homework Assignment 3
 * MAD LIBS  - This app allows the user to play the classic game of Mad Libs.
 * They are prompted to enter in a series of words after choosing a story, and
 * are then able to see their awesome story put together at the end.
 */
package com.example.akshayramaswamy.cs193a_hw3_aramaswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {
    Spinner spinner;

    /* Method: onCreate
     * This method creates a spinner with each story name. Spinner code was taken from Android developer website:
     * https://developer.android.com/guide/topics/ui/controls/spinner.html
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.madlib_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    /* Method: enterStory()
     * This method allow the user start the Mad Lib and go to a new activity
     * The name of the story they want is passed to the new activity
     */
    public void enterStory(View view) {
        Intent storyIntent = new Intent(this, FillInWordsActivity.class);
        String storyName = spinner.getSelectedItem().toString();
        storyIntent.putExtra("story", storyName);
        startActivity(storyIntent);
    }
}
