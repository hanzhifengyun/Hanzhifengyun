package com.hanzhifengyun.base.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.hanzhifengyun.base.R;

import java.util.Calendar;
import java.util.Date;


/**
 * 时间选择器
 */

public class DateTimePickDialog implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
    private Context mContext;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Calendar mCalendar;
    private AlertDialog mAlertDialog;

    private DateChangedCallBack mDateChangedCallBack;

    private boolean isTimePickerAvailable = false;


    public DateTimePickDialog(Context context, DateChangedCallBack dateChangedCallBack) {
        this(context, null, dateChangedCallBack);
    }

    public DateTimePickDialog(Context context, Date date, DateChangedCallBack dateChangedCallBack) {
        this.mContext = context;
        this.mDateChangedCallBack = dateChangedCallBack;
        mCalendar = Calendar.getInstance();
        if (date != null) {
            mCalendar.setTime(date);
        }
        initDatePicker();
    }

    private void initDatePicker() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_datepicker, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.dp_date);
        mTimePicker = (TimePicker) view.findViewById(R.id.tp_date);

        mDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
        mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(this);
        mAlertDialog = new AlertDialog.Builder(mContext)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mDateChangedCallBack != null) {
                            mDateChangedCallBack.onDateChanged(mCalendar.getTime());
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    public void setTimePickerAvailable(boolean isTimePickerAvailable) {
        this.isTimePickerAvailable = isTimePickerAvailable;
        if (isTimePickerAvailable) {
            mTimePicker.setVisibility(View.VISIBLE);
        } else {
            mTimePicker.setVisibility(View.GONE);
        }
    }

    public void setIs24HourView(boolean is24Hour) {
        mTimePicker.setIs24HourView(is24Hour);
    }


    public void show() {
        if (mAlertDialog != null) {
            mAlertDialog.show();
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(year, monthOfYear, dayOfMonth);
    }


    /**
     * @param view      The view associated with this listener.
     * @param hourOfDay The current hour.
     * @param minute    The current minute.
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
    }



    public interface DateChangedCallBack {
        void onDateChanged(Date date);
    }

    public void setCurrentDate(Date date) {
        mCalendar.setTime(date);
        updateDate();
    }

    private void updateDate() {
        mDatePicker.updateDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
    }

    /**
     * 设置最大日期
     *
     * @param maxDate 最大日期毫秒数
     */
    public void setMaxDate(long maxDate) {
        mDatePicker.setMaxDate(maxDate);
    }

    /**
     * 设置最大日期
     *
     * @param maxDate 最大日期
     */
    public void setMaxDate(Date maxDate) {
        setMaxDate(maxDate.getTime());
    }

    /**
     * 设置最小日期
     *
     * @param minDate 最小日期毫秒数
     */
    public void setMinDate(long minDate) {
        long currentDate = new Date().getTime();
        if (currentDate <= minDate) {
            mDatePicker.setMinDate(currentDate - 1000);
        } else {
            mDatePicker.setMinDate(minDate);
        }

    }

    /**
     * 设置最小日期
     *
     * @param minDate 最小日期
     */
    public void setMinDate(Date minDate) {
        setMinDate(minDate.getTime());
    }
}
