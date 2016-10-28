package com.jub3r.publlicappshub;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by Juber on 07/05/2016.
 */
@ParseClassName("ParseAppClass")
public class ParseAppClass extends ParseObject {

    public ParseAppClass() {
        // A default constructor is required.
    }

    @Override
    public String getObjectId() {
        return super.getObjectId();
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getCategory() {
        return getString("category");
    }

    public void setCategory(String category) {
        put("category", category);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public ArrayList<ParseFile> getImageFile() {

        ArrayList<ParseFile> images = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            images.add(getParseFile("image"+i));
        }

        return images;
    }

    public void setImageFile(ParseFile file, int i) {
        put("image"+i, file);
    }

    @Override
    public String toString() {
        return getName() + "\n" + getDescription();
    }
}
