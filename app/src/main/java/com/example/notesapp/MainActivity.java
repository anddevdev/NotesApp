package com.example.notesapp;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> listNotes = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ListView lvViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvViewNotes = findViewById(R.id.lvViewNotes);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.listNotes);
        this.lvViewNotes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_options_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
        String lastSavedNote = sharedPref.getString(Constants.NOTE_KEY, "NA");
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);

        if (savedSet != null) {

            this.listNotes.clear();
            this.listNotes.addAll(savedSet);
            this.adapter.notifyDataSetChanged();

        }

        Snackbar.make(lvViewNotes, String.format("%s: %s", "Last saved note", lastSavedNote), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_note:
                Intent i = new Intent(this, AddNoteActivity.class);
                startActivity(i);
                return true;

            case R.id.remove_note:
                lvViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                        final int itemToDelete = i;

                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are you sure?")
                                .setMessage("Do you want to delete this note?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        listNotes.remove(itemToDelete);
                                        adapter.notifyDataSetChanged();
                                        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE,MODE_PRIVATE);
                                        ArrayList<String> set = new ArrayList(MainActivity.listNotes);
                                        sharedPref.edit().putStringSet("notes", null).apply();
                                    }
                                }).setNegativeButton("No", null).show();
                        return true;





        }

});
        }
        return true;  }
}
