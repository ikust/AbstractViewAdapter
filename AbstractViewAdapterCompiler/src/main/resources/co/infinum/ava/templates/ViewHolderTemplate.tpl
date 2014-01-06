package ${packageName};

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import co.infinum.ava.AbstractViewHolder;

/**
 * Created by ivan on 06/01/14.
 */
public class ${className} extends FrameLayout implements AbstractViewHolder<${objectType}> {


    public static final Factory<${objectType}> FACTORY = new Factory<${objectType}>() {

        @Override
        public AbstractViewHolder<${objectType}> createView(Context context) {
            return new TemplateViewHolder(context);
            }
    };

        //TODO fields to hold views
        //private ImageView xImageView;
        //private TextView xTextView;
${fields}

    public ${className}(Context context) {
            super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //TODO replace with proper layout
        View viewLayout = inflater.inflate(${layoutId}, this, true);

        //TODO initialize views
        //xImageView = (ImageView) viewLayout.findViewById(R.id.image);
${initializeView}
     }

    @Override
    public View updateView(${objectType} item) {
        //TODO update with object properties
        //xImageView.setImageBitmap(item.getIcon());
${updateView}

        return this;
        }
    }
