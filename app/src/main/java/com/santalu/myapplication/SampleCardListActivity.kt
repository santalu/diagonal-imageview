package com.santalu.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_list.recyclerView

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class SampleCardListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = SampleAdapter()
  }

  class SampleAdapter : RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    override fun getItemCount(): Int {
      return 20
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
      val view = parent.inflate(R.layout.item_card_list)
      return SampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
      holder.itemView.setOnClickListener {
        it.context.toast("position $position clicked")
      }
    }

    class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  }

  companion object {

    fun start(activity: Activity) {
      val intent = Intent(activity, SampleCardListActivity::class.java)
      activity.startActivity(intent)
    }
  }

}