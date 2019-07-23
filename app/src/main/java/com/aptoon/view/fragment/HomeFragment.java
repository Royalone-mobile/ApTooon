package com.aptoon.view.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aptoon.view.activity.DetailActivity;
import com.aptoon.view.activity.VideoPlayerActivity;
import com.aptoon.view.adapter.HomeMainAdapter;
import com.aptoon.controllers.Apis;
import com.aptoon.view.design.KProgressHUD;
import com.aptoon.entity.HomeModel;
import com.aptoon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener {
    public HomeMainAdapter homeMainAdapter;
    private RecyclerView rvPreview;
    private RecyclerView rvNowAvailable;
    private RecyclerView rvMainHome;
    private RecyclerView category_fillter;
    private PreviewAdapter previewAdapter;
    private NowAvailableAdapter nowAvailableAdapter;
    private Category_fillter_top_Adapter Category_fillter_top_Adapter;
    private List<String> list;
    private List<String> listMain;
    private List<String> category_list_name;
    private ImageView imRightMenu;
    private LinearLayout liRightMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    Map<String, String> map;
    private ImageView ivupperbanner;
    private TextView tvbannertitle;
    private TextView tvBannerdis;
    private ImageView imAddToList;
    private LinearLayout lladdtolist;
    private ImageView imLike;
    private LinearLayout llplay;
    private ImageView imShare;
    private LinearLayout llinfo;
    private String home_banner_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
      //  initList();
        map = new HashMap<>();
        map.put("preview", "{\"video_id\":\"78,79\"}");
        map.put("category_id", "");
        GetHomeData(map);
        return view;
    }


    private void initList() {
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
        listMain.add("Popular on apToon");
        listMain.add("New on apToon");
        category_list_name.add("Top Movie");
        category_list_name.add("Top Movie");
        category_list_name.add("Top Movie");
        category_list_name.add("Top Movie");
        category_list_name.add("Top Movie");
        category_list_name.add("Top Movie");
        category_list_name.add("Top Movie");
//        previewAdapter.notifyDataSetChanged();
      //  nowAvailableAdapter.notifyDataSetChanged();
      //  homeMainAdapter.notifyDataSetChanged();
       // Category_fillter_top_Adapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        list = new ArrayList<>();
        listMain = new ArrayList<>();
        category_list_name = new ArrayList<>();
        swipeRefreshLayout=view.findViewById(R.id.container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                GetHomeData(map);
            }
        });
        rvPreview = view.findViewById(R.id.rvPreview);
        category_fillter = view.findViewById(R.id.category_fillter);
        rvNowAvailable = view.findViewById(R.id.rvNowAvailable);
        rvMainHome = view.findViewById(R.id.rvMainHome);
        imRightMenu = view.findViewById(R.id.imRightMenu);
        liRightMenu = view.findViewById(R.id.liRightMenu);
        imRightMenu.setOnClickListener(this);
        ivupperbanner = view.findViewById(R.id.ivupperbanner);
        ivupperbanner.setOnClickListener(this);
        tvbannertitle = view.findViewById(R.id.tvbannertitle);
        tvbannertitle.setOnClickListener(this);
        tvBannerdis = view.findViewById(R.id.tvBannerdis);
        tvBannerdis.setOnClickListener(this);
        imAddToList = view.findViewById(R.id.imAddToList);
        imAddToList.setOnClickListener(this);
        lladdtolist = view.findViewById(R.id.lladdtolist);
        lladdtolist.setOnClickListener(this);
        imLike = view.findViewById(R.id.imLike);
        imLike.setOnClickListener(this);
        llplay = view.findViewById(R.id.llplay);
        llplay.setOnClickListener(this);
       // imShare = view.findViewById(R.id.imShare);
        //imShare.setOnClickListener(this);
        llinfo = view.findViewById(R.id.home_info);
        llinfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imRightMenu:
                if (liRightMenu.getVisibility() == View.GONE) {
                    liRightMenu.setVisibility(View.VISIBLE);
                    imRightMenu.setImageResource(R.drawable.falter_a);
                } else {
                    liRightMenu.setVisibility(View.GONE);
                    imRightMenu.setImageResource(R.drawable.falter);
                }
                break;
            case R.id.home_info:
                startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("videoID",home_banner_id));
                break;
            case R.id.llplay:
                startActivity(new Intent(getActivity(), VideoPlayerActivity.class));
                break;
            default:
                break;

        }
    }

    private void GetHomeData(Map<String, String> map) {
        final KProgressHUD hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<HomeModel> call = Apis.getAPIService().GetAllHomeData(map);
        call.enqueue(new Callback<HomeModel>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                hud.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                HomeModel homeModel = response.body();
                if (homeModel != null) {
                    if (homeModel.getStatus().equals("200")) {
                        String pic = "";
                        if (homeModel.getData().getBanner().getThumbnail() != null && !homeModel.getData().getBanner().getThumbnail().isEmpty()) {
                            pic =/* IMAGE_URL + */homeModel.getData().getBanner().getThumbnail();
                            tvbannertitle.setText(homeModel.getData().getBanner().getTitle());
                            tvBannerdis.setText(homeModel.getData().getBanner().getDes());
                             home_banner_id=""+homeModel.getData().getBanner().getId();
                            try {
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.mipmap.ic_launcher_round)
                                        .error(R.mipmap.ic_launcher_round);
                                Glide.with(getActivity()).load(pic).apply(options).into(ivupperbanner);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (homeModel.getData().getPreview().size() != 0) {
                            List<HomeModel.Preview> categoryPreview = new ArrayList<>();
                            categoryPreview.addAll(homeModel.getData().getPreview());
                            previewAdapter = new PreviewAdapter(getActivity(), categoryPreview);
                            rvPreview.setAdapter(previewAdapter);
                            previewAdapter.notifyDataSetChanged();
                        }
                        if (homeModel.getData().getFilter().size() != 0) {

                            List<HomeModel.Filter> categoryFilter = new ArrayList<>();
                            categoryFilter.addAll(homeModel.getData().getFilter());
                            Category_fillter_top_Adapter = new Category_fillter_top_Adapter(getActivity(), categoryFilter);
                            category_fillter.setAdapter(Category_fillter_top_Adapter);
                            Category_fillter_top_Adapter.notifyDataSetChanged();
                        }
                        if (homeModel.getData().getNow_available().size() != 0) {
                            List<HomeModel.Now_available> categoryNow_available = new ArrayList<>();
                            categoryNow_available.addAll(homeModel.getData().getNow_available());
                            nowAvailableAdapter = new NowAvailableAdapter(getActivity(), categoryNow_available);
                            rvNowAvailable.setAdapter(nowAvailableAdapter);
                            nowAvailableAdapter.notifyDataSetChanged();
                        }
                        if (homeModel.getData().getCategory().size() != 0) {
                            List<HomeModel.Category> categoryCategory = new ArrayList<>();
                            categoryCategory.addAll(homeModel.getData().getCategory());
                            homeMainAdapter = new HomeMainAdapter(getActivity(), categoryCategory);
                            rvMainHome.setAdapter(homeMainAdapter);
                            homeMainAdapter.notifyDataSetChanged();
                        } else {
                            View parentLayout = getActivity().findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "There is no Video Found", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                    }
                }else {
                    Log.d("response", "Something wents wrong");
                }
            }
            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });
    }

    //done
    public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.MyViewHolder> {
        List<HomeModel.Preview> categoryPreview;
        Context context;
        LayoutInflater inflater;

        PreviewAdapter(Context context, List<HomeModel.Preview> categoryPreview) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.categoryPreview = categoryPreview;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.preview_list_row, null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            String pic = "";
            if (categoryPreview.get(position).getThumbnail() != null && !categoryPreview.get(position).getThumbnail().isEmpty()) {
                pic = /*IMAGE_URL +*/ categoryPreview.get(position).getThumbnail();
                try {

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);

                    Glide.with(getActivity()).load(pic).apply(options).into(holder.imageView1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("videoID",categoryPreview.get(position).getId()+""));
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryPreview.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView1;

            MyViewHolder(View view) {
                super(view);
                imageView1 = view.findViewById(R.id.imageView1);
            }
        }
    }

    //done
    public class NowAvailableAdapter extends RecyclerView.Adapter<NowAvailableAdapter.MyViewHolder> {
        List<HomeModel.Now_available> categoryNow_available;
        Context context;
        LayoutInflater inflater;
        NowAvailableAdapter(Context context, List<HomeModel.Now_available> categoryNow_available) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.categoryNow_available = categoryNow_available;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_available_list_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String pic = "";
            if (categoryNow_available.get(position).getThumbnail() != null && !categoryNow_available.get(position).getThumbnail().isEmpty()) {
                pic = /*IMAGE_URL + */categoryNow_available.get(position).getThumbnail();

                try {

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);

                    Glide.with(getActivity()).load(pic).apply(options).into(holder.now_available_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return categoryNow_available.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView now_available_image;
            MyViewHolder(View view) {
                super(view);
                now_available_image = view.findViewById(R.id.now_available_image);

            }
        }
    }

    //done
    public class Category_fillter_top_Adapter extends RecyclerView.Adapter<Category_fillter_top_Adapter.MyViewHolder> {
        List<HomeModel.Filter> categoryFilter;
        HomeModel.Filter all;
        Context context;
        LayoutInflater inflater;

        Category_fillter_top_Adapter(Context context, List<HomeModel.Filter> categoryFilter) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.categoryFilter = categoryFilter;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_fillter_adapter, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            if (categoryFilter.get(position).getCategory_title() != null && !categoryFilter.get(position).getCategory_title().isEmpty()) {

                    holder.category_name_fillter.setText(categoryFilter.get(position).getCategory_title());



            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.put("category_id", String.valueOf(categoryFilter.get(position).getCategory_id()));
                    GetHomeData(map);
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryFilter.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView category_name_fillter;

            MyViewHolder(View view) {
                super(view);
                category_name_fillter = view.findViewById(R.id.category_name_fillter);
            }
        }

    }

}
