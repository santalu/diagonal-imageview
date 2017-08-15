package com.santalu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.santalu.diagonalimageview.DiagonalImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by santalu on 14/08/2017.
 */

public class RecyclerViewComplexSampleActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, RecyclerViewComplexSampleActivity.class));
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
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

    static class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_PAGER = 1;
        private static final int TYPE_REGULAR = 2;

        private final Context mContext;
        private final LayoutInflater mInflater;

        SampleAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_PAGER:
                    return new SampleAdapter.PagerHolder(mInflater.inflate(R.layout.item_view_pager, parent, false));
                case TYPE_REGULAR:
                default:
                    return new SampleAdapter.ViewHolder(mInflater.inflate(R.layout.item_recycler_view, parent, false));
            }
        }

        @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case TYPE_PAGER:
                    PagerHolder pagerHolder = (PagerHolder) holder;
                    pagerHolder.bind();
                    break;
                case TYPE_REGULAR:
                default:
                    ViewHolder viewHolder = (ViewHolder) holder;
                    viewHolder.bind(position);
                    break;
            }
        }

        @Override public int getItemCount() {
            return 20;
        }

        @Override public int getItemViewType(int position) {
            return position == 0 ? TYPE_PAGER : TYPE_REGULAR;
        }

        class PagerHolder extends RecyclerView.ViewHolder {
            ViewPager viewPager;

            PagerHolder(View itemView) {
                super(itemView);
                viewPager = itemView.findViewById(R.id.view_pager);
            }

            void bind() {
                viewPager.setAdapter(new SamplePagerAdapter(mContext));
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            DiagonalImageView image;

            ViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                image.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View v) {
                        Log.v(DiagonalImageView.TAG, getAdapterPosition() + " clicked");
                    }
                });
            }

            void bind(int position) {
                Picasso.with(mContext)
                        .load("https://media.gq.com/photos/561e9f0ca7113fb922cd6151/master/pass/gq-guide-to-suits-color-2015-03.jpg")
                        .placeholder(R.drawable.demo)
                        .error(R.drawable.demo)
                        .into(image);

                if (position > 1) {
                    image.setPosition(DiagonalImageView.TOP);
                } else {
                    image.setPosition(DiagonalImageView.NONE);
                }
            }
        }
    }

    static class SamplePagerAdapter extends PagerAdapter {
        private final Context mContext;

        SamplePagerAdapter(Context context) {
            mContext = context;
        }

        @Override public int getCount() {
            return 3;
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ScaleType.CENTER_CROP);

            Picasso.with(mContext)
                    .load("https://www.thefashionisto.com/wp-content/uploads/ama/2011/1/dknyspringcampaign1.jpg")
                    .placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .into(imageView);

            container.addView(imageView);
            return imageView;
        }

        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    static class OverlapItemDecoration extends RecyclerView.ItemDecoration {
        private int mOverlap;

        OverlapItemDecoration(int overlap) {
            mOverlap = overlap;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) > 1) {
                outRect.top = mOverlap;
            }
        }
    }
}
