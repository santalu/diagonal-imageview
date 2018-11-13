package com.santalu.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class SampleGridActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_grid)
  }

  fun onImageClick(view: View) {
    toast("clicked")
  }

  companion object {

    fun start(activity: Activity) {
      with(activity) {
        intent = Intent(this, SampleGridActivity::class.java)
        startActivity(intent)
      }
    }
  }
}