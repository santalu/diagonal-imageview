package com.santalu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sampleGrid = findViewById(R.id.sample_grid);
        Button sampleList = findViewById(R.id.sample_list);
        Button sampleCard = findViewById(R.id.sample_card);
        Button sampleCollapsing = findViewById(R.id.sample_collapsing);

        sampleGrid.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                GridLayoutSampleActivity.start(MainActivity.this);
            }
        });

        sampleList.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                RecyclerViewSampleActivity.start(MainActivity.this);
            }
        });

        sampleCard.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                CardViewSampleActivity.start(MainActivity.this);
            }
        });

        sampleCollapsing.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                CollapsingToolbarSampleActivity.start(MainActivity.this);
            }
        });
    }
}
