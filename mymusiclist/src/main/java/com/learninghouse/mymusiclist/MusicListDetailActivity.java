package com.learninghouse.mymusiclist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MusicListDetailActivity extends Activity {
    private SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");
    private static final String SONG_TITLE = "SONG_TITLE";
    private static final String TAG = "MusicList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_detail);
        Intent intent = getIntent();
        String name = intent.getStringExtra(SONG_TITLE);
        Song song = new MyMusicListService().findOne(name);
        Log.d(TAG, "Song was passed in to new Activity: " + song.getName());

        TextView songName = (TextView) findViewById(R.id.textViewSongTitleText);
        songName.setText(song.getName());

        TextView songArtist = (TextView) findViewById(R.id.textViewSongArtistText);
        songArtist.setText(song.getArtist());

        TextView songAlbum = (TextView) findViewById(R.id.textViewSongAlbumText);
        songAlbum.setText(song.getAlbum());

        TextView songDate = (TextView) findViewById(R.id.textViewSongDateText);
        songDate.setText(df.format(song.getPublishedDate()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_list_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
