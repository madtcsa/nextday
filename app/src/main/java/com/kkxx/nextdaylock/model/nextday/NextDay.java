package com.kkxx.nextdaylock.model.nextday;

import com.google.gson.Gson;

/**
 * @author chenwei
 * @date 2018/2/12
 */

public class NextDay {

    private String date;
    private String month;
    private String week;
    private String city;
    private String content;
    private String musicTitle;
    private String musicArtist;
    private String musicUrl;
    private String name;
    private String background;
    private String pictureImg;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPictureImg() {
        return pictureImg;
    }

    public void setPictureImg(String pictureImg) {
        this.pictureImg = pictureImg;
    }

    @Override
    public String toString() {
        return "NextDay{" +
                "date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", week='" + week + '\'' +
                ", city='" + city + '\'' +
                ", content='" + content + '\'' +
                ", musicTitle='" + musicTitle + '\'' +
                ", musicArtist='" + musicArtist + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", name='" + name + '\'' +
                ", background='" + background + '\'' +
                ", pictureImg='" + pictureImg + '\'' +
                '}';
    }

}
