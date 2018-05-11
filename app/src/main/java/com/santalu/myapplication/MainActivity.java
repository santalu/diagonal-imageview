package com.santalu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Button sampleGrid = findViewById(R.id.sample_grid);
    Button sampleList = findViewById(R.id.sample_list);
    Button sampleListComplex = findViewById(R.id.sample_list_complex);
    Button sampleCard = findViewById(R.id.sample_card);
    Button sampleCollapsing = findViewById(R.id.sample_collapsing);

    sampleGrid.setOnClickListener(v -> GridLayoutSampleActivity.start(MainActivity.this));
    sampleList.setOnClickListener(v -> RecyclerViewSampleActivity.start(MainActivity.this));
    sampleListComplex.setOnClickListener(v -> RecyclerViewComplexSampleActivity.start(MainActivity.this));
    sampleCard.setOnClickListener(v -> CardViewSampleActivity.start(MainActivity.this));
    sampleCollapsing.setOnClickListener(v -> CollapsingToolbarSampleActivity.start(MainActivity.this));
  }
}
