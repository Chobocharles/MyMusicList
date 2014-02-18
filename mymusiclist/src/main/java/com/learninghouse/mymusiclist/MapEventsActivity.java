package com.learninghouse.mymusiclist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.learninghouse.mymusiclist.events.ArtistEvents;
import com.learninghouse.mymusiclist.events.Event;
import com.learninghouse.mymusiclist.util.UrlFetchUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class MapEventsActivity extends FragmentActivity {
    private static final String DEBUG = "MAP";
    private static final String FIND_EVENTS_BY_ARTIST_URL =
        "http://api.seatgeek.com/2/events?q=%s";
    private static final String SONG_TITLE = "SONG_TITLE";
    private SupportMapFragment mapFrag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_events);

        Intent intent = getIntent();
        String artistName = intent.getStringExtra(SONG_TITLE);

        if(mapIsAvailable()){
            mapFrag = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.event_map);

            String encode_url = "";
            try {
                encode_url =  URLEncoder.encode(artistName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            new ListEventsAsyncTask(this).execute(
                String.format(FIND_EVENTS_BY_ARTIST_URL,encode_url)
            );
        }
    }

    protected boolean mapIsAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (status == ConnectionResult.SUCCESS) {
            return (true);
        } else {
            Toast.makeText(this, "Google Maps not Available, Please try again", Toast.LENGTH_LONG)
                    .show();
            finish();
        }

        return (false);
    }

    class ListEventsAsyncTask extends AsyncTask<String, Void, ArtistEvents> {
        private Context context;
        public ListEventsAsyncTask(Context context){
            this.context=context;
        }
        @Override
        protected ArtistEvents doInBackground(String... url) {
            Log.d(DEBUG,url[0]);
            String json = UrlFetchUtil.getJSON(url[0]);
            Log.d(DEBUG,json);

            //ArtistEvents artistEvents =  new Gson().fromJson(json, ArtistEvents.class);
            ObjectMapper mapper = new ObjectMapper();
            ArtistEvents events = null;
            try {
                events = mapper.readValue(json, ArtistEvents.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return events;
        }

        @Override
        protected void onPostExecute(ArtistEvents artistEvents) {
            if (artistEvents!=null && artistEvents.getEvents().size()>0) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                List<Event> events =  artistEvents.getEvents();

                for(Event event:events){
                    LatLng latLng =  new LatLng(
                        event.getVenue().getLocation().getLat(),
                        event.getVenue().getLocation().getLon()
                    );

                    MarkerOptions marker = new MarkerOptions();
                    marker.position(latLng);
                    mapFrag.getMap().addMarker(marker);
                    builder.include(marker.getPosition());
                }

                int padding = 20; // offset from edges of the map in pixels
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mapFrag.getMap().animateCamera(cu);

            } else {
                Toast.makeText(
                    context, "No Events found for Artist", Toast.LENGTH_LONG)
                    .show();

                //some place in new york the author used.
                LatLng latLng = new LatLng(40.734641, -73.996181);

                float zoomlevel = 10.0f;

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,zoomlevel);
                mapFrag.getMap().animateCamera(cameraUpdate);

                MarkerOptions marker = new MarkerOptions();
                marker.position(latLng);
                mapFrag.getMap().addMarker(marker);
            }
        }
    }
}
