package com.jub3r.publlicappshub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by Juber on 29/03/2016.
 */
public class NewCodeActivity extends AppCompatActivity {

    private ParseCodeClass parseObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcode);

        Button submit = (Button) findViewById(R.id.submitcode);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCode();
            }
        });
    }

    private void addCode() {

        String name = ((EditText) findViewById(R.id.editexcodename)).getText().toString();
        String asoService = ((EditText) findViewById(R.id.editexcodeasoser)).getText().toString();
        String author = ((EditText) findViewById(R.id.editexcodeaut)).getText().toString();
        String codeLoc = ((EditText) findViewById(R.id.editexcodecodeloc)).getText().toString();

        boolean htmlCheck = ((CheckBox) findViewById(R.id.radiohtml)).isChecked();
        boolean androidCheck = ((CheckBox) findViewById(R.id.radioandroid)).isChecked();
        boolean iosCheck = ((CheckBox) findViewById(R.id.radioios)).isChecked();

        String platform = "";

        if (htmlCheck) {
            platform += "HTML5 ";
        }

        if (androidCheck) {
            platform += "Android ";
        }

        if (iosCheck) {
            platform += "IOS ";
        }

        if (name.isEmpty()) {
            finish();
        }

        Intent data = getIntent();

        Bundle extras = data.getExtras();

        String appId = null;
        if (data.hasExtra("appId")) {

            appId = extras.getString("appId");
        }
        else finish();

        parseObject = new ParseCodeClass();
        parseObject.setId(appId);
        parseObject.setName(name);
        parseObject.setPlatform(platform);
        parseObject.setAsoServ(asoService);
        parseObject.setAuthor(author);
        parseObject.setLocation(codeLoc);

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
