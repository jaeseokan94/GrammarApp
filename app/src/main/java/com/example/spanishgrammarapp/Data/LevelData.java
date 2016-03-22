package com.example.spanishgrammarapp.Data;

/**
 * Created by JAESEOKAN on 16/03/2016.
 */
public class LevelData {
        private String language;
        private String level;

        int id;

        //constructor
        public LevelData()
        {
        }
    public LevelData(String level)
    {
        this.language = language;
        this.level=level;

    }

        public LevelData(String language, String level)
        {
            this.language = language;
            this.level=level;

        }
        public LevelData(int id,String language, String level)
        {
            this.id=id;
            this.language = language;
            this.level = level;

        }

        // setters
        public void setId(int id) {
            this.id = id;
        }
        public void setLanguage(String language){
            this.language = language;
        }
        public void setLevel(String level){
        this.level = level;
    }

        public String getLanguage(){
            return language;
        }
        public String getLevel(){
        return level;
    }



        public int getId() {
            return id;
        }


        @Override
        public String toString(){
            return level ;
        }

    }


