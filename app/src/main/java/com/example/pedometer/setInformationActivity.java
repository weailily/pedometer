package com.example.pedometer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class setInformationActivity extends Activity implements View.OnClickListener,NumberPicker.OnValueChangeListener {
    public ImageView mBackOut;
    public TextView mOtherName;
    public TextView mSex;
    public TextView mAge;
    public TextView mWeight;
    public TextView mHeight;
    public LinearLayout mOtherNameSet;
    public LinearLayout mSexSet;
    public LinearLayout mAgeSet;
    public LinearLayout mWeightSet;
    public LinearLayout mHeightSet;
    public Button mSave;


    public SharedPreferencesUtils sp;
    public PersonData data;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_information);
        initViews();
        viewAddListener();
        initViewShow();
}
    public void initViews() {
        mBackOut = (ImageView)findViewById(R.id.person_back_out) ;
        mOtherName =(TextView)findViewById(R.id.tv_other_name);
        mSex = (TextView) findViewById(R.id.tv_sex);
        mAge = (TextView) findViewById(R.id.tv_age);
        mWeight = (TextView) findViewById(R.id.tv_weight);
        mHeight = (TextView) findViewById(R.id.tv_height);
        mOtherNameSet = (LinearLayout)findViewById(R.id.tv_userOtherName);
        mSexSet = (LinearLayout)findViewById(R.id.tv_sex_set);
        mAgeSet= (LinearLayout)findViewById(R.id.tv_age_set);
        mWeightSet= (LinearLayout)findViewById(R.id.tv_weight_set);
        mHeightSet= (LinearLayout)findViewById(R.id.tv_height_set);
        mSave = (Button)findViewById(R.id.btn_save_person);

        sp = new SharedPreferencesUtils(this);
        String name = sp.getParam("username","").toString().trim();
        List<PersonData> personDatas = DataSupport.where("userName = ?",name).find(PersonData.class);
        data = personDatas.get(0);
    }

    public void viewAddListener() {
        mBackOut.setOnClickListener(this);
        mOtherNameSet.setOnClickListener(this);
        mSexSet.setOnClickListener(this);
        mAgeSet.setOnClickListener(this);
        mWeightSet.setOnClickListener(this);
        mHeightSet.setOnClickListener(this);
        mSave.setOnClickListener(this);
    }
    private void initViewShow(){
        mOtherName.setText(data.getUserOtherName());
        mSex.setText(data.getSex());
        mAge.setText(String.valueOf(data.getUserAge()));
        mHeight.setText(String.valueOf(data.getUserHeight()));
        mWeight.setText(String.valueOf(data.getUserBodyWeight()));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_back_out:
                Intent intent = new Intent(setInformationActivity.this,PersonalActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_userOtherName:
                userOtherNameEdit();
                break;
            case R.id.tv_sex_set:
                sexShow();
                break;
            case R.id.tv_age_set:
                ageShow();
                break;
            case R.id.tv_weight_set:
                weightShow();
                break;
            case R.id.tv_height_set:
                heightShow();
                break;
            case R.id.btn_save_person:
                mSave();
            default:
                break;
        }
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }
    public void userOtherNameEdit(){
       final  EditText editText= new EditText(this);
        editText.setText(mOtherName.getText().toString().trim());
        new AlertDialog.Builder(this)
                .setTitle("昵称")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s = editText.getText().toString().trim();
                            if(s.equals("")) {
                                Toast.makeText(setInformationActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }else {
                            mOtherName.setText(s);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    public void sexShow(){
        String[] Sexs = {"男","女"};
        new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(Sexs, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            mSex.setText("男");

                        }else{
                            mSex.setText("女");
                        }
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
    public void ageShow(){
        RelativeLayout relativeLayout = (RelativeLayout)getLayoutInflater().inflate(R.layout.dialog,null);
        final NumberPicker np1 = (NumberPicker) relativeLayout.findViewById(R.id.numberPicker);
        np1.setMaxValue(80);
        np1.setMinValue(5);
        np1.setValue(data.getUserAge());
        np1.setWrapSelectorWheel(false);
        np1.setOnValueChangedListener(this);
        new AlertDialog.Builder(this)
                .setTitle("年龄")
                .setView(relativeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAge.setText(String.valueOf(np1.getValue()));
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
    public void weightShow(){
        LinearLayout linearLayout= (LinearLayout)getLayoutInflater().inflate(R.layout.dialog1,null);
        final NumberPicker np1 = (NumberPicker) linearLayout.findViewById(R.id.numberPicker1);
        np1.setMaxValue(150);
        np1.setMinValue(40);
        np1.setValue((int)data.getUserBodyWeight());
        np1.setWrapSelectorWheel(false);
        np1.setOnValueChangedListener(this);
        final NumberPicker np2 = (NumberPicker) linearLayout.findViewById(R.id.numberPicker2);
        np2.setMaxValue(9);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        np2.setOnValueChangedListener(this);
        new AlertDialog.Builder(this)
                .setTitle("体重")
                .setView(linearLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s = String.valueOf(np1.getValue())+"."+String.valueOf(np2.getValue());
                        mWeight.setText(s);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
    public void heightShow() {
        LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog1,null);
        final NumberPicker np1 = (NumberPicker) linearLayout.findViewById(R.id.numberPicker1);
        np1.setMaxValue(200);
        np1.setMinValue(140);
        np1.setValue((int)data.getUserHeight());
        np1.setWrapSelectorWheel(false);
        np1.setOnValueChangedListener(this);
        final NumberPicker np2 = (NumberPicker) linearLayout.findViewById(R.id.numberPicker2);
        np2.setMaxValue(9);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);
        np2.setOnValueChangedListener(this);
        new AlertDialog.Builder(this)
                .setTitle("身高")
                .setView(linearLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s = String.valueOf(np1.getValue())+"."+String.valueOf(np2.getValue());
                        mHeight.setText(s);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
    public void mSave(){
        String  otherName = mOtherName.getText().toString().trim();
        String  sex = mSex.getText().toString().trim();
        String  age = mAge.getText().toString().trim();
        String  weight = mWeight.getText().toString().trim();
        String  height = mHeight.getText().toString().trim();

        data.setUserOtherName(otherName);
        data.setSex(sex);
        data.setUserAge(Integer.valueOf(age));
        data.setUserBodyWeight(Double.valueOf(weight));
        data.setUserHeight(Double.valueOf(height));
        data.save();
        Toast.makeText(setInformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

    }


}

