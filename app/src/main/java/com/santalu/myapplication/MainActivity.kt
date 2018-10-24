package com.santalu.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.cardListSample
import kotlinx.android.synthetic.main.activity_main.gridSample
import kotlinx.android.synthetic.main.activity_main.listSample

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    listSample.setOnClickListener { SampleListActivity.start(this) }
    cardListSample.setOnClickListener { SampleCardListActivity.start(this) }
    gridSample.setOnClickListener { SampleGridActivity.start(this) }
  }
}
