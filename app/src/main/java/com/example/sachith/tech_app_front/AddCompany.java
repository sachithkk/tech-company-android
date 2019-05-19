/**
 *
 * Created by Sachith Tharaka
 *
 **/
package com.example.sachith.tech_app_front;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sachith.tech_app_front.enums.EndPoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//this class handles adding a tech company
public class AddCompany extends Fragment{

    private static Button buttonSend;
    private static TextInputLayout companyName,website,address,companyDescription,companyContact;
    private static AlertDialog.Builder builder;
    private static TextInputEditText c_name,c_webSite,c_address,c_description,c_contact;
    private static RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        requestQueue = Volley.newRequestQueue(getContext());
        builder = new AlertDialog.Builder(getContext());

        View view = inflater.inflate(R.layout.activity_home, container,false);

        companyName         = view.findViewById(R.id.company_name);
        website             = view.findViewById(R.id.company_website);
        address             = view.findViewById(R.id.company_address);
        companyDescription  = view.findViewById(R.id.company_description);
        companyContact      = view.findViewById(R.id.company_contact);
        buttonSend          = view.findViewById(R.id.btn_2);
        c_name              = view.findViewById(R.id.child_name);
        c_webSite           = view.findViewById(R.id.child_website);
        c_address           = view.findViewById(R.id.child_address);
        c_description       = view.findViewById(R.id.child_description);
        c_contact           = view.findViewById(R.id.child_contact);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(checkInputs()){
                   companyCreate();
               }
            }
        });

        initTextChangeListerner();

        return view;
    }

    /*
     * this method used for validate company name
     * */
    private void validateEditCompany(Editable s) {
        if (TextUtils.isEmpty(s)) {
            companyName.setError("This field required");
        }
        else{
            companyName.setError(null);
        }
    }

    //this method use for validate city
    private void validateEditCity(Editable s) {
        if (TextUtils.isEmpty(s)) {
            website.setError("This field required");
        }
        else{
            website.setError(null);
        }
    }

    //this method use for validate address of the company
    private void validateEditAddress(Editable s) {
        if (TextUtils.isEmpty(s)) {
            address.setError("This field required");
        }
        else{
            address.setError(null);
        }
    }

    //this method use for validate description
    private void validateEditDescription(Editable s) {
        if (TextUtils.isEmpty(s)) {
            companyDescription.setError("This field required");
        }
        else{
            companyDescription.setError(null);
        }
    }

    //this method use for validate contact number
    private void validateEditCompanyContact(Editable s) {
        if (TextUtils.isEmpty(s)) {
            companyContact.setError("This field required");
        }
        else{
            companyContact.setError(null);
        }
    }

    /*
     *  When user clicked save button fire these method for validation.
     * */
    private boolean validateCompanyName(){

        if(companyName.getEditText().getText().toString().trim().isEmpty()){
            companyName.setError("This field required");
            return false;
        }
        return true;

    }

    //this method use for validate city
    private boolean validateCity(){

        if(website.getEditText().getText().toString().trim().isEmpty()){
            website.setError("This field required");
            return false;
        }
        return true;

    }

    //this method use for validate address
    private boolean validateAddress(){

        if(address.getEditText().getText().toString().trim().isEmpty()){
            address.setError("This field required");
            return false;
        }
        return true;

    }

    //this method use for validate Description
    private boolean validateCompanyDescription(){

        if(companyDescription.getEditText().getText().toString().trim().isEmpty()){
            companyDescription.setError("This field required");
            return false;
        }
        return true;

    }

    //this method use for validate contact number
    private boolean validateCompanyContact(){

        if(companyContact.getEditText().getText().toString().trim().isEmpty()){
            companyContact.setError("This field required");
            return false;
        }
        return true;

    }

    //this method use for validate inputs of the user
    private boolean checkInputs(){
        if(validateCompanyName() & validateCity() & validateAddress() & validateCompanyDescription() & validateCompanyContact()){
            return true;
        }
        else{
            return false;
        }
    }


    //this method will insert the company to the database
    private void companyCreate(){

        HashMap<String , String> hm = new HashMap<>();
        hm.put("companyName",companyName.getEditText().getText().toString());
        hm.put("website",website.getEditText().getText().toString());
        hm.put("address",address.getEditText().getText().toString());
        hm.put("description" , companyDescription.getEditText().getText().toString());
        hm.put("contactNumber" , companyContact.getEditText().getText().toString());

        JSONObject jsonObject = new JSONObject(hm);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                EndPoints.CREATE_COMPANY.getUrl(),
                jsonObject,
                new Response.Listener<JSONObject>() {
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
                                website.getEditText().setText("");
                                address.getEditText().setText("");
                                companyDescription.getEditText().setText("");
                                companyContact.getEditText().setText("");

                                companyName.setError(null);
                                website.setError(null);
                                address.setError(null);
                                companyDescription.setError(null);
                                companyContact.setError(null);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else if(response.getString("responseCode").toString().equalsIgnoreCase("000")){
                        if(responseObj.getString("code").toString().equalsIgnoreCase("001")){
                            Toast.makeText(getContext(),responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else if(responseObj.getString("code").toString().equalsIgnoreCase("002")){
                            Toast.makeText(getContext(),responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Please check your internet connection", Toast.LENGTH_SHORT);
            }
        });

        requestQueue.add(jsonObjectRequest);
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

        c_webSite.addTextChangedListener(new TextWatcher() {
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

        c_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditDescription(s);
            }
        });

        c_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditCompanyContact(s);
            }
        });
    }
}
