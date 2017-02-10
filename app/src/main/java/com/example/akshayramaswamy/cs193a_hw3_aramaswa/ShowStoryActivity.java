package com.example.akshayramaswamy.cs193a_hw3_aramaswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowStoryActivity extends AppCompatActivity {

    /* Method: onCreate
     * This method displays the story with all placeholder text filled in,
     * passed in from the previous activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_story);
        Intent fullIntent = getIntent();
        String fullStory = fullIntent.getStringExtra("full_story");
        TextView storyText = (TextView) findViewById(R.id.full_story);
        storyText.setText(fullStory);

    }

    /* Method: mainMenu()
     * This method allows the user to create a new mad lib
     */
    public void mainMenu(View view) {
        Intent returnMenu = new Intent(this, MainActivity.class);
        startActivity(returnMenu);
    }
}
