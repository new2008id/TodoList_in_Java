package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    private LinearLayout linearLayoutNotes;
    private NotesAdapter notesAdapter;
    private RecyclerView rvNotes;
    private FloatingActionButton buttonAddNote;
    //    private final Database database = Database.getInstance();
//    private Handler handler = new Handler(Looper.getMainLooper());
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

//        viewModel = new MainViewModel(getApplication()); // не правильный способ инициализации
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

//        viewModel.getCount().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer count) {
//                Toast.makeText(
//                        MainActivity.this,
//                        String.valueOf(count),
//                        Toast.LENGTH_SHORT
//                ).show();
//            }
//        });

//        noteDatabase = NoteDatabase.getInstance(getApplication());

        initViews();

        notesAdapter = new NotesAdapter();

//        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
//            @Override
//            public void onNoteClick(Note note) {
//                viewModel.showCount();
//            }
//        });
        rvNotes.setAdapter(notesAdapter);

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

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

//                database.removeNote(note.getId());

//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        noteDatabase.notesDao().removeNote(note.getId());
////                        handler.post(new Runnable() {
////                            @Override
////                            public void run() {
////                                showNotes();
////                            }
////                        });
//                    }
//                });
//                thread.start();

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        showNotes();
//    }

    private void initViews() {
//        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        rvNotes = findViewById(R.id.rvNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }

//    private void showNotes() {
////        notesAdapter.setNotes(database.getNotes());
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Note> notes = noteDatabase.notesDao().getNotes();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        notesAdapter.setNotes(notes);
//                    }
//                });
//            }
//        });
//        thread.start();
//    }

    private void launchAddNoteActivity() {
        Intent intent = AddNoteActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }
}