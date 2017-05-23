package com.example.pedometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class PersonalActivity extends AppCompatActivity  implements View.OnClickListener {
    public TextView mA_home;
    public TextView mA_sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initViews();
        viewAddListener();
    }
    public void initViews(){
        mA_home = (TextView)findViewById(R.id.tv_home);
        mA_sport = (TextView)findViewById(R.id.tv_sport);
    }
    public void viewAddListener(){
        mA_home.setOnClickListener(this);
        mA_sport.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_sport:
                Intent intent3 = new Intent(PersonalActivity.this, SportActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_home:
                Intent intent4 = new Intent(PersonalActivity.this, MainActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
}
