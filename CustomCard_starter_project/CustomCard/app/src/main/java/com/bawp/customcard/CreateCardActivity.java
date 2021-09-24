package com.bawp.customcard;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.bawp.customcard.Constants.KEY_IMAGE_URI;

public class CreateCardActivity extends AppCompatActivity {
    private CustomCardViewModel customCardViewModel;
    private Button processCard, CancelProcess, seeCardButton;
    private ImageView imageView;
    private EditText quoteEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        imageView = findViewById(R.id.image_view);
        quoteEditText = findViewById(R.id.custom_quote_edtx);
        seeCardButton = findViewById(R.id.see_card_button);
        CancelProcess = findViewById(R.id.cancel_button);
        processCard = findViewById(R.id.process_button);
        progressBar = findViewById(R.id.progress_bar);

        customCardViewModel = new ViewModelProvider.AndroidViewModelFactory(
                (Application) getApplicationContext()).create(CustomCardViewModel.class);

        Intent intent = getIntent();
        String imageUriExtra = intent.getStringExtra(KEY_IMAGE_URI);

        customCardViewModel.setImageUri(imageUriExtra);
        if (customCardViewModel.getImageUri() != null) {
            Picasso.get()
                    .load(customCardViewModel.getImageUri())
                    .into(imageView);
        }

        customCardViewModel.getOutputWorkInfo().observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                if (workInfos == null || workInfos.isEmpty()) {
                    return;
                }

                WorkInfo workInfo = workInfos.get(0);
                boolean finished = workInfo.getState().isFinished();

                if (!finished) {
                    showWorkInProgress();
                } else {
                    showWorkfinished();
                    Data outputData = workInfo.getOutputData();
                    String outputImageUri = outputData.getString(KEY_IMAGE_URI);

                    //Check-out Button
                    if (!TextUtils.isEmpty(outputImageUri)) {
                        customCardViewModel.setOutputUri(outputImageUri);
                        seeCardButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        processCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(quoteEditText.getText().toString())) {
                    String quote = quoteEditText.getText().toString().trim();
                    customCardViewModel.processImageToCard(quote);
                }
            }
        });

        seeCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "onClick: See Card");
                Uri currentUri = customCardViewModel.getOutputUri();
                if (currentUri != null) {
                    Intent actionView = new Intent(Intent.ACTION_VIEW, currentUri);
                    if (actionView.resolveActivity(getPackageManager()) != null) {
                        startActivity(actionView);
                    }
                    processCard.setVisibility(View.VISIBLE);
                }
            }
        });

        CancelProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCardViewModel.cancelWork();
            }
        });
    }

    private void showWorkfinished() {
        progressBar.setVisibility(View.GONE);
        CancelProcess.setVisibility(View.GONE);
        seeCardButton.setVisibility(View.VISIBLE);
    }

    private void showWorkInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        CancelProcess.setVisibility(View.VISIBLE);
        processCard.setVisibility(View.GONE);
        seeCardButton.setVisibility(View.GONE);
    }
}
