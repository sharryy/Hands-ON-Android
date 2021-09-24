package com.anonymous.staticfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelloFragment extends Fragment {
    private EditText editText;
    private TextView textView;
    private Button button;

    private OnSelectedItemListener listener;

    public HelloFragment() {
    }

    public interface OnSelectedItemListener {
        void onUpdateSelected(String message);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hello, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnSelectedItemListener) {
            listener = (OnSelectedItemListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Must Implement HelloFragment.OnSelectedItemListener !");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSayHello("Updating...");
            }
        });

        editText = view.findViewById(R.id.edittext);
        textView = view.findViewById(R.id.textView);
    }

    private void updateSayHello(String message) {
        String newTime = System.currentTimeMillis() + editText.getText().toString() + message;
        listener.onUpdateSelected(newTime);
    }

    void sayHello(String text) {
        textView.setText(text);
    }

    void sayHello() {
        textView.setText(editText.getText().toString());
    }
}
