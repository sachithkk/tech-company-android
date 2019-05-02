package com.example.sachith.tech_app_front;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sachith.tech_app_front.domain.Company;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private Context context;
    private List<Company> list;
    private int layout ;

    public CompanyAdapter(int layout, Context context, List<Company> list) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    public CompanyAdapter(int layout) {
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Company company = list.get(position);

            holder.txt_name.setText("Name : " + company.getName());
            holder.txt_address.setText("Address : " + company.getAddress());
            holder.txt_city.setText("City : " + company.getCity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name, txt_address, txt_city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.main_name);
            txt_address = itemView.findViewById(R.id.main_address);
            txt_city = itemView.findViewById(R.id.main_city);
        }
    }
}