package com.learninghouse.mymusiclist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_main);

        ListView listView = (ListView)findViewById(R.id.listViewSongs);
        List<Song> songs = new MyMusicListService().findAll();
        final SongAdapter adapter =
                new SongAdapter(this, R.layout.listview_for_each_song, songs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Song o = (Song) adapter.getItem(position);
                Log.i(TAG, "You selected: " + o.getName());

                Intent intent = new Intent(view.getContext(), MusicListDetailActivity.class);
                intent.putExtra(SONG_TITLE,  o.getName());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_list_main, menu);
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
