package com.example.tharunkrishna.grocessory;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Tharun Krishna on 12/19/2015.
 */
public class Userdatabase extends ContentProvider {

    // fields for my content provider
    static final String PROVIDER_NAME = "com.example.tharunkrishna.grocessory.usertable";
    static final String URL = "content://" + PROVIDER_NAME + "/USER";
    static final Uri CONTENT_URI = Uri.parse(URL);

    // fields for the database
    static final String ID = "id";
    static final String NAME = "name";
    static final String PHONE = "phone";
    static final String EMAIL = "email";
    static final String PASSWORD = "pswd";
    static final String LOCATION = "location";


    // integer values used in content URI
    static final int USERTABLE = 1;
    static final int USERTABLE_ID = 2;

    DBHelper dbHelper;

    // projection map for a query
    private static HashMap<String, String> BirthMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "friends", USERTABLE);
        uriMatcher.addURI(PROVIDER_NAME, "friends/#", USERTABLE_ID);
    }

    // database declarations
    private SQLiteDatabase database;
    static final String DATABASE_NAME = "formersdb";
    static final String TABLE_NAME = "farmerstable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " phone TEXT NOT NULL, " +
                    " email TEXT NOT NULL, " +
                    " pswd TEXT NOT NULL, " +
                    " location TEXT NOT NULL);";

    // class that creates and manages the provider's database
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }


    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        Context context = getContext();
        dbHelper = new DBHelper(context);
        // permissions to be writable
        database = dbHelper.getWritableDatabase();

        if (database == null)
            return false;
        else
            return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case USERTABLE:
                queryBuilder.setProjectionMap(BirthMap);
                break;
            case USERTABLE_ID:
                queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            // No sorting-> sort on names by default
            sortOrder = NAME;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        long row = database.insert(TABLE_NAME, "", values);

        // If record is added successfully
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        } else {
            try {
                throw new SQLException("Fail to add a new record into " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       //  TODO Auto-generated method stub
        return 0;
        }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

