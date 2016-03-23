package com.example.spanishgrammarapp;

import android.app.ListActivity;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NitinKapoor on 21/03/16.
 */
class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3"));
    }


    public class Main extends ListActivity{

        private final String path = new String("/data/");
        private List<String> songs = new ArrayList<String>();


        public void updatePlaylist() {
            final ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songs);


            File home = new File(path);
            if (home.listFiles(new Mp3Filter()).length > 0) {
                for (File file : home.listFiles(new Mp3Filter())) {
                    songs.add(file.getName());
                }
                ArrayAdapter<String> songlist = new ArrayAdapter<String>(this, R.layout.content_recording_tool, songs);
                setListAdapter(songlist);
            }
        }

    }
}
