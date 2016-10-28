package com.jub3r.publlicappshub;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Juber on 09/05/2016.
 */
@ParseClassName("ParseCodeClass")
public class ParseCodeClass extends ParseObject {

    public ParseCodeClass() {

    }

    @Override
    public String getObjectId() {
        return super.getObjectId();
    }

    public void setId(String appId) {
        put("appId", appId);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getAsoServ() {
        return getString("asoServ");
    }

    public void setAsoServ(String asoServ) {
        put("asoServ", asoServ);
    }

    public String getAuthor() {
        return getString("author");
    }

    public void setAuthor(String author) {
        put("author", author);
    }

    public String getPlatform() {
        return getString("platform");
    }

    public void setPlatform(String platform) {
        put("platform", platform);
    }

    public String getLocation() {
        return getString("location");
    }

    public void setLocation(String location) {
        put("location", location);
    }

    @Override
    public String toString() {

        return "Code Name: " + getName() + "\n" +
                "Platform: " + getPlatform();
    }
}
