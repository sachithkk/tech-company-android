/**
 *
 * Created by Sajith Priyankara
 *
 **/
package com.example.sachith.tech_app_front;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//this class to load the fragment to the home screen
public class HomeActivity extends AppCompatActivity {

    private static Button buttonParse , buttonSend;
    private Intent intent;
    private static TextInputLayout companyName,city,address;
    private static AlertDialog.Builder builder;
    private static TextInputEditText c_name,c_city,c_address;
    private static RequestQueue requestQueue;
    private Fragment selectedFragment =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        requestQueue = Volley.newRequestQueue(this);
        builder = new AlertDialog.Builder(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        Intent intent2 = getIntent();

        int selectFragmentId = intent2.getIntExtra("FRAGMENT_NAME", 0);
        fragmentSelector(selectFragmentId);


    }

    //method to select fragment which user selected on the menu
    private void fragmentSelector(int selectFragmentId) {

        switch (selectFragmentId) {
            case R.id.nav_home:
                intent = new Intent(getApplicationContext(), HomeCompany.class);
                startActivity(intent);
                break;
            case R.id.nav_search:
                selectedFragment = new SearchCompany();
                break;
            case R.id.nav_edit:
                selectedFragment = new UpdateCompany();
                break;
            case R.id.nav_add:
                selectedFragment = new AddCompany();
                break;
            case R.id.nav_view:
                selectedFragment = new ViewCompany();
                break;
            case 0:
                toastMessage("Error fragment id");

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    fragmentSelector(item.getItemId());
                    return true;
                }
            };

    //method to show toast message
    private void toastMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}
