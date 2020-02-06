package com.msadev.cmms.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.msadev.cmms.Model.DashboardModel;
import com.msadev.cmms.R;

import java.util.List;

public class DashboardAdapter extends ArrayAdapter<DashboardModel> {

    Context context;
    List<DashboardModel> dashboardModelList;

    public DashboardAdapter (List<DashboardModel> dashboardModelList, Context context){
        super(context, R.layout.activity_main, dashboardModelList);
        this.dashboardModelList = dashboardModelList;
        this.context = context;
    }

}
