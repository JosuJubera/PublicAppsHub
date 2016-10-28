package com.jub3r.publlicappshub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * Activity for showing an app information
 *
 * This activity is used to display a layout resources for the information of an app.
 *
 * @author Josu Jubera
 * @version 2016.03.23
 */
public class AppActivity extends AppCompatActivity {

    /**
     * Class AppActivity
     * appName refers to the app's name
     * category refers to the apps' category
     * description refers to the app's description
     * images refers to the app's screenshots
     */
    private String appName = null;
    private String category = null;
    private String description = null;
    private ParseQueryAdapter<ParseCodeClass> codeList;
    private ListView codeListView = null;
    private ParseQueryAdapter<ParseDeploymentClass> deployList;
    private ListView deployListView = null;
    private SwipeRefreshLayout swipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Intent data = getIntent();

        Bundle extras = data.getExtras();

        String appId = null;
        if (data.hasExtra("appId")) {

            appId = extras.getString("appId");
        }
        else finish();

        codeListView = (ListView) findViewById(android.R.id.list);

        final String finalAppId = appId;
        codeList = new ParseQueryAdapter<>(this, new ParseQueryAdapter.QueryFactory<ParseCodeClass>() {
            public ParseQuery<ParseCodeClass> create() {

                final ParseQuery<ParseCodeClass> codes = ParseQuery.getQuery("ParseCodeClass");
                return codes.whereEqualTo("appId", finalAppId);
            }
        });
        codeList.setTextKey("name");
        codeListView.setAdapter(codeList);


        deployListView = (ListView) findViewById(R.id.list2);

        deployList = new ParseQueryAdapter<>(this, new ParseQueryAdapter.QueryFactory<ParseDeploymentClass>() {
            public ParseQuery<ParseDeploymentClass> create() {

                final ParseQuery<ParseDeploymentClass> codes = ParseQuery.getQuery("ParseDeploymentClass");
                return codes.whereEqualTo("appId", finalAppId);
            }
        });
        deployList.setTextKey("name");
        deployListView.setAdapter(deployList);


        final TextView appNameTextView = (TextView)findViewById(R.id.name2);
        final TextView categoryTextView = (TextView)findViewById(R.id.category2);
        final TextView descriptionTextView = (TextView)findViewById(R.id.text_desc2);

        ParseQuery<ParseAppClass> query = ParseQuery.getQuery("ParseAppClass");
        query.whereEqualTo("id", appId);
        query.getInBackground(appId, new GetCallback<ParseAppClass>() {
            public ArrayList<Bitmap> images;

            public void done(ParseAppClass object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {

                    appName = object.getName();
                    appNameTextView.setText(appName);

                    category = object.getCategory();
                    categoryTextView.setText(category);

                    description = object.getDescription();
                    descriptionTextView.setText(description);

                    images = new ArrayList<>();
                    try {
                        for (int i = 0; i <= 2; i++) {
                            if (object.has("image"+i)) {
                                byte[] bytes = object.getParseFile("image" + i).getData();
                                images.add(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                            }
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    int cont;
                    if (images.size() > 3){

                        cont = 3;
                    } else {

                        cont = images.size();
                    }
                    switch (cont) {

                        case 0:
                            TextView textView = (TextView) findViewById(R.id.vis_desc);
                            textView.setVisibility(View.INVISIBLE);
                            TextView textView1 = (TextView) findViewById(R.id.access_screen);
                            textView1.setVisibility(View.INVISIBLE);
                            TextView textView2 = (TextView) findViewById(R.id.screen1);
                            textView2.setVisibility(View.INVISIBLE);
                            TextView textView3 = (TextView) findViewById(R.id.screen2);
                            textView3.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            ImageView image1 = (ImageView)findViewById(R.id.image1);
                            image1.setImageBitmap(images.get(0));
                            break;

                        case 2:
                            image1 = (ImageView)findViewById(R.id.image1);
                            image1.setImageBitmap(images.get(0));
                            ImageView image2 = (ImageView)findViewById(R.id.image2);
                            image2.setImageBitmap(images.get(1));
                            break;

                        case 3:
                            image1 = (ImageView)findViewById(R.id.image1);
                            image1.setImageBitmap(images.get(0));
                            image2 = (ImageView)findViewById(R.id.image2);
                            image2.setImageBitmap(images.get(1));
                            ImageView image3 = (ImageView)findViewById(R.id.image3);
                            image3.setImageBitmap(images.get(2));
                            break;

                        default:
                            break;
                    }
                }
            }
        });

        Button addCode = (Button) findViewById(R.id.addcode);
        final String finalId = appId;
        addCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, NewCodeActivity.class);
                intent.putExtra("appId", finalId);
                startActivity(intent);
            }
        });

        Button addDeployment = (Button) findViewById(R.id.adddeploy);
        addDeployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, NewDeploymentActivity.class);
                intent.putExtra("appId",finalId);
                startActivity(intent);
            }
        });

        codeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AppActivity.this, CodeActivity.class);

                intent.putExtra("id", codeList.getItem(position).getObjectId());
                intent.putExtra("appId", finalId);

                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);
            }
        });

        deployListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AppActivity.this, DeploymentActivity.class);

                intent.putExtra("id", deployList.getItem(position).getObjectId());

                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);
            }
        });

        update();
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

    private void update() {
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                codeListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        codeList.loadObjects();
                    }
                }, 5000);
                deployListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        deployList.loadObjects();
                    }
                }, 5000);
            }
        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
