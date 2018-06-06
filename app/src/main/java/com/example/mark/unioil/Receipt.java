package com.example.mark.unioil;

/**
 * Created by Jen on 6/5/2018.
 */

public class Receipt {
    private String drNumber;
    private String drUserName;
    private String drCustomerName;

    public Receipt(String drNumber, String drUserName, String drCustomerName) {
        this.drNumber = drNumber;
        this.drUserName = drUserName;
        this.drCustomerName = drCustomerName;
    }

    public String getDrNumber() {
        return drNumber;
    }

    public void setDrNumber(String drNumber) {
        this.drNumber = drNumber;
    }

    public String getDrUserName() {
        return drUserName;
    }

    public void setDrUserName(String drUserName) {
        this.drUserName = drUserName;
    }

    public String getDrCustomerName() {
        return drCustomerName;
    }

    public void setDrCustomerName(String drCustomerName) {
        this.drCustomerName = drCustomerName;
    }

    public Receipt() {
    }


}
