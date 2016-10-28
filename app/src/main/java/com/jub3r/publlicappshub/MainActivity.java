/**
 * PACKAGE
 */
package com.jub3r.publlicappshub;

/**
* IMPORTS
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseQueryAdapter;

/**
 * Activity for showing initial app list
 *
 * This activity is used to display a layout resources for a list of apps.
 *
 * @author Josu Jubera
 * @version 2016.03.23
 */
public class MainActivity extends AppCompatActivity{

    /**
     * App list
     */
    private ParseQueryAdapter<ParseAppClass> mainAdapter;
    private SwipeRefreshLayout swipeLayout;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainAdapter = new ParseQueryAdapter<>(this, "ParseAppClass");
        mainAdapter.setTextKey("name");

        mainAdapter.loadObjects();

        myListView = (ListView) findViewById(android.R.id.list);

        myListView.setAdapter(mainAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, AppActivity.class);

                intent.putExtra("appId", mainAdapter.getItem(position).getObjectId());

                startActivity(intent);
            }
        });
        update();
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
                        mainAdapter.loadObjects();
                    }
                }, 5000);
            }
        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.newDesign){

            Intent intent = new Intent(this, NewDesignActivity.class);
            startActivityForResult(intent, 1);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
