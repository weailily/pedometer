package com.example.pedometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class SportActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView mA_home;
    public TextView mA_information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        initViews();
        viewAddListener();

    }
    public void initViews(){
        mA_home = (TextView)findViewById(R.id.tv_home);
        mA_information = (TextView)findViewById(R.id.tv_info);
    }
    public void viewAddListener(){
        mA_home.setOnClickListener(this);
        mA_information.setOnClickListener(this);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_info:
                Intent intent3 = new Intent(SportActivity.this,PersonalActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.tv_home:
                Intent intent4 = new Intent(SportActivity.this, MainActivity.class);
                startActivity(intent4);
                finish();
                break;
            default:
                break;
        }
    }
}
