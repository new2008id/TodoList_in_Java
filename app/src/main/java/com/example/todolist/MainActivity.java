package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    private LinearLayout linearLayoutNotes;
    private NotesAdapter notesAdapter;
    private RecyclerView rvNotes;
    private FloatingActionButton buttonAddNote;
//    private final Database database = Database.getInstance();

    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        noteDatabase = NoteDatabase.getInstance(getApplication());
        initViews();
        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
//                database.removeNote(note.getId());
//                showNotes();
            }
        });
        rvNotes.setAdapter(notesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
//                database.removeNote(note.getId());
                noteDatabase.notesDao().removeNote(note.getId());
                showNotes();
            }
        });
        itemTouchHelper.attachToRecyclerView(rvNotes);

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddNoteActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initViews() {
//        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        rvNotes = findViewById(R.id.rvNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

    private void showNotes() {
//        notesAdapter.setNotes(database.getNotes());
        notesAdapter.setNotes(noteDatabase.notesDao().getNotes());
    }

    private void launchAddNoteActivity() {
        Intent intent = AddNoteActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }
}