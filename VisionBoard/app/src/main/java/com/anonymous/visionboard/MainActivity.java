package com.anonymous.visionboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anonymous.visionboard.model.Board;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";

    BoardListFragment boardListFragment;
    List<Board> dummyBoards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            boardListFragment = new BoardListFragment();
            fragmentTransaction.add(R.id.main_activity_frame, boardListFragment, TAG_LIST_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            boardListFragment = (BoardListFragment) fragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT);
        }

//        dummyBoards = new ArrayList<>();
//        dummyBoards.add(new Board("Travel", "Travel with Family", R.drawable.maldivas));
//        dummyBoards.add(new Board("Buy a house", "Travel with Family", R.drawable.maldives));
//        dummyBoards.add(new Board("Invest", "Travel with Family", R.drawable.ontario));
//        dummyBoards.add(new Board("Be Free", "Travel with Family", R.drawable.senegal));
//        dummyBoards.add(new Board("Mesmerising", "Travel with Family", R.drawable.utah));
//
//        boardListFragment.setBoards(dummyBoards);
    }
}
