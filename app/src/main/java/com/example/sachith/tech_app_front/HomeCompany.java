package com.example.sachith.tech_app_front;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeCompany extends AppCompatActivity {

    Button search_btn, edit_btn, view_btn, add_btn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu);

        intent = new Intent(this, HomeActivity.class);
        search_btn = findViewById(R.id.btn_home_search);
        edit_btn = findViewById(R.id.btn_home_edit_company);
        view_btn = findViewById(R.id.btn_home_view_company);
        add_btn = findViewById(R.id.btn_home_add_company);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", R.id.nav_search);
                startOtherActivity();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", R.id.nav_edit);
                startOtherActivity();
            }
        });

        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", R.id.nav_view);
                startOtherActivity();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", R.id.nav_add);
                startOtherActivity();
            }
        });

    }
    public void startOtherActivity() {
        startActivity(intent);
    }

}
