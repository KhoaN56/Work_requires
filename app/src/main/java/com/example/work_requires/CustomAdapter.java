package com.example.work_requires;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    int layout;
    List<WorkRequirement> requirementList;

    public CustomAdapter(Context context, int layout, List<WorkRequirement> requirementList) {
        this.context = context;
        this.layout = layout;
        this.requirementList = requirementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.requires_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setter(requirementList.get(position));
    }

    @Override
    public int getItemCount() {
        return requirementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo;
        TextView requirePos;
        TextView companyName;
        TextView area;
        TextView salary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.companyLogo);
            requirePos = itemView.findViewById(R.id.title);
            companyName = itemView.findViewById(R.id.compName);
            area = itemView.findViewById(R.id.sex);
            salary = itemView.findViewById(R.id.salary);
        }

        public void setter(WorkRequirement requirement){
            this.salary.setText(String.valueOf(requirement.getSalary()));
            this.area.setText(requirement.getArea());
            this.companyName.setText(requirement.getCompanyName());
            this.requirePos.setText(requirement.getJobName());
            this.companyLogo.setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
