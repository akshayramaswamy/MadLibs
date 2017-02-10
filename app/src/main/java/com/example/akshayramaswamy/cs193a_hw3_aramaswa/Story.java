package com.example.akshayramaswamy.cs193a_hw3_aramaswa;

/*
 * CS 193A, Marty Stepp
 *
 * *** NOTE: YOU MAY NEED TO MODIFY THIS FILE BY PUTTING IT INTO A 'package'. ***
 *
 * This class represents a Mad Libs story that comes from a text file.
 * You can construct it and pass an input stream or Scanner to read the story text.
 * After constructing it, you can ask it for each placeholder by calling
 * getPlaceholder(i), then filling in that placeholder by calling setPlaceholder(i, string).
 *
 * You can get the story's text by calling its toString method.
 * A Story is Serializable, so it can be packed into an Intent as "extra" data.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import java.util.*;

public class Story implements Serializable {
    private String text;                      // text of the story
    private List<Placeholder> placeholders;   // list of placeholders to fill in
    private boolean htmlMode;                 // set true to surround placeholders with <b></b> tags

    /** constructs a new empty Story */
    public Story() {
        text = "";
        placeholders = new ArrayList<>();
        htmlMode = false;
    }

    /** constructs a new Story reading its text from the given input stream */
    public Story(InputStream stream) {
        this();
        read(stream);
    }

    /** constructs a new Story reading its text from the given Scanner */
    public Story(Scanner input) {
        this();
        read(input);
    }

    /** Returns "an" if the the placeholder at the given index begins with a vowel (AEIOU), else "a". */
    public String getAvsAn(int index) {
        return getAvsAn(placeholders.get(index).text);
    }

    /** Returns "an" if the given string begins with a vowel (AEIOU), else "a". */
    public String getAvsAn(String s) {
        return startsWithVowel(s) ? "an" : "a";
    }

    /** returns the placeholder text at the given index */
    public String getPlaceholder(int index) {
        return placeholders.get(index).text;
    }

    /** returns total number of placeholders in the story */
    public int getPlaceholderCount() {
        return placeholders.size();
    }

    /** reads initial story text from the given input stream */
    public void read(InputStream stream) {
        read(new Scanner(stream));
    }

    /** reads initial story text from the given Reader */
    public void read(Reader input) {
        read(new Scanner(input));
    }

    /** reads initial story text from the given Scanner */
    public void read(Scanner input) {
        // read data from input source
        StringBuilder sb = new StringBuilder();
        while (input.hasNextLine()) {
            sb.append(input.nextLine());
            sb.append('\n');
        }
        this.text = sb.toString();

        // find placeholders in input
        int lt = text.indexOf('<');
        int gt = text.indexOf('>');
        while (lt >= 0 && gt > lt) {
            String phText = text.substring(lt + 1, gt);
            Placeholder ph = new Placeholder(phText, lt);
            placeholders.add(ph);

            lt = text.indexOf('<', gt + 1);
            if (lt < 0) break;
            gt = text.indexOf('>', lt + 1);
        }
    }

    /**
     * sets whether this story will be outputted in HTML mode with B tags around placeholders
     * (default false)
     */
    public void setHtmlMode(boolean value) {
        htmlMode = value;
    }

    /** sets the placeholder at the given index to be replaced by the given text */
    public void setPlaceholder(int index, String replacementText) {
        placeholders.get(index).replacement = replacementText;
    }

    /** Returns true if the placeholder at the given index begins with a vowel. */
    public boolean startsWithVowel(int index) {
        return startsWithVowel(placeholders.get(index).text);
    }

    /** Returns true if the given string begins with a vowel (AEIOU). */
    public boolean startsWithVowel(String s) {
        return s != null && !s.isEmpty() &&
                "aeiou".indexOf(s.trim().toLowerCase().charAt(0)) >= 0;
    }

    /** Returns story text, including any placeholders filled in. */
    public String toString() {
        String storyText = text;

        // fill in the placeholders
        // (go backwards so that the indexes don't shift under our feet)
        for (int i = placeholders.size() - 1; i >= 0; i--) {
            Placeholder ph = placeholders.get(i);
            String repl = ph.replacement;
            if (htmlMode) {
                repl = "<b>" + repl + "</b>";
            }

            String before = storyText.substring(0, ph.index);
            String after = storyText.substring(ph.index + ph.text.length() + 2);   // +2 because of <>
            storyText = before + repl + after;
        }

        return storyText;
    }

    /**
     * A small class to represent a placeholder such as "<proper noun>" and an index in the
     * original text at which it occurs.
     */
    private static class Placeholder implements Serializable {
        private String text;
        private String replacement;
        private int index;

        /**
         * Creates a new placeholder storing the given text at the given index
         * in the original story.
         */
        public Placeholder(String text, int index) {
            this.text = text;
            this.replacement = "";
            this.index = index;
        }

        /** Returns a text representation of the placeholder for debugging. */
        public String toString() {
            return "{text=" + text + ", replacement=" + replacement + ", index=" + index + "}";
        }
    }
}
