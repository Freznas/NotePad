package com.example.notepad;
//Datamanager hanterar dataöverföring och lagringen i sharedpreferences
//model

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
    //Hämtar alla sparade anteckningar från sharedpreferences och skickar tillbaka dem som en lista av Strings.
    {
        List<String> notes = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            notes.add(entry.getValue().toString());
        }
        return notes;
    }

    public void saveNote(int position, String title, String content)
    //Sparar anteckning i sharedpreferences och använder position som nyckel för kunna hantera flera anteckningar.

    {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String note = title + "|" + content;
        editor.putString(String.valueOf(position), note);
        editor.apply();
    }

    public boolean deleteNoteByTitle(String title)
    //tar bort anteckningar genom att gå igenom alla sparade anteckningar och ta bort den med matchande titel som man valt att redigera.

    {

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
    // hämtar anteckning baserat på position i listan

    {
        List<String> notes = getNotes();
        if (position >= 0 && position < notes.size())
        {
            return notes.get(position);
        }
        return null;
    }

    public boolean updateNoteByPosition(int position, String newTitle, String newContent)
    //Uppdaterar befintlig anteckning baserat på positionen i listan.

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
    // Hämta den senast sparade titeln från SharedPreferences

    {

        return sharedPreferences.getString("savedTitle", "");
    }

    public String getSavedContent()
    // Hämta den senast sparade innehållet från SharedPreferences

    {

        return sharedPreferences.getString("savedContent", "");
    }
}