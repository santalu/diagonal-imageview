package com.santalu.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.cardListSample
import kotlinx.android.synthetic.main.activity_main.gridSample
import kotlinx.android.synthetic.main.activity_main.listSample

class MainActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    listSample.setOnClickListener {
      startActivity(Intent(this, SampleListActivity::class.java))
    }
    cardListSample.setOnClickListener {
      startActivity(Intent(this, SampleCardListActivity::class.java))
    }
    gridSample.setOnClickListener {
      startActivity(Intent(this, SampleGridActivity::class.java))
    }
  }
}

internal fun ViewGroup.inflate(layoutRes: Int): View =
  LayoutInflater.from(context).inflate(layoutRes, this, false)

internal fun Context.toast(text: CharSequence) =
  Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
