package com.example.work_requires;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<WorkRequirement> requirementList;

    public CustomAdapter(Context context, int layout, List<WorkRequirement> requirementList) {
        this.context = context;
        this.layout = layout;
        this.requirementList = requirementList;
    }


    @Override
    public int getCount() {
        return requirementList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView companyLogo;
        TextView requirePos;
        TextView companyName;
        TextView area;
        TextView salary;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.companyLogo = convertView.findViewById(R.id.companyLogo);
            holder.requirePos = convertView.findViewById(R.id.title);
            holder.companyName = convertView.findViewById(R.id.compName);
            holder.area = convertView.findViewById(R.id.area);
            holder.salary = convertView.findViewById(R.id.salary);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();
        WorkRequirement requirement = requirementList.get(position);
        holder.companyLogo.setImageResource(R.mipmap.ic_launcher);
        holder.companyName.setText(requirement.getCompanyName());
        holder.requirePos.setText(requirement.getJobName());
        holder.area.setText(requirement.getArea());
        holder.salary.setText(requirement.getSalary());
        return convertView;
    }
}
