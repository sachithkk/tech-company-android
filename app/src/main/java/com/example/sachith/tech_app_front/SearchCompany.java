/**
 *
 * Created by Sajith Priyankara
 *
 **/
package com.example.sachith.tech_app_front;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sachith.tech_app_front.domain.Company;
import com.example.sachith.tech_app_front.enums.EndPoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// method to search company and view details
public class SearchCompany extends Fragment implements CompanyAdapter.OnItemClickListener{

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Company> companyList;
    private CompanyAdapter adapter;
    private EditText searchBox;
    private JSONArray array;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.search_company, container, false);
        mList = view.findViewById(R.id.search_company_list);
        searchBox = view.findViewById(R.id.search_company_txt);

        companyList = new ArrayList<>();


        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);

        getData();

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                companyList.clear();
                filterData();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;


    }

    // method to get data from database
    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                EndPoints.GET_ALL_COMPANY.getUrl(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                             array = response.getJSONArray("responseObject");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toastMessage("error");
                        progressDialog.dismiss();
                        System.out.println("ERRORRR : " + error);
                    }
                }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }

    // method use to filter data
    // when user type in the text box this method will be called
    private void filterData() {

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                if (object.getString("name").toString().toLowerCase().contains(searchBox.getText().toString().toLowerCase())) {
                    Company company = new Company();

                    company.setId(object.getInt("id"));
                    company.setName(object.getString("name"));
                    company.setAddress(object.getString("address"));
                    company.setContactNum(object.getString("contactNumber"));
                    company.setWeb(object.getString("website"));
                    company.setDescription(object.getString("description"));

                    companyList.add(company);
                }
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new CompanyAdapter(R.layout.single_company, getContext(), companyList);
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(SearchCompany.this);

        adapter.notifyDataSetChanged();

    }

    // method to display toast message
    private void toastMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int posistion) {

        Company clickedCompany = companyList.get(posistion);

        ViewSingleCompany viewSingleCompany = new ViewSingleCompany();
        Bundle bundle = new Bundle();
        bundle.putString("name" , clickedCompany.getName());
        bundle.putString("address" , clickedCompany.getAddress());
        bundle.putString("contactNum",clickedCompany.getContactNum());
        bundle.putString("web" , clickedCompany.getWeb());
        bundle.putString("description" , clickedCompany.getDescription());
        viewSingleCompany.setArguments(bundle);
        viewSingleCompany.show(getFragmentManager(),"My dialog");

    }

}
