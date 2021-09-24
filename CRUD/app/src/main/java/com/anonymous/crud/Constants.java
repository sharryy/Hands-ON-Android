package com.anonymous.crud;

import android.provider.BaseColumns;

public class Constants {

    public Constants() {
    }

    public static class DatabaseColumns implements BaseColumns{
        public static final String TABLE_NAME = "studentList";
        public static final String COLUMN_UNIVERSITY = "university";
        public static final String COLUMN_STUDENT_ID = "studentID";
        public static final String COLUMN_FIRST_NAME = "firstname";
        public static final String COLUMN_LAST_NAME = "lastname";
        public static final String COLUMN_DEPARTMENT = "department";
        public static final String COLUMN_FEE = "fee";
        public static final String COLUMN_IS_SUBMITTED = "isSubmitted";
        public static final String COLUMN_IS_PASSED = "isPassed";
        public static final String COLUMN_REMARKS = "remarks";
    }
}
