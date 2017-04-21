package com.example.pedometer;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import java.text.DateFormat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.pedometer.SharedPreferencesUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetPlanActivity extends Activity implements View.OnClickListener{
    private SharedPreferencesUtils sp;
    static final String DEFAULT_TARGET = "7000";

    public ImageView backOut;//返回键
    private EditText et_plan_number;//每天锻炼目标步数
    private CheckBox cb_remind;//锻炼提醒开关按钮
    private TextView tv_remind_time;//锻炼提醒时间设置
    private Button btn_save;//设置保存

    private String stepNumberTarget;
    private String remind;
    private String remindTime;

    public void  assignViews(){
        backOut = (ImageView)findViewById(R.id.plan_back_out);
        et_plan_number =(EditText)findViewById(R.id.step_target);
        cb_remind = (CheckBox)findViewById(R.id.cb_remind);
        tv_remind_time = (TextView)findViewById(R.id.tv_remind_time);
        btn_save = (Button)findViewById(R.id.btn_save);
    }
    public void  addListener(){
        backOut.setOnClickListener(this);
        tv_remind_time.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.step_plan_exercise);
        assignViews();
        initDate();
        addListener();
    }

    public void initDate(){
        sp = new SharedPreferencesUtils(this);
        stepNumberTarget = (String)sp.getParam("stepNumberTarget","700");
        remind = (String)sp.getParam("remind","1");
        remindTime = (String)sp.getParam("remindTime","21:00");
        if (stepNumberTarget.isEmpty()||"0".equals(stepNumberTarget)){
                et_plan_number.setText(DEFAULT_TARGET);
        }else {
            et_plan_number.setText(stepNumberTarget);
        }
        if (!remind.isEmpty()){
            if ("0".equals(remind)){
                cb_remind.setChecked(false);
            }else {
                cb_remind.setChecked(true);
            }
        }
        if (!remindTime.isEmpty()){
            tv_remind_time.setText(remindTime);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.plan_back_out:
                finish();
                break;
            case R.id.tv_remind_time:
                showTimeDialog1();
                break;
            case R.id.btn_save:
                save();
                break;
            default:
                break;
        }
    }

    public void save(){
        stepNumberTarget = et_plan_number.getText().toString().trim();
        if (cb_remind.isChecked()){
            remind = "1";
        }else {
            remind = "0";
        }
        remindTime = tv_remind_time.getText().toString().trim();
        if (stepNumberTarget.isEmpty() || "0".equals(stepNumberTarget)){
            sp.setParam("stepNumberTarget","7000");
            this.stepNumberTarget = "7000";
        }else {
            sp.setParam("stepNumberTarget",stepNumberTarget);
        }
        sp.setParam("remind",remind);
        if (remindTime.isEmpty()){
            sp.setParam("remindTime","21:00");
            this.remindTime = "21:00";
        }else {
            sp.setParam("remindTime",remindTime);
        }
        finish();
    }

    private void showTimeDialog1() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        final DateFormat df = new SimpleDateFormat("HH:mm",Locale.CHINA);

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String remainTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                Date date = null;
                try {
                    date = df.parse(remainTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (null != date) {
                    calendar.setTime(date);
                }
                tv_remind_time.setText(df.format(date));
            }
        }, hour, minute, true).show();
    }
}
