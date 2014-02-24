package com.learninghouse.mymusiclist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private Song song = null;
    public static final int LIST_TAB = 0;
    public static final int EVENT_TAB = 1;
    public static final int MAP_TAB = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //sets a subtitle
        actionBar.setSubtitle("Super Cool subtitle");
        actionBar.show();

        ActionBar.Tab tab = null;

        //build list of songs
        tab = actionBar.newTab();
        tab.setText("Song List");
        tab.setTabListener(
             new SimpleTabListener(this, "com.learninghouse.mymusiclist.MusicListFragment")
        );
        actionBar.addTab(tab);

        //details of a given song
        tab = actionBar.newTab();
        tab.setText("Details");
        tab.setTabListener(
                new SimpleTabListener(this, "com.learninghouse.mymusiclist.MusicListDetailFragment")
        );
        actionBar.addTab(tab);

        //event detail for a given artist
        tab = actionBar.newTab();
        tab.setText("Events");
        tab.setTabListener(
                new SimpleTabListener(this, "com.learninghouse.mymusiclist.MapEventsFragment")
        );
        actionBar.addTab(tab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menuAdd:
                addItem(item);
                return true;
            case R.id.menuRefresh:
                refreshData(item);
                return true;
            case R.id.menuExit:
                onExitClicked(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(MenuItem menuItem){
        Intent intent = new Intent(this, AddSongActivity.class);
        startActivity(intent);

        showToast("Adding new Song");
    }

    public boolean refreshData(MenuItem menuItem){
        showToast("Refreshing Data");
        return true;
    }

    public boolean onExitClicked(MenuItem menuItem){
        finish();
        return true;
    }

    private void showToast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
    }


    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
