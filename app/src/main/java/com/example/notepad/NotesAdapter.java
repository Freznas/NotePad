package com.example.notepad;
//Custom adapter för hantera listan av anteckningar och fylla listview
//Controller

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<String>
{
    DataManager dataManager;


    public NotesAdapter(Context context, List<String> notes, DataManager dataManager)
    {
        super(context, 0, notes);
        this.dataManager = dataManager;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    //här har vi metod för att skapa element i listan, den fyller i titel och innehåll och lägger till onclick.

    {
        View listItemView = convertView;
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_notes, parent, false);
        }

        String currentNote = getItem(position);
        if (currentNote != null)
        {
            final String[] noteParts = currentNote.split("\\|");
            TextView titleTextView = listItemView.findViewById(R.id.tv_headline);
            if (noteParts.length > 0)
            {
                titleTextView.setText(noteParts[0]);
            }
            final String titleToDelete = noteParts[0];

            listItemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Intent editIntent = new Intent(getContext(), NotesActivity.class);
                    editIntent.putExtra("edit_note_position", position);
                    getContext().startActivity(editIntent);
                }
            });

            return listItemView;
        }

        return listItemView;
    }

    public void deleteNoteAtPosition(int position)
    //tar bort anteckning från både listan och sharedpreferences via positionen för att identifiera vilken som ska tas bort.

    {
        if (position >= 0 && position < getCount())
        {
            String noteToDelete = getItem(position);
            if (noteToDelete != null)
            {
                String[] noteParts = noteToDelete.split("\\|");
                if (noteParts.length > 0)
                {
                    String titleToDelete = noteParts[0];
                    dataManager.deleteNoteByTitle(titleToDelete);
                    remove(noteToDelete);

                }
            }
        }
    }
}