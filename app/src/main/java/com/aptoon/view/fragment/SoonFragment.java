package com.aptoon.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aptoon.controllers.Apis;
import com.aptoon.view.design.KProgressHUD;
import com.aptoon.entity.Soon;
import com.aptoon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoonFragment extends Fragment {

    private RecyclerView soon_list;
    private Soon_List_Adapter Soon_List_Adapter;
    private List<Soon.Data> list;
  private  TextView no_soon_found;
  private  SwipeRefreshLayout pullToRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_soon, container, false);
        initView(view);
        Soon();
        return view;
    }
    private void initView(View view) {
        //list = new ArrayList<>();
//        list.add(1);
//        list.add(0);
//        list.add(2);
//        list.add(1);
//        list.add(0);
//        list.add(2);
//        list.add(1);
//        list.add(0);
//        list.add(2);
//        list.add(1);
        pullToRefresh = (SwipeRefreshLayout)view.findViewById(R.id.pullToRefresh);
        soon_list = view.findViewById(R.id.soon_list);
        no_soon_found=view.findViewById(R.id.no_soon_found);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Soon();
            }
        });

    }
    private void Soon() {
        final KProgressHUD hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<Soon> call = Apis.getAPIService().getSoon();
        call.enqueue(new Callback<Soon>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<Soon> call, Response<Soon> response) {
                hud.dismiss();
                pullToRefresh.setRefreshing(false);
                Soon soondata=response.body();
                if(soondata.getData().isEmpty()){
                    no_soon_found.setVisibility(View.VISIBLE);
                    no_soon_found.setText(soondata.getMessage());
                }else {
                    list = new ArrayList<>();
                    list.addAll(soondata.getData());
                    Soon_List_Adapter = new Soon_List_Adapter(getActivity());
                    soon_list.setAdapter(Soon_List_Adapter);
                    Soon_List_Adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Soon> call, Throwable t) {
                hud.dismiss();
                pullToRefresh.setRefreshing(false);
                Log.d("response", "vv" + t.getMessage());
            }
        });

    }
    public class Soon_List_Adapter extends RecyclerView.Adapter<Soon_List_Adapter.MyViewHolder> {
        Context context;
        LayoutInflater inflater;

        Soon_List_Adapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.soon_adapter_list, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.from_date.setText(list.get(position).getDate());
                holder.movie_title.setText( Html.fromHtml(list.get(position).getTitle()));
               holder.movie_descriptions.setText(Html.fromHtml(list.get(position).getDes()));
                       RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.soon_image)
                        .error(R.drawable.soon_image);
            Glide.with(context).load(list.get(position).getThumbnail()).apply(options).into(holder.soon_thumbnail);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView from_date;
            LinearLayout notification_section;
            TextView movie_title;
            TextView movie_descriptions;
            ImageView soon_thumbnail;


            MyViewHolder(View view) {
                super(view);
                from_date = view.findViewById(R.id.from_date);
                movie_title = view.findViewById(R.id.movie_title);
                movie_descriptions = view.findViewById(R.id.movie_descriptions);
                soon_thumbnail=view.findViewById(R.id.soon_thumbnail);
            }
        }
    }
}
