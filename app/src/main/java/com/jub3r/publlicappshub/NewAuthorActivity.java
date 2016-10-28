package com.jub3r.publlicappshub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Juber on 19/05/2016.
 */
public class NewAuthorActivity extends AppCompatActivity {

    private ParseAuthorClass parseObject;
    private String appId;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newauthor);

        Button addImages = (Button)findViewById(R.id.addimage);
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "imagen"), 0);
            }
        });

        Button submit = (Button) findViewById(R.id.submitauthor);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthor();
            }
        });
    }

    public void addAuthor() {

        String name = ((EditText) findViewById(R.id.editexauthorname)).getText().toString();
        String location = ((EditText) findViewById(R.id.editexauthorlocation)).getText().toString();
        String organization = ((EditText) findViewById(R.id.editexauthororganization)).getText().toString();
        String webpage = ((EditText) findViewById(R.id.editexauthorwebpage)).getText().toString();
        String asoCode = ((EditText) findViewById(R.id.editexauthorasocode)).getText().toString();

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

        parseObject = new ParseAuthorClass();
        parseObject.setId(appId);
        parseObject.setName(name);
        parseObject.setLocation(location);
        parseObject.setOrganization(organization);
        parseObject.setWeb(webpage);
        byte[] datas;
        ParseFile file;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        datas = stream.toByteArray();
        file = new ParseFile("image", datas);
        parseObject.setImageFile(file);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            try {
                image = (MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
