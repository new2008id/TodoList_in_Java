package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Insert
    void addNote(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void removeNote(int id);
}
