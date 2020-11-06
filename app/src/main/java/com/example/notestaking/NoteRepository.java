package com.example.notestaking;
import android.app.Application;
import java.util.List;
import androidx.lifecycle.LiveData;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        mNoteDao = db.getNoteDao();
        mAllNotes = mNoteDao.getAllNodes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }
    void delete(Note note)
    {
        NoteDatabase.databaseWriteExecutor.execute(()->{
            mNoteDao.delete(note);
        });
    }
}
