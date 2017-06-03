package com.example.pedometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class HistoryShowActivity extends AppCompatActivity {

    public StepArcView stepArcView ;
    public TextView distance;
    public TextView energy;

    public SharedPreferencesUtils sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_show);
        stepArcView = (StepArcView)findViewById(R.id.step_arc_view_history);
        distance = (TextView)findViewById(R.id.tv_distance_history);
        energy = (TextView)findViewById(R.id.tv_energy_history);
        sp = new SharedPreferencesUtils(this);
        String  stepNumberTarget = (String)sp.getParam("stepNumberTarget","700");

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        List<StepData> StepDatas = DataSupport.where("today = ?",date).find(StepData.class);
        if(StepDatas!=null) {
            StepData data = StepDatas.get(0);
            stepArcView.setCurrentCount(Integer.parseInt(stepNumberTarget),Integer.parseInt(data.getStepCount()));
            distance.setText(String.valueOf(data.getDistance()));
            energy.setText(String.valueOf(data.getEnergy()));
        }
    }
}
