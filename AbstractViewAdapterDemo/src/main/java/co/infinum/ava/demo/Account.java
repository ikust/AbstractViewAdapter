
package co.infinum.ava.demo;

import android.graphics.Bitmap;

import co.infinum.ava.annotations.ListLayout;
import co.infinum.ava.annotations.ListView;

/**
 * Created by ivan on 12/17/13.
 */
@ListLayout(R.layout.listitem_account)
public class Account {


    protected String name;

    protected String number;

    protected double availableAmount;

    protected Bitmap icon;

    @ListView(R.id.balance)
    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    @ListView(R.id.image)
    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @ListView(R.id.accountName)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ListView(R.id.accountNumber)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
