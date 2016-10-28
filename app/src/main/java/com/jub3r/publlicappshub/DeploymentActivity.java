package com.jub3r.publlicappshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by Juber on 13/04/2016.
 */
public class DeploymentActivity extends AppCompatActivity {

    private String name = null;
    private String asoCode = null;
    private String url = null;
    private String country = null;
    private String region = null;
    private String manager = null;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment);

        Intent data = getIntent();
        Bundle extras = data.getExtras();

        if (data.hasExtra("id")){
            id = extras.getString("id");
        }

        final TextView nameTextView = (TextView)findViewById(R.id.depName);
        final TextView asoCodeTextView = (TextView)findViewById(R.id.dep_aso_code);
        final TextView urlTextView = (TextView)findViewById(R.id.depUrl);
        final TextView countryTextView = (TextView)findViewById(R.id.country);
        final TextView regionTextView = (TextView)findViewById(R.id.region);
        final TextView serManaTextView = (TextView)findViewById(R.id.serMana);

        ParseQuery<ParseDeploymentClass> query = ParseQuery.getQuery("ParseDeploymentClass");
        query.whereEqualTo("id", id);
        query.getInBackground(id, new GetCallback<ParseDeploymentClass>() {
            @Override
            public void done(ParseDeploymentClass object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    name = object.getName();
                    nameTextView.setText(name);

                    asoCode = object.getAsoCode();
                    asoCodeTextView.setText(asoCode);

                    url = object.getUrl();
                    urlTextView.setText(url);

                    country = object.getCountry();
                    countryTextView.setText(country);

                    region = object.getRegion();
                    regionTextView.setText(region);

                    manager = object.getSerMana();
                    serManaTextView.setText(manager);
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
