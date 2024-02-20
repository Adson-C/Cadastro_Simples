package com.ads.restaurantetectoy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.restaurantetectoy.R;
import com.ads.restaurantetectoy.model.CadastroActivity;

public class MainActivity extends AppCompatActivity {

    Button btnEscreva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEscreva = findViewById(R.id.btnEscrevase);
        btnEscreva.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });

    }

}