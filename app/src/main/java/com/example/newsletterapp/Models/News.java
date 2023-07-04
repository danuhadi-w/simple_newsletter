package com.example.newsletterapp.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class News implements Serializable{
    private String name;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public News(String name, String author, String title, String description, String url, String urlToImage, String publishedAt){
        this.name = name;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getName(){
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getUrl(){
        return url;
    }

    public String getUrlToImage(){
        return urlToImage;
    }

    public String getPublishedAt(){
        if(Build.VERSION_CODES.O >= Build.VERSION.SDK_INT){
            return getFormattedDate();
        }
        return publishedAt;
    }
    public String getFormattedDate(){
        String str = "2022-01-01T10:15:30";
        String[] dayOfWeek = {"","Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};
        String[] months = {"","Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli",
                "Agustus", "September", "Oktober", "November", "Desember"};
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        int dayOfWeekIndex = dateTime.getDayOfWeek().getValue();
        int monthIndex = dateTime.getMonthValue();

        String[] dateTimeSeparated = publishedAt.split("T");
        String[] dateSeparated = dateTimeSeparated[0].split("-");
        String[] timeSeparated = dateTimeSeparated[1].split(":");

        return dayOfWeek[dayOfWeekIndex] + ", " + dateSeparated[2] + " " + months[monthIndex] + " " + dateSeparated[0] + " " + timeSeparated[0] + "." + timeSeparated[1];

    }

}
