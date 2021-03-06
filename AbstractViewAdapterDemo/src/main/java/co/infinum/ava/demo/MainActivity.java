package co.infinum.ava.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import co.infinum.ava.AbstractViewAdapter;
import co.infinum.ava.annotations.InjectList;

public class MainActivity extends ActionBarActivity {

    @InjectList(R.id.list)
    AbstractViewAdapter<Account> abstractViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AbstractViewAdapter.injectAdapters(this);

        Account account = new Account();
        account.setAvailableAmount(102.54);
        account.setName("Current account");
        account.setNumber("HR1234567789");
        account.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        abstractViewAdapter.add(account);


    }

}
