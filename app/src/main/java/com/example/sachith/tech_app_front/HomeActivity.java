package com.example.sachith.tech_app_front;

import android.content.DialogInterface;
import android.icu.text.StringPrepParseException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sachith.tech_app_front.enums.EndPoints;
import com.example.sachith.tech_app_front.validateInpits.TextValidator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    /*
     * https://stackoverflow.com/questions/51889837/cannot-connect-to-localhost-api-from-android-app --> for how to work api with real android device.
     *
     *
    */

    //private static TextView textView;
    private static Button buttonParse , buttonSend;

    private static TextInputLayout companyName,city,address;
    private static AlertDialog.Builder builder;
    private static TextInputEditText c_name,c_city,c_address;

    private static RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_company);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

//        requestQueue = Volley.newRequestQueue(this);
//        builder = new AlertDialog.Builder(this);
//
//        initComponent();
//
//        buttonParse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                parseJson();
//            }
//        });
//
//        buttonSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if(checkInputs()){
//                   companyCreate();
//               }
//            }
//        });
//
//        /*
//         * initiate TextInputEditText Listerner
//         * after clicked textInputEditText check if it has error or not
//         */
//        initTextChangeListerner();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new ViewCompany();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchCompany();
                            break;
                        case R.id.nav_edit:
                            selectedFragment = new UpdateCompany();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new UpdateCompany();
                            break;
                        case R.id.nav_view:
                            selectedFragment = new ViewCompany();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    /*
     * These method used for validate Listerner
     *
     * */
    private void validateEditCompany(Editable s) {
        if (TextUtils.isEmpty(s)) {
            companyName.setError("This field required");
        }
        else{
            companyName.setError(null);
        }
    }

    private void validateEditCity(Editable s) {
        if (TextUtils.isEmpty(s)) {
            city.setError("This field required");
        }
        else{
            city.setError(null);
        }
    }

    private void validateEditAddress(Editable s) {
        if (TextUtils.isEmpty(s)) {
            address.setError("This field required");
        }
        else{
            address.setError(null);
        }
    }

    /*
     *  When user clicked save button fire these method for validation.
     *
     * */
    private boolean validateCompanyName(){

        if(companyName.getEditText().getText().toString().trim().isEmpty()){
            companyName.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean validateCity(){

        if(city.getEditText().getText().toString().trim().isEmpty()){
            city.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean validateAddress(){

        if(address.getEditText().getText().toString().trim().isEmpty()){
            address.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean checkInputs(){
        if(validateCompanyName() & validateCity() & validateAddress()){
            return true;
        }
        else{
            return false;
        }
    }



    private void companyCreate(){

        HashMap<String , String> hm = new HashMap<>();
        hm.put("companyName",companyName.getEditText().getText().toString());
        hm.put("city",city.getEditText().getText().toString());
        hm.put("address",address.getEditText().getText().toString());

        JSONObject jsonObject = new JSONObject(hm);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, EndPoints.CREATE_COMPANY.getUrl(),
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject responseObj = new JSONObject(response.getString("responseObject"));

                    if(response.getString("responseCode").toString().equalsIgnoreCase("201")){

                        builder.setMessage("Successfully").setTitle(responseObj.getString("message"))
                                .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                companyName.getEditText().setText("");
                                city.getEditText().setText("");
                                address.getEditText().setText("");

                                companyName.setError(null);
                                city.setError(null);
                                address.setError(null);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        //Toast.makeText(getApplicationContext(),responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("responseCode").toString().equalsIgnoreCase("000")){
                        if(responseObj.getString("code").toString().equalsIgnoreCase("001")){
                            Toast.makeText(getApplicationContext(),responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else if(responseObj.getString("code").toString().equalsIgnoreCase("002")){
                            Toast.makeText(getApplicationContext(),responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_SHORT);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void parseJson() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, EndPoints.GET_ALL_COMPANY.getUrl(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayRequest = response.getJSONArray("responseObject");

                    for(int i = 0 ; i < jsonArrayRequest.length() ; i++){
                        JSONObject employee = jsonArrayRequest.getJSONObject(i);

                        int age = employee.getInt("id");
                        String firstName = employee.getString("city");

                        //textView.append(firstName + ", "+ age+"\n\n");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void initComponent(){
        //textView    = findViewById(R.id.result);
        buttonParse      = findViewById(R.id.btn_1);
        companyName = findViewById(R.id.company_name);
        city        = findViewById(R.id.company_city);
        address     = findViewById(R.id.company_address);
        buttonSend  = findViewById(R.id.btn_2);
        c_name = findViewById(R.id.child_name);
        c_city = findViewById(R.id.child_city);
        c_address = findViewById(R.id.child_address);
    }

    private void initTextChangeListerner(){

        c_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditCompany(s);
            }
        });

        c_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditCity(s);
            }
        });

        c_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditAddress(s);
            }
        });
    }
}
