package com.learninghouse.mymusiclist;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

public class AddSongActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        setupActionBar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new AddSongFragment())
                .commit();
        }
    }


    public void addSongToBackend(View v){
        showToast("Adding Song");

        EditText songName = (EditText)findViewById(R.id.song_title);
        EditText songAlbum = (EditText)findViewById(R.id.song_album);
        EditText songArtistName = (EditText)findViewById(R.id.song_artist_name);
        EditText songYouTubeId = (EditText)findViewById(R.id.song_youtube_id);

        //persist
        Song song = new Song();
        song.setAlbum(songAlbum.getText().toString());
        song.setArtist(songArtistName.getText().toString());
        song.setName(songName.getText().toString());
        song.setYouTubeVideoId(songName.getText().toString());

        MyMusicListService service = MyMusicListService.getInstance();
        service.saveSong(song);

        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Super Cool subtitle");
        actionBar.show();
    }

    private void showToast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
    }

    /**
     * inner class fragment
     */
    public static class AddSongFragment extends Fragment {

        public AddSongFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_music, container, false);
            return rootView;
        }
    }

}
