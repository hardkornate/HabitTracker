package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {
        // Column names
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_HABIT = "habit";
        public static final String COLUMN_COUNT = "count";
        public static final String COLUMN_DAYS_TRACKED = "days";
        public static final String COLUMN_TIMES_PER_DAY = "rate";
        // Table name
        static final String TABLE_NAME = "habits";


    }

}
