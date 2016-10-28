package com.jub3r.publlicappshub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by Juber on 19/05/2016.
 */
public class AuthorActivity extends AppCompatActivity {

    private String name = null;
    private String location = null;
    private String organization = null;
    private String webpage = null;
    private String id;
    private Bitmap image;
    private String asoCode = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        Intent data = getIntent();
        Bundle extras = data.getExtras();

        if (data.hasExtra("id")){
            id = extras.getString("id");
        }

        final TextView nameTextView = (TextView)findViewById(R.id.authorName);
        final TextView locationTextView = (TextView)findViewById(R.id.authorlocation);
        final TextView organizationTextView = (TextView)findViewById(R.id.authororganization);
        final TextView webpageTextView = (TextView)findViewById(R.id.authorwebpage);
        final ImageView imageTextView = (ImageView)findViewById(R.id.authorimage);
        final TextView asoCodeTextView = (TextView)findViewById(R.id.author_aso_code);


        ParseQuery<ParseAuthorClass> query = ParseQuery.getQuery("ParseAuthorClass");
        query.whereEqualTo("id", id);
        query.getInBackground(id, new GetCallback<ParseAuthorClass>() {
            @Override
            public void done(ParseAuthorClass object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    name = object.getName();
                    nameTextView.setText(name);

                    location = object.getLocation();
                    locationTextView.setText(location);

                    organization = object.getOrganization();
                    organizationTextView.setText(organization);

                    webpage = object.getWeb();
                    webpageTextView.setText(webpage);

                    asoCode = object.getAsoCode();
                    asoCodeTextView.setText(asoCode);

                    try {
                        if (object.has("image")) {
                            byte[] bytes = object.getImageFile().getData();
                            image = (BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                            imageTextView.setImageBitmap(image);
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
