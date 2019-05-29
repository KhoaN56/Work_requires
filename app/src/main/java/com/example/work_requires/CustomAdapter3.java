package com.example.work_requires;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder>{
    private ViewCandidateList context;
    private List<User> userList;
    private CustomAdapter3.OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public CustomAdapter3(List<User> userList, ViewCandidateList context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.candidate_item, viewGroup, false);
        return new CustomAdapter3.ViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter3.ViewHolder viewHolder, int position) {
        viewHolder.setter(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setOnItemClickListener(CustomAdapter3.OnItemClickListener listener){
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView candidateName;
        TextView experience;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView, final CustomAdapter3.OnItemClickListener listener) {
            super(itemView);
            candidateName = itemView.findViewById(R.id.name);
            experience = itemView.findViewById(R.id.experience);
            deleteButton = itemView.findViewById(R.id.deleteCandidateBtn);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.deleteCandidate(getAdapterPosition());
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

        private void setter(User user){
            this.experience.setText(user.getExperience() == 0?"Chưa có kinh nghiệm":user.getExperience()+" năm kinh nghiệm");
            this.candidateName.setText(user.getName());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
