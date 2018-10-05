package com.santalu.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.support.v7.widget.RecyclerView.State
import android.view.View
import android.view.ViewGroup
import com.santalu.widget.DiagonalImageView
import kotlinx.android.synthetic.main.activity_list.recyclerView
import kotlinx.android.synthetic.main.item_list.view.image

/**
 * Created by fatih.santalu on 7/24/2018.
 */

class SampleListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    with(recyclerView) {
      val overlap = resources.getDimensionPixelSize(R.dimen.overlap_size)
      addItemDecoration(OverlapItemDecoration(-overlap))
      setHasFixedSize(true)
      adapter = SampleAdapter()
    }
  }

  class SampleAdapter : RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    override fun getItemCount(): Int {
      return 20
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
      val view = parent.inflate(R.layout.item_list)
      return SampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
      with(holder.itemView) {
        image.position = if (position == 0) DiagonalImageView.NONE else DiagonalImageView.TOP
        setOnClickListener { context.toast("position $position clicked") }
      }
    }

    class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  }

  class OverlapItemDecoration(private val overlap: Int) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
      super.getItemOffsets(outRect, view, parent, state)
      if (parent.getChildAdapterPosition(view) > 0) {
        outRect.top = overlap
      }
    }
  }

  companion object {

    fun start(activity: Activity) {
      with(activity) {
        intent = Intent(activity, SampleListActivity::class.java)
        startActivity(intent)
      }
    }
  }
}