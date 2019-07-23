package com.aptoon.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aptoon.utils.LocaleHelper;
import com.aptoon.R;

import java.util.ArrayList;
import java.util.List;

public class Downloads extends AppCompatActivity {

    private RecyclerView recycler_view;
    private DownloadsAdapter downloadsAdapter;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);
        initView();
        initList();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initList() {
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(2);
        list.add(1);
        downloadsAdapter.notifyDataSetChanged();
    }

    private void initView() {
        list = new ArrayList<>();
        recycler_view = findViewById(R.id.recycler_view);
        downloadsAdapter = new DownloadsAdapter();
        recycler_view.setAdapter(downloadsAdapter);
    }

    public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.MyViewHolder> {

        int lastPosition = -1;

        DownloadsAdapter() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.download_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            switch (list.get(position)) {
                case 0:
                    holder.imMainDownload.setVisibility(View.GONE);
                    holder.imDownload.setImageResource(R.drawable.download);
                    holder.tvDownloadStatus.setText("");
                    break;
                case 1:
                    holder.imMainDownload.setVisibility(View.VISIBLE);
                    holder.imMainDownload.setImageResource(R.drawable.download_o);
                    holder.imMainDownload.setPadding(12, 12, 12, 12);
                    holder.imDownload.setImageResource(R.drawable.download);
                    holder.tvDownloadStatus.setText("Download Failed");
                    break;
                case 2:
                    holder.imMainDownload.setVisibility(View.VISIBLE);
                    holder.imMainDownload.setImageResource(R.drawable.play_circle);
                    holder.imDownload.setImageResource(R.drawable.downlo_complate);
                    holder.tvDownloadStatus.setText("");
                    break;
            }




            Animation animation = AnimationUtils.loadAnimation(Downloads.this,
                    (position > lastPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.itemView.clearAnimation();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imMainThumbnail;
            ImageView imMainDownload;
            ImageView imDownload;
            TextView tvTitle;
            TextView tvType;
            TextView tvDownloadStatus;
            TextView tvDownload1;

            MyViewHolder(View view) {
                super(view);
                imMainThumbnail = view.findViewById(R.id.imMainThumbnail);
                imMainDownload = view.findViewById(R.id.imMainDownload);
                imDownload = view.findViewById(R.id.imDownload);
                tvTitle = view.findViewById(R.id.tvTitle);
                tvType = view.findViewById(R.id.tvType);
                tvDownloadStatus = view.findViewById(R.id.tvDownloadStatus);
                tvDownload1 = view.findViewById(R.id.tvDownload1);
            }
        }
    }

}