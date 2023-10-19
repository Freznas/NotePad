package com.example.notepad;

import android.os.Bundle;

import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NotesActivity extends AppCompatActivity
{
    ListView noteList;
    NotesAdapter adapter;
    DataManager dataManager;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        dataManager = new DataManager(this);
        List<String> notes = dataManager.getNotes();
        adapter = new NotesAdapter(this,notes,dataManager);
        adapter.notifyDataSetChanged();
        noteList.setAdapter(adapter);
    }

    public String[] split(String s)
    {
        return null;
    }
}