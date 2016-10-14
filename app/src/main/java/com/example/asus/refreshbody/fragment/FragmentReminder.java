package com.example.asus.refreshbody.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.adapter.ReminderCursorAdapter;
import com.example.asus.refreshbody.provider.PlanContract;
import com.example.asus.refreshbody.utils.UiUtils;
import com.example.asus.refreshbody.utils.iLog;

/**
 * Created by solei on 13/10/2016.
 */

public class FragmentReminder extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private String TAG = FragmentReminder.this.getClass().getSimpleName();

    private Switch notificationState;
    private ImageButton addReminderButton;
    private ListView listReminder;

    private static final int LOAD_ALL_DATA = 100;

    protected ReminderCursorAdapter mAdapter;
    protected Uri mUri = null;

    private String[] mProjection;

    private String mSelection;

    private String[] mSelectionArgs;

    private String mSortOrder;

    private OnListItemSelectedListener mCallback;

    public interface OnListItemSelectedListener {
        void onListItemSelected(Uri uri, long id);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onListItemSelected(getContentUri(), id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnListItemSelectedListener)activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_reminder, container, false);
        setupView(layout);
        registerEvent();
        return layout;
    }

    private void registerEvent() {
        addReminderButton.setOnClickListener(this);
        notificationState.setOnClickListener(this);
        notificationState.setChecked(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUri = getContentUri();
        initializeQueryParams();
        mAdapter = new ReminderCursorAdapter(getActivity(), null, 0);
        listReminder.setAdapter(mAdapter);
        getLoaderManager().initLoader(LOAD_ALL_DATA, null, this);
        listReminder.setSelector(R.drawable.list_selector_ripple_effect);
        listReminder.setDivider(getListDividerDrawable());

        registerForContextMenu(listReminder);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_list_item, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Cursor plan = (Cursor) listReminder.getItemAtPosition(info.position);
        int columnHour = plan.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_HOUR);
        int timeHour = plan.getInt(columnHour);
        int columnMin = plan.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_MINUTE);
        int timeMinute = plan.getInt(columnMin);
        menu.setHeaderTitle(UiUtils.getTimeFormat(getActivity(), timeHour, timeMinute));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                // Because dispatchContextItemSelected to all fragment until receive true
                // so we perform deletion only while user visible hint
                if (getUserVisibleHint()) {
                    String where = PlanContract.PlanEntry._ID + " = ?";
                    String id = String.valueOf(info.id);
                    getActivity().getContentResolver().delete(getContentUri(), where, new String[]{id});
                    return true;
                }
            default:
                return super.onContextItemSelected(item);
        }
    }

    protected Drawable getListDividerDrawable() {
        Drawable divider;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            divider = getActivity().getDrawable(R.drawable.list_divider_default);
        } else {
            divider = getActivity().getResources().getDrawable(R.drawable.list_divider_default);
        }
        return divider;
    }

    protected void initializeQueryParams() {
        mProjection = null;
        mSelection = null;
        mSelectionArgs = null;
        mSortOrder = null;
    }

    protected Uri getContentUri() {
        return PlanContract.PlanEntry.CONTENT_URI;
    }

    private void setupView(View layout) {
        notificationState = (Switch) layout.findViewById(R.id.switch_notification);
        addReminderButton = (ImageButton) layout.findViewById(R.id.bt_add_plan);
        listReminder = (ListView) layout.findViewById(R.id.list_reminder);

        listReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onListItemSelected(PlanContract.PlanEntry.CONTENT_URI, id);

            }
        });

        addListViewBottomPadding(listReminder);
    }

    private void addListViewBottomPadding(ListView listView) {
        listView.setPadding(
                listView.getPaddingLeft(),
                listView.getPaddingTop(),
                listView.getPaddingRight(),
                getActivity().getResources().getDimensionPixelSize(R.dimen.list_item_height)
        );
        listView.setClipToPadding(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_plan:
                Toast.makeText(getActivity(), "add plan", Toast.LENGTH_SHORT).show();
                FragmentReminderPlanDetail fragment = FragmentReminderPlanDetail.newInstance(-1);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.switch_notification:
                Toast.makeText(getActivity(), "Switch noti", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        iLog.d(iLog.LogTag.UI, TAG + " onCreateLoader() id : " + id + " args : " + args);
        return new CursorLoader(getActivity(),
                mUri, mProjection, mSelection, mSelectionArgs, mSortOrder);

    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        iLog.d(iLog.LogTag.UI, TAG + " onLoadFinished() loader : " + loader + " data : " + data);
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        iLog.d(iLog.LogTag.UI, TAG + " onLoaderReset() loader : " + loader);
        mAdapter.swapCursor(null);
    }

}
