package co.infinum.ava;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.lang.reflect.InvocationTargetException;
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
        //TODO improve, this is test code

        String injectorClassName = activity.getClass().getName() + INJECTOR_CLASS_NAME_SUFIX;

        try {
            invoke(Class.forName(injectorClassName).newInstance(), INJECTOR_METHOD_NAME, activity);
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
            invoke(Class.forName(injectorClassName).newInstance(), INJECTOR_METHOD_NAME, object, rootView);
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

    public static Object invoke(Object object, String methodName, Object... args) throws Exception {
        Class[] parameterTypes = new Class[args.length];
        Exception runtimeException = new Exception("Problem with dynamic invocation of method '" + methodName + "'");
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        try {
            Method method = object.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(object, args);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw runtimeException;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        }
    }

}
