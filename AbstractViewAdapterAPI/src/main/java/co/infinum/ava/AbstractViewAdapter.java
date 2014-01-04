package co.infinum.ava;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Adapter that can be injected with views using AbstractViewHolder.
 *
 * Created by ivan on 12/15/13.
 */
public class AbstractViewAdapter<T> extends ArrayAdapter<T> {

    /**
     * Factory for creating abstract view holders.
     */
    protected AbstractViewHolder.Factory<T> abstractViewFactory;

    /**
     * Used to store current abstract view holder.
     */
    private AbstractViewHolder<T> abstractViewHolder;

    /**
     * Creates new AbstractViewAdapter.
     *
     * @param context
     * @param abstractViewFactory
     * @param items
     */
    public AbstractViewAdapter(Context context, AbstractViewHolder.Factory<T> abstractViewFactory, ArrayList<T> items) {
        super(context, 0, items);
        this.abstractViewFactory = abstractViewFactory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            abstractViewHolder = abstractViewFactory.createView(getContext());

            convertView = abstractViewHolder.updateView(getItem(position));
            convertView.setTag(abstractViewHolder);
        } else {
            abstractViewHolder = (AbstractViewHolder<T>)convertView.getTag();
            abstractViewHolder.updateView(getItem(position));
            return convertView;
        }

        return convertView;
    }
}
