package com.anonymous.visionboard.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.anonymous.visionboard.data.ApplicationDatabase;

import java.util.List;

public class CatalogViewModel extends AndroidViewModel {

    private LiveData<List<Board>> boards;

    public CatalogViewModel(@NonNull Application application) {
        super(application);

        ApplicationDatabase database = ApplicationDatabase.getInstance(this.getApplication());
        boards = database.boardDao().loadAllBoards();
    }

    public LiveData<List<Board>> getBoards(){
        return boards;
    }
}
