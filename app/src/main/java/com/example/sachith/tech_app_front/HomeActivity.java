package com.example.sachith.tech_app_front;

import android.icu.text.StringPrepParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private static TextView textView;
    private static Button buttonParse , buttonSend;

    private static EditText companyName,city,address;

    private static RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestQueue = Volley.newRequestQueue(this);

        initComponent();

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJson();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyCreate();
            }
        });
    }

    private void companyCreate(){
        String uri = "http://192.168.8.100:8080/tech/companies/create";

        HashMap<String , String> hm = new HashMap<>();
        hm.put("companyName",companyName.getText().toString());
        hm.put("city",city.getText().toString());
        hm.put("address",address.getText().toString());

        JSONObject jsonObject = new JSONObject(hm);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uri,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject responseObj = new JSONObject(response.getString("responseObject"));

                    if(response.getString("responseCode").toString().equalsIgnoreCase("201")){
                        Toast.makeText(getApplicationContext(),responseObj.getString("message"), Toast.LENGTH_SHORT).show();
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
        //String uri = "https://api.myjson.com/bins/rlr4s";
          String uri = "http://192.168.8.100:8080/tech/companies";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayRequest = response.getJSONArray("responseObject");

                    for(int i = 0 ; i < jsonArrayRequest.length() ; i++){
                        JSONObject employee = jsonArrayRequest.getJSONObject(i);

                        int age = employee.getInt("id");
                        String firstName = employee.getString("city");

                        textView.append(firstName + ", "+ age+"\n\n");

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
        textView    = findViewById(R.id.result);
        buttonParse      = findViewById(R.id.btn_1);
        companyName = findViewById(R.id.company_name);
        city        = findViewById(R.id.company_city);
        address     = findViewById(R.id.company_address);
        buttonSend  = findViewById(R.id.btn_2);
    }
}
