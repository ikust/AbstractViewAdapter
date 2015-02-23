
package co.infinum.ava.demo;

import android.graphics.Bitmap;

import co.infinum.ava.annotations.ListElement;
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

    @ListElement(R.id.balance)
    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    @ListElement(R.id.image)
    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @ListElement(R.id.accountName)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ListElement(R.id.accountNumber)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
