package com.bawp.customcard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;

public class SelectImageActivity extends AppCompatActivity {
    private static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";
    private static final int MAX_NUMBER_REQUEST_PERMISSIONS = 2;
    private static final int REQUEST_CODE_IMAGE = 1000;
    private static final String TAG = "Select Image Activity";
    private int mPermissionRequestCount;
    private Button selectImageButton;
    public static final int REQUEST_CODE_PERMISSIONs = 101;
    private static final List<String> sPermissions = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        selectImageButton = findViewById(R.id.select_image_button);

        if (savedInstanceState != null) {
            mPermissionRequestCount = savedInstanceState.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0);
        }

        //Make sure that app has correct permission
        requestPermissionsIfNecessary();

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE);
            }
        });
    }

    private void requestPermissionsIfNecessary() {
        if (!checkCallingPermission()) {
            if (mPermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                mPermissionRequestCount += 1;
                ActivityCompat.requestPermissions(this, sPermissions.toArray(new String[0]),
                        REQUEST_CODE_PERMISSIONs);
            } else {
                Toast.makeText(this, R.string.go_set_permissions, Toast.LENGTH_LONG).show();
                selectImageButton.setEnabled(false); //Disable Button
            }
        }
    }

    private boolean checkCallingPermission() {
        boolean hasPermissions = true;
        for (String permission : sPermissions) {
            hasPermissions &= ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermissions;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE:
                    handleImageRequestResult(data);
                    break;
                default:
                    Log.i(TAG, "Unknown Request Code! ");
            }
        } else {
            Log.e(TAG, String.format("Unexpected Result Code %s", resultCode));
        }
    }

    private void handleImageRequestResult(Intent data) {
        Uri imageUri = null;
        if (data.getClipData() != null) {
            imageUri = data.getClipData().getItemAt(0).getUri();
        } else if (data.getData() != null) {
            imageUri = data.getData();
        }
        if (imageUri == null) {
            Log.e(TAG, "Invalid Image Input: ");
            return;
        }

        Intent filterIntent = new Intent(this, CreateCardActivity.class);
        filterIntent.putExtra(Constants.KEY_IMAGE_URI, imageUri.toString());
        startActivity(filterIntent);
    }
}
