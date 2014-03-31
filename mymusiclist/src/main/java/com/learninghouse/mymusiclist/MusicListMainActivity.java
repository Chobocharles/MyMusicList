package com.learninghouse.mymusiclist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MusicListMainActivity extends Activity {

    private static final String SONG_TITLE = "SONG_TITLE";
    private static final String TAG = "MusicList";

    private MediaPlayer mSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_main);

        mSound = MediaPlayer.create(this, R.raw.click);

        ListView listView = (ListView)findViewById(R.id.listViewSongs);
        List<Song> songs = new MyMusicListService().findAll();
        final SongAdapter adapter =
                new SongAdapter(this, R.layout.listview_for_each_song, songs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mSound.start();
                Song o = (Song) adapter.getItem(position);
                Log.i(TAG, "You selected: " + o.getName());

                Intent intent = new Intent(view.getContext(), MusicListDetailActivity.class);
                intent.putExtra(SONG_TITLE,  o.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSound!=null){
            mSound.stop();
            mSound.release();
            mSound = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.music_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
