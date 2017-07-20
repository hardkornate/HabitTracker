package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import static com.example.android.habittracker.data.HabitContract.HabitEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);

    }

    @Override
    protected void onStart() {

        super.onStart();
        insertHabits();
        displayDatabaseInfo();

    }

    private void insertHabits(){

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

    }

    public static long getTimesPerDay(int days, int count){

        return(days/count);

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */

    private void displayDatabaseInfo() {

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
        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> habits.
            // _id - habit - count - days - rate
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT + " - " +
                    HabitEntry.COLUMN_COUNT + " - " +
                    HabitEntry.COLUMN_DAYS_TRACKED + " - " +
                    HabitEntry.COLUMN_TIMES_PER_DAY + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT);
            int countColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_COUNT);
            int daysTrackedColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DAYS_TRACKED);
            int timesPerDayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_TIMES_PER_DAY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentHabit = cursor.getString(habitColumnIndex);
                int currentCount = cursor.getInt(countColumnIndex);
                int currentDaysTracked = cursor.getInt(daysTrackedColumnIndex);
                long currentTimesPerDay = cursor.getLong(timesPerDayColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentHabit + " - " +
                        currentCount + " - " +
                        currentDaysTracked + " - " +
                        currentTimesPerDay));

            }

        } finally {

            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

}