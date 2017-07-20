package com.example.android.habittracker.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Hardkornate on 7/19/17.
 */

public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
// give it an empty constructor.
    private HabitContract() {}

public static final class HabitEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "habits";

        // Column names
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_HABIT = "habit";
        public static final String COLUMN_COUNT = "count";
        public static final String COLUMN_DAYS_TRACKED = "days";
        public static final String COLUMN_TIMES_PER_DAY = "rate";


    }

}
