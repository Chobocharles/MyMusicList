package com.learninghouse.mymusiclist;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MusicListFragment extends Fragment {

    private static final String SONG_TITLE = "SONG_TITLE";
    private static final String TAG = "MusicList";

    private MediaPlayer mSound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_music_list_main,container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        mSound = MediaPlayer.create(getActivity(), R.raw.click);

        ListView listView = (ListView)getActivity().findViewById(R.id.listViewSongs);
        List<Song> songs = new MyMusicListService().findAll();

        //default the first song into the activity
        MainActivity activity =  (MainActivity)getActivity();
        for(Song song: songs){
            activity.setSong(song);
            break;
        }

        final SongAdapter adapter =
                new SongAdapter(getActivity(), R.layout.listview_for_each_song, songs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            mSound.start();
            Song o = (Song) adapter.getItem(position);
            Log.i(TAG, "You selected: " + o.getName());

            MainActivity activity =  (MainActivity)getActivity();
            activity.setSong(o);
            activity.getSupportActionBar().getTabAt(MainActivity.EVENT_TAB).select();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mSound!=null){
            mSound.stop();
            mSound.release();
            mSound = null;
        }
    }
}
