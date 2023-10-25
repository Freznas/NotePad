package com.example.notepad;
//Byggd utifrån MVC arkitekturen
//här presenterar vi listan av sparade anteckningar som man kan redigera/ta bort,samt knapp för lägga till nya anteckningar
//Controller

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Button registerButton;
    ListView noteList;
    NotesAdapter adapter;
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButton = findViewById(R.id.btn_register);
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

        List<String> notes = dataManager.getNotes();
        //hämtar lista med anteckningar från getNotes metoden i min dataManager

        adapter = new NotesAdapter(this, notes, dataManager);
        noteList.setAdapter(adapter);
        //fyller listview med det som hämtas via rad 41
    }

}