package com.example.work_requires.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.work_requires.R;
import com.example.work_requires.models.Noti;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CustomAdapterNoti extends BaseAdapter {

    private List<Noti> notificationList;
    private int layout;
    private Context context;

    //Day
    Date today;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");

    //Message
    String mes;
    String mes1;
    String mes2;
    String mes3;
    String mes4;

    class ViewHolder{
        TextView message, dayTime;
    }

    public CustomAdapterNoti(List<Noti> notificationList, int layout, Context context) {
        this.notificationList = notificationList;
        this.layout = layout;
        this.context = context;
        today = new Date(System.currentTimeMillis());
        mes1 = context.getString(R.string.noti_seen_profile);
        mes2 = context.getString(R.string.noti_applied_job);
//        mes3 = context.getString(R.string.);
//        mes4 = context.getString(R.string.);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int i) {
        return notificationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.message = convertView.findViewById(R.id.message);
            holder.dayTime = convertView.findViewById(R.id.dayTime);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Noti noti = notificationList.get(position);

        switch (noti.getAction()){
            case 1:
                mes = noti.getName() + " " + mes1;
                holder.message.setText(mes);
                break;
            case 2:
                mes = noti.getName() + " " + mes2;
                holder.message.setText(mes);
                break;
            case 3:
                break;
            case 4:
                break;
        }
        String[] day = noti.getDayTime().split(" ");
        holder.dayTime.setText(isMoreThanOneDay(noti.getDayTime())?day[1]:day[0]);
        return convertView;
    }

    private boolean isMoreThanOneDay(String inputDate) {
        try{
            //iDate = inputDate
            Date iDate = dateFormat.parse(inputDate);
            long dif = today.getTime() - iDate.getTime();
            long getDaysDiff = TimeUnit.MILLISECONDS.toDays(dif);
            return (getDaysDiff >= 1);    // >1 la ngay hien tai cach ngay input hon 1 ngay
        }catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
}
