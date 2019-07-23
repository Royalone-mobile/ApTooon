package com.aptoon.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.aptoon.view.activity.DetailActivity;
import com.aptoon.controllers.Apis;
import com.aptoon.entity.Search_Data;
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

public class SearchFragment extends Fragment {


    private String search;
    private List<Search_Data.Data> image;
    Search_adapter search_adpter;
    private ImageView search_icon;
    private ImageView cancel;
    private EditText edit_search;
    private RelativeLayout search_field;
    private RecyclerView search_item;
    private TextView whats_new_next;
    private TextView content_search,search_not_found;
    private ProgressBar search_loader;
    private static final String TAG = SearchFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        image = new ArrayList<>();
        search_icon = view.findViewById(R.id.search_icon);
        cancel = view.findViewById(R.id.cancel);
        edit_search = view.findViewById(R.id.edit_search);
        search_field = view.findViewById(R.id.search_field);
        search_item = view.findViewById(R.id.search_item);
        whats_new_next = view.findViewById(R.id.whats_new_next);
        content_search = view.findViewById(R.id.content_search);
        search_not_found=view.findViewById(R.id.search_not_found);
        search_loader=view.findViewById(R.id.search_loader);

//        image.add(R.drawable.image);
//
//        .add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
//        image.add(R.drawable.image);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cancel.setVisibility(View.GONE);
                content_search.setVisibility(View.INVISIBLE);
                whats_new_next.setVisibility(View.INVISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Animation mLoadAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
                        mLoadAnimation.setDuration(20000);
                        cancel.setVisibility(View.GONE);
                        search_adpter.notifyDataSetChanged();
                        content_search.setVisibility(View.VISIBLE);
                        whats_new_next.setVisibility(View.VISIBLE);
                        search_not_found.setVisibility(View.GONE);
                        image.clear();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

                search = edit_search.getText().toString().trim();
                if(search!=null)
                {
                   // TastyToast.makeText(getActivity(), search, TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    Map<String, String> map = new HashMap<>();
                    map.put("title", search);
                    Search(map);
                }
                else {
                    search_not_found.setVisibility(View.GONE);
                    content_search.setVisibility(View.VISIBLE);
                    whats_new_next.setVisibility(View.VISIBLE);
                    search_not_found.setVisibility(View.GONE);
                }

            }
        });

    }
 public void Search(Map<String, String> map){
     search_loader.setVisibility(View.VISIBLE);
//     Call<Search_Data> call = Apis.getAPIService().Search_Data(map);
//     call.enqueue(new Callback<Search_Data>() {
//         @SuppressLint("Assert")
//         @Override
//         public void onResponse(Call<Search_Data> call, Response<Search_Data> response) {
//             search_loader.setVisibility(View.GONE);
//             cancel.setVisibility(View.VISIBLE);
//             Search_Data userdata=response.body();
//             Log.e(TAG,"data"+response);
//                 if (userdata.getData().isEmpty()) {
//                     image.clear();
//                     search_not_found.setVisibility(View.VISIBLE);
//                     search_not_found.setText(userdata.getMessage());
//                 } else {
//                     image.clear();
//                     image.addAll(userdata.getData());
//                     search_adpter = new Search_adapter(getActivity());
//                     search_item.setAdapter(search_adpter);
//                     search_item.setHasFixedSize(true);
//                     search_item.setLayoutManager(new GridLayoutManager(getContext(), 3));
//                     search_adpter.notifyDataSetChanged();
//                     search_not_found.setVisibility(View.GONE);
//             }
//         }
//         @Override
//         public void onFailure(Call<Search_Data> call, Throwable t) {
//             search_loader.setVisibility(View.GONE);
//             Log.d("response", "vv" + t.getMessage());
//         }
//     });

 }
    public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ViewHolder> {
        Context context;
        LayoutInflater inflater;
        //private ArrayList<Integer> image;

        Search_adapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            //this.image = images;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_list_show, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.image)
                    .error(R.drawable.image);
            Glide.with(context).load(image.get(position).getThumbnail()).apply(options).into(holder.searh_image);
            holder.searh_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("videoID",image.get(position).getId()+""));
                }
            });
        }

        @Override
        public int getItemCount() {
            return image.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView searh_image;
            ViewHolder(View itemView) {
                super(itemView);
                searh_image = itemView.findViewById(R.id.search_image);
            }
        }

    }


}
