package com.example.notepad;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager
{
    SharedPreferences sharedPreferences;
    String SHARED_PREFERENCES_NAME = "My Notes";

    public DataManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public List<String> getNotes()
    {
        List<String> notes = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) // förklara?
        {
            notes.add(entry.getValue().toString());
        }
        return notes;
    }

    public void saveNote(int position, String title, String content)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String note = title + "|" + content; //här har vi strecket!
        editor.putString(String.valueOf(position), note);
        editor.apply();
    }

    public boolean deleteNoteByTitle(String title)
    { // kika på problem med att element inte tas bort?

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            String entryValue = entry.getValue().toString();
            String[] noteParts = entryValue.split("\\|");

            if (noteParts.length > 0 && noteParts[0].equals(title))
            {
                editor.remove(entry.getKey());
                boolean isRemoved = editor.commit();
                return isRemoved;
            }
        }
        return false;
    }

    public String getNoteByPosition(int position)
    {
        List<String> notes = getNotes();
        if (position >= 0 && position < notes.size())
        {
            return notes.get(position);
        }
        return null;
    }

    public boolean updateNoteByPosition(int position, String newTitle, String newContent)
    {
        List<String> notes = getNotes();
        if (position >= 0 && position < notes.size())
        {
            String updatedNote = newTitle + "|" + newContent;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(position), updatedNote);
            return editor.commit();
        }
        return false;
    }

    public String getSavedTitle()
    {
        // Hämta den senast sparade titeln från SharedPreferences
        return sharedPreferences.getString("savedTitle", "");
    }

    public String getSavedContent()
    {
        // Hämta den senast sparade innehållet från SharedPreferences
        return sharedPreferences.getString("savedContent", "");
    }
}