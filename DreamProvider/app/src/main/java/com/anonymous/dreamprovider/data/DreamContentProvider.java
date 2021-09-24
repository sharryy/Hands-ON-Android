package com.anonymous.dreamprovider.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anonymous.dreamprovider.DreamDao;
import com.anonymous.dreamprovider.model.Dream;

import java.util.Objects;

public class DreamContentProvider extends ContentProvider {
    private DreamDao dreamDao;

    public static final String TAG = DreamContentProvider.class.getName();

    public static final String AUTHORITY = "com.anonymous.dreamprovider";
    public static final String DREAM_TABLE = "dream_tbl";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";

    //content://com.anonymous.dreamprovider/dream_tbl/1
    public static final int DREAMS = 1;     //Adding Dream
    public static final int DREAM_ID = 2;   //Updating Dream

    public static final String URL = "content://" + AUTHORITY + "/" + DREAM_TABLE;
    public static final Uri content_URI = Uri.parse(URL);

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, DREAM_TABLE, DREAMS);
        uriMatcher.addURI(AUTHORITY, DREAM_TABLE + "/#", DREAM_ID);
    }

    @Override
    public boolean onCreate() {
        dreamDao = ApplicationDatabase.getINSTANCE(getContext()).getDreamDao();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: ");
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case DREAMS:
                cursor = dreamDao.findAll();
                if (getContext() != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch ((uriMatcher.match(uri))) {
            case DREAMS:
                return "vnd.android.cursor.dir/" + DREAM_TABLE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: ");
        switch (uriMatcher.match(uri)) {
            case DREAMS:
                if (getContext() != null) {
                    if (values != null) {
                        long id = dreamDao.insert(Dream.fromContentValues(values));
                        if (id != 0) {
                            getContext().getContentResolver().notifyChange(uri, null);
                            return ContentUris.withAppendedId(uri, id);
                        }
                    }
                }
            case DREAM_ID:
                throw new IllegalArgumentException("Invalid URI: Insert Failed " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        Log.d(TAG, "delete: ");
        switch (uriMatcher.match(uri)) {
            case DREAMS:
                if (getContext() != null) {
                    count = dreamDao.deleteAll();
                    getContext().getContentResolver().notifyChange(uri, null);
                    return count;
                }
            case DREAM_ID:
                if (getContext() != null) {
                    count = dreamDao.delete(ContentUris.parseId(uri));
                    getContext().getContentResolver().notifyChange(uri, null);
                    return count;
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: ");
        switch (uriMatcher.match(uri)) {
            case DREAMS:
                if (getContext() != null) {
                    int count = dreamDao.update(Dream.fromContentValues(Objects.requireNonNull(values)));
                    if (count != 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        return count;
                    }
                }
            case DREAM_ID:
                throw new IllegalArgumentException("Invalid URI: Can't Update");
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
