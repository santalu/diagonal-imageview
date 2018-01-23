package com.santalu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.santalu.diagonalimageview.DiagonalImageView;

/**
 * Created by santalu on 7/18/17.
 */

public class RecyclerViewSampleActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, RecyclerViewSampleActivity.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int overlap = getResources().getDimensionPixelSize(R.dimen.overlap_size);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new OverlapItemDecoration(-overlap));
        recyclerView.setAdapter(new SampleAdapter(this));
    }

    static class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

        private final LayoutInflater inflater;

        SampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public SampleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SampleAdapter.ViewHolder(
                    inflater.inflate(R.layout.item_recycler_view, parent, false));
        }

        @Override public void onBindViewHolder(SampleAdapter.ViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override public int getItemCount() {
            return 20;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            DiagonalImageView image;

            ViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                image.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View v) {
                        Log.v("cmd", getAdapterPosition() + " clicked");
                    }
                });
            }

            void bind(int position) {
                if (position == 0) {
                    image.setPosition(DiagonalImageView.NONE);
                } else {
                    image.setPosition(DiagonalImageView.TOP);
                }
            }
        }
    }

    static class OverlapItemDecoration extends RecyclerView.ItemDecoration {

        private int overlap;

        OverlapItemDecoration(int overlap) {
            this.overlap = overlap;
        }

        @Override
        public void getItemOffsets(Rect outRect,
                View view,
                RecyclerView parent,
                RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = overlap;
            }
        }
    }
}
