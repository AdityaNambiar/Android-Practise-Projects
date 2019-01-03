package com.example.aditya.customdialogpractice;

import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ListView subFactors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> list = new ArrayList<String>(
                Arrays.asList(this.getResources().getStringArray(R.array.subfactors))
        );

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_lv, list);
        subFactors = (ListView) findViewById(R.id.lv);
        subFactors.setAdapter(adapter);

        subFactors.setOnItemClickListener(this);
    }

    public void showDialog(View v) {
        CustomListDialog customDialog = new CustomListDialog();
        customDialog.show(getFragmentManager(),"customDialog");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(view);
    }
}
