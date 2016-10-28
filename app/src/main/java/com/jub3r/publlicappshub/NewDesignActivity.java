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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Activity for showing app creator view
 *
 * This activity is used to display a layout resources for adding an app.
 *
 * @author Josu Jubera
 * @version 2016.03.23
 */
public class NewDesignActivity extends AppCompatActivity {

    /**
     * Class NewDesignActivity
     * images refers to the app's screenshots' paths
     */
    ArrayList<Bitmap> images = new ArrayList<>();
    private ParseAppClass parseObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapp);

        TextView cont = (TextView)findViewById(R.id.textView9);
        cont.setText(images.size() + " imagenes");

        Button addImages = (Button)findViewById(R.id.button3);
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "imagen"), 0);
            }
        });

        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addApp();
            }
        });
    }

    public boolean addApp() {

        String name = ((EditText) findViewById(R.id.edittext1)).getText().toString();

        int selectedRadioButton = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        String category = null;
        switch (selectedRadioButton) {

            case R.id.radio1:
                category = getString(R.string.edu_cat);
                break;

            case R.id.radio2:
                category = getString(R.string.hea_cat);
                break;

            case R.id.radio3:
                category = getString(R.string.tra_cat);
                break;
        }
        if (category == null) {

            return false;
        }

        String description = ((EditText) findViewById(R.id.edittext2)).getText().toString();

        if (name.isEmpty()) {

            finish();

            return false;
        }

        parseObject = new ParseAppClass();
        parseObject.setName(name);
        parseObject.setCategory(category);
        parseObject.setDescription(description);
        byte[] data;
        ParseFile file;
        for (int i = 0; i < images.size(); i++) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            images.get(i).compress(Bitmap.CompressFormat.PNG, 100, stream);
            data = stream.toByteArray();
            file = new ParseFile("image", data);
            parseObject.setImageFile(file,i);
        }

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

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            try {
                images.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
                TextView cont = (TextView)findViewById(R.id.textView9);
                cont.setText(images.size() + " imagenes");
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
