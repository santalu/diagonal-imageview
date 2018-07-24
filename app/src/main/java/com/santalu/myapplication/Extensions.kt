package com.santalu.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by fatih.santalu on 7/24/2018.
 */

fun ViewGroup.inflate(layoutRes: Int): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Context.toast(text: CharSequence) {
  Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}