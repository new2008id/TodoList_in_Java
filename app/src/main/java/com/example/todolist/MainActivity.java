package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private NotesAdapter notesAdapter;
    private RecyclerView rvNotes;
    private FloatingActionButton buttonAddNote;
    private MainViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();

        notesAdapter = new NotesAdapter();

        rvNotes.setAdapter(notesAdapter);

        viewModel.getNotes().observe(this, notes -> notesAdapter.setNotes(notes));

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
                viewModel.remove(note);
            }
        });
        itemTouchHelper.attachToRecyclerView(rvNotes);

        buttonAddNote.setOnClickListener(view -> launchAddNoteActivity());
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

    private void launchAddNoteActivity() {
        Intent intent = AddNoteActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }
}