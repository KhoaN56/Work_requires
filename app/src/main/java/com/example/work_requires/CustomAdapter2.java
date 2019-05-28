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

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> implements Filterable {
    private List<WorkRequirement> requirementList;
    private List<WorkRequirement> requirementFullList;
    private CustomAdapter2.OnItemClickListener itemClickListener;
    private MainActivityCompany context;

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

    public CustomAdapter2(List<WorkRequirement> requirementList, MainActivityCompany context) {
        this.requirementList = requirementList;
        requirementFullList = new ArrayList<>(requirementList);
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.posted_item, viewGroup, false);
        return new CustomAdapter2.ViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter2.ViewHolder viewHolder, int position) {
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

    public void setOnItemClickListener(CustomAdapter2.OnItemClickListener listener){
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobName;
        TextView numberOfApplied;
        ImageButton editButton;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView, final CustomAdapter2.OnItemClickListener listener) {
            super(itemView);
            jobName = itemView.findViewById(R.id.postedJobName);
            numberOfApplied = itemView.findViewById(R.id.numberOfCandidateTV);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deletePostedRequireBtn);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.editRequirement(getAdapterPosition());
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.deleteRequirement(getAdapterPosition());
                }
            });

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
            this.numberOfApplied.setText("Số người nôp hồ sơ: "+requirement.getApplied());
            this.jobName.setText(requirement.getJobName());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
