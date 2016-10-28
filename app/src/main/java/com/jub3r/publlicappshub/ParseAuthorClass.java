package com.jub3r.publlicappshub;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by Juber on 19/05/2016.
 */
@ParseClassName("ParseAuthorClass")
public class ParseAuthorClass extends ParseObject {

    public ParseAuthorClass() {}

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

    public String getOrganization() {
        return getString("organization");
    }

    public void setOrganization(String organization) {
        put("organization", organization);
    }

    public String getLocation() {
        return getString("location");
    }

    public void setLocation(String location) {
        put("location", location);
    }

    public String getWeb() {
        return getString("web");
    }

    public void setWeb(String web) {
        put("web", web);
    }

    public ParseFile getImageFile() {
        return getParseFile("image");
    }

    public void setImageFile(ParseFile file) {
        put("image", file);
    }

    public String getAsoCode() {
        return getString("asoCode");
    }

    public void setAsoCode(String asoCode) {
        put("asoCode", asoCode);
    }
}
