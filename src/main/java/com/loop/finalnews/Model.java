package com.loop.finalnews;

public class Model {
    private String Mainnews;
    private String Type;
    private String Date;
    private String Url;
    private String Name;

    public Model(String Mainnews1, String Type1, String Date1, String Url1, String Name1) {
        Mainnews = Mainnews1;
        Type = Type1;
        Date = Date1;
        Url = Url1;
        Name = Name1;
    }

    public String mainnews() {
        return Mainnews;
    }

    public String type() {
        return Type;
    }

    public String getDate() {
        return Date;
    }

    public String url() {
        return Url;
    }

    public String name() {
        return Name;
    }
}