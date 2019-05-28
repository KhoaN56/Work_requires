package com.example.work_requires;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder> implements Filterable {
    private List<WorkRequirement> requirementList;
    private List<WorkRequirement> requirementFullList;
    private CustomAdapter3.OnItemClickListener itemClickListener;

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

    public CustomAdapter3(List<WorkRequirement> requirementList) {
        this.requirementList = requirementList;
        requirementFullList = new ArrayList<>(requirementList);
    }

    @NonNull
    @Override
    public CustomAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.posted_item, viewGroup, false);
        return new CustomAdapter3.ViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter3.ViewHolder viewHolder, int position) {
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

    public void setOnItemClickListener(CustomAdapter3.OnItemClickListener listener){
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobName;
        TextView numberOfApplied;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView, final CustomAdapter3.OnItemClickListener listener) {
            super(itemView);
            jobName = itemView.findViewById(R.id.postedJobName);
            numberOfApplied = itemView.findViewById(R.id.numberOfCandidateTV);
            deleteButton = itemView.findViewById(R.id.deleteButton);

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

        private void setter(WorkRequirement requirement){
            this.numberOfApplied.setText(String.valueOf(requirement.getApplied()));
            this.jobName.setText(requirement.getJobName());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
