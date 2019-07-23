package com.aptoon.view.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aptoon.utils.LocaleHelper;
import com.aptoon.controllers.Apis;
import com.aptoon.view.design.KProgressHUD;
import com.aptoon.utils.DirectoryHelper;
import com.aptoon.utils.DownloadSongService;
import com.aptoon.entity.VideoDetailsModel;
import com.aptoon.entity.VideoLikeModel;
import com.aptoon.R;
import com.aptoon.utils.UserSessionManager;
import com.aptoon.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sdsmdg.tastytoast.TastyToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    String video_id = "";
    Map<String, String> map;
    private RecyclerView rvRelated;
    private RecyclerView rvTrailerMore;
    private DetailsAdapter detailsAdapter;
    private TrailerMoreAdapter trailerMoreAdapter;
    private List<Integer> list;
    private TextView tvRelated;
    private TextView tvTrailerAndMore;
    private View viewRelated;
    private View viewTrailerAndMore;
    private ImageView expandedImage;
    private ImageView imMainDownload;
    private CollapsingToolbarLayout toolbar_layout;
    private AppBarLayout app_bar;
    private TextView tvvideotitie;
    private TextView tvdate;
    private TextView tvdis;
    private ImageView imAddToList;
    private ImageView imLike;
    private ImageView imShare;
    private ImageView imBack;
    UserSessionManager session;
    String ID;
    private static final String SONG_DOWNLOAD_PATH = "https://cloudup.com/files/inYVmLryD4p/download";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DirectoryHelper.createDirectory(this);
        initView();
        initList();
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        ID = user.get(session.KEY_ID);
        try {
            video_id = getIntent().getStringExtra("videoID");
            map = new HashMap<>();
            map.put("video_id", "78");
            map.put("user_id", "69");
//            map.put("video_id", video_id);
//           map.put("user_id", ID);
            GetVideoDetail(map);
        } catch (Exception e) {

        }

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
        //detailsAdapter.notifyDataSetChanged();
    }

    private void initView() {
        list = new ArrayList<>();
        tvRelated = findViewById(R.id.tvRelated);
        tvTrailerAndMore = findViewById(R.id.tvTrailerAndMore);
        viewRelated = findViewById(R.id.viewRelated);
        viewTrailerAndMore = findViewById(R.id.viewTrailerAndMore);
        rvRelated = findViewById(R.id.rvRelated);
        rvTrailerMore = findViewById(R.id.rvTrailerMore);


        tvRelated.setOnClickListener(this);
        tvTrailerAndMore.setOnClickListener(this);
        expandedImage = findViewById(R.id.expandedImage);
        expandedImage.setOnClickListener(this);
        imMainDownload = findViewById(R.id.imMainDownload);
        imMainDownload.setOnClickListener(this);
        toolbar_layout = findViewById(R.id.toolbar_layout);
        toolbar_layout.setOnClickListener(this);
        app_bar = findViewById(R.id.app_bar);
        app_bar.setOnClickListener(this);
        tvvideotitie = findViewById(R.id.tvvideotitie);
        tvvideotitie.setOnClickListener(this);
        tvdate = findViewById(R.id.tvdate);
        tvdate.setOnClickListener(this);
        tvdis = findViewById(R.id.tvdis);
        tvdis.setOnClickListener(this);
        imAddToList = findViewById(R.id.imAddToList);
        imAddToList.setOnClickListener(this);
        imLike = findViewById(R.id.imLike);
        imLike.setOnClickListener(this);
        imShare = findViewById(R.id.imShare);
        imShare.setOnClickListener(this);
        imBack = findViewById(R.id.imBack);
        imBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvRelated:
                viewRelated.setBackgroundColor(Color.parseColor("#FF0500"));
                viewTrailerAndMore.setBackgroundColor(0);
                rvRelated.setVisibility(View.VISIBLE);
                rvTrailerMore.setVisibility(View.GONE);
                break;

            case R.id.tvTrailerAndMore:
                viewRelated.setBackgroundColor(0);
                viewTrailerAndMore.setBackgroundColor(Color.parseColor("#FF0500"));
                rvRelated.setVisibility(View.GONE);
                rvTrailerMore.setVisibility(View.VISIBLE);
                break;

            case R.id.imLike:
                VideoLIke(map);
                break;

            case R.id.imAddToList:
                Addtolist(map);
                break;

        }
    }

    private void GetVideoDetail(Map<String, String> map) {
        final KProgressHUD hud = KProgressHUD.create(DetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<VideoDetailsModel> call = Apis.getAPIService().Video_details(map);
        call.enqueue(new Callback<VideoDetailsModel>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<VideoDetailsModel> call, Response<VideoDetailsModel> response) {
                hud.dismiss();
                // swipeRefreshLayout.setRefreshing(false);
                VideoDetailsModel videoDetailsModel = response.body();
                if (videoDetailsModel != null) {
                    if (videoDetailsModel.getStatus().equals("200")) {

                        tvdate.setText(videoDetailsModel.getData().getVideo().getDate());
                        tvdis.setText(videoDetailsModel.getData().getVideo().getDes());
                        tvvideotitie.setText(videoDetailsModel.getData().getVideo().getTitle());
                        String pic = "";
                        if (videoDetailsModel.getData().getVideo().getThumbnail() != null && !videoDetailsModel.getData().getVideo().getThumbnail().isEmpty()) {
                            pic = videoDetailsModel.getData().getVideo().getThumbnail();

                            try {
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.mipmap.ic_launcher_round)
                                        .error(R.mipmap.ic_launcher_round);
                                Glide.with(DetailActivity.this).load(pic).apply(options).into(expandedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (videoDetailsModel.getData().getRelated().size() != 0) {
                            List<VideoDetailsModel.Related> categoryRelated = new ArrayList<>();
                            categoryRelated.addAll(videoDetailsModel.getData().getRelated());
                            DetailsAdapter detailsAdapter = new DetailsAdapter(DetailActivity.this, categoryRelated);
                            rvRelated.setAdapter(detailsAdapter);
                            detailsAdapter.notifyDataSetChanged();
                        }
                        if (videoDetailsModel.getData().getTrailer().size() != 0) {
                            List<VideoDetailsModel.Trailer> categoryTrailer = new ArrayList<>();
                            categoryTrailer.addAll(videoDetailsModel.getData().getTrailer());
                            TrailerMoreAdapter trailerMoreAdapter = new TrailerMoreAdapter(DetailActivity.this, categoryTrailer);
                            rvTrailerMore.setAdapter(trailerMoreAdapter);
                            trailerMoreAdapter.notifyDataSetChanged();
                        }
                      /*   if (homeModel.getData().getNow_available().size() != 0) {
                            List<HomeModel.Now_available> categoryNow_available = new ArrayList<>();
                            categoryNow_available.addAll(homeModel.getData().getNow_available());
                            nowAvailableAdapter = new HomeFragment.NowAvailableAdapter(getActivity(), categoryNow_available);
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
                        }*/
                    }
                } else {
                    Log.d("response", "Something wents wrong");
                }
            }

            @Override
            public void onFailure(Call<VideoDetailsModel> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });
    }

    private void VideoLIke(Map<String, String> map) {
        final KProgressHUD hud = KProgressHUD.create(DetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<VideoLikeModel> call = Apis.getAPIService().VideoLike(map);
        call.enqueue(new Callback<VideoLikeModel>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<VideoLikeModel> call, Response<VideoLikeModel> response) {
                hud.dismiss();
                // swipeRefreshLayout.setRefreshing(false);
                VideoLikeModel videoLikeModel = response.body();
                if (videoLikeModel != null) {
                    if (videoLikeModel.getStatus().equals("200")) {
                        TastyToast.makeText(DetailActivity.this, videoLikeModel.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    }
                } else {
                    Log.d("response", "Something wents wrong");
                }
            }

            @Override
            public void onFailure(Call<VideoLikeModel> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });
    }

    private void Addtolist( Map<String, String> map) {
        final KProgressHUD hud = KProgressHUD.create(DetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<VideoLikeModel> call = Apis.getAPIService().VideoAddtoList(map);
        call.enqueue(new Callback<VideoLikeModel>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<VideoLikeModel> call, Response<VideoLikeModel> response) {
                hud.dismiss();
                // swipeRefreshLayout.setRefreshing(false);
                VideoLikeModel videoLikeModel = response.body();
                if (videoLikeModel != null) {
                    if (videoLikeModel.getStatus().equals("200")) {
                        TastyToast.makeText(DetailActivity.this, videoLikeModel.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    }
                } else {
                    Log.d("response", "Something wents wrong");
                }
            }

            @Override
            public void onFailure(Call<VideoLikeModel> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });
    }

    public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
        List<VideoDetailsModel.Related> categoryRelated;
        Context context;
        LayoutInflater inflater;


        DetailsAdapter(Context context, List<VideoDetailsModel.Related> categoryRelated) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.categoryRelated = categoryRelated;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.related_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            String pic = "";
            if (categoryRelated.get(position).getThumbnail() != null && !categoryRelated.get(position).getThumbnail().isEmpty()) {
                pic = /*IMAGE_URL +*/ categoryRelated.get(position).getThumbnail();
                try {

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);


                    Glide.with(context).load(pic).apply(options).into(holder.imMainThumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            holder.tvTitle.setText(categoryRelated.get(position).getTitle());
            holder.tvdis.setText(categoryRelated.get(position).getDes());
            holder.imDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.checkAndRequestPermissions(context);
                    startService(DownloadSongService.getDownloadService(context, SONG_DOWNLOAD_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                }
            });

        }
        @Override
        public int getItemCount() {
            return categoryRelated.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imMainThumbnail;
            private ImageView imMainDownload;
            private RelativeLayout relStart;
            private TextView tvTitle;
            private TextView tvType;
            private TextView tvdis;
            private ImageView imDownload;
            private LinearLayout relEnd;

            MyViewHolder(View view) {
                super(view);
                tvTitle = view.findViewById(R.id.tvTitle);
                imMainThumbnail = view.findViewById(R.id.imMainThumbnail);
                tvType = view.findViewById(R.id.tvType);
                tvdis = view.findViewById(R.id.tvdis);
                imDownload=view.findViewById(R.id.imDownload);
            }
        }
    }

    public class TrailerMoreAdapter extends RecyclerView.Adapter<TrailerMoreAdapter.MyViewHolder> {


        List<VideoDetailsModel.Trailer> categorytrailer;
        Context context;
        LayoutInflater inflater;

        TrailerMoreAdapter(Context context, List<VideoDetailsModel.Trailer> categorytrailer) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.categorytrailer = categorytrailer;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trailer_and_more_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String pic = "";
            if (categorytrailer.get(position).getThumbnail() != null && !categorytrailer.get(position).getThumbnail().isEmpty()) {
                pic = /*IMAGE_URL +*/ categorytrailer.get(position).getThumbnail();
                try {

                    RequestOptions options = new RequestOptions()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round);


                    Glide.with(context).load(pic).apply(options).into(holder.trailer_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return categorytrailer.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView trailer_image;

            MyViewHolder(View view) {
                super(view);
                trailer_image = view.findViewById(R.id.trailer_image);
            }
        }
    }
}