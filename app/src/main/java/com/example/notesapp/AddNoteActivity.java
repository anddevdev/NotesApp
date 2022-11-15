package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    EditText edNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.edNote = findViewById(R.id.edNote);
    }

    public void onBtnSaveAndCloseClick(View view){

        String noteToAdd = this.edNote.getText().toString();
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE,this.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPref.edit();
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);
        Set<String> newSet = new HashSet<>();
        if(savedSet != null){
            newSet.addAll(savedSet);
        }

        newSet.add(noteToAdd);
        editor.putString(Constants.NOTE_KEY, noteToAdd);
        editor.putStringSet(Constants.NOTES_ARRAY_KEY, newSet);
        editor.apply();

        finish();

    }

}


