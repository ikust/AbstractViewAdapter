package co.infinum.ava.demo;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.infinum.ava.AbstractViewAdapter;
import co.infinum.ava.annotations.InjectList;

/**
 * Created by ivan on 18/02/14.
 */
public class ListFragment extends Fragment {

    @InjectList(R.id.list)
    AbstractViewAdapter<Account> abstractViewAdapter;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, null);

        AbstractViewAdapter.injectAdapters(this, rootView);

        Account account = new Account();
        account.setAvailableAmount(102.54);
        account.setName("Current account");
        account.setNumber("HR1234567789");
        account.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        abstractViewAdapter.add(account);

        return rootView;
    }
}
