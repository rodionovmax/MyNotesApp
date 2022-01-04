package com.gb.mynotesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gb.mynotesapp.R;
import com.gb.mynotesapp.data.Constants;
import com.gb.mynotesapp.data.InMemoryRepoImpl;
import com.gb.mynotesapp.data.Note;
import com.gb.mynotesapp.data.Repo;

import java.util.ArrayList;
import java.util.Arrays;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "happy ";
    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;

    private Repo repository = InMemoryRepoImpl.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        saveNote = findViewById(R.id.edit_note_update_btn);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.NOTE)) {
            Note note = (Note) intent.getSerializableExtra(Constants.NOTE);
            if (note.getId() != null) {
                id = note.getId();
            }
            Log.d(TAG, id + " of updating activity");
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
        saveNote.setOnClickListener(this);
    }

    /*
    * Click to update/create new note
    */
    @Override
    public void onClick(View view) {
        String updatedTitle = title.getText().toString();
        String updatedDesc = description.getText().toString();
        Note note = new Note(id, updatedTitle, updatedDesc);

        // note without id (aka id = -1) should be added to repository
        if (id == -1) {
            repository.create(note);
        } else {
            repository.update(note);
        }
        onBackPressed();
    }
}
