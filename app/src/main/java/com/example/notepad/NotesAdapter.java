package com.example.notepad;

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
    {
        View listItemView = convertView;
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_notes, parent, false);
        }

        String currentNote = getItem(position);
        if (currentNote != null)
        {
            final String[] noteParts = currentNote.split("\\|"); // förstå?
            TextView titleTextView = listItemView.findViewById(R.id.tv_headline);
            if (noteParts.length > 0)
            {
                titleTextView.setText(noteParts[0]);
            }
            final String titleToDelete = noteParts[0];

            //problem?
            listItemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Öppna NotesActivity för redigering när listelement klickas
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