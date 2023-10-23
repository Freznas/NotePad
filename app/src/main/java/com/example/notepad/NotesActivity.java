package com.example.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NotesActivity extends AppCompatActivity
{

    private EditText titleEditText;
    private EditText contentEditText;
    private NotesAdapter adapter;
    Button saveButton;
    Button deleteButton;
    Button returnButton;
    private DataManager dataManager;
    private int editNotePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        dataManager = new DataManager(this);

        adapter = new NotesAdapter(this, dataManager.getNotes(), dataManager);

        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);
        saveButton = findViewById(R.id.btn_save);
        returnButton = findViewById(R.id.btn_return);
        deleteButton = findViewById(R.id.btn_delete);
        List<String> notes = dataManager.getNotes();
        // Hämta anteckningar från SharedPreferences
        String savedTitle = dataManager.getSavedTitle();
        String savedContent = dataManager.getSavedContent();

        // Fyll inmatningsfälten om det finns sparade anteckningar
        titleEditText.setText(savedTitle);
        contentEditText.setText(savedContent);

        // Hämta positionen för anteckningen att redigera
        editNotePosition = getIntent().getIntExtra("edit_note_position", -1);
        if (editNotePosition != -1)
        {
            // Redigeringsläge
            String noteToEdit = dataManager.getNoteByPosition(editNotePosition);
            if (noteToEdit != null)
            {
                // Fyll in titel och innehåll i EditText-fälten för redigering
                String[] noteParts = noteToEdit.split("\\|");
                if (noteParts.length == 2)
                {
                    titleEditText.setText(noteParts[0]);
                    contentEditText.setText(noteParts[1]);
                }
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Hämta innehållet från inmatningsfälten
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();

                if (editNotePosition != -1)
                {
                    // Du redigerar en befintlig anteckning
                    boolean isUpdated = dataManager.updateNoteByPosition(editNotePosition, title, content);
                    if (isUpdated)
                    {
                        // Uppdateringen lyckades, återvänd till huvudsidan
                        Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else
                {
                    // Du skapar en ny anteckning
                    // Här lägger du till en position för den nya anteckningen (t.ex. 0)
                    int newPosition = dataManager.getNotes().size();
                    dataManager.saveNote(newPosition, title, content);  //Troligtvis här problemet ligger, tilldelar endast "Key" 0 om och om igen.

                    // Återgå till huvudskärmen
                    Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // När användaren går tillbaka, återvänd till huvudskärmen
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });

        Button deleteButton = findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (editNotePosition != -1)
                {
                    adapter.deleteNoteAtPosition(editNotePosition);
                    Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}