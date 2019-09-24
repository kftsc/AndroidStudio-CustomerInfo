package kftsc.custapp;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kftsc on 1/19/2019.
 */

public class PurchaseList {
    ArrayList<PurchaseDataProvider> list;

    public PurchaseList() {
        list = new ArrayList<PurchaseDataProvider>();
    }

    public void addOne(PurchaseDataProvider info) {
        list.add(info);
    }

    public double advanceSum(boolean Qi, boolean chai, String qi93, String qi98, String chai0, String chai_10) {
        double result = 0;
        if (Qi == true && chai == false) {
            for (PurchaseDataProvider each: list) {
                if (each.getType().equals("汽油")) {
                    result += Double.valueOf(each.getAmount());
                }
            }
            return result;
        }
        else if (Qi == false && chai == true) {
            for (PurchaseDataProvider each: list) {
                if (each.getType().equals("柴油")) {
                    result += Double.valueOf(each.getAmount());
                }
            }
            return result;
        }
        else if (Qi == true && chai == true){
            return amountSum();
        }
        else {
            //return amountSum();
            return sumByCategory(qi93, qi98, chai0, chai_10);
        }
    }

    public double sumByCategory(String qi93, String qi98, String chai0, String chai_10) {
        double result = 0;
        for (PurchaseDataProvider each: list) {
            if (each.getCategory().equals(qi93) || each.getCategory().equals(qi98) ||
                    each.getCategory().equals(chai0) || each.getCategory().equals(chai_10)) {
                result += Double.valueOf(each.getAmount());
            }
        }
        return result;
    }

    public double amountSum() {
        double result = 0;
        for (PurchaseDataProvider each: list) {
            result += Double.valueOf(each.getAmount());
        }
        return result;
    }

}
