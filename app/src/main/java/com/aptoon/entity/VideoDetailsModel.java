package com.aptoon.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDetailsModel {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("trailer")
        private List<Trailer> trailer;
        @Expose
        @SerializedName("related")
        private List<Related> related;
        @Expose
        @SerializedName("video")
        private Video video;

        public List<Trailer> getTrailer() {
            return trailer;
        }

        public void setTrailer(List<Trailer> trailer) {
            this.trailer = trailer;
        }

        public List<Related> getRelated() {
            return related;
        }

        public void setRelated(List<Related> related) {
            this.related = related;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }
    }

    public static class Trailer {
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

    public static class Related {
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

    public static class Video {
        @Expose
        @SerializedName("created_at")
        private String created_at;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("total_like")
        private int total_like;
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

        public int getTotal_like() {
            return total_like;
        }

        public void setTotal_like(int total_like) {
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
