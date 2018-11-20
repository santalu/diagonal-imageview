package com.santalu.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.santalu.diagonalimageview.DiagonalImageView

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class SampleGridActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_grid)
  }

  fun onImageClick(view: View) {
    if (view is DiagonalImageView) {
      toast("start ${view.start} end ${view.end} clicked")
    }
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