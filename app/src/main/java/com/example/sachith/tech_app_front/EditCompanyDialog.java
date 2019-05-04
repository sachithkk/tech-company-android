package com.example.sachith.tech_app_front;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sachith.tech_app_front.enums.EndPoints;
import com.example.sachith.tech_app_front.enums.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditCompanyDialog extends DialogFragment {

    /*
     * initialize company edit details inputs.
     */
    private TextInputEditText companyName,companyWeb,companyAddres,companyContact,companyDesc;

    /*
     * initialize edit and delete button.
     */
    ImageButton updateEnableButton,deleteButton;
    Button editSave, cancel;

    // initialize company id as negative one.
    int companyId = -1;

    /*
     * volley initialize.
     */
    private static RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        requestQueue = Volley.newRequestQueue(getContext());

        View view = inflater.inflate(R.layout.edit_company_dialog, container,false);

        companyName    = view.findViewById(R.id.view_company_name);
        companyWeb     = view.findViewById(R.id.view_company_web);
        companyAddres  = view.findViewById(R.id.view_company_address);
        companyContact = view.findViewById(R.id.view_company_contact);
        companyDesc    = view.findViewById(R.id.view_company_description);
        updateEnableButton   = view.findViewById(R.id.btn_edit_popup);
        deleteButton   = view.findViewById(R.id.btn_delete_popup);
        editSave = view.findViewById(R.id.edit_save);
        cancel = view.findViewById(R.id.edit_cancel);

        /*
         * when company edit window display company update submit button invisible.
         * then user click edit button that will display.
         */
        editSave.setVisibility(View.INVISIBLE);

        /*
         * catch the passing data and set values in input fields.
         */
        Bundle bundle = getArguments();
        companyId = bundle.getInt("id");
        String name = (String) bundle.get("name");
        String address = (String) bundle.get("address");
        String contact = (String) bundle.get("contactNum");
        String webSite = (String) bundle.get("web");
        String description = (String) bundle.get("description");

        companyName.setText(name);
        companyWeb.setText(webSite);
        companyDesc.setText(description);
        companyAddres.setText(address);
        companyContact.setText(contact);

        /*
         * set delete button click listener.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Alert");
                builder1.setMessage("Do you want to delete this");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(companyId != -1){
                                    deleteCompany(companyId);
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.setCanceledOnTouchOutside(false);
                alert11.show();
            }
        });

        cancelEditWindow();

        updateEnableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSave.setVisibility(View.VISIBLE);
            }
        });

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(companyId != -1){
                    updateCompany(companyId);
                }
            }
        });

        return view;

    }

    private void updateCompany(int companyId){
        if(checkInputs()){

            HashMap<String , String> updatePayload = new HashMap<>();

            updatePayload.put("companyName",companyName.getText().toString());
            updatePayload.put("website",companyWeb.getText().toString());
            updatePayload.put("address",companyAddres.getText().toString());
            updatePayload.put("description" , companyDesc.getText().toString());
            updatePayload.put("contactNumber" , companyContact.getText().toString());

            JSONObject jsonObject = new JSONObject(updatePayload);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    EndPoints.COMPANY.getUrl()+companyId+"/update",
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                   JSONObject responseObj = new JSONObject(response.getString("responseObject"));
                                   String successCode = response.getString("responseCode");

                                if(successCode.equals("204")){

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                    builder1.setTitle("Success Message");
                                    builder1.setMessage(responseObj.getString("message"));
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    /*
                                                     * After updating company call for get data method refresh.
                                                     */
                                                    UpdateCompany updateCompany = new UpdateCompany();
                                                    updateCompany.getData();

                                                    dialog.cancel();
                                                    getDialog().dismiss();
                                                }
                                            });
                                    AlertDialog alert11 = builder1.create();
                                    alert11.setCanceledOnTouchOutside(false);
                                    alert11.show();
                                }
                                else{

                                    JSONObject invalidResponse = new JSONObject(response.getString("responseObject"));
                                    String errorCode = invalidResponse.getString("code");
                                    String errorMessage = invalidResponse.getString("message");

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                    builder1.setTitle("Error Message");
                                    builder1.setCancelable(true);

                                        builder1.setMessage(errorMessage);
                                        builder1.setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                        AlertDialog alert11 = builder1.create();
                                        alert11.setCanceledOnTouchOutside(false);
                                        alert11.show();
                                }

                            } catch (JSONException e) {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setTitle("Error Message");
                                builder1.setMessage("Unknown Error Occurred, check you network connection.");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.setCanceledOnTouchOutside(false);
                                alert11.show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                            builder1.setTitle("Error Message");
                            builder1.setMessage("Unknown Error Occurred, check you network connection.");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.setCanceledOnTouchOutside(false);
                            alert11.show();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        }
    }

    /*
     * This method includes delete a particular company implementation.
     * and handle the system throw custom exception.
     *
     */
    private void deleteCompany(int companyId){
        boolean result = false;

        HashMap<String , String> payload = new HashMap<>();
        payload.put("status", Status.IN_ACTIVE.getStatus());

        JSONObject jsonObject = new JSONObject(payload);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                EndPoints.COMPANY.getUrl()+companyId+"/deleteCompany",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject responseObj = new JSONObject(response.getString("responseObject"));
                            String successCode = response.getString("responseCode");

                            if(successCode.equalsIgnoreCase("200")){

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setTitle("Success Message");
                                builder1.setMessage(responseObj.getString("message"));
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                /*
                                                 * After deleting company call for get data method refresh.
                                                 */
                                                UpdateCompany updateCompany = new UpdateCompany();
                                                updateCompany.getData();

                                                dialog.cancel();
                                                getDialog().dismiss();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.setCanceledOnTouchOutside(false);
                                alert11.show();
                            }
                            else{
                                JSONObject invalidResponse = new JSONObject(response.getString("responseObject"));
                                String errorCode = invalidResponse.getString("code");
                                String errorMessage = invalidResponse.getString("message");

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setTitle("Error Message");
                                builder1.setCancelable(true);

                                    builder1.setMessage(errorMessage);
                                    builder1.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.setCanceledOnTouchOutside(false);
                                    alert11.show();
                            }


                        } catch (JSONException e) {

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                            builder1.setTitle("Error Message");
                            builder1.setMessage("Unknown Error Occurred, check you network connection.");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.setCanceledOnTouchOutside(false);
                            alert11.show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setTitle("Error Message");
                        builder1.setMessage("Unknown Error Occurred, check you network connection.");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.setCanceledOnTouchOutside(false);
                        alert11.show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void cancelEditWindow(){

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    /*
     * These method used for validate Listener
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
            companyWeb.setError("This field required");
        }
        else{
            companyWeb.setError(null);
        }
    }

    private void validateEditAddress(Editable s) {
        if (TextUtils.isEmpty(s)) {
            companyAddres.setError("This field required");
        }
        else{
            companyAddres.setError(null);
        }
    }

    private void validateEditDescription(Editable s) {
        if (TextUtils.isEmpty(s)) {
            companyDesc.setError("This field required");
        }
        else{
            companyDesc.setError(null);
        }
    }

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
     *
     * */
    private boolean validateCompanyName(){

        if(companyName.getText().toString().trim().isEmpty()){
            companyName.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean validateCity(){

        if(companyWeb.getText().toString().trim().isEmpty()){
            companyWeb.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean validateAddress(){

        if(companyAddres.getText().toString().trim().isEmpty()){
            companyAddres.setError("This field required");
            return false;
        }
        return true;

    }


    private boolean validateCompanyDescription(){

        if(companyDesc.getText().toString().trim().isEmpty()){
            companyDesc.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean validateCompanyContact(){

        if(companyContact.getText().toString().trim().isEmpty()){
            companyContact.setError("This field required");
            return false;
        }
        return true;

    }

    private boolean checkInputs(){
        if(validateCompanyName() & validateCity() & validateAddress() & validateCompanyDescription() & validateCompanyContact()){
            return true;
        }
        else{
            return false;
        }
    }

}
