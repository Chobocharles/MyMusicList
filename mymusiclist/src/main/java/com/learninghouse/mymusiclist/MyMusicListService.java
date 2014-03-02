package com.learninghouse.mymusiclist;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyMusicListService {
    private static MyMusicListService myMusicListService;
    private static List<Song> songs;

    private MyMusicListService(){
        songs = new ArrayList<Song>();

        songs.add(new Song("I'm Yours","Jason Mraz","We Sing, We Dance, We Steal Things",getDate(2008, 05, 15),"EkHTsc9PU2A"));
        songs.add(new Song("Kryptonite","3 Doors Down","The Better Life",getDate(2001, 01, 17),"xPU8OAjjS4k"));
        songs.add(new Song("Timber","Pit Bull","The Better Life",getDate(2013, 10, 7),"hHUbLv4ThOo"));
        songs.add(new Song("Dark Horse","Katy Perry","Single",getDate(2013, 12, 17),"F9S-88WxPdE"));
        songs.add(new Song("Counting Stars","One Republic","Single",getDate(2013, 6, 14),"IIwyTMVXTuw"));
        songs.add(new Song("Demons","Imagine Dragons","Single",getDate(2013, 10, 22),"mWRsgZuwf_8"));
        songs.add(new Song("Drink A Beer","Luke Bryan","Crash My Party",getDate(2013, 11, 11),"f4qqdbRMYG0"));
        songs.add(new Song("Burn","Ellie Goulding","Halcyon",getDate(2013, 03, 13),"MpHrkbma3ng"));
        songs.add(new Song("Story Of My Life","One Direction","Story Of My Life",getDate(2013, 11, 25),"W-TE_Ys4iwM"));
        songs.add(new Song("Let Her Go","Passenger","All the Little Lights",getDate(2012, 7, 12),"Ginx7WKq5GE"));
    }

    public static MyMusicListService getInstance(){
        if(myMusicListService==null){
            myMusicListService = new MyMusicListService();
        }
        return myMusicListService;
    }


    public List<Song> findAll(){
        return songs;
    }

    public Song findOne(String name){
        for(Song song:songs){
            if(song.getName().equals(name)){
                return song;
            }
        }
        return new Song();
    }

    public void saveSong(Song song){
        List<Song> newSongs = findAll();
        newSongs.add(song);
        this.songs = newSongs;
    }

    private static Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year,month,day,0,0);
        return c.getTime();
    }
}
