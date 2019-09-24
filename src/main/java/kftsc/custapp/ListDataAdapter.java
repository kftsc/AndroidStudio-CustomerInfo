package kftsc.custapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kftsc on 1/17/2019.
 */

public class ListDataAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ListDataAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView type, category, amount, time;

    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
        Collections.sort(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.type = (TextView) row.findViewById(R.id.text_type);
            layoutHandler.category = (TextView) row.findViewById(R.id.text_category);
            layoutHandler.amount = (TextView) row.findViewById(R.id.text_amount);
            layoutHandler.time = (TextView) row.findViewById(R.id.text_time);
            row.setTag(layoutHandler);

        }
        else {
            layoutHandler = (LayoutHandler) row.getTag();
        }

        //origin
        PurchaseDataProvider provider = (PurchaseDataProvider) this.getItem(position);
        layoutHandler.type.setText(provider.getType());
        layoutHandler.category.setText(provider.getCategory());
        layoutHandler.amount.setText(provider.getAmount());
        layoutHandler.time.setText(displayTimeForm(provider.getTime()));
        /*
        if (SearchActivity.flag ) {
            for (int i = position; i < list.size(); ) {
                PurchaseDataProvider provider = (PurchaseDataProvider) this.getItem(i);
                if (Integer.valueOf(provider.getTime()) >= timeCalculator(SearchActivity.current_time , SearchActivity.duration_time)
                        && Integer.valueOf(provider.getTime()) <= SearchActivity.current_time ) {
                    layoutHandler.type.setText(provider.getType());
                    layoutHandler.category.setText(provider.getCategory());
                    layoutHandler.amount.setText(provider.getAmount());
                    layoutHandler.time.setText(displayTimeForm(provider.getTime()));
                    break;
                } else {
                    i++;
                }
            }
        }
        else {
            PurchaseDataProvider provider = (PurchaseDataProvider) this.getItem(position);
            layoutHandler.type.setText(provider.getType());
            layoutHandler.category.setText(provider.getCategory());
            layoutHandler.amount.setText(provider.getAmount());
            layoutHandler.time.setText(displayTimeForm(provider.getTime()));
        }
        */
        return row;
    }

    public String displayTimeForm(String time) {
        if(time.length() == 8) {
            return time.substring(0, 4) + "/" + time.substring(4, 6) + "/" + time.substring(6, 8);
        }
        else {
            return time;
        }
    }
/*
    public Integer timeCalculator(Long curr, Long dur) {
       // System.out.println(curr);
       // System.out.println(dur);
       // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        int currMon = Integer.valueOf(curr.toString().substring(4,6));
        int currYear = Integer.valueOf(curr.toString().substring(0,4));
        String date= curr.toString().substring(6,8);
        Long durNum = dur / 100;
        while (durNum >= currMon) {
            durNum = durNum - currMon;
            currYear--;
            currMon = 12;
        }
        durNum = currMon - durNum;
        String month;
        if (durNum < 10) {
            month = "0" + String.valueOf(durNum);
        }
        else {
            month = String.valueOf(durNum);
        }
        String result =  String.valueOf(currYear) + month + date;
        return Integer.valueOf(result);
    }
    */
}
