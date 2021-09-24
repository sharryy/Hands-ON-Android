package com.anonymous.staticfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements HelloFragment.OnSelectedItemListener {
    public static final String HELLO_FRAGMENT = "HELLO_TAG";

    private Button button;

    HelloFragment helloFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        button = findViewById(R.id.replace);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                fragmentTransaction.replace(R.id.main_frame, new DetailsFragment());

                fragmentTransaction.addToBackStack("BACK");

                fragmentTransaction.commit();
            }
        });

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            helloFragment = new HelloFragment();
            fragmentTransaction.add(R.id.main_frame, helloFragment, HELLO_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            helloFragment = (HelloFragment) fragmentManager.findFragmentByTag(HELLO_FRAGMENT);
        }


    }

    @Override
    public void onUpdateSelected(String message) {
        HelloFragment helloFragment = (HelloFragment) getSupportFragmentManager().findFragmentByTag(HELLO_FRAGMENT);

        if (helloFragment != null) {
            helloFragment.sayHello(message);
        }
    }
}
