package kftsc.custapp;

import android.support.annotation.NonNull;

/**
 * Created by kftsc on 1/16/2019.
 */

public class PurchaseDataProvider implements Comparable<PurchaseDataProvider> {
    private String type;
    private String category;
    private String amount;
    private String time;

    public PurchaseDataProvider(String type, String category, String amount, String time) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int compareTo (PurchaseDataProvider other) {
        if (Integer.valueOf(this.getTime()).compareTo(Integer.valueOf(other.getTime())) < 0) {
            return 1;
        }
        else if (Integer.valueOf(this.getTime()).compareTo(Integer.valueOf(other.getTime())) > 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
