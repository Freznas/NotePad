package com.example.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<NotesActivity>

{
    private DataManager dataManager;
    public NotesAdapter(Context context, List<String> notes, DataManager dataManager)
    {
        super(context,0);
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
        NotesActivity currentNote = getItem(position);
        final String [] noteParts = currentNote.split("\\|");
        TextView titleTextView = listItemView.findViewById(R.id.tv_headline);
        if (noteParts.length>0)
        {
            titleTextView.setText(noteParts[0]);
        }
        final String titleToDelete = noteParts[0];

        listItemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                boolean isRemoved = dataManager.deleteNoteByTitle(titleToDelete);
                if(isRemoved){
                    remove(currentNote);
                    notifyDataSetChanged();
                }
                return false;
            }
        });



        return listItemView;
    }
}