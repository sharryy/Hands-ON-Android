package com.bawp.customcard.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.bawp.customcard.Constants.CUSTOM_QUOTE;
import static com.bawp.customcard.Constants.KEY_IMAGE_URI;
import static com.bawp.customcard.workers.CleanupWorker.TAG;

public class CardWorker extends Worker {
    public static final String TAg = CardWorker.class.getSimpleName();

    public CardWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        CardWorkerUtils.makeStatusNotification("Writing quote into Image", applicationContext);

        CardWorkerUtils.sleep();

        String imageResourceUri = getInputData().getString(KEY_IMAGE_URI);
        String quote = getInputData().getString(CUSTOM_QUOTE);

        ContentResolver contentResolver = applicationContext.getContentResolver();

        //Create a Bitmap
        try {
            Bitmap photo = BitmapFactory.decodeStream(
                    contentResolver.openInputStream(Uri.parse(imageResourceUri)));

            //Write Text On Image
            Bitmap output = CardWorkerUtils.overlayTextOnBitmap(photo, applicationContext, quote);

            //Write Bitmap on temp file
            Uri outputUri = CardWorkerUtils.writeBitmapToFile(applicationContext, output);

            Data outputData = new Data.Builder().putString(KEY_IMAGE_URI, outputUri.toString()).build();

            return Result.success(outputData);

        } catch (Throwable e) {
            Log.i(TAG, "Error Writing Quote onto Image : ");
            return Result.failure();
        }
    }
}
