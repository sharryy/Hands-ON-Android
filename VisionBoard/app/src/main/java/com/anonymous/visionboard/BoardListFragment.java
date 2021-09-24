package com.anonymous.visionboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.anonymous.visionboard.model.Board;
import com.anonymous.visionboard.model.CatalogViewModel;

import java.util.ArrayList;
import java.util.List;

public class BoardListFragment extends Fragment {
    private ArrayList<Board> boardArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter(boardArrayList);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_list, container, false);
        recyclerView = view.findViewById(R.id.list);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Context context = view.getContext();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(boardRecyclerViewAdapter);

    }

    void setBoards(final List<Board> boardList) {
        for (Board board : boardList) {
            if (!boardArrayList.contains(board)) {
                boardArrayList.add(board);
                boardRecyclerViewAdapter.notifyItemInserted(boardArrayList.indexOf(board));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_item) {
            startActivity(new Intent(getContext(), AddBoardActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu, menu);
    }

    private void setUpViewModel() {
        CatalogViewModel viewModel = ViewModelProviders.of(this)
                .get(CatalogViewModel.class);
        viewModel.getBoards().observe(getViewLifecycleOwner(), new Observer<List<Board>>() {
            @Override
            public void onChanged(List<Board> boards) {
                for (Board board :
                        boards) {
                    Log.d("TAG", "onChanged: " + board.toString());
                    boardArrayList.add(board);
                    boardRecyclerViewAdapter.notifyItemInserted(boardArrayList.indexOf(board));

                }
            }
        });

        boardRecyclerViewAdapter.setOnRowClickListener(new BoardRecyclerViewAdapter.OnRowClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Board board = boardArrayList.get(position);
                Intent i = new Intent(getContext(), BoardDetailActivity.class);
                i.putExtra("board", board);
                startActivity(i);

            }
        });

    }
}
