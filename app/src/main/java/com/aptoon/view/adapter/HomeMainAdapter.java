package com.aptoon.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.aptoon.entity.HomeModel;
import com.aptoon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class HomeMainAdapter extends RecyclerView.Adapter<HomeMainAdapter.MyViewHolder> {
    List<HomeModel.Category> list;
    Context context;
    HomeModel homeModel;

    public HomeMainAdapter(Context context, List<HomeModel.Category> list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_main_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<HomeModel.Video> list1 = new ArrayList<>();
        list1 = list.get(position).getVideo();
        holder.rvMainList.setAdapter(new MainAdapter(list1, context));

        holder.tvMainText.setText(list.get(position).getCategory_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainText;
        RecyclerView rvMainList;

        MyViewHolder(View view) {
            super(view);
            tvMainText = view.findViewById(R.id.tvMainText);
            rvMainList = view.findViewById(R.id.rvMainList);
        }
    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
        List<HomeModel.Video> listMain;

        Context context;

        MainAdapter(List<HomeModel.Video> listMain, Context context) {
            this.listMain = listMain;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_main_list_row_1, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String pic = "";
            if (listMain.get(position).getThumbnail() != null && !listMain.get(position).getThumbnail().isEmpty()) {
                pic = /*IMAGE_URL +*/ listMain.get(position).getThumbnail();

                Log.d("vihalsldfh", pic + "");
                try {

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);


                    Glide.with(context).load(pic).apply(options).into(holder.imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return listMain.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            MyViewHolder(View view) {

                super(view);
                imageView = view.findViewById(R.id.imageView1);

            }
        }
    }
}