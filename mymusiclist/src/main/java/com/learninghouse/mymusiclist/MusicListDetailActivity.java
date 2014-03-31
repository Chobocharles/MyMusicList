package com.learninghouse.mymusiclist;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.gson.Gson;
import com.learninghouse.mymusiclist.search.ImageResults;
import com.learninghouse.mymusiclist.search.Result;
import com.learninghouse.mymusiclist.util.Keys;
import com.learninghouse.mymusiclist.util.UrlFetchUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

public class MusicListDetailActivity extends Activity {
    private static final String URL="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private static final SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");
    private static final String SONG_TITLE = "SONG_TITLE";
    private static final String TAG = "MusicList";

    private MediaPlayer mClickSound;
    private MediaPlayer mFailSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_detail);
        Intent intent = getIntent();
        String name = intent.getStringExtra(SONG_TITLE);
        final Song song = new MyMusicListService().findOne(name);
        Log.d(TAG, "Song was passed in to new Activity: " + song.getName());

        TextView songName = (TextView) findViewById(R.id.textViewSongTitleText);
        songName.setText(song.getName());

        TextView songArtist = (TextView) findViewById(R.id.textViewSongArtistText);
        songArtist.setText(song.getArtist());

        TextView songAlbum = (TextView) findViewById(R.id.textViewSongAlbumText);
        songAlbum.setText(song.getAlbum());

        TextView songDate = (TextView) findViewById(R.id.textViewSongDateText);
        songDate.setText(df.format(song.getPublishedDate()));

        Button songEvents = (Button) findViewById(R.id.buttonShowEvents);
        songEvents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapEventsActivity.class);
                intent.putExtra(SONG_TITLE,  song.getArtist());
                startActivity(intent);
            }
        });

        Button playSong = (Button) findViewById(R.id.buttonPlayVideo);

        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startIndex = 0;
                int startTimeMillis = 0;
                boolean autoPlay = true;
                boolean lightBoxMode = true;

                Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                        (MusicListDetailActivity) view.getContext(),
                        Keys.YOUTUBE_DEVELOPER_KEY,
                        song.getYouTubeVideoId(),
                        startTimeMillis,
                        autoPlay,
                        lightBoxMode);
                Toast.makeText(view.getContext(), "Loading Youtube Idx: " + song.getYouTubeVideoId(), Toast.LENGTH_LONG).show();
                view.getContext().startActivity(intent);
            }
        });

        new RandomImageAsyncTask(this).execute(song.getName(),song.getAlbum(),song.getArtist());

        mClickSound = MediaPlayer.create(this, R.raw.click);

        final ImageView imageView = (ImageView)findViewById(R.id.imageViewSong);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.loading);
                mClickSound.start();
                new RandomImageAsyncTask(MusicListDetailActivity.this)
                        .execute(song.getName(), song.getAlbum(), song.getArtist());
            }
        });


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mClickSound!=null){
            mClickSound.stop();
            mClickSound.release();
            mClickSound = null;
        }
        if(mFailSound!=null){
            mFailSound.stop();
            mFailSound.release();
            mFailSound = null;
        }
    }

    class RandomImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        private Context context;
        public RandomImageAsyncTask(Context context){
            this.context=context;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            //moved to a utility class so it can be called in many locations.
            String json = UrlFetchUtil.getJSON(URL + joinString(params));
            Log.d("",json);

            //search google for images
            ImageResults imageResults = new Gson().fromJson(json, ImageResults.class);

            //attempt at least three times to get an image
            String image2get = getRandomImageUrl(imageResults,0);

            //return image
            Bitmap bitmap = fetchImage(image2get);
            if(bitmap!=null){
                return bitmap;
            }else{
                mFailSound =  MediaPlayer.create(context, R.raw.wrong);
                mFailSound.start();
                buildSimpleNotification("Failed to load image", params[0]);
                return BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.loading);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView iv = (ImageView) findViewById(R.id.imageViewSong);
            if(iv!=null && bitmap!=null){
                iv.setImageBitmap(bitmap);
            }
        }


        private String getRandomImageUrl(ImageResults imageResults, int count){
            if (imageResults==null){
                return null;
            }

            int lCount = count;

            if (lCount>3){
                Log.e(TAG,"Unable to retrieve an image on three attempts");
                return null;
            }
            List<Result> results = imageResults.getResponseData().getResults();

            int randomNumber = ( int )( Math.random() * results.size());

            for(int i = randomNumber; i<results.size(); i++){
                Result result = results.get(i);

                if (Integer.parseInt(result.getHeight())<=600 &&
                    Integer.parseInt(result.getWidth())<=800){
                    return result.getUnescapedUrl();
                }
                else{
                    i++;
                }
            }

            return getRandomImageUrl(imageResults, count);
        }
        private Bitmap fetchImage(String strUrl){
            if(strUrl==null){
                return null;
            }

            try {
                URL url = new URL(strUrl);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                if(httpCon.getResponseCode()!=200){
                    throw new Exception("Failed to Connect");
                }

                InputStream is = httpCon.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (MalformedURLException e) {
                Log.e(TAG, "malformedurl: " + strUrl);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //return default image if nothing is loaded
            return BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.loading);
        }
        private String joinString(String... params){
            StringBuilder sb = new StringBuilder();
            for(String param:params){
                param = param.replace(" ","+");//remove spaces
                sb.append(param + "+");
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        }

        private void buildSimpleNotification(String msg, String name){
            Intent refreshIntent = new Intent(context, MusicListDetailActivity.class);
            refreshIntent.putExtra(SONG_TITLE, name);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,refreshIntent,0);

            NotificationCompat.Builder builder  = new NotificationCompat.Builder(context)
                    .setContentTitle("MyMusicList")
                    .setContentText(msg)
                    .setTicker(msg)
                    .setSmallIcon(R.drawable.info)
                    .setAutoCancel(true)
                    .addAction(R.drawable.refresh, "Reload", pendingIntent);


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, builder.build());
        }
    }
}
