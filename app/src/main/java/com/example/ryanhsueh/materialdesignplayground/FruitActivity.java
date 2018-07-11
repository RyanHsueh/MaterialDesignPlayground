package com.example.ryanhsueh.materialdesignplayground;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "key_fruit_name";
    public static final String FRUIT_IMAGE_ID = "key_fruit_image_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolBar = findViewById(R.id.collapsing_toolbar);
        collapsingToolBar.setTitle(fruitName);

        TextView TextFruitContent = findViewById(R.id.text_fruit_content);
        ImageView imageFruit = findViewById(R.id.image_fruit);

        Glide.with(this).load(fruitImageId).into(imageFruit);
        TextFruitContent.setText(getnerateFruitContent(fruitName));
    }

    private String getnerateFruitContent(String fruitName) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<500 ; i++) {
            sb.append(fruitName).append(" ");
        }

        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
