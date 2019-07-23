package com.aptoon.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aptoon.utils.LocaleHelper;
import com.aptoon.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
public class My_Coins extends AppCompatActivity {
    private RecyclerView recycler_view;
    private MyCoinsAdapter myCoinsAdapter;
    private List<Integer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__coins);
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
        myCoinsAdapter.notifyDataSetChanged();
    }

    private void initView() {
        list = new ArrayList<>();
        recycler_view = findViewById(R.id.recycler_view);

        myCoinsAdapter = new MyCoinsAdapter();
        recycler_view.setAdapter(myCoinsAdapter);
    }

    public class MyCoinsAdapter extends RecyclerView.Adapter<MyCoinsAdapter.MyViewHolder> {


        MyCoinsAdapter() {
        }

        @Override
        public MyCoinsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mycoins_list_row, parent, false);

            return new MyCoinsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyCoinsAdapter.MyViewHolder holder, int position) {
            switch (list.get(position)) {
//                case 0:
//                    holder.imMainDownload.setVisibility(View.GONE);
//                    holder.imDownload.setImageResource(R.drawable.download);
//                    holder.tvDownloadStatus.setText("");
//                    break;
//                case 1:
//                    holder.imMainDownload.setVisibility(View.VISIBLE);
//                    holder.imMainDownload.setImageResource(R.drawable.download_o);
//                    holder.imMainDownload.setPadding(12, 12, 12, 12);
//                    holder.imDownload.setImageResource(R.drawable.download);
//                    holder.tvDownloadStatus.setText("Download Failed");
//                    break;
//                case 2:
//                    holder.imMainDownload.setVisibility(View.VISIBLE);
//                    holder.imMainDownload.setImageResource(R.drawable.play_circle);
//                    holder.imDownload.setImageResource(R.drawable.downlo_complate);
//                    holder.tvDownloadStatus.setText("");
//                    break;
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

//            ImageView imMainThumbnail;
//            ImageView imMainDownload;
//            ImageView imDownload;
//            TextView tvTitle;
//            TextView tvType;
//            TextView tvDownloadStatus;
//            TextView tvDownload1;

            MyViewHolder(View view) {
                super(view);
//                imMainThumbnail = view.findViewById(R.id.imMainThumbnail);
//                imMainDownload = view.findViewById(R.id.imMainDownload);
//                imDownload = view.findViewById(R.id.imDownload);
//                tvTitle = view.findViewById(R.id.tvTitle);
//                tvType = view.findViewById(R.id.tvType);
//                tvDownloadStatus = view.findViewById(R.id.tvDownloadStatus);
//                tvDownload1 = view.findViewById(R.id.tvDownload1);
            }
        }
    }
}