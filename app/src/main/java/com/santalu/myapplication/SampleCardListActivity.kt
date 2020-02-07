package com.santalu.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.santalu.myapplication.SampleCardListActivity.SampleAdapter.SampleViewHolder
import kotlinx.android.synthetic.main.activity_list.recyclerView

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class SampleCardListActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    recyclerView.apply {
      setHasFixedSize(true)
      adapter = SampleAdapter()
    }
  }

  class SampleAdapter: Adapter<SampleViewHolder>() {

    override fun getItemCount(): Int = 20

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
      val view = parent.inflate(R.layout.item_card_list)
      return SampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
      with(holder.itemView) {
        setOnClickListener { context.toast("position $position clicked") }
      }
    }

    class SampleViewHolder(itemView: View): ViewHolder(itemView)
  }
}