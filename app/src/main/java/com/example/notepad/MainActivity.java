package com.example.notepad;
//Har använt mig av MVC arkitekturen

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//onresume?
public class MainActivity extends AppCompatActivity
{

    Button addnoteButton;


    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addnoteButton = findViewById(R.id.btn_register);

        dataManager = new DataManager(this);

        addnoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

    }
}
