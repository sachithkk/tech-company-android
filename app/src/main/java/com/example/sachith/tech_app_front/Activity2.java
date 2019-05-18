package com.example.sachith.tech_app_front;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sachith.tech_app_front.domain.Company;

public class Activity2 extends DialogFragment {

    private TextView companyName,companyWeb,companyAddres,companyContact,companyDesc;
    private Button closeButton;
    public static final String WEB_SITE = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_2, container,false);

        companyName = view.findViewById(R.id.view_company_name);
        companyWeb = view.findViewById(R.id.view_company_web);
        companyAddres = view.findViewById(R.id.view_company_address);
        companyContact = view.findViewById(R.id.view_company_contact);
        companyDesc = view.findViewById(R.id.view_company_description);
        closeButton = view.findViewById(R.id.popup_close);

        Bundle bundle = getArguments();
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

        companyWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CompanyWebSite.class);
                intent.putExtra(WEB_SITE, companyWeb.getText().toString());
                startActivity(intent);
            }
        });

//        DialogFragment.getDialog().setCanceledOnTouchOutside(false);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;

    }
}
