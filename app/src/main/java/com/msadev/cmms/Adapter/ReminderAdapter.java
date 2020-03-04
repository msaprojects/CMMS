package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.msadev.cmms.Model.ReminderModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReminderAdapter extends ArrayAdapter<ReminderModel> {

    List<ReminderModel> reminderModelList;
    Context context;
    ArrayList<ReminderModel> arrayList;

    public ReminderAdapter(List<ReminderModel> reminderModelList, Context context){
        super(context, R.layout.design_l__reminder, reminderModelList);
        this.reminderModelList = reminderModelList;
        this.context = context;
        this.arrayList = new ArrayList<ReminderModel>();
        this.arrayList.addAll(reminderModelList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__reminder, null, true);
        ReminderModel rm = reminderModelList.get(position);

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        reminderModelList.clear();
        if (filters.length()==0){
            reminderModelList.addAll(arrayList);
        }else {
            for (ReminderModel rm : arrayList){
                if (rm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    reminderModelList.add(rm);
                }
            }
        }
        notifyDataSetChanged();
    }

}
