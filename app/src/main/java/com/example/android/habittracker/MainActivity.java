package com.example.android.habittracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

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
        mDbHelper = HabitDbHelper.insertHabits(mDbHelper);

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try (Cursor cursor = HabitDbHelper.displayDatabaseInfo(mDbHelper)) {
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

        }

        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.


    }

}