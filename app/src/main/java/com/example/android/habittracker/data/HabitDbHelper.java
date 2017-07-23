package com.example.android.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

import static com.example.android.habittracker.data.HabitContract.HabitEntry.TABLE_NAME;

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "habits.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     *
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static HabitDbHelper insertHabits(HabitDbHelper mDbHelper) {

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Deletes all data from the database
        db.delete(TABLE_NAME, null, null);

        // Create a ContentValues object where column names are the keys,
        // and habits are the values.
        ContentValues values = new ContentValues();

        values.put(HabitEntry.COLUMN_HABIT, "Smoking");
        values.put(HabitEntry.COLUMN_COUNT, 41);
        values.put(HabitEntry.COLUMN_DAYS_TRACKED, 3);
        values.put(HabitEntry.COLUMN_TIMES_PER_DAY, (getTimesPerDay(41, 3)));

        db.insert(TABLE_NAME, null, values);

        values.put(HabitEntry.COLUMN_HABIT, "Eating");
        values.put(HabitEntry.COLUMN_COUNT, 9);
        values.put(HabitEntry.COLUMN_DAYS_TRACKED, 3);
        values.put(HabitEntry.COLUMN_TIMES_PER_DAY, (getTimesPerDay(9, 3)));

        db.insert(TABLE_NAME, null, values);

        values.put(HabitEntry.COLUMN_HABIT, "Sleeping");
        values.put(HabitEntry.COLUMN_COUNT, 3);
        values.put(HabitEntry.COLUMN_DAYS_TRACKED, 3);
        values.put(HabitEntry.COLUMN_TIMES_PER_DAY, (getTimesPerDay(3, 3)));

        db.insert(TABLE_NAME, null, values);

        return mDbHelper;

    }

    private static long getTimesPerDay(int days, int count) {

        return (days / count);

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */

    public static Cursor displayDatabaseInfo(HabitDbHelper mDbHelper) {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT,
                HabitEntry.COLUMN_COUNT,
                HabitEntry.COLUMN_DAYS_TRACKED,
                HabitEntry.COLUMN_TIMES_PER_DAY};

        // Perform a query on the pets table
        return db.query(
                TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the habit table
        String SQL_CREATE_HABITS_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_COUNT + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_DAYS_TRACKED + " INTEGER NOT NULL DEFAULT 1, "
                + HabitEntry.COLUMN_TIMES_PER_DAY + " LONG NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

}