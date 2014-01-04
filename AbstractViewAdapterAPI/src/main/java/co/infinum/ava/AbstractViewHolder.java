package co.infinum.ava;

import android.view.View;
import android.content.Context;

/**
 * An interface that defines a view holder that can be used along with AbstractViewAdapter.
 * <p>
 * Implementation should implement a Factory for the AbstractViewHolder as well. This factory will
 * be used to create a new instance of the concrete AbstractViewHolder.
 * <p>
 * Concrete view holder must override updateView(T) method that returns updated view for the given
 * list item object.
 *
 * @param <T> list item type
 *
 * Created by ivan on 12/15/13.
 */
public interface AbstractViewHolder<T> {

    /**
     * Factory for creating AbstractViewHolder.
     *
     * @param <T> list item type
     */
    public interface Factory<T> {

        /**
         * Creates a new instance of the AbstractViewHolder that is used to display items in AbstractViewAdapter.
         *
         * @param context Context used to create the view holder
         * @return new instance of view holder
         */
        public AbstractViewHolder<T> createView(Context context);
    }

    /**
     * Updates the instance of the View that is used to display items in AbstractViewAdapter.
     *
     * @param item view should be updated to visually represent this item
     * @return updated View that represents give item in the adapter
     */
    public View updateView(T item);
}
