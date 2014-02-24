package com.learninghouse.mymusiclist;

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
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
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

import android.support.v4.app.Fragment;

public class MusicListDetailFragment extends Fragment {
    private static final String URL="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private static final SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy (EEE)");
    private static final String SONG_TITLE = "SONG_TITLE";
    private static final String TAG = "MusicList";

    private MediaPlayer mClickSound;
    private MediaPlayer mFailSound;

    private String name=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_music_list_detail, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        MainActivity activity =  (MainActivity)getActivity();

        if(activity.getSong()!=null){
            final Song song = activity.getSong();
            Log.d(TAG, "Song was passed in to new Activity: " + song.getName());

            TextView songName = (TextView) getActivity().findViewById(R.id.textViewSongTitleText);
            songName.setText(song.getName());

            TextView songArtist = (TextView) getActivity().findViewById(R.id.textViewSongArtistText);
            songArtist.setText(song.getArtist());

            TextView songAlbum = (TextView) getActivity().findViewById(R.id.textViewSongAlbumText);
            songAlbum.setText(song.getAlbum());

            TextView songDate = (TextView) getActivity().findViewById(R.id.textViewSongDateText);
            songDate.setText(df.format(song.getPublishedDate()));

            Button songEvents = (Button) getActivity().findViewById(R.id.buttonShowEvents);
            songEvents.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(view.getContext(), MapEventsFragment.class);
                    //intent.putExtra(SONG_TITLE,  song.getArtist());
                    //startActivity(intent);
                    MainActivity activity =  (MainActivity)getActivity();
                    activity.setSong(song);
                    activity.getSupportActionBar().getTabAt(MainActivity.MAP_TAB).select();
                }
            });

            Button playSong = (Button) getActivity().findViewById(R.id.buttonPlayVideo);

            playSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int startIndex = 0;
                    int startTimeMillis = 0;
                    boolean autoPlay = true;
                    boolean lightBoxMode = true;

                    Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                        getActivity(),
                        Keys.YOUTUBE_DEVELOPER_KEY,
                        song.getYouTubeVideoId(),
                        startTimeMillis,
                        autoPlay,
                        lightBoxMode);
                    Toast.makeText(view.getContext(), "Loading Youtube Idx: " + song.getYouTubeVideoId(), Toast.LENGTH_LONG).show();
                    view.getContext().startActivity(intent);
                }
            });

            new RandomImageAsyncTask(getActivity()).execute(song.getName(),song.getAlbum(),song.getArtist());

            mClickSound = MediaPlayer.create(getActivity(), R.raw.click);

            final ImageView imageView = (ImageView)getActivity().findViewById(R.id.imageViewSong);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setImageResource(R.drawable.loading);
                    mClickSound.start();
                    new RandomImageAsyncTask(getActivity())
                            .execute(song.getName(), song.getAlbum(), song.getArtist());
                }
            });
        }

    }


    @Override
    public void onDestroy() {
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

            //search google for images, gson approach
            ObjectMapper mapper = new ObjectMapper();
            ImageResults imageResults = null;
            try {
                imageResults = mapper.readValue(json, ImageResults.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
            ImageView iv = (ImageView) getActivity().findViewById(R.id.imageViewSong);
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
            //todo: thought we moved this.
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
            Intent refreshIntent = new Intent(context, MusicListDetailFragment.class);
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
                    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, builder.build());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
