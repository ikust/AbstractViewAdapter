package co.infinum.ava;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Adapter that can be injected with views using AbstractViewHolder.
 *
 * Created by ivan on 12/15/13.
 */
public class AbstractViewAdapter<T> extends ArrayAdapter<T> {

    protected static final String INJECTOR_CLASS_NAME_SUFIX = "$$AdapterInjector";

    protected static final String INJECTOR_METHOD_NAME = "inject";

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

    public static void injectAdapters(Activity activity) {
        String injectorClassName = activity.getClass().getName() + INJECTOR_CLASS_NAME_SUFIX;

        try {
            Object injectorObject = Class.forName(injectorClassName).newInstance();

            Method method = getActivityInjectMethod(injectorObject, activity);
            method.invoke(injectorObject, activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void injectAdapters(Object object, View rootView) {
        String injectorClassName = object.getClass().getName() + INJECTOR_CLASS_NAME_SUFIX;

        try {
            Object injectorObject = Class.forName(injectorClassName).newInstance();

            Method method = getObjectInjectMethod(injectorObject, object);
            method.invoke(injectorObject, object, rootView);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns method for activity injection.
     *
     * @param injectorObject
     * @param activity
     * @return
     * @throws NoSuchMethodException
     */
    private static Method getActivityInjectMethod(Object injectorObject, Activity activity) throws NoSuchMethodException {
        Class[] parameterTypes = new Class[] {
            activity.getClass()
        };

        return injectorObject.getClass().getMethod(INJECTOR_METHOD_NAME, parameterTypes);
    }

    /**
     * Returns method for object injection.
     *
     * @param injectorObject
     * @param object
     * @return
     * @throws NoSuchMethodException
     */
    private static Method getObjectInjectMethod(Object injectorObject, Object object) throws NoSuchMethodException {
        Class[] parameterTypes = new Class[] {
                object.getClass(),
                View.class
        };

        return injectorObject.getClass().getMethod(INJECTOR_METHOD_NAME, parameterTypes);
    }

}
