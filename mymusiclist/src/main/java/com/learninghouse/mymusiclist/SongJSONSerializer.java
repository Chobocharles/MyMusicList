package com.learninghouse.mymusiclist;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesleyreisz on 3/2/14.
 */
public class SongJSONSerializer {
    private static final String TAG = "JSON Serializer";

    private Context mContext;
    private String mFileName;

    public SongJSONSerializer(Context context, String fileName){
        mContext = context;
        mFileName = fileName;
    }

    public void saveSongs(List<Song> songs) throws  JSONException, IOException{
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(songs);

        Writer writer = null;
        try{
            OutputStream out = mContext
                .openFileOutput(mFileName,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(json);

        }finally{
            if(writer!=null){
                writer.close();
            }
        }
    }

    public List<Song> loadSongs() throws IOException, JSONException{
        List<Song> songs = new ArrayList<Song>();
        BufferedReader reader = null;
        try{
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String json = null;
            if((json=reader.readLine())!=null){
                ObjectMapper mapper = new ObjectMapper();
                songs = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Song.class));
            }
        }catch(FileNotFoundException e){
            //ignore happens on first load
        }finally{
            if(reader!=null){
                reader.close();
            }
        }

        return songs;
    }
}
