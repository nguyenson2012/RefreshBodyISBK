package com.example.asus.refreshbody.utils.view;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableRow;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.utils.UiUtils;

public class DayButton extends CheckBox {

    private DayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private DayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private DayButton(Context context) {
        super(context);
    }

    public DayButton(Context context, int day) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setButtonDrawable(null);
        } else setButtonDrawable(new StateListDrawable());
        setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        setText(UiUtils.getDayString(context, day));
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        int textColorId = R.color.black;
        if (checked) {
            textColorId = R.color.blue;
        }
        setTextColor(ActivityCompat.getColor(getContext(), textColorId));
    }
}
