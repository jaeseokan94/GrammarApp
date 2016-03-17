package com.example.spanishgrammarapp.Data;

/**
 * Created by JAESEOKAN on 16/03/2016.
 */
public class LanguageData {
    private String language;
    private String level;
    private String topic;
    private String subtopic;
    int id;

    //constructor
    public LanguageData()
    {
    }

    public LanguageData(String language)
    {
        this.language = language;

    }
    public LanguageData(int id,String language)
    {
        this.id=id;
        this.language = language;

    }

    // setters
    public void setId(int id) {
        this.id = id;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return language;
    }



    public int getId() {
        return id;
    }


    @Override
    public String toString(){
        return language ;
    }

}
