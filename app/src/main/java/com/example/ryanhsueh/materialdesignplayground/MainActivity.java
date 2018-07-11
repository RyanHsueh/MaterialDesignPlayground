package com.example.ryanhsueh.materialdesignplayground;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;

    private Fruit[] FRUITS = new Fruit[]{
            new Fruit("banana", R.drawable.banana),
            new Fruit("tomato", R.drawable.tomato),
            new Fruit("mango", R.drawable.mango),
            new Fruit("muskmelon", R.drawable.muskmelon),
            new Fruit("strawberry", R.drawable.strawberry),
            new Fruit("grapes", R.drawable.grapes),
            new Fruit("avocado", R.drawable.avocado),
    };

    private List<Fruit> mFruitList = new ArrayList<>();

    private FruitAdapter mFruitAdapter;

    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_add);
        }

        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_dashboard);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawer.closeDrawers();
                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        mFruitAdapter = new FruitAdapter(mFruitList);
        recyclerView.setAdapter(mFruitAdapter);


        mSwipeLayout = findViewById(R.id.swipe_layout);
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_call:
                Toast.makeText(this, "Menu Call", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_camera:
                Toast.makeText(this, "Menu Camera", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_edit:
                Toast.makeText(this, "Menu Edit", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void initFruits() {
        mFruitList.clear();
        for (int i=0; i<50 ; i++) {
            Random random = new Random();
            int index = random.nextInt(FRUITS.length);
            mFruitList.add(FRUITS[index]);
        }
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        mFruitAdapter.notifyDataSetChanged();
                        mSwipeLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
