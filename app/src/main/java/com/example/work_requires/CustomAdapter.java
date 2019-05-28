package com.example.work_requires;

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
    private List<WorkRequirement> requirementList;
    private List<WorkRequirement> requirementFullList;
    private CustomAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }
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

    public CustomAdapter(List<WorkRequirement> requirementList) {
        this.requirementList = requirementList;
        requirementFullList = new ArrayList<>(requirementList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.requires_item, viewGroup, false);
        return new CustomAdapter.ViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder viewHolder, int position) {
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

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo;
        TextView requirePos;
        TextView companyName;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.companyLogo);
            requirePos = itemView.findViewById(R.id.title);
            companyName = itemView.findViewById(R.id.compName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onClick(position);
                        }
                    }
                }
            });
        }

        public void setter(WorkRequirement requirement){
            this.companyName.setText(requirement.getCompanyName());
            this.requirePos.setText(requirement.getJobName());
            this.companyLogo.setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
