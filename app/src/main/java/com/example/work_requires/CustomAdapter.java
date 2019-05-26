package com.example.work_requires;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    private Context context;
    private int layout;
    private List<WorkRequirement> requirementList;
    private List<WorkRequirement> requirementFullList;
    private Filter requirementFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WorkRequirement> filteredRequirement = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
                filteredRequirement.addAll(requirementFullList);
            else {
                String filter = constraint.toString().toLowerCase().trim();
                for(WorkRequirement requirement:requirementFullList){
                    if(requirement.getJobName().toLowerCase().contains(filter))
                        filteredRequirement.add(requirement);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredRequirement;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            requirementList.clear();
            requirementList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public CustomAdapter(Context context, int layout, List<WorkRequirement> requirementList) {
        this.context = context;
        this.layout = layout;
        this.requirementList = requirementList;
        requirementFullList = new ArrayList<>(requirementList);
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

    public void updateList(){
        notifyDataSetChanged();
        requirementFullList = requirementList;
    }

    @Override
    public Filter getFilter() {
        return requirementFilter;
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
            area = itemView.findViewById(R.id.spn_sex);
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
