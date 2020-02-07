package com.santalu.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.santalu.diagonalimageview.DiagonalImageView

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class SampleGridActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_grid)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  fun onImageClick(view: View) {
    if (view is DiagonalImageView) {
      toast("start ${view.start} end ${view.end} clicked")
    }
  }
}