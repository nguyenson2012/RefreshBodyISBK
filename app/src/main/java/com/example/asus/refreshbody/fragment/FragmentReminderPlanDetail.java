package com.example.asus.refreshbody.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.provider.PlanContract;
import com.example.asus.refreshbody.utils.UiUtils;
import com.example.asus.refreshbody.utils.view.DayButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by solei on 13/10/2016.
 */

public class FragmentReminderPlanDetail extends android.support.v4.app.Fragment implements View.OnClickListener {

    public static final String TAG = FragmentReminderPlanDetail.class.getSimpleName();

    // Constants used to saveInstanceState
    private static final String TIME_PICKER_HOUR = "time_picker_hour";
    private static final String TIME_PICKER_MINUTE = "time_picker_minute";

    private final CompoundButton[] dayButtons = new CompoundButton[7];

    private TimePicker mTimePicker;

    private TextView reminderSoundTextView, vibrationStateTextView;
    private Switch reminderSoundSwitch, vibrationSwitch;

    protected long id = -1;

    protected Cursor mData = null;

    public static FragmentReminderPlanDetail newInstance(long id) {
        FragmentReminderPlanDetail fragment = new FragmentReminderPlanDetail();
        Bundle bundle = new Bundle();
        bundle.putLong(PlanContract.PlanEntry._ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.reminder_plan_detail, container, false);
        setHasOptionsMenu(true);
        setupView(layout);
        registerEvent();
        return layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            id = getArguments().getLong(PlanContract.PlanEntry._ID);
            if (id != -1) {
                mData = getActivity().getContentResolver().query(
                        PlanContract.PlanEntry.CONTENT_URI, null,
                        PlanContract.PlanEntry._ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null
                );
            }

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mTimePicker.setCurrentHour(savedInstanceState.getInt(TIME_PICKER_HOUR));
            mTimePicker.setCurrentMinute(savedInstanceState.getInt(TIME_PICKER_MINUTE));
        }
    }

    private void registerEvent() {
        reminderSoundSwitch.setOnClickListener(this);
        vibrationSwitch.setOnClickListener(this);
    }

    private void setupView(View root) {
        reminderSoundTextView = (TextView) root.findViewById(R.id.tv_reminder_sound_state);
        vibrationStateTextView = (TextView) root.findViewById(R.id.tv_vibration_state);

        reminderSoundSwitch = (Switch) root.findViewById(R.id.switch_reminder_sound);
        vibrationSwitch = (Switch) root.findViewById(R.id.switch_vibration);
        reminderSoundSwitch.setChecked(true);
        vibrationSwitch.setChecked(true);

        mTimePicker = (TimePicker) root.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));

        LinearLayout repeatDays = (LinearLayout) root.findViewById(R.id.repeat_days);

        // Build button for each day.
        for (int i = 0; i < 7; i++) {
            DayButton dayButton = new DayButton(getActivity(), i);
            repeatDays.addView(dayButton);
            dayButtons[i] = dayButton;
        }

        if (mData != null && mData.moveToFirst()) {
            mTimePicker.setCurrentHour(mData.getInt(mData.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_HOUR)));
            mTimePicker.setCurrentMinute(mData.getInt(mData.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_MINUTE)));

            byte[] daysByte = mData.getBlob(mData.getColumnIndex(PlanContract.PlanEntry.COLUMN_DAYS));

            ArrayList<Byte> daysArrayList = UiUtils.getDaysArrayList(daysByte);
            for (int i = 0; i < daysArrayList.size(); i++) {
                dayButtons[daysArrayList.get(i)].setChecked(true);
            }

            reminderSoundSwitch.setChecked(mData.getInt(mData.getColumnIndex(PlanContract.PlanEntry.COLUMN_SOUND)) == 1);
            vibrationSwitch.setChecked(mData.getInt(mData.getColumnIndex(PlanContract.PlanEntry.COLUMN_VIBRATION)) == 1);
        }
    }

    private boolean savePlan() {

        ArrayList<Byte> daysByteArrayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (dayButtons[i].isChecked()) {
                daysByteArrayList.add((byte) i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(PlanContract.PlanEntry.COLUMN_TIME_HOUR, mTimePicker.getCurrentHour());
        values.put(PlanContract.PlanEntry.COLUMN_TIME_MINUTE, mTimePicker.getCurrentMinute());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(daysByteArrayList);
            byte[] days = byteArrayOutputStream.toByteArray();

            values.put(PlanContract.PlanEntry.COLUMN_DAYS, days);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (reminderSoundSwitch.isChecked()) values.put(PlanContract.PlanEntry.COLUMN_SOUND, 1);
        else values.put(PlanContract.PlanEntry.COLUMN_SOUND, 0);

        if (vibrationSwitch.isChecked()) values.put(PlanContract.PlanEntry.COLUMN_VIBRATION, 1);
        else values.put(PlanContract.PlanEntry.COLUMN_VIBRATION, 0);

        if (id != -1) {
            getActivity().getContentResolver().update(
                    PlanContract.PlanEntry.CONTENT_URI,
                    values,
                    PlanContract.PlanEntry._ID + " = ?",
                    new String[]{String.valueOf(id)}
            );
        } else {
            values.put(PlanContract.PlanEntry.COLUMN_ENABLE, 1);
            getActivity().getContentResolver().insert(PlanContract.PlanEntry.CONTENT_URI, values);
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_reminder_sound:
                if (reminderSoundSwitch.isChecked())
                    reminderSoundTextView.setText(getString(R.string.on));
                else reminderSoundTextView.setText(getString(R.string.off));
                break;
            case R.id.switch_vibration:
                if (vibrationSwitch.isChecked())
                    vibrationStateTextView.setText(getString(R.string.on));
                else vibrationStateTextView.setText(getString(R.string.off));
                break;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(TIME_PICKER_HOUR, mTimePicker.getCurrentHour());
        outState.putInt(TIME_PICKER_MINUTE, mTimePicker.getCurrentMinute());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        id = -1;
        mData = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cancel_save, menu);

        //gone all item except save and cancel
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() != R.id.action_save && item.getItemId() != R.id.action_cancel) {
                item.setVisible(false);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!savePlan()) {
                    return true;
                }
            case R.id.action_cancel:
                FragmentReminder fragment = new FragmentReminder();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
