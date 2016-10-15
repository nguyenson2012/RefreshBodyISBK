package com.example.asus.refreshbody.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class PlanContract {
    private PlanContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.asus.refreshbody.provider";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_REMINDER_PLAN = "reminder_plan";


    public static class PlanEntry implements BaseColumns {
        public static final String COLUMN_TIME_HOUR = "column_time_hour";
        public static final String COLUMN_TIME_MINUTE = "column_time_minute";
        public static final String COLUMN_DAYS = "column_days";
        public static final String COLUMN_SOUND = "column_sound";
        public static final String COLUMN_VIBRATION = "column_vibration";
        public static final String COLUMN_ENABLE = "column_enable";
        public static final String COLUMN_MODE = "column_mode";
        public static final String COLUMN_LABEL = "column_label";
        public static final String COLUMN_NOTE = "column_note";
        public static final String COLUMN_WORKINGTIME = "column_time";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REMINDER_PLAN).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_REMINDER_PLAN;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_REMINDER_PLAN;


        public static final String TABLE_NAME = "reminder_plan_table";

        public static Uri buildTableUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
