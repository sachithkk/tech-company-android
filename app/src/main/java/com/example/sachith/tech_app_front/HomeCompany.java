package com.example.sachith.tech_app_front;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                intent.putExtra("FRAGMENT_NAME", "search");
                startOtherActivity();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", "edit");
                startOtherActivity();
            }
        });

        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", "list");
                startOtherActivity();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("FRAGMENT_NAME", "add");
                startOtherActivity();
            }
        });




    }
    public void startOtherActivity() {
        startActivity(intent);
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
