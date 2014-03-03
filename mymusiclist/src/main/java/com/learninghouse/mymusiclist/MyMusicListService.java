package com.learninghouse.mymusiclist;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyMusicListService {
    private static MyMusicListService myMusicListService;
    private List<Song> mSongs;
    private Context mContext;
    private static final String SONG_LIST = "song_list.json";

    private MyMusicListService(Context context){
        this.mContext = context;

        mSongs = new ArrayList<Song>();

        /*
        //now loaded from a json file
        mSongs.add(new Song("I'm Yours","Jason Mraz","We Sing, We Dance, We Steal Things",getDate(2008, 05, 15),"EkHTsc9PU2A"));
        mSongs.add(new Song("Kryptonite","3 Doors Down","The Better Life",getDate(2001, 01, 17),"xPU8OAjjS4k"));
        mSongs.add(new Song("Timber","Pit Bull","The Better Life",getDate(2013, 10, 7),"hHUbLv4ThOo"));
        mSongs.add(new Song("Dark Horse","Katy Perry","Single",getDate(2013, 12, 17),"F9S-88WxPdE"));
        mSongs.add(new Song("Counting Stars","One Republic","Single",getDate(2013, 6, 14),"IIwyTMVXTuw"));
        mSongs.add(new Song("Demons","Imagine Dragons","Single",getDate(2013, 10, 22),"mWRsgZuwf_8"));
        mSongs.add(new Song("Drink A Beer","Luke Bryan","Crash My Party",getDate(2013, 11, 11),"f4qqdbRMYG0"));
        mSongs.add(new Song("Burn","Ellie Goulding","Halcyon",getDate(2013, 03, 13),"MpHrkbma3ng"));
        mSongs.add(new Song("Story Of My Life","One Direction","Story Of My Life",getDate(2013, 11, 25),"W-TE_Ys4iwM"));
        mSongs.add(new Song("Let Her Go","Passenger","All the Little Lights",getDate(2012, 7, 12),"Ginx7WKq5GE"));
        */
    }

    public static MyMusicListService getInstance(Context context){
        if(myMusicListService==null){
            myMusicListService = new MyMusicListService(context);
        }
        return myMusicListService;
    }


    public List<Song> findAll(){
        SongJSONSerializer serializer = new SongJSONSerializer(mContext, SONG_LIST);
        try {
            mSongs = serializer.loadSongs();
        } catch (IOException e) {
            //ignore on first load
        } catch (JSONException e) {
            Log.e("", "unable to read file: " + e);
            mSongs = new ArrayList<Song>();
        }
        return mSongs;
    }

    public Song findOne(String name){
        for(Song song:mSongs){
            if(song.getName().equals(name)){
                return song;
            }
        }
        return new Song();
    }

    public void addSong(Song song){
        List<Song> newSongs = findAll();
        newSongs.add(song);
        this.mSongs = newSongs;
    }

    public void saveSongs(){
        SongJSONSerializer serializer = new SongJSONSerializer(mContext, SONG_LIST);
        try {
            serializer.saveSongs(mSongs);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Song> loadSongs(){
        return new ArrayList<Song>();
    }

    private static Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year,month,day,0,0);
        return c.getTime();
    }
}
