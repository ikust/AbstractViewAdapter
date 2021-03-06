package ${packageName};

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ImageView;

import co.infinum.ava.AbstractViewHolder;

/**
 * Do not modify this class it has been generated with AbstractViewAdapter library.
 */
public class ${className} extends FrameLayout implements AbstractViewHolder<${objectType}> {


    public static final Factory<${objectType}> FACTORY = new Factory<${objectType}>() {

        @Override
        public AbstractViewHolder<${objectType}> createView(Context context) {
            return new ${className}(context);
            }
    };

${fields}
    public ${className}(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewLayout = inflater.inflate(${layoutId}, this, true);

${initializeView}
     }

    @Override
    public View updateView(${objectType} item) {
${updateView}
        return this;
     }
}
