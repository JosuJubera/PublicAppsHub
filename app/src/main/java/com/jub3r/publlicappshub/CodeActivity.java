package com.jub3r.publlicappshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juber on 11/04/2016.
 */
public class CodeActivity extends AppCompatActivity {

    private String codeName = null;
    private String asoServ = null;
    private String author = null;
    private String platform = null;
    private String location = null;
    private ParseQueryAdapter<ParseDeploymentClass> deployList;
    private ListView myListView = null;
    private String id;
    private SwipeRefreshLayout swipeLayout;
    private String appId;
    private ParseQueryAdapter<ParseAuthorClass> authorList;
    private AdapterView authorListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        myListView = (ListView) findViewById(android.R.id.list);
        authorListView = (ListView) findViewById(R.id.list2);

        Intent data = getIntent();
        Bundle extras = data.getExtras();

        if (data.hasExtra("appId")) {

            appId = extras.getString("appId");
        }
        else finish();

        if (data.hasExtra("id")) {

            id = extras.getString("id");
        }
        else finish();

        final TextView codeNameTextView = (TextView)findViewById(R.id.codeName);
        final TextView asoServTextView = (TextView)findViewById(R.id.aso_ser);
        final TextView authorTextView = (TextView)findViewById(R.id.author);
        final TextView platformTextView = (TextView)findViewById(R.id.platform);
        final TextView locationTextView = (TextView)findViewById(R.id.codeloc);


        ParseQuery<ParseCodeClass> queryCode = ParseQuery.getQuery("ParseCodeClass");
        queryCode.whereEqualTo("id", id);
        queryCode.getInBackground(id, new GetCallback<ParseCodeClass>() {
            @Override
            public void done(ParseCodeClass object, ParseException e) {
                if (object == null) {
                    Log.e("Code", "Failed to get code");
                }
                else{
                    codeName = object.getName();
                    codeNameTextView.setText(codeName);

                    asoServ = object.getAsoServ();
                    asoServTextView.setText(asoServ);

                    author = object.getAuthor();
                    authorTextView.setText(author);

                    platform = object.getPlatform();
                    platformTextView.setText(platform);

                    location = object.getLocation();
                    locationTextView.setText(location);
                }
            }
        });

        Button addDeployment = (Button) findViewById(R.id.adddeploy);
        addDeployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CodeActivity.this, NewDeploymentActivity.class);
                intent.putExtra("appId", appId);
                intent.putExtra("asoCode", codeName);
                startActivity(intent);
            }
        });

        deployList = new ParseQueryAdapter<>(this, new ParseQueryAdapter.QueryFactory<ParseDeploymentClass>() {
            public ParseQuery<ParseDeploymentClass> create() {

                final ParseQuery<ParseDeploymentClass> codes = ParseQuery.getQuery("ParseDeploymentClass");
                return codes.whereEqualTo("asoCode", codeName);
            }
        });

        deployList.setTextKey("name");
        myListView.setAdapter(deployList);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CodeActivity.this, DeploymentActivity.class);

                intent.putExtra("id", deployList.getItem(position).getObjectId());

                startActivity(intent);
            }
        });
        deployList.loadObjects();

        Button addAuthor = (Button) findViewById(R.id.addauthor);
        addAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CodeActivity.this, NewAuthorActivity.class);
                intent.putExtra("appId", appId);
                intent.putExtra("asoCode", codeName);
                startActivity(intent);
            }
        });

        authorList = new ParseQueryAdapter<>(this, new ParseQueryAdapter.QueryFactory<ParseAuthorClass>() {
            public ParseQuery<ParseAuthorClass> create() {

                final ParseQuery<ParseAuthorClass> codes = ParseQuery.getQuery("ParseAuthorClass");
                return codes.whereEqualTo("asoCode", codeName);
            }
        });

        authorList.setTextKey("name");
        authorListView.setAdapter(authorList);

        authorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CodeActivity.this, AuthorActivity.class);

                intent.putExtra("id", authorList.getItem(position).getObjectId());

                startActivity(intent);
            }
        });
        authorList.loadObjects();

        Toast.makeText(
                getBaseContext(),
                "Desliza abajo para actualizar.",
                Toast.LENGTH_SHORT).show();
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
                myListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        deployList.loadObjects();
                    }
                }, 5000);
                authorListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        authorList.loadObjects();
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
