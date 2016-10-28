package com.jub3r.publlicappshub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by Juber on 13/04/2016.
 */
public class NewDeploymentActivity extends AppCompatActivity {

    private ParseDeploymentClass parseObject;
    private String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdeployment);

        Spinner spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapterCountry);

        Spinner spinnerRegion = (Spinner) findViewById(R.id.spinnerRegion);
        ArrayAdapter<CharSequence> adapterRegion = ArrayAdapter.createFromResource(this,
                R.array.regions_array, android.R.layout.simple_spinner_item);
        adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapterRegion);

        Button submit = (Button) findViewById(R.id.submitdep);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDeployment();
            }
        });
    }

    public void addDeployment() {

        String name = ((EditText) findViewById(R.id.editexdepname)).getText().toString();
        String asoCode = ((EditText) findViewById(R.id.editexdepasocode)).getText().toString();
        String url = ((EditText) findViewById(R.id.editexdepurl)).getText().toString();
        String serMana = ((EditText) findViewById(R.id.editexdepsermana)).getText().toString();
        String country = ((Spinner) findViewById(R.id.spinnerCountry)).getSelectedItem().toString();
        String region = ((Spinner) findViewById(R.id.spinnerRegion)).getSelectedItem().toString();

        if (name.isEmpty()) {
            finish();
        }

        Intent data = getIntent();

        Bundle extras = data.getExtras();

        if (data.hasExtra("appId")) {

            appId = extras.getString("appId");
        }
        else finish();

        if (data.hasExtra("asoCode")) {

            asoCode = extras.getString("asoCode");
        }


        parseObject = new ParseDeploymentClass();
        parseObject.setId(appId);
        parseObject.setName(name);
        parseObject.setUrl(url);
        parseObject.setSerMana(serMana);
        parseObject.setCountry(country);
        parseObject.setRegion(region);
        parseObject.setAsoCode(asoCode);

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(
                            getBaseContext(),
                            "Guardado. Desliza abajo para actualizar.",
                            Toast.LENGTH_SHORT).show();
                    Log.v("Object saved in server" + parseObject.getObjectId(),"ParseAppClass()");

                } else {
                    Toast.makeText(
                            getBaseContext(),
                            "Error retrieving: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();Log.v("failed saved to server"+ e.getMessage(),"ParseAppClass()");
                }
            }
        });
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
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
