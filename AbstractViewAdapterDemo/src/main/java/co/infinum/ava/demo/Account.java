
package co.infinum.ava.demo;

import android.graphics.Bitmap;

import co.infinum.ava.annotations.ListLayout;

/**
 * Created by ivan on 12/17/13.
 */
@ListLayout(R.layout.listitem_account)
public class Account {


    protected String name;

    protected String number;

    protected double availableAmount;

    protected Bitmap icon;

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
