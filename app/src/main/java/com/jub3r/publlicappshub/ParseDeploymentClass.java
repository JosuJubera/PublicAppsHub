package com.jub3r.publlicappshub;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Juber on 12/05/2016.
 */
@ParseClassName("ParseDeploymentClass")
public class ParseDeploymentClass extends ParseObject {

    public ParseDeploymentClass() {

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

    public String getAsoCode() {
        return getString("asoCode");
    }

    public void setAsoCode(String asoCode) {
        put("asoCode", asoCode);
    }

    public String getUrl() {
        return getString("url");
    }

    public void setUrl(String url) {
        put("url", url);
    }

    public String getSerMana() {
        return getString("serMana");
    }

    public void setSerMana(String serMana) {
        put("serMana", serMana);
    }

    public String getCountry() {
        return getString("country");
    }

    public void setCountry(String country) {
        put("country", country);
    }

    public String getRegion() {
        return getString("region");
    }

    public void setRegion(String region) {
        put("region", region);
    }

    @Override
    public String toString() {

        return "App: " + getName() + "\n" +
                "Location: " + getCountry() + getRegion();
    }
}
