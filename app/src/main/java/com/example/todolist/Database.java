package com.example.todolist;

import java.util.ArrayList;
import java.util.Random;

public class Database {
    private final ArrayList<Note> notes = new ArrayList<>();
    private static Database instance = null;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Note note = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(note);
        }
    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                notes.remove(note);
                break;
            }
        }
    }
}
