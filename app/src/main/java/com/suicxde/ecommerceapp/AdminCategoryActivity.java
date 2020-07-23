package com.suicxde.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts,sportsTshirts,dresses,sweaters;
    private ImageView glasses,hats,purses,shoes;
    private ImageView headphones,laptops,watches,mobilephones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        tshirts = (ImageView) findViewById(R.id.t_shirts);
        sportsTshirts = (ImageView) findViewById(R.id.sports_t_shirts);
        dresses = (ImageView) findViewById(R.id.female_dress);
        sweaters = (ImageView) findViewById(R.id.swaters);
        glasses = (ImageView) findViewById(R.id.glasses);
        hats = (ImageView) findViewById(R.id.hats);
        purses = (ImageView) findViewById(R.id.purses);
        shoes = (ImageView) findViewById(R.id.shoes);
        headphones = (ImageView) findViewById(R.id.headphones);
        laptops = (ImageView) findViewById(R.id.laptops);
        watches = (ImageView) findViewById(R.id.watches);
        mobilephones = (ImageView) findViewById(R.id.mobilephones);






        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });

        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sportsTshirts");
                startActivity(intent);
            }
        });
        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","dresses");
                startActivity(intent);
            }
        });
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sweaters");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","hats");
                startActivity(intent);
            }
        });
        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","headphones");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });
        mobilephones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","mobilephones");
                startActivity(intent);
            }
        });

    }
}