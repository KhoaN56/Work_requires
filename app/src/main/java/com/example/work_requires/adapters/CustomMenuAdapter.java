package com.example.work_requires.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.work_requires.R;

import java.util.ArrayList;
import java.util.List;

public class CustomMenuAdapter extends BaseAdapter {

    private List<Integer>icon;
    private List<String>function;
    int layout;
    Context context;

    public CustomMenuAdapter(int layout, Context context) {
        this.function = function;
        this.layout = layout;
        this.context = context;
        function = new ArrayList<>();
        function.add("Xem thông tin cá nhân");
        function.add("Đăng xuất");
        icon = new ArrayList<>();
        icon.add(R.drawable.ic_person_primary_color);
        icon.add(R.drawable.ic_log_out);
    }

    @Override
    public int getCount() {
        return function.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        ImageView icon;
        TextView functionName;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.functionName = convertView.findViewById(R.id.functionName);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.icon.setImageResource(icon.get(i));
        holder.functionName.setText(function.get(i));
        return convertView;
    }
}
