package com.learninghouse.mymusiclist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.learninghouse.mymusiclist.jsonObject.ImageResults;
import com.learninghouse.mymusiclist.jsonObject.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class MusicListDetailActivity extends Activity {
    private static final String URL="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private static final SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");
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

        new RandomImageAsyncTask().execute(song.getName(),song.getAlbum(),song.getArtist());
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

    class RandomImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String json = getJSON(URL + joinString(params),1000);
            Log.d("",json);

            ImageResults imageResults = new Gson().fromJson(json, ImageResults.class);
            String image2get = getFirstImage(imageResults);
            return getImage(image2get);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView iv = (ImageView) findViewById(R.id.imageViewSong);
            if(iv!=null && bitmap!=null){
                iv.setImageBitmap(bitmap);
            }
        }

        private String getJSON(String url, int timeout) {
            try {
                java.net.URL u = new URL(url);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(timeout);
                c.setReadTimeout(timeout);
                c.connect();
                int status = c.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(
                            new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (MalformedURLException ex) {
                Log.e("", ex.getMessage());
            } catch (IOException ex) {
                Log.e("", ex.getMessage());
            }
            return null;
        }
        private String getFirstImage(ImageResults imageResults){
            for(Result result : imageResults.getResponseData().getResults()){
                if (Integer.parseInt(result.getHeight())<=600 &&
                    Integer.parseInt(result.getWidth())<=800){
                    return result.getUnescapedUrl();
                }
            }
            return null;
        }
        private Bitmap getImage(String strUrl){
            try {
                URL url = new URL(strUrl);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                if(httpCon.getResponseCode()!=200){
                    throw new Exception("Failed to Connect");
                }

                InputStream is = httpCon.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private String joinString(String... params){
            StringBuilder sb = new StringBuilder();
            for(String param:params){
                param = param.replace(" ","+");//remove spaces
                sb.append(param + "+");
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
    }
}
