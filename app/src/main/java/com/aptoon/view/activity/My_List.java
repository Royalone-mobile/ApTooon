package com.aptoon.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.aptoon.utils.LocaleHelper;
import com.aptoon.controllers.Apis;
import com.aptoon.view.design.KProgressHUD;
import com.aptoon.entity.MyVideoListModel;
import com.aptoon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class My_List extends AppCompatActivity {

    private RecyclerView my_list;
    private My_List_Adapter My_List_Adapter;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__list);
        initView();
        GetMyListl("69");
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initView() {
        list = new ArrayList<>();
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
        my_list = findViewById(R.id.my_list);

    }

    private void GetMyListl(String id) {
        final KProgressHUD hud = KProgressHUD.create(My_List.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<MyVideoListModel> call = Apis.getAPIService().VideoPlaylist(id);
        call.enqueue(new Callback<MyVideoListModel>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<MyVideoListModel> call, Response<MyVideoListModel> response) {
                hud.dismiss();
                // swipeRefreshLayout.setRefreshing(false);
                MyVideoListModel myVideoListModel = response.body();
                if (myVideoListModel != null) {
                    if (myVideoListModel.getStatus().equals("200")) {

                        if (myVideoListModel.getData().size() != 0) {
                            List<MyVideoListModel.Data> mylist = new ArrayList<>();
                            mylist.addAll(myVideoListModel.getData());
                            My_List_Adapter my_list_adapter = new My_List_Adapter(My_List.this, mylist);
                            my_list.setAdapter(my_list_adapter);
                            my_list_adapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    Log.d("response", "Something wents wrong");
                }
            }

            @Override
            public void onFailure(Call<MyVideoListModel> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });
    }

    public class My_List_Adapter extends RecyclerView.Adapter<My_List_Adapter.MyViewHolder> {
        List<MyVideoListModel.Data> categoryRelated;
        Context context;
        LayoutInflater inflater;


        My_List_Adapter(Context context, List<MyVideoListModel.Data> categoryRelated) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.categoryRelated = categoryRelated;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_list_adapter_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            String pic = "";
            if (categoryRelated.get(position).getThumbnail() != null && !categoryRelated.get(position).getThumbnail().isEmpty()) {
                pic = /*IMAGE_URL +*/ categoryRelated.get(position).getThumbnail();
                try {

                    RequestOptions options = new RequestOptions()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);


                    Glide.with(context).load(pic).apply(options).into(holder.my_list_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            holder.my_list_title.setText(categoryRelated.get(position).getTitle());
            holder.my_list_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(My_List.this, DetailActivity.class).putExtra("videoID",categoryRelated.get(position).getId()+""));
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryRelated.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView my_list_image;
            private TextView my_list_title;

            MyViewHolder(View view) {
                super(view);
                my_list_image = view.findViewById(R.id.my_list_image);
                my_list_title = view.findViewById(R.id.my_list_title);
            }
        }
    }
}