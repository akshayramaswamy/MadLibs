package com.example.akshayramaswamy.cs193a_hw3_aramaswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Scanner;
import stanford.androidlib.SimpleActivity;

public class FillInWordsActivity extends SimpleActivity {

    /* Global vars set so that we can use them in onClick method,
    * intialized in onCreate
    */
    Story story;
    int placeHolderIndex;
    TextView wordsLeft;
    TextView wordType;
    EditText inputtedWord;
    int numberWords;

    /* Method: onCreate
     * This method creates the initial story object using the story name entered by the user
     * in the previous activity. It uses this to set the numbers of words left to fill
     * and the type of word the user has to enter in to begin with
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_words);
        Intent storyIntent = getIntent();
        String storyName = storyIntent.getStringExtra("story");
        Scanner scan = setScanner(storyName);
        story = new Story(scan);
        placeHolderIndex = 0;
        numberWords = story.getPlaceholderCount();
        wordsLeft = (TextView) findViewById(R.id.num_words);
        wordType = (TextView) findViewById(R.id.word_type);
        wordsLeft.setText(numberWords + " word(s) left");
        wordType.setText("Please enter a(n) " + story.getPlaceholder(placeHolderIndex));
    }

    /* Method: setScanner()
     * This method sets the scanner based on the word the user entered in for the story
     * they want their mad lib to be.
     */
    private Scanner setScanner(String storyName){
        Scanner scan = null;
        switch (storyName){
            case "Tarzan": scan = new Scanner(
                    getResources().openRawResource(R.raw.tarzan));
                break;
            case "Marty": scan = new Scanner(
                    getResources().openRawResource(R.raw.marty));
                break;
            case "Dance": scan = new Scanner(
                    getResources().openRawResource(R.raw.dance));
                break;
            case "Clothing": scan = new Scanner(
                    getResources().openRawResource(R.raw.clothing));
                break;
            case "University": scan = new Scanner(
                    getResources().openRawResource(R.raw.university));
                break;
            case "Wannabe": scan = new Scanner(
                    getResources().openRawResource(R.raw.wannabe));
                break;
        }
        return scan;
    }

    /* Method: enterWord()
     * This method is called when the user clicks the OK button to enter their word in.
     * The text on the screen is updated, and the placeholder text in the story object is set.
     * This continues until the number of words left equals zero, in which case we go to the
     * show story activity
     */
    public void enterWord(View view) {
        inputtedWord = (EditText) findViewById(R.id.inputted_word);
        story.setPlaceholder(placeHolderIndex, inputtedWord.getText().toString());
        placeHolderIndex++;
        numberWords--;
        if(numberWords <=0){
            Intent showStoryIntent = new Intent(FillInWordsActivity.this, ShowStoryActivity.class);
            showStoryIntent.putExtra("full_story", story.toString());
            startActivity(showStoryIntent);
        return;
        }
        wordsLeft.setText(numberWords + " word(s) left");
        wordType.setText("Please enter a(n) " + story.getPlaceholder(placeHolderIndex));
        inputtedWord.setText("");
        inputtedWord.setHint("Enter word");

    }
}
