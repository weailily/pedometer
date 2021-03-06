package com.example.pedometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HistoryActivity extends Activity {
    public ImageView backOut;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.step_history);
        backOut = (ImageView) findViewById(R.id.history_back_out);
        listView = (ListView) findViewById(R.id.lv_history);
        backOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<StepData> stepDataList = DataSupport.findAll(StepData.class);
        int n = stepDataList.size();
        int i = 0;
        final String[] stepData_ToString = new String[n];
        for (StepData stepData : stepDataList) {
            stepData_ToString[i++] = stepData.toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoryActivity.this, android.R.layout.simple_list_item_1, stepData_ToString);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = stepData_ToString[i].substring(6,16);
                Intent intent = new Intent(HistoryActivity.this,HistoryShowActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }

}
