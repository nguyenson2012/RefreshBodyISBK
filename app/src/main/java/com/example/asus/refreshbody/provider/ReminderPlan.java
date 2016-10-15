package com.example.asus.refreshbody.provider;

import android.database.Cursor;

import com.example.asus.refreshbody.utils.UiUtils;

import java.io.Serializable;

/**
 * Created by solei on 13/10/2016.
 */

public class ReminderPlan implements Serializable {
    public static final String CLASS_NAME = ReminderPlan.class.getSimpleName();

    public interface Extras {
        String ID = "extras_id";
    }

    private static class Time implements Serializable {
        private int hour;
        private int minute;
        private int second;

        public Time(int hour, int minute, int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        public Time(int hour, int minute) {
            this(hour, minute, 0);
        }

        @Override
        public String toString() {
            return UiUtils.getTimeFormat(hour, minute);
        }
    }

    private long id;
    private Time mTime;
    private byte[] mDays;
    private boolean mSound;
    private boolean mVibration;
    private int mReminderMode;
    private String mLabel;
    private String mNote;
    private int mWorkingTime;

    public ReminderPlan(long id, Time time, byte[] days, int reminderMode, String label, String note, int workingTime, boolean sound, boolean vibration) {
        if (id < 0) {
            throw new IllegalArgumentException(CLASS_NAME + " ERROR: id must larger than or equal to 0");
        }
        if (time == null) {
            throw new IllegalArgumentException(CLASS_NAME + " ERROR: Time has not been set");
        }
        this.id = id;
        this.mTime = time;
        this.mDays = days;
        this.mReminderMode = reminderMode;
        this.mNote = note;
        this.mLabel = label;
        this.mSound = sound;
        this.mVibration = vibration;
        this.mWorkingTime = workingTime;
    }

    public boolean getRepeat() {
        return UiUtils.getDaysArrayList(mDays).size() != 0;
    }

    public long getId() {
        return id;
    }

    public int getMode(){
        return mReminderMode;
    }

    public String getLabel() {
        return mLabel;
    }

    public byte[] getDays() {
        return mDays;
    }

    public String getTime() {
        return mTime.toString();
    }
    public String getNote() {
        return mNote;
    }

    public int getWorkingTime() {
        return mWorkingTime;
    }

    public boolean getSound() {
        return mSound;
    }

    public boolean getVibration() {
        return mVibration;
    }

    @Override
    public String toString() {
        return "ReminderPlan id : " + id + " time : " + mTime + " mode: " + mReminderMode +
                " sound: " + mSound + " vibration: " + mVibration;
    }

    public static class Builder {

        private long id = -1;   //undefine
        private Time mTime = null;
        private byte[] mDays;
        private boolean mSound;
        private boolean mVibration;
        private int mReminderMode;
        private String mLabel;
        private String mNote;
        private int mWorkingTime;

        private void setId(long id) {
            this.id = id;
        }

        public Builder setPlanFromCursor(Cursor data) {
            long _id = data.getLong(data.getColumnIndex(PlanContract.PlanEntry._ID));
            int hour = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_HOUR));
            int minute = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_MINUTE));
            byte[] days = data.getBlob(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_DAYS));
            int mode = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_MODE));
            String label = data.getString(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_LABEL));
            String note = data.getString(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_NOTE));
            int workingTime = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_WORKINGTIME));
            boolean sound = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_SOUND)) == 1;
            boolean vibration = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_VIBRATION)) == 1;

            setId(_id);
            setTime(hour, minute);
            setDays(days);
            setMode(mode);
            setLabel(label);
            setNote(note);
            setSound(sound);
            setVibration(vibration);
            return this;
        }

        private void setWorkingTime(int workingTime) {
            this.mWorkingTime = workingTime;
        }

        private void setMode(int mode) {
            this.mReminderMode = mode;
        }

        private void setLabel(String label){
            this.mLabel = label;
        }

        private void setNote(String note) {
            this.mNote = note;
        }

        private void setTime(int hour, int minute, int second) {
            if (hour >= 24) {
                throw new IllegalArgumentException("BasePlan Hour incorrect: " + hour);
            }
            if (minute >= 60) {
                throw new IllegalArgumentException("BasePlan Minute incorrect: " + hour);
            }
            if (second >= 60) {
                throw new IllegalArgumentException("BasePlan Second incorrect: " + hour);
            }

            mTime = new Time(hour, minute, second);
        }

        private void setTime(int hour, int minute) {
            setTime(hour, minute, 0);
        }

        private void setDays(byte[] days) {
            mDays = days;
        }

        private void setSound(boolean sound) {
            this.mSound = sound;
        }

        private void setVibration(boolean vibration) {
            this.mVibration = vibration;
        }

        public ReminderPlan build() {
            return new ReminderPlan(id, mTime, mDays, mReminderMode, mLabel, mNote, mWorkingTime, mSound, mVibration);
        }

    }

}
