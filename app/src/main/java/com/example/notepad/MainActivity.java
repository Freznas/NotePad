package com.example.notepad;
//Har använt mig av MVC arkitekturen

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

//onresume?
public class MainActivity extends AppCompatActivity
{
    Button registerButton;
    ListView noteList; // Lägg till ListView
    NotesAdapter adapter;
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButton = findViewById(R.id.btn_register);
        // Initialisera noteList
        noteList = findViewById(R.id.lv_notesMain);
        dataManager = new DataManager(this);
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
        // Hämta anteckningar från DataManager
        List<String> notes = dataManager.getNotes();
        // Skapa och sätt adaptern
        adapter = new NotesAdapter(this, notes, dataManager);
        noteList.setAdapter(adapter);
    }

}
