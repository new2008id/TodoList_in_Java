package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private Button buttonSaveNote;
    private RadioButton rbHighPriority;
    private RadioButton rbMediumPriority;
    private RadioButton rbLowPriority;
    private RadioGroup rgPriority;
    private EditText etEnterNote;
    //    private final Database database = Database.getInstance();
//    private Handler handler = new Handler(Looper.getMainLooper());
//    private NoteDatabase noteDatabase;
    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        noteDatabase = NoteDatabase.getInstance(getApplication());
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if (shouldCloseScreen) {
                    finish();
                }
            }
        });
        initViews();
        rbLowPriority.setChecked(true);

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void initViews() {
        buttonSaveNote = findViewById(R.id.buttonSaveNote);
        rbHighPriority = findViewById(R.id.rbHighPriority);
        rbMediumPriority = findViewById(R.id.rbMediumPriority);
        rbLowPriority = findViewById(R.id.rbLowPriority);
        rgPriority = findViewById(R.id.rgPriority);
        etEnterNote = findViewById(R.id.etEnterNote);
    }

    private void saveNote() {
        String text = etEnterNote.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(
                    this,
                    "Введите описание заметки",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            int priority = getPriority();
//            int id = database.getNotes().size();
            Note note = new Note(text, priority);
            viewModel.saveNote(note);



            finish();
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    noteDatabase.notesDao().addNote(note);
//                    handler.post(new Runnable() { // вызываем на главном потоке
//                        @Override
//                        public void run() {
//                            finish(); // завершаем работу Activity
//                        }
//                    });
//                }
//            });
//            thread.start();

//            database.addNote(note);


            Log.d("AddNoteActivity", "Note saved: " + note.toString());
        }
    }

    private int getPriority() {
        int priority;
        if (rbLowPriority.isChecked()) {
            priority = 0;
        } else if (rbMediumPriority.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}