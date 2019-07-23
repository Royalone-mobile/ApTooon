package com.aptoon.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModel {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("status")
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("filter")
        private List<Filter> filter;
        @Expose
        @SerializedName("now_available")
        private List<Now_available> now_available;
        @Expose
        @SerializedName("category")
        private List<Category> category;
        @Expose
        @SerializedName("preview")
        private List<Preview> preview;
        @Expose
        @SerializedName("banner")
        private Banner banner;

        public List<Filter> getFilter() {
            return filter;
        }

        public void setFilter(List<Filter> filter) {
            this.filter = filter;
        }

        public List<Now_available> getNow_available() {
            return now_available;
        }

        public void setNow_available(List<Now_available> now_available) {
            this.now_available = now_available;
        }

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
        }

        public List<Preview> getPreview() {
            return preview;
        }

        public void setPreview(List<Preview> preview) {
            this.preview = preview;
        }

        public Banner getBanner() {
            return banner;
        }

        public void setBanner(Banner banner) {
            this.banner = banner;
        }
    }

    public static class Filter {
        @Expose
        @SerializedName("category_title")
        private String category_title;

        @SerializedName("all")
        private String all;
        @Expose
        @SerializedName("category_id")
        private int category_id;
        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }
        public String getCategory_title() {
            return category_title;
        }

        public void setCategory_title(String category_title) {
            this.category_title = category_title;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }
    }

    public static class Now_available {
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("total_like")
        private String total_like;
        @Expose
        @SerializedName("des")
        private String des;
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("thumbnail")
        private String thumbnail;
        @Expose
        @SerializedName("video")
        private String video;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("category_id")
        private int category_id;
        @Expose
        @SerializedName("id")
        private int id;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTotal_like() {
            return total_like;
        }

        public void setTotal_like(String total_like) {
            this.total_like = total_like;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Category {
        @Expose
        @SerializedName("video")
        private List<Video> video;
        @Expose
        @SerializedName("category_title")
        private String category_title;
        @Expose
        @SerializedName("category_id")
        private int category_id;

        public List<Video> getVideo() {
            return video;
        }

        public void setVideo(List<Video> video) {
            this.video = video;
        }

        public String getCategory_title() {
            return category_title;
        }

        public void setCategory_title(String category_title) {
            this.category_title = category_title;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }
    }

    public static class Video {
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("total_like")
        private String total_like;
        @Expose
        @SerializedName("des")
        private String des;
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("thumbnail")
        private String thumbnail;
        @Expose
        @SerializedName("video")
        private String video;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("category_id")
        private int category_id;
        @Expose
        @SerializedName("id")
        private int id;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTotal_like() {
            return total_like;
        }

        public void setTotal_like(String total_like) {
            this.total_like = total_like;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Preview {
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("total_like")
        private String total_like;
        @Expose
        @SerializedName("des")
        private String des;
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("thumbnail")
        private String thumbnail;
        @Expose
        @SerializedName("video")
        private String video;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("category_id")
        private int category_id;
        @Expose
        @SerializedName("id")
        private int id;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTotal_like() {
            return total_like;
        }

        public void setTotal_like(String total_like) {
            this.total_like = total_like;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Banner {
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("total_like")
        private String total_like;
        @Expose
        @SerializedName("des")
        private String des;
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("thumbnail")
        private String thumbnail;
        @Expose
        @SerializedName("video")
        private String video;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("category_id")
        private int category_id;
        @Expose
        @SerializedName("id")
        private int id;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTotal_like() {
            return total_like;
        }

        public void setTotal_like(String total_like) {
            this.total_like = total_like;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
