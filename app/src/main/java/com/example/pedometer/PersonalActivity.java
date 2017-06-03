package com.example.pedometer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;


public class PersonalActivity extends AppCompatActivity  implements View.OnClickListener,NumberPicker.OnValueChangeListener{
    public TextView mA_home;
    public TextView mA_sport;
    public LinearLayout mInformation;
    public ImageView head_portrait;
    public TextView mUserOtherNmae;
    public TextView userName;
    public TextView mSex;
    public TextView mAge;
    public TextView mBmi;
    public TextView mBody;
    public Button mCount;
    public TextView mSportSug;
    public Button mLoginOut;

    private PersonData personData;
    private SharedPreferencesUtils sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initViews();
        viewAddListener();
        sp = new SharedPreferencesUtils(this);
        initViewsShow();
}
    public void initViews(){
        mA_home = (TextView)findViewById(R.id.tv_home);
        mA_sport = (TextView)findViewById(R.id.tv_sport);
        mInformation =(LinearLayout)findViewById(R.id.ll_information);
        mUserOtherNmae = (TextView)findViewById(R.id.tv_userOtherName_show);
        userName = (TextView)findViewById(R.id.tv_username);
        head_portrait = (ImageView) findViewById(R.id.im_touxiang);
        mSex = (TextView)findViewById(R.id.tv_sex_show);
        mAge = (TextView)findViewById(R.id.tv_age_show);
        mBmi = (TextView)findViewById(R.id.tv_bmi);
        mBody = (TextView)findViewById(R.id.tv_body);
        mCount = (Button) findViewById(R.id.btn_count);
        mSportSug = (TextView)findViewById(R.id.tv_sport_sug);
        mLoginOut = (Button)findViewById(R.id.btn_login_out);
    }
    public void viewAddListener(){
        mA_home.setOnClickListener(this);
        mA_sport.setOnClickListener(this);
        mInformation.setOnClickListener(this);
        mCount.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);
    }
    public void initViewsShow(){
        String name = sp.getParam("username","").toString().trim();
        userName.setText(name);
        List<PersonData> personDatas = DataSupport.where("userName = ?",name).find(PersonData.class);
        personData = personDatas.get(0);
        try{
            mUserOtherNmae.setText(personData.getUserOtherName());
            mSex.setText(personData.getSex());
            if(personData.getSex().equals("男")){
                head_portrait.setImageResource(R.drawable.m_touxiang);
            }else{
                head_portrait.setImageResource(R.drawable.f_touxiang);
            }
            mAge.setText(String.valueOf(personData.getUserAge()));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(PersonalActivity.this,"出错",Toast.LENGTH_SHORT).show();
        }


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sport:
                Intent intent3 = new Intent(PersonalActivity.this, SportActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.tv_home:
                Intent intent4 = new Intent(PersonalActivity.this, MainActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.ll_information:
                Intent intent5 = new Intent(PersonalActivity.this,setInformationActivity.class);
                startActivity(intent5);
            case R.id.btn_count:
                count();
                break;
            case R.id.btn_login_out:
                sp.setParam("username","");
                Intent intent6 = new Intent(PersonalActivity.this,LoginActivity.class);
                startActivity(intent6);
                finish();
            default:
                break;
        }
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }
    public void count(){
        int age = personData.getUserAge();
        double weight = personData.getUserBodyWeight();
        double height = personData.getUserHeight();
        String sex =personData.getSex();
        double bmi = weight/((height*height)/10000);
        mBmi.setText(String.valueOf(Math.floor(bmi*10)/10));
        if(bmi <= 16.4){
            mBody.setText("极瘦");
        } else if(bmi<18.4){
            mBody.setText("偏瘦");
        }else if(bmi<24.9){
            mBody.setText("正常");
        }else if(bmi<29.9){
            mBody.setText("过重");
        }else{
            mBody.setText("肥胖");
        }
         if(sex.equals("男")){
             if(age >= 5 &&age <= 18){
                mSportSug.setText("短跑和跳跃,这两个种运动有助于青少年身体快速成长,提高人在无氧条件下的能力,提高大脑皮层兴奋和抑制交题的能力,使反应速度加快.");
             } else if(age>=19&&age<=40) {
                 if (bmi <= 24.9) {
                     mSportSug.setText("长跑，锻炼自己的心肺能力，提高心血管、呼吸神经等系统的功能，增强自己的抵抗能力，特别是对某些慢性疾病有很好的体疗作用");
                 }else{
                     mSportSug.setText("室内健身锻炼,进行肌肉力量训练,燃烧脂肪");
                 }
             }else if(age>40&&age<60){
                 if (bmi <= 24.9){
                     mSportSug.setText("健步走，提高心血管、呼吸神经等系统的功能，增强自己的抵抗能力，特别是对某些慢性疾病有很好的体疗作用");
                 }else{
                     mSportSug.setText("室内健身锻炼,进行肌肉力量训练,燃烧脂肪，注意饮食习惯");
                 }
             }else{
                 mSportSug.setText("散步，有助于血液的流通，增强自身免疫力");
             }
        }
        if(sex.equals("女")) {
            if(age >= 5 &&age <= 18){
                mSportSug.setText("短跑和跳跃,这两个种运动有助于青少年身体快速成长,提高人在无氧条件下的能力,提高大脑皮层兴奋和抑制交题的能力,使反应速度加快.");
            }
            if(age>=19&&age<=40) {
                if (bmi <= 24.9) {
                    mSportSug.setText("长跑，锻炼自己的心肺能力，提高心血管、呼吸神经等系统的功能，增强自己的抵抗能力，特别是对某些慢性疾病有很好的体疗作用");
                } else{
                    mSportSug.setText("室内健身锻炼,进行瑜伽等训练,燃烧脂肪，注意饮食习惯");
                }
            }if(age>40&&age<60){
                if (bmi <= 24.9){
                    mSportSug.setText("健步走，提高心血管、呼吸神经等系统的功能，增强自己的抵抗能力，特别是对某些慢性疾病有很好的体疗作用");
                }else{
                    mSportSug.setText("室内健身锻炼,进行瑜伽等训练,燃烧脂肪，控制饮食习惯");
                }
            }else{
                mSportSug.setText("散步，有助于血液的流通，增强自身免疫力");
            }
        }



    }




}
